package id.kotlin.hspbtool

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.RingtoneManager
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_add_record_ventaza.*
import org.json.JSONObject
import android.widget.*
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_add_maintenance_ac.*
import kotlinx.android.synthetic.main.activity_add_maintenance_ventaza.*
import java.lang.ref.WeakReference

class LoadingActivity : AppCompatActivity() {
    var dataTapTime:String=""
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        val task = MyAsyncTask(this)
        task.execute(100)
      val progressBar:ProgressBar= findViewById(R.id.progressBarLoading)
        var i=0
        val handler = Handler()
        i=progressBar!!.progress

        val textLoad:TextView=findViewById(R.id.textViewLoadingStatus)
        Thread(Runnable {

            while(i<100){
                i+=10
                handler.post(
                    Runnable {
                        progressBar!!.progress=i
                        textLoad!!.text="Loading : "+i.toString()+" / "+progressBar!!.max

                    }
                )
                try{
                    Thread.sleep(1000)


                }
                catch (e: InterruptedException)
                {
                    e.printStackTrace()
                }
            }
//            Toast.makeText(applicationContext,"Student data is empty, Add the data first",Toast.LENGTH_SHORT).show()


            val intent = Intent (this@LoadingActivity,MainActivity::class.java)
            startActivity(intent)
        }).start()



    }


    companion object {
        class MyAsyncTask internal constructor(context: LoadingActivity) : AsyncTask<Int, String, String?>() {

            private var resp: String? = null
            private val activityReference: WeakReference<LoadingActivity> = WeakReference(context)

            override fun onPreExecute() {
                val activity = activityReference.get()
                if (activity == null || activity.isFinishing) return

            }

            override fun doInBackground(vararg params: Int?): String? {
                publishProgress("Sleeping Started") // Calls onProgressUpdate()
                try {

                } catch (e: InterruptedException) {
                    e.printStackTrace()
                    resp = e.message
                } catch (e: Exception) {
                    e.printStackTrace()
                    resp = e.message
                }

                return resp
            }


            override fun onPostExecute(result: String?) {

                // execution of result of Long time consuming operation
                val activity = activityReference.get()
                if (activity == null || activity.isFinishing) return
                // access Activity member variables or modify the activity's UI

            }

            override fun onProgressUpdate(vararg text: String?) {

                val activity = activityReference.get()
                if (activity == null || activity.isFinishing) return

                Toast.makeText(activity, text.firstOrNull(), Toast.LENGTH_SHORT).show()

            }
        }
    }


}