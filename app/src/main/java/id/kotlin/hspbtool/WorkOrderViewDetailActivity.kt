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
import id.kotlin.hspbtool.domain.ShowDataVentaza
import kotlinx.android.synthetic.main.activity_add_maintenance_ac.*
import org.w3c.dom.Text
import java.lang.Exception


class WorkOrderViewDetailActivity : AppCompatActivity() {

    var buttonDoWorkOrder:Button?=null
    var EditTextWorkOrderCodeWD:TextView?=null
    var editTextCodeWorkOrderDetail:TextView?=null
    var editTextNoteDetailWorkOrderWD:EditText?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_work_order_detail_view)
//        setSupportActionBar(findViewById(R.id.toolbar))

        super.onCreate(savedInstanceState)
        var sharedPref: SharedPreferences
        EditTextWorkOrderCodeWD= findViewById(R.id.EditTextWorkOrderCodeWD)
        var EditTextWorkOrderTypeWD= findViewById(R.id.EditTextWorkOrderTypeWD) as TextView
        var editTextPriorityWD= findViewById(R.id.editTextPriorityWD) as TextView
        var editTextDepartementWD= findViewById(R.id.editTextDepartementWD) as TextView
        var editTextWorkOrderItemNameWD= findViewById(R.id.editTextWorkOrderItemNameWD) as EditText
        var editTextLocationWorkOrderWD= findViewById(R.id.editTextLocationWorkOrderWD) as EditText
        editTextNoteDetailWorkOrderWD= findViewById(R.id.editTextNoteDetailWorkOrderWD) as EditText
        var imageWOD= findViewById(R.id.imageTakedWorkOrderWD) as ImageView
        editTextCodeWorkOrderDetail= findViewById(R.id.EditTextWorkOrderCodeDetailWD)
        var EditTextWorkOrderCodeWDText=""
        var EditTextWorkOrderTypeWDText=""
        var editTextPriorityWDText=""
        var editTextDepartementWDText=""
        var editTextWorkOrderItemNameWDText=""
        var editTextLocationWorkOrderWDText=""
        var txtWorkOrderDetailText=""

     buttonDoWorkOrder=findViewById(R.id.buttonDoWorkOrder)

        sharedPref = getSharedPreferences("deptCodeLogin", Context.MODE_PRIVATE)
        val departementWO: String =
            sharedPref.getString("deptCodeLogin", "").toString()


        sharedPref = getSharedPreferences("WOCodeDetailFromList", Context.MODE_PRIVATE)
        val Wo_code_detail__from_list: String =
            sharedPref.getString("WOCodeDetailFromList", null).toString().replace("Code WO Detail = ","")

        editTextCodeWorkOrderDetail!!.setText(Wo_code_detail__from_list)
        buttonDoWorkOrder!!.setBackgroundColor(Color.BLUE)
        buttonDoWorkOrder!!.setOnClickListener {



            if(departementWO!="D0007")
            {
                showDialogNotAuthorize()
            }
            else
            {
                showDialog()
            }

        }


        sharedPref = getSharedPreferences("WOCodeFromList", Context.MODE_PRIVATE)
        val Wo_code_from_list: String =
            sharedPref.getString("WOCodeFromList", null).toString().replace("Code WO = ","")
        Log.e("WO Code From List",Wo_code_from_list)

            AndroidNetworking.post(ApiEndPoint.view_work_order_detail)
                .addBodyParameter("wo_code", Wo_code_from_list)
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
                                Log.e("Error WOD",e?.errorDetail.toString())
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

                      //  Log.e("Error WOD",anError?.errorDetail?.toString())

                    }

                }


                )



    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.costum_dialog_do_work_order)

        val yesBtn = dialog.findViewById(R.id.buttonSaveDialogDoWorkOrder) as Button
        val cancelBtn = dialog.findViewById(R.id.buttonCancelDialogDoWorkOrder) as Button
        val editTextNoteMaintenance = dialog.findViewById(R.id.editTextNoteDoWorkOrder) as EditText
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        yesBtn.setOnClickListener { dialog.dismiss()
        updateStatusWorkOrder(editTextNoteMaintenance.text.toString())

            buttonDoWorkOrder!!.isEnabled=false


        }
        dialog.show()

    }


    fun updateStatusWorkOrder(note:String)
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

    }

    fun showDialogNotAuthorize()
    {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.costum_dialog_warning_cant_do_work_order)

        val okBtn = dialog.findViewById(R.id.buttonOkWarningCantDoWorkOrder) as Button

        okBtn.setOnClickListener { dialog.dismiss()

            dialog.dismiss()


        }
        dialog.show()
    }
}