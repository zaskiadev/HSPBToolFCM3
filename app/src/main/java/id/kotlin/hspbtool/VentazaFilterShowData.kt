package id.kotlin.hspbtool

import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_add_record_ventaza.*
import org.json.JSONObject
import android.content.SharedPreferences
import android.content.Context
import android.content.Intent

class VentazaFilterShowData : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventaza_filter_show_data)
        /*setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/

        val arrayVentazaChooseTapOrBattery = resources.getStringArray(R.array.ventaza_filter_choose)
        val arrayVentazaFilterBy = resources.getStringArray(R.array.ventaza_filter_by)

        //val ventazaChoose = resources.getStringArray(R.array.ventaza_choose)
        val spinnerVentazaChoose = findViewById<Spinner>(R.id.spinnerFilterBatteryOrTimeCard)
        var textSpinnerVentazaChooseSelected=""
        val adapterVentazaChoose = ArrayAdapter(this,
            R.layout.spinner_item, arrayVentazaChooseTapOrBattery)
        spinnerVentazaChoose.adapter = adapterVentazaChoose

        spinnerVentazaChoose.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                textSpinnerVentazaChooseSelected=arrayVentazaChooseTapOrBattery[position]


            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        val spinnerVentazaFilterBy = findViewById<Spinner>(R.id.spinnerFilterBy)
        var textSpinnerVentazaFilterBySelected=""
        val adapterVentazaFilterBy = ArrayAdapter(this,
            R.layout.spinner_item, arrayVentazaFilterBy)
        spinnerVentazaFilterBy.adapter = adapterVentazaFilterBy

        spinnerVentazaFilterBy.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                textSpinnerVentazaFilterBySelected=arrayVentazaFilterBy[position]


            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        val button_click= findViewById(R.id.buttonShowFilterDataVentaza) as Button


        button_click.setOnClickListener {
            var sharedPref : SharedPreferences
            sharedPref=getSharedPreferences("SpinerVentazaChooseShowData", Context.MODE_PRIVATE)
            var sharedPrefEditor= sharedPref.edit()

            sharedPrefEditor.putString("SpinerVentazaChooseShowData",textSpinnerVentazaChooseSelected)
            sharedPrefEditor.apply()


            sharedPref=getSharedPreferences("SpinerVentazaFilterByShowData", Context.MODE_PRIVATE)
            sharedPrefEditor= sharedPref.edit()

            sharedPrefEditor.putString("SpinerVentazaFilterByShowData",textSpinnerVentazaFilterBySelected)
            sharedPrefEditor.apply()
            val intent = Intent (this@VentazaFilterShowData,ViewRecordBatteryActivity::class.java)
            startActivity(intent)}


        }
    }
