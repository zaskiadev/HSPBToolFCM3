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
import android.content.SharedPreferences
import android.content.Context
import android.util.Log
import id.kotlin.hspbtool.adapter.AdapterCardListViewWorkOrderListView
import id.kotlin.hspbtool.domain.WorkOrderView
import java.lang.Exception


class ViewRecordWorkOrderActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_view_record_work_order)
//        setSupportActionBar(findViewById(R.id.toolbar))

        super.onCreate(savedInstanceState)
        var sharedPref: SharedPreferences


        sharedPref = getSharedPreferences("SpinerWorkOrderFilterByShowData", Context.MODE_PRIVATE)
        val WOFilterShow: String =
            sharedPref.getString("SpinerWorkOrderFilterByShowData", "").toString()



        sharedPref = getSharedPreferences("deptCodeLogin", Context.MODE_PRIVATE)
        val departementWO: String =
            sharedPref.getString("deptCodeLogin", "").toString()


        sharedPref = getSharedPreferences("editTextStartRangeWorkOrder", Context.MODE_PRIVATE)
        val startDateRangeWO: String =
            sharedPref.getString("editTextStartRangeWorkOrder", "").toString()


        sharedPref = getSharedPreferences("editTextEndRangeWorkOrder", Context.MODE_PRIVATE)
        val endDateRangeWO: String =
            sharedPref.getString("editTextEndRangeWorkOrder", "").toString()



        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title =
                "Work Order Data"

            val dataView: ArrayList<WorkOrderView> = ArrayList()

            val mRecyclerView2: RecyclerView = findViewById(R.id.mRecyclerViewWorkOrder)

            mRecyclerView2.setHasFixedSize(true)
            mRecyclerView2.layoutManager = LinearLayoutManager(this)
            //Toast.makeText(applicationContext,"Student data is empty, Add the data first",Toast.LENGTH_SHORT).show()


            AndroidNetworking.post(ApiEndPoint.view_work_order)
                .addBodyParameter("filter_by", WOFilterShow)
                .addBodyParameter("date_range_start", startDateRangeWO)
                .addBodyParameter("date_range_finish", endDateRangeWO)
                .addBodyParameter("dept_code", departementWO)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {

                        val jsonArray = response?.optJSONArray("hasil")

                    dataView.clear()

Log.e("Jumlah Data di WO",""+jsonArray?.length())
                        for (i in 1 until jsonArray?.length()!!) {

                            val jsonObject = jsonArray?.optJSONObject(i)


                            dataView.add(
                                WorkOrderView(
                                    "Code WO = " + jsonObject.getString("code_wo"),
                                    "Code WO Detail = "+jsonObject.getString("code_wo_detail"),
                                    "User Create = " + jsonObject.getString("user_wo_create"),
                                    "Departement = " + jsonObject.getString("dept_name"),
                                    "WO Type= " + jsonObject.getString("jenis_wo"),
                                    "WO Priority = " + jsonObject.getString("skala"),
                                    "Date Start = " + jsonObject.getString("date_wo_start"),
                                    "Date End = " + jsonObject.getString("date_wo_end"),
                                    "Status WO = " + jsonObject.getString("status")
                                )
                                )


                            if (jsonArray?.length() - 1 == i) {

                                //loading.dismiss()
                                /*  val adapter = RVAAdapterStudent(applicationContext,arrayList)
                                            adapter.notifyDataSetChanged()
                                            mRecyclerView.adapter = adapter*/
                                val adapter = AdapterCardListViewWorkOrderListView(dataView,this@ViewRecordWorkOrderActivity)
                                adapter.notifyDataSetChanged()

                                //tampilkan data dalam recycler view
                                mRecyclerView2.adapter = adapter


                            }

                        }

                    }

                    override fun onError(anError: ANError?) {

                         Log.e("View Record Error:",anError?.toString())

                    }
                })


            //tampilkan data dalam recycler view
            //mRecyclerView.adapter = adapter;


    }




}