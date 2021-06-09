package id.kotlin.hspbtool

import android.content.Context
import android.content.SharedPreferences
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_add_record_ventaza.*
import org.json.JSONObject
import android.widget.AdapterView;
import android.view.View

class AddRecordActivityVentaza : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_record_ventaza)

        val sharedPref : SharedPreferences
        sharedPref=   getSharedPreferences("UserName", Context.MODE_PRIVATE)
        val userLoginName : String = sharedPref.getString("UserName","Belum Login").toString()

        val ventazaChoose = resources.getStringArray(R.array.ventaza_choose)
        val spinner = findViewById<Spinner>(R.id.spinnerVentaza)
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
        val button_click= findViewById(R.id.buttonAddRecord) as Button


        button_click.setOnClickListener {


            if(textSpinnerSelected=="")
            {
                Toast.makeText(applicationContext, "Please select action first in spinner", Toast.LENGTH_SHORT).show()
            }
            else {
                if(textSpinnerSelected=="Add Record Battery") {
                    AndroidNetworking.post(ApiEndPoint.crete_record_battery)
                            .addBodyParameter("room_number", editTextTextRoomNumber.text.toString())
                            .addBodyParameter("user", userLoginName)
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(object : JSONObjectRequestListener {

                                override fun onResponse(response: JSONObject?) {

                                    if (response?.getString("message")?.contains("successfully")!!) {
                                        Toast.makeText(applicationContext, "Data recorded successfully", Toast.LENGTH_SHORT).show()
                                        editTextTextRoomNumber.text.clear();
                                    //this@AddRecordActivity.finish()
                                    }

                                }

                                override fun onError(anError: ANError?) {
                                    Toast.makeText(applicationContext, "Koneksi Error", Toast.LENGTH_LONG).show()
                                }


                            })
                }
                else if(textSpinnerSelected=="Add Record Time Card")
                {
                    AndroidNetworking.post(ApiEndPoint.create_tap_record)
                            .addBodyParameter("room_number", editTextTextRoomNumber.text.toString())
                            .addBodyParameter("user", userLoginName)
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(object : JSONObjectRequestListener {

                                override fun onResponse(response: JSONObject?) {

                                    if (response?.getString("message")?.contains("successfully")!!) {
                                        Toast.makeText(applicationContext, "Data recorded successfully", Toast.LENGTH_SHORT).show()
                                        editTextTextRoomNumber.text.clear();
                                    //this@AddRecordActivity.finish()
                                    }

                                }

                                override fun onError(anError: ANError?) {
                                    Toast.makeText(applicationContext, "Koneksi Error", Toast.LENGTH_LONG).show()
                                }


                            })
                }
                else
                {
                    AndroidNetworking.post(ApiEndPoint.create_keytag_record)
                            .addBodyParameter("room_number", editTextTextRoomNumber.text.toString())
                            .addBodyParameter("user", userLoginName)
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(object : JSONObjectRequestListener {

                                override fun onResponse(response: JSONObject?) {

                                    if (response?.getString("message")?.contains("successfully")!!) {
                                        Toast.makeText(applicationContext, "Data recorded successfully", Toast.LENGTH_SHORT).show()
                                        //this@AddRecordActivity.finish()
                                        editTextTextRoomNumber.text.clear();
                                    }

                                }

                                override fun onError(anError: ANError?) {
                                    Toast.makeText(applicationContext, "Koneksi Error", Toast.LENGTH_LONG).show()
                                }


                            })
                }
            }
        }
    }
}