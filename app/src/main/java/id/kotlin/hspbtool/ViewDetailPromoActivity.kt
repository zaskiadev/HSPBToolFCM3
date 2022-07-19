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
import id.kotlin.hspbtool.adapter.AdapterCardListViewTapTimeCard
import id.kotlin.hspbtool.domain.BannerPromo
import id.kotlin.hspbtool.domain.ShowDataVentaza
import java.lang.Exception


class ViewDetailPromoActivity : AppCompatActivity() {


    var buttonDoWorkOrder: Button?=null
    var EditTextWorkOrderCodeWD: TextView?=null
    var editTextCodeWorkOrderDetail: TextView?=null
    var editTextNoteDetailWorkOrderWD: EditText?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_promo_detail)
//        setSupportActionBar(findViewById(R.id.toolbar))

        super.onCreate(savedInstanceState)
        var sharedPref: SharedPreferences
        var imagePromoDetail= findViewById(R.id.imagePromoDetail) as ImageView
        var textViewPromoDetail= findViewById(R.id.textViewPromoDetail) as TextView


        sharedPref = getSharedPreferences("PromoNumber", Context.MODE_PRIVATE)
        val promoDetailNumber: String =
            sharedPref.getString("PromoNumber", "Kosong").toString()

        sharedPref = getSharedPreferences("imagePromoDetail", Context.MODE_PRIVATE)
        val promoDetailImage: String =
            sharedPref.getString("imagePromoDetail", "Kosong").toString()

        Log.e("Promo Number Dari View Detail Activity : ",promoDetailNumber)

        var sharedPrefEditor= sharedPref.edit()
        if (promoDetailNumber!="Kosong")
        {
            Picasso.get().load(promoDetailImage).into(imagePromoDetail)
            textViewPromoDetail.setText(promoDetailNumber)
            sharedPref=getSharedPreferences("PromoNumber",Context.MODE_PRIVATE)
            sharedPref.edit().clear().apply()
            sharedPref=getSharedPreferences("imagePromoDetail",Context.MODE_PRIVATE)
            sharedPref.edit().clear().apply()
        }

    /*    AndroidNetworking.post(ApiEndPoint.view_work_order_detail)
            .addBodyParameter("wo_codes", Wo_code_from_list)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {


                    val jsonArray = response?.optJSONArray("hasilnya")

                    Log.e("jumlah array",""+jsonArray?.length()!!)
                    // Log.e("Jumlah Array di Wo Detail:",jsonArray?.length().toString())


                    for (i in 1 until jsonArray?.length()!!) {

                        Log.e("Masuk sini ya", ""+i)

                        try {


                            val jsonObject = jsonArray?.optJSONObject(i)
///Users/fandy/Library/Android/sdk

                            var textLocation = ""



                            textLocation="http://103.84.233.186:8742/hspb_tool/image_work_order/"+jsonObject.getString("image_location").toString()

                            if(jsonObject.getString("image_location").toString()!="null")
                            {
                                Picasso.get().load(textLocation).into(imageWOD)
                            }


                            EditTextWorkOrderCodeWDText = jsonObject.getString("code_wo")
                            EditTextWorkOrderTypeWDText = jsonObject.getString("jenis")
                            editTextPriorityWDText = jsonObject.getString("skala")
                            editTextDepartementWDText = jsonObject.getString("dept_name")
                            editTextWorkOrderItemNameWDText =
                                jsonObject.getString("nama_barang")
                            editTextLocationWorkOrderWDText = jsonObject.getString("location")
                            txtWorkOrderDetailText = jsonObject.getString("wo_minta")


                        }
                        catch(e:ANError)
                        {
                            Log.e("Error WOD",e?.errorDetail?.toString())
                        }
                        EditTextWorkOrderCodeWD!!.setText(EditTextWorkOrderCodeWDText)
                        EditTextWorkOrderTypeWD.setText(EditTextWorkOrderTypeWDText)
                        editTextPriorityWD.setText(editTextPriorityWDText)
                        editTextDepartementWD.setText(editTextDepartementWDText)
                        editTextWorkOrderItemNameWD.setText(editTextWorkOrderItemNameWDText)
                        editTextLocationWorkOrderWD.setText(editTextLocationWorkOrderWDText)
                        editTextNoteDetailWorkOrderWD!!.setText(txtWorkOrderDetailText)
                    }



                }
                override fun onError(anError: ANError?) {

                    Log.e("Error WOD",anError?.errorDetail?.toString())

                }

            }


            )
*/


    }



    /*fun updateStatusWorkOrder(note:String)
    {
        var code_wo= EditTextWorkOrderCodeWD!!.text.toString()
        var code_wo_detail=editTextCodeWorkOrderDetail!!.text.toString()
        var noteSent=""
        noteSent="- "+note+" - "+editTextNoteDetailWorkOrderWD!!.text.toString()
        AndroidNetworking.post(ApiEndPoint.update_work_order_data)
            .addBodyParameter("code_wo", code_wo)
            .addBodyParameter("code_wo_detail", code_wo_detail)
            .addBodyParameter("note",noteSent)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {

                    if (response?.getString("message")?.contains("successfully")!!) {
                        Toast.makeText(applicationContext, "Data recorded successfully", Toast.LENGTH_SHORT).show()

                        this@WorkOrderViewDetailActivity.finish()
                    }

                }

                override fun onError(anError: ANError?) {
                    Toast.makeText(applicationContext, "Koneksi Error", Toast.LENGTH_LONG).show()
                }


            })

    }*/





}