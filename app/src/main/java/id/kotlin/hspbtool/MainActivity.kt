package id.kotlin.hspbtool

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.Section
import id.kotlin.hspbtool.item.BannerCarouselItem
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    var jumlahKamarTap : Int=0
    var jumlahKamarGantiBattery : Int=0
    var jumlahRuanganMaintenanceAC : Int=0
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)


//        setSupportActionBar(toolbar)
  //      findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title =
    //        "Hotel Santika Premiere Bintaro"
        var sharedPref : SharedPreferences
        sharedPref=   getSharedPreferences("UserName", Context.MODE_PRIVATE)
        val userLoginName : String = sharedPref.getString("UserName", "Belum Login").toString()
        sharedPref=   getSharedPreferences("deptCodeLogin", Context.MODE_PRIVATE)
        val deptCode : String = sharedPref.getString("deptCodeLogin", "Belum Login").toString()
        var deptName : String=""
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        val headerView = navView.getHeaderView(0)
        val userNameUsed : TextView = headerView.findViewById(R.id.userNameNavbar)
        userNameUsed.setText(userLoginName)

        if(deptCode=="D0001")
        {
            deptName="Accounting"
        }
        else if(deptCode=="D0002")
        {
            deptName="Housekeeping"
        }
        else if (deptCode=="D0003")
        {
            deptName="Front Office"
        }
        else if(deptCode=="D0004")
        {
            deptName="F&B Product"
        }
        else if (deptCode=="D0005")
        {
            deptName="F&B Service"
        }
        else if (deptCode=="D0006")
        {
            deptName="Human Resources"
        }
        else if (deptCode=="D0007")
        {
            deptName="Engineering"
        }
        else if (deptCode=="D0008")
        {
            deptName="Sales"
        }


        val DeptCodeUsed : TextView = headerView.findViewById(R.id.deptNameNavbar)
        DeptCodeUsed.setText("Dept: "+deptName)

        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_hotel_information, R.id.nav_engineering_data_record, R.id.work_order
                ), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener { MenuItem->
            when(MenuItem.itemId) {

                R.id.nav_hotel_information -> {
                    val intent = Intent(this@MainActivity, hotel_information_test_activity::class.java)
                    startActivity(intent)
                }

                R.id.nav_engineering_data_record -> {
                    val intent = Intent(this@MainActivity, MenuEngineeringRecord::class.java)
                    startActivity(intent)
                }
                R.id.work_order-> {
                    val intent = Intent(this@MainActivity, MenuWorkOrder::class.java)
                    startActivity(intent)
                }
            }
            drawerLayout.closeDrawers()
            true
        }

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_stat_ic_notification)
                .setContentTitle("Welcome " + userLoginName)
                .setContentText("Welcome to HSPB Tool " + userLoginName)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        // https://developer.android.com/training/notify-user/build-notification#Priority
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(1, notificationBuilder.build())

        getDataTimeCardNotification()
        getDataBatteryNotification()
        getDataACNotification()
        if(deptCode=="D0007")
        {


            Handler().postDelayed({
                notificationBuilder("Urgent To Execute : ", "Jumlah kamar harus di tap time card : "+
                        jumlahKamarTap +", Jumlah kamar yang harus di ganti battery ventaza : "+jumlahKamarGantiBattery+
                        ", Jumlah ruangan yang harus di maintenance AC : "+ jumlahRuanganMaintenanceAC
                    ,"Notification Engineering")
            }, 2000)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(applicationContext, "click on setting", Toast.LENGTH_LONG).show()
                true
            }
            R.id.change_password -> {
                val intent = Intent(this@MainActivity, ChangePassword::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }


    }

    fun notificationBuilder(Title:String?, Text:String?, notificationChannelNamed:String)
    {
        val intent2 = Intent(this, MainActivity::class.java)
        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent2, PendingIntent.FLAG_ONE_SHOT)
        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_stat_ic_notification)
            .setContentTitle(Title)
            .setContentText(Text)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigTextStyle().bigText(Text))
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        // https://developer.android.com/training/notify-user/build-notification#Priority
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, notificationChannelNamed, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationBuilder.build())
    }


    fun getDataBatteryNotification()
    {
        //var dataNoKamar:String=""
        var jumlahkamar=0
        AndroidNetworking.post(ApiEndPoint.notification_change_battery_count)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {

                    val jsonArray = response?.optJSONArray("result")

                    if (jsonArray?.length() != 0)
                    //loading.dismiss()
                    {

                        for (i in 0 until jsonArray?.length()!!) {
                      //      jumlahkamar++
                            val jsonObject = jsonArray?.optJSONObject(i)


                            jumlahKamarGantiBattery = jsonObject.getInt("jumlah_urgent")

                        }
                      //  notificationBuilder(
                        //    "Urgent To Change Battery : " + jumlahkamar + " Room ",
                          //  "Silahkan lihat data untuk melihat detailnya", "Change Battery"
                        //)
                    }
                }

                override fun onError(anError: ANError?) {
                    Toast.makeText(applicationContext,"Koneksi Error", Toast.LENGTH_LONG).show()
                }


            })

    }

    fun getDataTimeCardNotification()
    {
        //var dataNoKamar:String=""
        var jumlahkamar=0
        AndroidNetworking.post(ApiEndPoint.notification_tap_time_card_count)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {

                    val jsonArray = response?.optJSONArray("result")

                    if (jsonArray?.length() != 0) {
                        //loading.dismiss()


                        for (i in 0 until jsonArray?.length()!!) {

                            val jsonObject = jsonArray?.optJSONObject(i)


                            jumlahKamarTap= jsonObject.getInt("jumlah_urgent")

                        }
                        /*notificationBuilder(
                            "Urgent To Execute Tap Time Card : " + jumlahkamar + " Room ",
                            "Silahkan lihat data untuk melihat detailnya", "TAP TIME CARD"
                        )*/
                    }
                }

                override fun onError(anError: ANError?) {
                    Toast.makeText(applicationContext,"Koneksi Error", Toast.LENGTH_LONG).show()
                }


            })


    }

    fun getDataACNotification()
    {
        //var dataNoKamar:String=""
        var jumlahkamar=0
        AndroidNetworking.post(ApiEndPoint.notification_ac_maintenance_count)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {

                    val jsonArray = response?.optJSONArray("result")

                    if (jsonArray?.length() != 0)
                    //loading.dismiss()
                    {

                        for (i in 0 until jsonArray?.length()!!) {
                           // jumlahkamar++
                            val jsonObject = jsonArray?.optJSONObject(i)


                            jumlahRuanganMaintenanceAC = jsonObject.getInt("jumlah_urgent")

                        }
                        //notificationBuilder(
                          //  "Urgent To Maintenance AC in Room : " + jumlahkamar + " Room ",
                            //"Silahkan lihat data untuk melihat detailnya","Maintenance AC"
                        //)
                    }
                }

                override fun onError(anError: ANError?) {
                    Toast.makeText(applicationContext,"Koneksi Error", Toast.LENGTH_LONG).show()
                }


            })

    }


}