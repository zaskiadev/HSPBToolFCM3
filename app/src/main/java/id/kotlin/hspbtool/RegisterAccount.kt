package id.kotlin.hspbtool

import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_add_record_ventaza.*
import kotlinx.android.synthetic.main.activity_change_password.*
import org.json.JSONObject
import java.util.*
import javax.mail.*
import javax.mail.internet.*


class RegisterAccount : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        //setSupportActionBar(findViewById(R.id.toolbar))


        val button_click = findViewById(R.id.buttonAddRegisterUser) as Button
        val editTextUser = findViewById(R.id.editTextRegisterUserName) as EditText
        val editTextPassword = findViewById(R.id.editTextRegisterPassword) as EditText
        val editTextEmail = findViewById(R.id.editTextRegisterEmail) as EditText


        button_click.setOnClickListener {


            AndroidNetworking.post(ApiEndPoint.register_account)
                    .addBodyParameter("user_name", editTextUser.text.toString())
                    .addBodyParameter("password", editTextPassword.text.toString())
                .addBodyParameter("email", editTextEmail.text.toString())
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(object : JSONObjectRequestListener {

                        override fun onResponse(response: JSONObject?) {


                            if (response?.getString("message")?.contains("successfully")!!) {
                                Toast.makeText(applicationContext, "Register Success, Please Check Your Mail", Toast.LENGTH_LONG).show()
                                sendEmail(editTextEmail.text.toString())
                                this@RegisterAccount.finish()
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

    fun sendEmail(sendTo:String?)
    {
        val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy = ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
            //your codes here
        }
        val props = Properties()
        putIfMissing(props, "mail.smtp.host", "mail.santika.com")
        putIfMissing(props, "mail.smtp.port", "587")
        putIfMissing(props, "mail.smtp.auth", "true")
        putIfMissing(props, "mail.smtp.starttls.enable", "true")

        val session = Session.getDefaultInstance(props, object : javax.mail.Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication("edp@bintaropremiere.santika.com", "F417dyicy")
            }
        })

        session.debug = true

        try {
            val mimeMessage = MimeMessage(session)
            mimeMessage.setFrom(InternetAddress("edp@bintaropremiere.santika.com"))
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendTo))
            mimeMessage.setRecipients(Message.RecipientType.CC, InternetAddress.parse("edp@bintaropremiere.santika.com", false))
            mimeMessage.setText("Your register account already submited. You can used HSPB Tool Application after your registered account approved by IT Hotel Santika Premiere Bintaro ")
            mimeMessage.subject = "Register Account At HSPB Tool Application"
            mimeMessage.sentDate = Date()

            val smtpTransport = session.getTransport("smtp")
            smtpTransport.connect()
            smtpTransport.sendMessage(mimeMessage, mimeMessage.allRecipients)
            smtpTransport.close()
        } catch (messagingException: MessagingException) {
            messagingException.printStackTrace()
            Toast.makeText(applicationContext, "Koneksi Error", Toast.LENGTH_LONG).show()
        }
    }

    private fun putIfMissing(props: Properties, key: String, value: String) {
        if (!props.containsKey(key)) {
            props[key] = value
        }
    }
}