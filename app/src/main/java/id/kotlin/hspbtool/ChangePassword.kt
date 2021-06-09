package id.kotlin.hspbtool

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_add_record_ventaza.*
import kotlinx.android.synthetic.main.activity_change_password.*
import org.json.JSONObject

class ChangePassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        //setSupportActionBar(findViewById(R.id.toolbar))

        val sharedPref: SharedPreferences
        sharedPref = getSharedPreferences("UserName", Context.MODE_PRIVATE)
        val userLoginName: String = sharedPref.getString("UserName", "Belum Login").toString()

        val button_click = findViewById(R.id.buttonChangePassword) as Button
        val textViewUser = findViewById(R.id.textViewUser) as TextView

        textViewUser.text= textViewUser.text.toString() +" "+userLoginName

        button_click.setOnClickListener {


            AndroidNetworking.post(ApiEndPoint.change_password)
                    .addBodyParameter("new_password", editTextNewPassword.text.toString())
                    .addBodyParameter("user", userLoginName)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(object : JSONObjectRequestListener {

                        override fun onResponse(response: JSONObject?) {

                            if (response?.getString("message")?.contains("successfully")!!) {
                                Toast.makeText(applicationContext, "Connection Success", Toast.LENGTH_LONG).show()
                                this@ChangePassword.finish()
                            }

                        }

                        override fun onError(anError: ANError?) {
                            Toast.makeText(applicationContext, "Koneksi Error", Toast.LENGTH_LONG).show()
                        }


                    })

            /*findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/

        }
    }
}