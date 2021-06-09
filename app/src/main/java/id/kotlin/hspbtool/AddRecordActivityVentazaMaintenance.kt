package id.kotlin.hspbtool

import android.content.Context
import android.content.SharedPreferences
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_add_record_ventaza.*
import org.json.JSONObject
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_add_maintenance_ventaza.*

class AddRecordActivityVentazaMaintenance : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_maintenance_ventaza)

        val sharedPref : SharedPreferences
        sharedPref=   getSharedPreferences("UserName", Context.MODE_PRIVATE)
        val userLoginName : String = sharedPref.getString("UserName","Belum Login").toString()
        var maintenance_type : String=""


        val button_click= findViewById(R.id.buttonAddMaintenance) as Button
        val radioGroupVentazaSelection= findViewById(R.id.radioGroupChooseMaintenanceVentaza) as RadioGroup
        lateinit var radioButton: RadioButton

        button_click.setOnClickListener {

            val intSelectButton: Int = radioGroupVentazaSelection!!.checkedRadioButtonId
            radioButton = findViewById(intSelectButton)
           maintenance_type= radioButton.text.toString()



                    AndroidNetworking.post(ApiEndPoint.create_maintenance_ventaza)
                            .addBodyParameter("room_number", editTextTextRoomNumberMaintenanceVentaza.text.toString())
                            .addBodyParameter("user", userLoginName)
                            .addBodyParameter("maintenance_type",maintenance_type)
                            .addBodyParameter("remark",editTextNoteMaintenanceVentaza.text.toString())
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(object : JSONObjectRequestListener {

                                override fun onResponse(response: JSONObject?) {

                                    if (response?.getString("message")?.contains("successfully")!!) {
                                        Toast.makeText(applicationContext, "Data recorded successfully", Toast.LENGTH_SHORT).show()

                                        this@AddRecordActivityVentazaMaintenance.finish()
                                    }

                                }

                                override fun onError(anError: ANError?) {
                                    Toast.makeText(applicationContext, "Koneksi Error", Toast.LENGTH_LONG).show()
                                }


                            })

        }
    }
}