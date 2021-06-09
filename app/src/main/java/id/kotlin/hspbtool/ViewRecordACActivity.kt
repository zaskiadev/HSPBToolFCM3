package id.kotlin.hspbtool

import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import id.kotlin.hspbtool.adapter.AdapterCardListViewRoomBattery
import id.kotlin.hspbtool.domain.RoomBatteryView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONObject
import kotlin.collections.ArrayList
import android.widget.Toast
import android.content.SharedPreferences
import android.content.Context
import id.kotlin.hspbtool.adapter.AdapterCardListViewTapTimeCard
import id.kotlin.hspbtool.domain.ShowDataVentaza
import java.lang.Exception


class ViewRecordACActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_view_record_battery)
        setSupportActionBar(findViewById(R.id.toolbar))

        super.onCreate(savedInstanceState)
        var sharedPref: SharedPreferences


        sharedPref = getSharedPreferences("SpinerACFilterByShowData", Context.MODE_PRIVATE)
        val VentazaFilterShow: String =
            sharedPref.getString("SpinerACFilterByShowData", "Belum Login").toString()



            findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title =
                "View AC Record Data"

            val dataView: ArrayList<RoomBatteryView> = ArrayList()

            val mRecyclerView2: RecyclerView = findViewById(R.id.mRecyclerView)

            mRecyclerView2.setHasFixedSize(true)
            mRecyclerView2.layoutManager = LinearLayoutManager(this)
            //Toast.makeText(applicationContext,"Student data is empty, Add the data first",Toast.LENGTH_SHORT).show()

            AndroidNetworking.post(ApiEndPoint.read_record_ac)
                .addBodyParameter("filter_by", VentazaFilterShow)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {

                    override fun onResponse(response: JSONObject?) {

                        dataView.clear()

                        val jsonArray = response?.optJSONArray("result")

                        if (jsonArray?.length() == 0) {
                            //loading.dismiss()

                        }

                        for (i in 0 until jsonArray?.length()!!) {

                            var user:String?;
                            user="";
                            val jsonObject = jsonArray?.optJSONObject(i)

                            try {
                                if(jsonObject.getString("user")!="")
                                {
                                    user=jsonObject.getString("user")
                                }

                            }
                            catch(e : Exception)
                            {

                            }

                            dataView.add(
                                RoomBatteryView(
                                    "Room Number = " + jsonObject.getString("room_number"),
                                    "Last Date Maintenance = " + jsonObject.getString("date_maintenance"),
                                    "Next Date Maintenance = " + jsonObject.getString("date_next_maintenance"),
                                    "Lat Execute By = " + user)
                                )


                            if (jsonArray?.length() - 1 == i) {

                                //loading.dismiss()
                                /*  val adapter = RVAAdapterStudent(applicationContext,arrayList)
                                            adapter.notifyDataSetChanged()
                                            mRecyclerView.adapter = adapter*/
                                val adapter = AdapterCardListViewRoomBattery(dataView)
                                adapter.notifyDataSetChanged()

                                //tampilkan data dalam recycler view
                                mRecyclerView2.adapter = adapter


                            }

                        }

                    }

                    override fun onError(anError: ANError?) {

                        // Log.d("ONERROR",anError?.errorDetail?.toString())

                    }
                })


            //tampilkan data dalam recycler view
            //mRecyclerView.adapter = adapter;


    }




}