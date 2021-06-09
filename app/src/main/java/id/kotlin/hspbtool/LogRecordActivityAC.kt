package id.kotlin.hspbtool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import id.kotlin.hspbtool.adapter.AdaptarCardListViewLogRecordVentazaAll

import id.kotlin.hspbtool.domain.LogRecordVentazaAll
import kotlinx.android.synthetic.main.activity_add_record_ventaza.*
import kotlinx.android.synthetic.main.activity_log_record.*
import org.json.JSONObject
import java.lang.Exception
import id.kotlin.hspbtool.adapter.AdaptarCardListViewLogRecordMaintenanceVentaza
import id.kotlin.hspbtool.adapter.AdapterCardListViewDataMaintenanceAc
import id.kotlin.hspbtool.domain.LogRecordMaintenanceVentaza
import id.kotlin.hspbtool.domain.ShowDataACMaintenanceRoom
import kotlinx.android.synthetic.main.activity_log_record_maintenance_ac.*


class LogRecordActivityAC : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_record_maintenance_ac)

//        setSupportActionBar(findViewById(R.id.toolbar))

        //findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = "Log Record"


        var isCleaning:String="No"; var isTuningFilter:String="No"; var isBlower:String="No"; var isCoilEvavorator:String="No"; var isVacumDrain:String="No"; var isDuctingConnection:String="No"
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


                                    if(jsonObject.getString("isCleaning")=="1")
                                    {
                                        isCleaning="Yes"
                                    }
                                    else
                                    {
                                        isCleaning="No"
                                    }
                                    if(jsonObject.getString("isTuningFilter")=="1")
                                    {
                                        isTuningFilter="Yes"
                                    }
                                    else
                                    {
                                        isTuningFilter="No"
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
                                    dataViewMaintenance.add(
                                            ShowDataACMaintenanceRoom(
                                                    "Room Number = " + jsonObject.getString("room_number"),
                                                    "Date Maintenance = " + jsonObject.getString("date_maintenance"),
                                                    "User = " + user,
                                                    "Is Cleaning = " + isCleaning,
                                                    "Is Tuning Filter = "+isTuningFilter,
                                                    "Is Blower = " +isBlower,
                                                    "Is Coil Evavorator = "+isCoilEvavorator,
                                                    "Is Vacum Drain = "+isVacumDrain,
                                                    "Is Checking Ducting Connection = "+isDuctingConnection,
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