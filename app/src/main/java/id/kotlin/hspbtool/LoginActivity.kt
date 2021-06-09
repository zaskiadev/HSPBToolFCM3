package id.kotlin.hspbtool

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_add_record_ventaza.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_gallery.*
import org.json.JSONObject

class LoginActivity: AppCompatActivity()
{
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    val sharedPref : SharedPreferences
    sharedPref=getSharedPreferences("UserName", Context.MODE_PRIVATE)
    var sharedPrefEditor= sharedPref.edit()
    val button_click= findViewById(R.id.buttonSignIn) as Button

    button_click.setOnClickListener {

        AndroidNetworking.post(ApiEndPoint.login)
                .addBodyParameter("user",textViewUser.text.toString())
                .addBodyParameter("pass",textViewPassword.text.toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {

                    override fun onResponse(response: JSONObject?) {

                        if(response?.getString("message")?.contains("successfully")!!){
                            Toast.makeText(applicationContext,"Login Success", Toast.LENGTH_LONG).show()
                            val userName= textViewUser.text.toString()
                            sharedPrefEditor.putString("UserName",userName)
                            sharedPrefEditor.apply()

                            val intent = Intent (this@LoginActivity,MainActivity::class.java)
                            startActivity(intent)
                            //this@.finish()
                        }
                        else
                        {
                            Toast.makeText(applicationContext,"Login failed, please try again", Toast.LENGTH_LONG).show()
                        }

                    }

                    override fun onError(anError: ANError?) {
                        Toast.makeText(applicationContext,"Koneksi Error", Toast.LENGTH_LONG).show()
                    }


                })
    }
}
}