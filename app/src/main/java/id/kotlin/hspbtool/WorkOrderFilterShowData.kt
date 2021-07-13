package id.kotlin.hspbtool

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.content.SharedPreferences
import android.content.Context
import android.content.Intent
import android.text.InputType
import android.app.DatePickerDialog
import java.util.*

class WorkOrderFilterShowData : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_order_filter_show_data)
        val arrayWorkOrderFilterBy = resources.getStringArray(R.array.work_order_filter_by)
        val spinnerWorkOrderFilterBy = findViewById<Spinner>(R.id.spinnerWorkOrderFilterBy)
        var textSpinnerWorkOrderByChoose=""
        val adapterWorkOrderFilterBy = ArrayAdapter(this,
            R.layout.spinner_item, arrayWorkOrderFilterBy)
        spinnerWorkOrderFilterBy.adapter = adapterWorkOrderFilterBy
        val editTextStartDate= findViewById(R.id.startDateFilterWorkOrder) as EditText
        val editTextEndDate= findViewById(R.id.endDateFilterWorkOrder) as EditText

        editTextEndDate.setText("")
        editTextStartDate.setText("")
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        editTextEndDate.setOnClickListener{
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                editTextEndDate.setText(""+year+"-"+dayOfMonth+"-"+dayOfMonth)
            }, year, month, day)
            dpd.show()
        }
        editTextStartDate.setOnClickListener{
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                editTextStartDate.setText(""+year+"-"+dayOfMonth+"-"+dayOfMonth)
            }, year, month, day)
            dpd.show()
        }


        spinnerWorkOrderFilterBy.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
               textSpinnerWorkOrderByChoose=arrayWorkOrderFilterBy[position]

                if(textSpinnerWorkOrderByChoose!="Show Work Order With Range Date")
                {
                   editTextStartDate.isEnabled=false
                    editTextStartDate.inputType=InputType.TYPE_NULL
                    editTextEndDate.isEnabled=false
                    editTextEndDate.inputType=InputType.TYPE_NULL

                    editTextEndDate.setText("")
                    editTextStartDate.setText("")
                }
                else
                {
                    editTextStartDate.isEnabled=true
                    editTextStartDate.inputType=InputType.TYPE_CLASS_DATETIME
                    editTextEndDate.isEnabled=true
                    editTextEndDate.inputType=InputType.TYPE_CLASS_DATETIME

                    editTextEndDate.setText("")
                    editTextStartDate.setText("")
                }


            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }



        val button_click= findViewById(R.id.buttonShowFilterWorkOrder) as Button


        button_click.setOnClickListener {
            var sharedPref : SharedPreferences
            sharedPref=getSharedPreferences("SpinerWorkOrderFilterByShowData", Context.MODE_PRIVATE)
            var sharedPrefEditor= sharedPref.edit()

            sharedPrefEditor.putString("SpinerWorkOrderFilterByShowData",textSpinnerWorkOrderByChoose)
            sharedPrefEditor.apply()


            sharedPref=getSharedPreferences("editTextStartRangeWorkOrder", Context.MODE_PRIVATE)
            sharedPrefEditor= sharedPref.edit()

            sharedPrefEditor.putString("editTextStartRangeWorkOrder",editTextStartDate.text.toString())
            sharedPrefEditor.apply()


            sharedPref=getSharedPreferences("editTextEndRangeWorkOrder", Context.MODE_PRIVATE)
            sharedPrefEditor= sharedPref.edit()

            sharedPrefEditor.putString("editTextEndRangeWorkOrder",editTextEndDate.text.toString())
            sharedPrefEditor.apply()
            val intent = Intent (this@WorkOrderFilterShowData,ViewRecordWorkOrderActivity::class.java)
            startActivity(intent)}


        }
    }
