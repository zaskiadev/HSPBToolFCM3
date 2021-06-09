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


class ViewRecordBatteryActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_view_record_battery)
        setSupportActionBar(findViewById(R.id.toolbar))

        super.onCreate(savedInstanceState)
        var sharedPref: SharedPreferences
        sharedPref = getSharedPreferences("SpinerVentazaChooseShowData", Context.MODE_PRIVATE)


        val VentazaChooseShowData: String =
            sharedPref.getString("SpinerVentazaChooseShowData", "Belum Login").toString()


        sharedPref = getSharedPreferences("SpinerVentazaFilterByShowData", Context.MODE_PRIVATE)
        val VentazaFilterShow: String =
            sharedPref.getString("SpinerVentazaFilterByShowData", "Belum Login").toString()


        if (VentazaChooseShowData == "View Record Battery") {
            findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title =
                "View Record Battery"

            val dataView: ArrayList<RoomBatteryView> = ArrayList()

            val mRecyclerView2: RecyclerView = findViewById(R.id.mRecyclerView)

            mRecyclerView2.setHasFixedSize(true)
            mRecyclerView2.layoutManager = LinearLayoutManager(this)
            //Toast.makeText(applicationContext,"Student data is empty, Add the data first",Toast.LENGTH_SHORT).show()

            AndroidNetworking.post(ApiEndPoint.read_record_battery)
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
                                if(jsonObject.getString("user_change_battery")!="")
                                {
                                    user=jsonObject.getString("user_change_battery")
                                }

                            }
                            catch(e : Exception)
                            {

                            }

                            dataView.add(
                                RoomBatteryView(
                                    "Room Number = " + jsonObject.getString("no_kamar"),
                                    "Last Change Battery = " + jsonObject.getString("last_change_battery"),
                                    "Next Change Battery = " + jsonObject.getString("next_change_battery"),
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

        } else if(VentazaChooseShowData=="View Record Time Card"){
            findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title =
                    "View Record Tap Time Card"
            val dataViewVentaza: ArrayList<ShowDataVentaza> = ArrayList()
            val mRecyclerView2: RecyclerView = findViewById(R.id.mRecyclerView)

            mRecyclerView2.setHasFixedSize(true)
            mRecyclerView2.layoutManager = LinearLayoutManager(this)
            //Toast.makeText(applicationContext,"Student data is empty, Add the data first",Toast.LENGTH_SHORT).show()
            AndroidNetworking.post(ApiEndPoint.read_record_tap_time_card)
                .addBodyParameter("filter_by", VentazaFilterShow)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {

                    override fun onResponse(response: JSONObject?) {

                        dataViewVentaza.clear()

                        val jsonArray = response?.optJSONArray("result")

                        if (jsonArray?.length() == 0) {
                            //loading.dismiss()

                        }

                        for (i in 0 until jsonArray?.length()!!) {


                           // val jsonObject = jsonArray?.optJSONObject(i)

                            var user:String?;
                            user="";
                            val jsonObject = jsonArray?.optJSONObject(i)

                            try {

                                    user=jsonObject.getString("user")


                            }
                            catch(e : Exception)
                            {

                            }


                            dataViewVentaza.add(
                                ShowDataVentaza(
                                    "Room Number = " + jsonObject.getString("no_kamar"),
                                    "Last Tap Time Card = " + jsonObject.getString("last_tap_time_card"),
                                    "Next Tap Time Card = " + jsonObject.getString("next_tap_time_card"),
                                        "Last Execute By = "+ user

                                )
                            )

                            if (jsonArray?.length() - 1 == i) {

                                //loading.dismiss()
                                /*  val adapter = RVAAdapterStudent(applicationContext,arrayList)
                                            adapter.notifyDataSetChanged()
                                            mRecyclerView.adapter = adapter*/
                                val adapter = AdapterCardListViewTapTimeCard(dataViewVentaza)
                                adapter.notifyDataSetChanged()

                                //tampilkan data dalam recycler view
                                mRecyclerView2.adapter = adapter


                            }

                        }

                    }

                    override fun onError(anError: ANError?) {

                        // Log.d("ONERROR",anError?.errorDetail?.toString())
                        Toast.makeText(applicationContext, "Connection Failure", Toast.LENGTH_SHORT)
                            .show()
                    }
                })


            //tampilkan data dalam recycler view
            //mRecyclerView.adapter = adapter;



        }

        else{

            findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title =
                    "View Record Keytag"
            val dataViewVentaza: ArrayList<ShowDataVentaza> = ArrayList()
            val mRecyclerView2: RecyclerView = findViewById(R.id.mRecyclerView)

            mRecyclerView2.setHasFixedSize(true)
            mRecyclerView2.layoutManager = LinearLayoutManager(this)
            //Toast.makeText(applicationContext,"Student data is empty, Add the data first",Toast.LENGTH_SHORT).show()
            AndroidNetworking.post(ApiEndPoint.read_record_keytag)
                    .addBodyParameter("filter_by", VentazaFilterShow)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(object : JSONObjectRequestListener {

                        override fun onResponse(response: JSONObject?) {

                            dataViewVentaza.clear()

                            val jsonArray = response?.optJSONArray("result")

                            if (jsonArray?.length() == 0) {
                                //loading.dismiss()
                                Toast.makeText(
                                        applicationContext,
                                        "Data is Empty",
                                        Toast.LENGTH_SHORT
                                ).show()
                            }

                            for (i in 0 until jsonArray?.length()!!) {

                                //val jsonObject = jsonArray?.optJSONObject(i)
                                var user:String?;
                                user="";
                                val jsonObject = jsonArray?.optJSONObject(i)

                                try {

                                        user=jsonObject.getString("user_keytag_update")


                                }
                                catch(e : Exception)
                                {

                                }
                                dataViewVentaza.add(
                                        ShowDataVentaza(
                                                "Room Number = " + jsonObject.getString("no_kamar"),
                                                "Last Keytag Tap = " + jsonObject.getString("last_keytag_update"),
                                                "Next Keytag Tap = " + jsonObject.getString("next_keytag_update"),
                                                "Last Execute by = "+ user
                                                )
                                )

                                if (jsonArray?.length() - 1 == i) {

                                    //loading.dismiss()
                                    /*  val adapter = RVAAdapterStudent(applicationContext,arrayList)
                                                adapter.notifyDataSetChanged()
                                                mRecyclerView.adapter = adapter*/
                                    val adapter = AdapterCardListViewTapTimeCard(dataViewVentaza)
                                    adapter.notifyDataSetChanged()

                                    //tampilkan data dalam recycler view
                                    mRecyclerView2.adapter = adapter


                                }

                            }

                        }

                        override fun onError(anError: ANError?) {

                            // Log.d("ONERROR",anError?.errorDetail?.toString())
                            Toast.makeText(applicationContext, "Connection Failure", Toast.LENGTH_SHORT)
                                    .show()
                        }
                    })


            //tampilkan data dalam recycler view
            //mRecyclerView.adapter = adapter;


        }
    }




}