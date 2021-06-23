package id.kotlin.hspbtool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import java.util.TimeZone
import java.text.DateFormat
import java.util.Locale
import java.util.Date
import kotlinx.android.synthetic.main.activity_add_record_ventaza.*
import kotlinx.android.synthetic.main.activity_log_record.*
import org.json.JSONObject
import java.lang.Exception
import id.kotlin.hspbtool.adapter.AdapterCardListViewDataMaintenanceAc
import id.kotlin.hspbtool.domain.ShowDataACMaintenanceRoom
import kotlinx.android.synthetic.main.activity_log_record_maintenance_ac.*
import java.text.SimpleDateFormat


class LogRecordActivityAC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_record_maintenance_ac)

//        setSupportActionBar(findViewById(R.id.toolbar))

        //findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = "Log Record"


        var IsRepairBrokenPart:String="No"; var isCleaningFilter:String="No"; var isBlower:String="No"; var isCoilEvavorator:String="No"; var isVacumDrain:String="No"; var isDuctingConnection:String="No"
        val button_click= findViewById(R.id.buttonShowLogDataAC) as Button



        button_click.setOnClickListener {



                val dataViewMaintenance: ArrayList<ShowDataACMaintenanceRoom> = ArrayList()

                val mRecyclerView2: RecyclerView = findViewById(R.id.mRecyclerViewLogAC)


                AndroidNetworking.post(ApiEndPoint.read_log_ac)
                        .addBodyParameter("room_number", editTextTextRoomNumberACShowLog.text.toString())
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(object : JSONObjectRequestListener {

                            override fun onResponse(response: JSONObject?) {

                                dataViewMaintenance.clear()

                                val jsonArray = response?.optJSONArray("result")

                                if (jsonArray?.length() == 0) {
                                    //loading.dismiss()

                                }

                                for (i in 0 until jsonArray?.length()!!) {

                                    var user: String?;
                                    user = "";
                                    val jsonObject = jsonArray?.optJSONObject(i)

                                    try {
                                        if (jsonObject.getString("user") != "") {
                                            user = jsonObject.getString("user")
                                        }

                                    } catch (e: Exception) {

                                    }


                                    if(jsonObject.getString("isRepairBrokenPart")=="1")
                                    {
                                        IsRepairBrokenPart="Yes"
                                    }
                                    else
                                    {
                                        IsRepairBrokenPart="No"
                                    }
                                    if(jsonObject.getString("isCleaningFilter")=="1")
                                    {
                                        isCleaningFilter="Yes"
                                    }
                                    else
                                    {
                                        isCleaningFilter="No"
                                    }
                                    if(jsonObject.getString("isBlower")=="1")
                                    {
                                        isBlower="Yes"
                                    }
                                    else{
                                        isBlower="No"
                                    }
                                    if(jsonObject.getString("isCoilEvavorator")=="1")
                                    {
                                        isCoilEvavorator="Yes"
                                    }
                                    else{
                                        isCoilEvavorator="No"
                                    }
                                    if(jsonObject.getString("isVacumDrain")=="1")
                                    {
                                        isVacumDrain="Yes"
                                    }
                                    else{
                                        isVacumDrain="No"
                                    }
                                    if(jsonObject.getString("isCheckingDuctingConnection")=="1")
                                    {
                                        isDuctingConnection="Yes"
                                    }
                                    else{
                                        isDuctingConnection="No"
                                    }
                                    val date:String="";
                                    fun String.toDate(dateFormat: String = "yyyy-MM-dd HH:mm:ss", timeZone: TimeZone = TimeZone.getTimeZone("UTC")): Date {
                                        val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
                                        parser.timeZone = timeZone
                                        return parser.parse(this)
                                    }

                                    fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getTimeZone("Asia/Oral")): String {
                                        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
                                        formatter.timeZone = timeZone
                                        return formatter.format(this)
                                    }
                                    dataViewMaintenance.add(
                                            ShowDataACMaintenanceRoom(
                                                    "Room Number = " + jsonObject.getString("room_number"),
                                                    "Date Maintenance = " + jsonObject.getString("date_maintenance").toDate().formatTo("dd-MM-YYYY HH:mm:ss "),
                                                    "User = " + user,
                                                    "Is Repair Broken Part = " + IsRepairBrokenPart,
                                                    "Is Cleaning Filter = "+isCleaningFilter,
                                                    "Is Cleaning Blower = " +isBlower,
                                                    "Is Cleaning Coil/Evavorator = "+isCoilEvavorator,
                                                    "Is Vacum Drain = "+isVacumDrain,
                                                    "Is Check Flexible Join = "+isDuctingConnection,
                                                    "Remark = "+jsonObject.getString("remark")
                                            )
                                    )


                                    if (jsonArray?.length() - 1 == i) {

                                        //loading.dismiss()
                                        /*  val adapter = RVAAdapterStudent(applicationContext,arrayList)
                                                adapter.notifyDataSetChanged()
                                                mRecyclerView.adapter = adapter*/



                                    }


                                }

                                var adapterData = AdapterCardListViewDataMaintenanceAc(dataViewMaintenance)


                                //Toast.makeText(applicationContext,"jumlah data maintenance : "+dataViewMaintenance.size,Toast.LENGTH_LONG).show()



                                adapterData.notifyDataSetChanged()
                                mRecyclerView2.apply {
                                    layoutManager=LinearLayoutManager(this@LogRecordActivityAC)
                                }
                                //tampilkan data dalam recycler view
                                mRecyclerView2.adapter = adapterData
                                mRecyclerView2.setHasFixedSize(true)
                            }

                            override fun onError(anError: ANError?) {

                                // Log.d("ONERROR",anError?.errorDetail?.toString())
                                Toast.makeText(applicationContext,anError.toString(),Toast.LENGTH_LONG).show()


                            }
                        })



        }

    }
}