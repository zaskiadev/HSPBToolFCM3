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
import id.kotlin.hspbtool.domain.LogRecordMaintenanceVentaza


class LogRecordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_record)

//        setSupportActionBar(findViewById(R.id.toolbar))

        //findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = "Log Record"
        val dataView : ArrayList<LogRecordVentazaAll> = ArrayList()

        //Toast.makeText(applicationContext,"Student data is empty, Add the data first",Toast.LENGTH_SHORT).show()

        val ventazaChoose = resources.getStringArray(R.array.show_log_ventaza)
        val spinner = findViewById<Spinner>(R.id.spinnerChooseLog)
        var textSpinnerSelected=""
        val adapter = ArrayAdapter(this,
                R.layout.spinner_item, ventazaChoose)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                textSpinnerSelected=ventazaChoose[position]


            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        val button_click= findViewById(R.id.buttonShowLogDataVentazaFilter) as Button



        button_click.setOnClickListener {

            var judul:String=""




            //Toast.makeText(applicationContext,"Student data is empty, Add the data first",Toast.LENGTH_SHORT).show()



            /*if (textSpinnerSelected == "All Data") {
                var dataView: ArrayList<LogRecordVentazaAll> = ArrayList()

                val mRecyclerView2: RecyclerView = findViewById(R.id.mRecyclerViewLog)
                var adapterData = AdaptarCardListViewLogRecordVentazaAll(dataView)



                for(j in 0..3) {



                    AndroidNetworking.post(ApiEndPoint.read_log_ventaza)
                            .addBodyParameter("filter_by", ventazaChoose[j])
                            .addBodyParameter("room_number", editTextTextRoomNumberShowLog.text.toString())
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

                                        var user: String?;
                                        user = "";
                                        val jsonObject = jsonArray?.optJSONObject(i)

                                        try {
                                            if (jsonObject.getString("user_execute") != "") {
                                                user = jsonObject.getString("user_execute")
                                            }



                                        dataView.add(
                                                LogRecordVentazaAll(
                                                        "Title = " + ventazaChoose[j],
                                                        "Room Number = " + jsonObject.getString("room_number"),
                                                        "Last Date Executed = " + jsonObject.getString("last_date_executed"),
                                                        "Next Date Executed = " + jsonObject.getString("next_date_executed"),
                                                        "Last Execute By = " + user)
                                        )


                                        if (jsonArray?.length() - 1 == i) {

                                            //loading.dismiss()
                                            *//*  val adapter = RVAAdapterStudent(applicationContext,arrayList)
                                                adapter.notifyDataSetChanged()
                                                mRecyclerView.adapter = adapter*//*



                                        }
                                        } catch (e: Exception) {

                                        }

                                    }

                                    adapterData = AdaptarCardListViewLogRecordVentazaAll(dataView)
                                    adapterData.notifyDataSetChanged()
                                    mRecyclerView2.apply {
                                        layoutManager=LinearLayoutManager(this@LogRecordActivity)
                                    }
                                    //tampilkan data dalam recycler view
                                    mRecyclerView2.adapter = adapterData
                                    mRecyclerView2.setHasFixedSize(true)

                                }

                                override fun onError(anError: ANError?) {

                                    // Log.d("ONERROR",anError?.errorDetail?.toString())

                                }
                            })

                }





            }*/
            if(textSpinnerSelected=="Maintenance Ventaza")
            {Toast.makeText(applicationContext,textSpinnerSelected,Toast.LENGTH_LONG).show()
                val dataViewMaintenance: ArrayList<LogRecordMaintenanceVentaza> = ArrayList()

                val mRecyclerView2: RecyclerView = findViewById(R.id.mRecyclerViewLog)


                AndroidNetworking.post(ApiEndPoint.read_log_ventaza)
                        .addBodyParameter("filter_by", textSpinnerSelected)
                        .addBodyParameter("room_number", editTextTextRoomNumberShowLog.text.toString())
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

                                    var user: String?;
                                    user = "";
                                    val jsonObject = jsonArray?.optJSONObject(i)

                                    try {
                                        if (jsonObject.getString("user") != "") {
                                            user = jsonObject.getString("user")
                                        }

                                    } catch (e: Exception) {

                                    }

                                    dataViewMaintenance.add(
                                            LogRecordMaintenanceVentaza(
                                                    "Title = " + textSpinnerSelected,
                                                    "Room Number = " + jsonObject.getString("no_kamar"),
                                                    "Date Maintenance = " + jsonObject.getString("date_maintenance"),
                                                    "Maintenance Type = " + jsonObject.getString("maintenace_type"),
                                                    "Remark = " + jsonObject.getString("remark"),
                                                    "User = "+user)
                                    )


                                    if (jsonArray?.length() - 1 == i) {

                                        //loading.dismiss()
                                        /*  val adapter = RVAAdapterStudent(applicationContext,arrayList)
                                                adapter.notifyDataSetChanged()
                                                mRecyclerView.adapter = adapter*/



                                    }


                                }

                                var adapterData = AdaptarCardListViewLogRecordMaintenanceVentaza(dataViewMaintenance)


                                //Toast.makeText(applicationContext,"jumlah data maintenance : "+dataViewMaintenance.size,Toast.LENGTH_LONG).show()



                                adapter.notifyDataSetChanged()
                                mRecyclerView2.apply {
                                    layoutManager=LinearLayoutManager(this@LogRecordActivity)
                                }
                                //tampilkan data dalam recycler view
                                mRecyclerView2.adapter = adapterData
                                mRecyclerView2.setHasFixedSize(true)
                            }

                            override fun onError(anError: ANError?) {

                                // Log.d("ONERROR",anError?.errorDetail?.toString())
                                Toast.makeText(applicationContext,"Koneksi Error",Toast.LENGTH_LONG).show()


                            }
                        })


            }
            else {
                val dataView: ArrayList<LogRecordVentazaAll> = ArrayList()

                val mRecyclerView2: RecyclerView = findViewById(R.id.mRecyclerViewLog)


                AndroidNetworking.post(ApiEndPoint.read_log_ventaza)
                        .addBodyParameter("filter_by", textSpinnerSelected)
                        .addBodyParameter("room_number", editTextTextRoomNumberShowLog.text.toString())
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

                                    var user: String?;
                                    user = "";
                                    val jsonObject = jsonArray?.optJSONObject(i)

                                    try {
                                        if (jsonObject.getString("user_execute") != "") {
                                            user = jsonObject.getString("user_execute")
                                        }

                                    } catch (e: Exception) {

                                    }

                                    dataView.add(
                                            LogRecordVentazaAll(
                                                    "Title = " + textSpinnerSelected,
                                                    "Room Number = " + jsonObject.getString("room_number"),
                                                    "Last Date Executed = " + jsonObject.getString("last_date_executed"),
                                                    "Next Date Executed = " + jsonObject.getString("next_date_executed"),
                                                    "Last Execute By = " + user)
                                    )


                                    if (jsonArray?.length() - 1 == i) {

                                        //loading.dismiss()
                                        /*  val adapter = RVAAdapterStudent(applicationContext,arrayList)
                                                adapter.notifyDataSetChanged()
                                                mRecyclerView.adapter = adapter*/



                                    }


                                }

                                var adapterData = AdaptarCardListViewLogRecordVentazaAll(dataView)



                                adapter.notifyDataSetChanged()
                                mRecyclerView2.apply {
                                    layoutManager=LinearLayoutManager(this@LogRecordActivity)
                                }
                                //tampilkan data dalam recycler view
                                mRecyclerView2.adapter = adapterData
                                mRecyclerView2.setHasFixedSize(true)

                            }

                            override fun onError(anError: ANError?) {

                                // Log.d("ONERROR",anError?.errorDetail?.toString())


                            }
                        })





            }
        }

    }
}