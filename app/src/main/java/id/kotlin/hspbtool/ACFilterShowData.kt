package id.kotlin.hspbtool

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_record_ventaza.*
import android.content.SharedPreferences
import android.content.Context
import android.content.Intent

class ACFilterShowData : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ac_filter_show_data)
        val arrayACFilterBy = resources.getStringArray(R.array.ventaza_filter_by)
        val spinnerACFilterBy = findViewById<Spinner>(R.id.spinnerACFilterBy)
        var textSpinnerACFilterByChoose=""
        val adapterACFilterBy = ArrayAdapter(this,
            R.layout.spinner_item, arrayACFilterBy)
        spinnerACFilterBy.adapter = adapterACFilterBy

        spinnerACFilterBy.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                textSpinnerACFilterByChoose=arrayACFilterBy[position]


            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        val spinnerVentazaFilterBy = findViewById<Spinner>(R.id.spinnerFilterBy)

        val button_click= findViewById(R.id.buttonShowFilterAC) as Button


        button_click.setOnClickListener {
            var sharedPref : SharedPreferences
            sharedPref=getSharedPreferences("SpinerACFilterByShowData", Context.MODE_PRIVATE)
            var sharedPrefEditor= sharedPref.edit()

            sharedPrefEditor.putString("SpinerACFilterByShowData",textSpinnerACFilterByChoose)
            sharedPrefEditor.apply()
            val intent = Intent (this@ACFilterShowData,ViewRecordACActivity::class.java)
            startActivity(intent)}


        }
    }
