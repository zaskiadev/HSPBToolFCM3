package id.kotlin.hspbtool

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

class WorkOrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_order)

        val buttonTakePictureWO : Button=findViewById(R.id.buttonTakePictureWorkOrder)

        buttonTakePictureWO.setOnClickListener{
            val intent = Intent(this@WorkOrderActivity, TakePictureWorkOrderActivity::class.java)
            startActivity(intent)
        }
    }
}