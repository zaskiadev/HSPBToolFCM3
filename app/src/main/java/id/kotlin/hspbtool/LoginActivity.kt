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
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_add_record_ventaza.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_gallery.*
import org.json.JSONObject


class LoginActivity: AppCompatActivity()
{
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    val pbLogin : ProgressBar= findViewById(R.id.PBLogin)
    pbLogin.visibility= View.INVISIBLE
    val sharedPref : SharedPreferences
    sharedPref=getSharedPreferences("UserName", Context.MODE_PRIVATE)
    var sharedPrefEditor= sharedPref.edit()
    val button_click_signin= findViewById(R.id.buttonSignIn) as Button
    val button_click_signup= findViewById(R.id.buttonSignUp) as Button
    button_click_signin.setOnClickListener {

        AndroidNetworking.post(ApiEndPoint.login)
                .addBodyParameter("user",textViewUser.text.toString())
                .addBodyParameter("pass",textViewPassword.text.toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {

                    override fun onResponse(response: JSONObject?) {
                        val jsonArray = response?.optJSONArray("result")

                        if (jsonArray?.length() == 0) {
                            //loading.dismiss()
                            Toast.makeText(applicationContext,"Login failed, please try again", Toast.LENGTH_LONG).show()
                        }
                        else {
                            for (i in 0 until jsonArray?.length()!!) {


                                val jsonObject = jsonArray?.optJSONObject(i)
                                // Log.e("getWO",jsonObject.getString("code_wo"))
                                Toast.makeText(
                                    applicationContext,
                                    "Login Success",
                                    Toast.LENGTH_LONG
                                ).show()
                                val userName = textViewUser.text.toString()
                                sharedPrefEditor.putString("UserName", userName)
                                sharedPrefEditor.apply()

                                val sharedPref =
                                    getSharedPreferences("deptCodeLogin", Context.MODE_PRIVATE)
                                val sharedPrefEditor = sharedPref.edit()
                                sharedPrefEditor.putString(
                                    "deptCodeLogin",
                                    jsonObject.getString("dept_code")
                                )
                                sharedPrefEditor.apply()
                                Log.e("Dept Code Login", jsonObject.getString("dept_code"))

                            }
                            pbLogin.visibility = View.VISIBLE
                            getDataTimeCard()
                            pbLogin.visibility = View.INVISIBLE


                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }

                    override fun onError(anError: ANError?) {

                        Toast.makeText(applicationContext,"Koneksi Error", Toast.LENGTH_LONG).show()
                    }


                })
    }
    button_click_signup.setOnClickListener{
        val intent = Intent (this@LoginActivity,RegisterAccount::class.java)
        startActivity(intent)
    }




}

    fun getDataBattery()
    {
        var dataNoKamar:String=""
        var jumlahkamar=0
        AndroidNetworking.post(ApiEndPoint.notification_change_battery)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {

                    val jsonArray = response?.optJSONArray("result")

                    if (jsonArray?.length() != 0)
                        //loading.dismiss()
                    {

                        for (i in 0 until jsonArray?.length()!!) {
                            jumlahkamar++
                            val jsonObject = jsonArray?.optJSONObject(i)


                            dataNoKamar = dataNoKamar + jsonObject.getString("no_kamar") + " , "

                        }
                        notificationBuilder(
                            "Urgent To Change Battery : " + jumlahkamar + " Room ",
                            "List room number : " + dataNoKamar
                        )
                    }
                }

                override fun onError(anError: ANError?) {
                    Toast.makeText(applicationContext,"Koneksi Error", Toast.LENGTH_LONG).show()
                }


            })
    }

    fun getDataTimeCard()
    {
        var dataNoKamar:String=""
        var jumlahkamar=0
        AndroidNetworking.post(ApiEndPoint.notification_tap_time_card)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {

                    val jsonArray = response?.optJSONArray("result")

                    if (jsonArray?.length() != 0) {
                        //loading.dismiss()


                        for (i in 0 until jsonArray?.length()!!) {
                            jumlahkamar++
                            val jsonObject = jsonArray?.optJSONObject(i)


                            dataNoKamar = dataNoKamar + jsonObject.getString("no_kamar") + " , "

                        }
                        notificationBuilder(
                            "Urgent To Execute Tap Time Card : " + jumlahkamar + " Room ",
                            "List room number : " + dataNoKamar
                        )
                    }
                }

                override fun onError(anError: ANError?) {
                    Toast.makeText(applicationContext,"Koneksi Error", Toast.LENGTH_LONG).show()
                }


            })


    }

    fun getDataAC()
    {
        var dataNoKamar:String=""
        var jumlahkamar=0
        AndroidNetworking.post(ApiEndPoint.notification_ac_maintenance)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {

                    val jsonArray = response?.optJSONArray("result")

                    if (jsonArray?.length() != 0)
                    //loading.dismiss()
                    {

                        for (i in 0 until jsonArray?.length()!!) {
                            jumlahkamar++
                            val jsonObject = jsonArray?.optJSONObject(i)


                            dataNoKamar = dataNoKamar + jsonObject.getString("room_number") + " , "

                        }
                        notificationBuilder(
                            "Urgent To Maintenance AC in Room : " + jumlahkamar + " Room ",
                            "List room number : " + dataNoKamar
                        )
                    }
                }

                override fun onError(anError: ANError?) {
                    Toast.makeText(applicationContext,"Koneksi Error", Toast.LENGTH_LONG).show()
                }


            })
    }


    fun notificationBuilder(Title:String?, Text:String?)
    {
        val intent2 = Intent(this, LoginActivity::class.java)
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
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
            val channel = NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationBuilder.build())
    }
}