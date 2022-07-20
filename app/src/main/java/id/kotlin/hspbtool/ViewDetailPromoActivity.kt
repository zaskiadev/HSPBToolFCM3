package id.kotlin.hspbtool

import android.app.Dialog
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
import android.graphics.Color
import android.util.Log
import android.view.Window
import android.widget.*
import com.squareup.picasso.Picasso
import id.kotlin.hspbtool.adapter.AdapterCardListViewListPromo
import id.kotlin.hspbtool.adapter.AdapterCardListViewTapTimeCard
import id.kotlin.hspbtool.domain.BannerPromo
import id.kotlin.hspbtool.domain.ListPromoView
import id.kotlin.hspbtool.domain.ShowDataVentaza
import java.lang.Exception


class ViewDetailPromoActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_promo_detail)

        super.onCreate(savedInstanceState)
        val dataView: ArrayList<ListPromoView> = ArrayList()
        val mRecyclerView2: RecyclerView = findViewById(R.id.mRecyclerView)

        mRecyclerView2.setHasFixedSize(true)
        mRecyclerView2.layoutManager = LinearLayoutManager(this)

        var sharedPref: SharedPreferences
        sharedPref = getSharedPreferences("SpinerVentazaChooseShowData", Context.MODE_PRIVATE)


        val VentazaChooseShowData: String =
            sharedPref.getString("SpinerVentazaChooseShowData", "Belum Login").toString()





        // val test:MutableList<BannerPromo> = mutableListOf()
        AndroidNetworking.post(ApiEndPoint.list_banner)
            // .addBodyParameter("filter_by", textSpinnerSelected)
            //.addBodyParameter("room_number", editTextTextRoomNumberShowLog.text.toString())
            .setPriority(Priority.IMMEDIATE)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {



                    val jsonArray = response?.optJSONArray("result")

                    if (jsonArray?.length() == 0) {
                        //loading.dismiss()

                    }
                    var index=0
                    for (i in 0 until jsonArray?.length()!!) {


                        val jsonObject = jsonArray?.optJSONObject(i)

                        dataView.add(
                            ListPromoView(
                                jsonObject.getString("nama_promo").replace("_"," "),
                                "http://103.84.233.186:8742/hspb_tool/image_promo/"+jsonObject.getString("nama_promo")+
                                        "_"+
                                        jsonObject.getInt("bulan")+"_"+jsonObject.getInt("tahun")+".jpg",
                                jsonObject.getString("deskripsi_promo")

                                )

                        )


                        if (jsonArray?.length() - 1 == i) {
                            val adapter = AdapterCardListViewListPromo(dataView)
                            adapter.notifyDataSetChanged()

                            //tampilkan data dalam recycler view
                            mRecyclerView2.adapter = adapter
                        }


                    }

                }

                override fun onError(anError: ANError?) {

                    // Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Koneksi Error",Toast.LENGTH_LONG).show()

                }
            })

    }









}