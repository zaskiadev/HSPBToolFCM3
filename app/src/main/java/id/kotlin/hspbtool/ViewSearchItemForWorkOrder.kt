package id.kotlin.hspbtool

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.android.material.appbar.CollapsingToolbarLayout
import id.kotlin.hspbtool.adapter.AdapterCardListViewItemWorkOrderView
import id.kotlin.hspbtool.domain.DataItemWorkOrderView
import org.json.JSONObject


class ViewSearchItemForWorkOrder : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_view_data_item_work_order)

//        setSupportActionBar(findViewById(R.id.toolbar))

        super.onCreate(savedInstanceState)

        var sharedPref: SharedPreferences


        sharedPref = getSharedPreferences("itemName", Context.MODE_PRIVATE)
        val item_name_work_order_spf: String =
            sharedPref.getString("itemName", "").toString()

        val buttonChooseThisItem : Button = findViewById(R.id.buttonChooseThisItem)

          //  findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = "Data Item Work Order"

            val dataView: ArrayList<DataItemWorkOrderView> = ArrayList()

            val mRecyclerView2: RecyclerView = findViewById(R.id.mRecyclerViewItemWorkOrder)

            mRecyclerView2.setHasFixedSize(true)
            mRecyclerView2.layoutManager = LinearLayoutManager(this)
            //Toast.makeText(applicationContext,"Student data is empty, Add the data first",Toast.LENGTH_SHORT).show()

            AndroidNetworking.post(ApiEndPoint.get_data_item_work_order)
                .addBodyParameter("item_name", item_name_work_order_spf)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {

                    override fun onResponse(response: JSONObject?) {

                        dataView.clear()

                        val jsonArray = response?.optJSONArray("result")

                        if (jsonArray?.length() == 1) {
                            dataView.add(
                                DataItemWorkOrderView(
                                    "Empty Data" ,
                                    "Please Just Create New Data" ,
                                    "From Work Order Screen" ,
                                    "Thank You")
                            )

                            val adapter = AdapterCardListViewItemWorkOrderView(dataView)
                            adapter.notifyDataSetChanged()

                            //tampilkan data dalam recycler view
                            mRecyclerView2.adapter = adapter

                        }

                        for (i in 1 until jsonArray?.length()!!) {

                            var deptCode:String=""
                            var deptName:String=""

                            val jsonObject = jsonArray?.optJSONObject(i)
                            deptCode=jsonObject.getString("dept_code")
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

                            dataView.add(
                                DataItemWorkOrderView(
                                    "Item Code = " + jsonObject.getString("code_barang"),
                                    "Item Name = " + jsonObject.getString("nama_barang"),
                                    "Location = " + jsonObject.getString("location"),
                                    "Departement = " + deptName)
                                )


                            if (jsonArray?.length() - 1 == i) {

                                //loading.dismiss()
                                /*  val adapter = RVAAdapterStudent(applicationContext,arrayList)
                                            adapter.notifyDataSetChanged()
                                            mRecyclerView.adapter = adapter*/


                                val adapter = AdapterCardListViewItemWorkOrderView(dataView)
                                adapter.notifyDataSetChanged()

                                //tampilkan data dalam recycler view
                                mRecyclerView2.adapter = adapter


                            }

                        }

                    }

                    override fun onError(anError: ANError?) {
                        Toast.makeText(applicationContext,anError.toString(), Toast.LENGTH_LONG).show()


                    }
                })


            //tampilkan data dalam recycler view
            //mRecyclerView.adapter = adapter;

        buttonChooseThisItem.setOnClickListener {
            Toast.makeText(applicationContext,"", Toast.LENGTH_LONG).show()
            var sharedPref:SharedPreferences
            sharedPref=getSharedPreferences("intentFrom",Context.MODE_PRIVATE)
            var sharedPrefEditor=sharedPref.edit()
            sharedPrefEditor.putString("intentFrom","itemSelectWorkOrder")
            sharedPrefEditor.apply()

        }

    }




}