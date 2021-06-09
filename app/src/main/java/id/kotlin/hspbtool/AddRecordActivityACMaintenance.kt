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
import kotlinx.android.synthetic.main.activity_add_maintenance_ac.*
import kotlinx.android.synthetic.main.activity_add_maintenance_ventaza.*

class AddRecordActivityACMaintenance : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_maintenance_ac)

        val sharedPref : SharedPreferences
        sharedPref=   getSharedPreferences("UserName", Context.MODE_PRIVATE)
        val userLoginName : String = sharedPref.getString("UserName","Belum Login").toString()
        var maintenance_type : String=""

        var isCleaning:Int=0; var isTuningFilter:Int=0; var isBlower:Int=0; var isCoilEvavorator:Int=0; var isVacumDrain:Int=0
        var isCheckingDuctingConnection:Int=0

        val button_click= findViewById(R.id.buttonAddMaintenanceAC) as Button

        val chkIsCleaning = findViewById(R.id.CBCleaningAC) as CheckBox
        val chkIsTuningFilter = findViewById(R.id.CBTuningFilter) as CheckBox
        val chkIsBlower = findViewById(R.id.CBBlowerAC) as CheckBox
        val chkIsCoilEvavorator = findViewById(R.id.CBCoilEvavorator) as CheckBox
        val chkIsVacumDrain = findViewById(R.id.CBVacumDrain) as CheckBox
        val chkIsCheckingDucting= findViewById(R.id.CBDuctingConnection) as CheckBox


        button_click.setOnClickListener {


            if(chkIsCleaning.isChecked)
            {
                isCleaning=1
            }
            if(chkIsTuningFilter.isChecked)
            {
                isTuningFilter=1
            }
            if(chkIsBlower.isChecked)
            {
                isBlower=1
            }
            if(chkIsCoilEvavorator.isChecked)
            {
                isCoilEvavorator=1
            }
            if(chkIsVacumDrain.isChecked)
            {
                isVacumDrain=1
            }
            if(chkIsCheckingDucting.isChecked)
            {
                isCheckingDuctingConnection=1
            }


                    AndroidNetworking.post(ApiEndPoint.create_maintenance_ac_room)
                            .addBodyParameter("room_number", editTextTextRoomNumberMaintenanceAC.text.toString())
                            .addBodyParameter("user", userLoginName)
                            .addBodyParameter("remark",editTextNoteMaintenanceAC.text.toString())
                            .addBodyParameter("isCleaning",isCleaning.toString())
                            .addBodyParameter("isTuningFilter", isTuningFilter.toString())
                            .addBodyParameter("isBlower", isBlower.toString())
                                .addBodyParameter("isCoilEvavorator", isCoilEvavorator.toString())
                            .addBodyParameter("isVacumDrain", isVacumDrain.toString())
                            .addBodyParameter("isCheckingDuctingConnection", isCheckingDuctingConnection.toString())
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(object : JSONObjectRequestListener {

                                override fun onResponse(response: JSONObject?) {

                                    if (response?.getString("message")?.contains("successfully")!!) {
                                        Toast.makeText(applicationContext, "Data recorded successfully", Toast.LENGTH_SHORT).show()

                                        this@AddRecordActivityACMaintenance.finish()
                                    }

                                }

                                override fun onError(anError: ANError?) {
                                    Toast.makeText(applicationContext, "Koneksi Error", Toast.LENGTH_LONG).show()
                                }


                            })

        }
    }
}