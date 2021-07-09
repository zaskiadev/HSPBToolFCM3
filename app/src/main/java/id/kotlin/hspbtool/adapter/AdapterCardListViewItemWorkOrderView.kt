package id.kotlin.hspbtool.adapter
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.kotlin.hspbtool.R
import id.kotlin.hspbtool.domain.DataItemWorkOrderView
import kotlinx.android.synthetic.main.list_card_data_item_work_order.view.*


class AdapterCardListViewItemWorkOrderView(private val list:List<DataItemWorkOrderView>) : RecyclerView.Adapter<AdapterCardListViewItemWorkOrderView.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.list_card_data_item_work_order,parent,false))
    }

    override fun getItemCount(): Int = list?.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        var isClicked:Boolean=false;
        holder.view.item_work_order_code_barang.text = list?.get(position)?.code_barang
        holder.view.item_work_order_nama_barang.text = list?.get(position)?.nama_barang
        holder.view.item_work_order_location.text = list?.get(position)?.location
        holder.view.item_work_order_departement.text = list?.get(position)?.dept_code
        var name:String?=list?.get(position).nama_barang

        holder.view.cardListItemWorkOrder.setOnClickListener(View.OnClickListener {

                v->
            /*Snackbar.make(
                view,
                "Clicked element $name",
                Snackbar.LENGTH_LONG
            ).show()*/
            val myActivity = v.context as Activity

            var sharedPref=   myActivity.getSharedPreferences("stateSelectItem", Context.MODE_PRIVATE)
            isClicked= sharedPref.getBoolean("stateSelectItem",false)

            if(isClicked==false)
            {

                var sharedPref:SharedPreferences
                sharedPref=myActivity.getSharedPreferences("stateSelectItem",Context.MODE_PRIVATE)
                var sharedPrefEditor=sharedPref.edit()
                sharedPrefEditor.putBoolean("stateSelectItem",true)
                sharedPrefEditor.apply()


                sharedPref=myActivity.getSharedPreferences("codeBarang",Context.MODE_PRIVATE)
                sharedPrefEditor=sharedPref.edit()
                sharedPrefEditor.putString("codeBarang",  list?.get(position)?.code_barang)
                sharedPrefEditor.apply()

                sharedPref=myActivity.getSharedPreferences("namaBarang",Context.MODE_PRIVATE)
                sharedPrefEditor=sharedPref.edit()
                sharedPrefEditor.putString("namaBarang",list?.get(position)?.nama_barang)
                sharedPrefEditor.apply()

                sharedPref=myActivity.getSharedPreferences("location",Context.MODE_PRIVATE)
                sharedPrefEditor=sharedPref.edit()
                sharedPrefEditor.putString("location",list?.get(position)?.location)
                sharedPrefEditor.apply()

                sharedPref=myActivity.getSharedPreferences("departement",Context.MODE_PRIVATE)
                sharedPrefEditor=sharedPref.edit()
                sharedPrefEditor.putString("departement",list?.get(position)?.dept_code)
                sharedPrefEditor.apply()



                isClicked==true
                holder.view.cardListItemWorkOrder.setCardBackgroundColor(Color.CYAN)
            }
            else
            {
                val myActivity = v.context as Activity
                var sharedPref:SharedPreferences
                sharedPref=myActivity.getSharedPreferences("stateSelectItem",Context.MODE_PRIVATE)
                var sharedPrefEditor=sharedPref.edit()
                sharedPrefEditor.putBoolean("stateSelectItem",false)
                sharedPrefEditor.apply()


                sharedPref=myActivity.getSharedPreferences("codeBarang",Context.MODE_PRIVATE)
                sharedPrefEditor=sharedPref.edit()
                sharedPrefEditor.putString("codeBarang",  null)
                sharedPrefEditor.apply()

                sharedPref=myActivity.getSharedPreferences("namaBarang",Context.MODE_PRIVATE)
                sharedPrefEditor=sharedPref.edit()
                sharedPrefEditor.putString("namaBarang",null)
                sharedPrefEditor.apply()

                sharedPref=myActivity.getSharedPreferences("location",Context.MODE_PRIVATE)
                sharedPrefEditor=sharedPref.edit()
                sharedPrefEditor.putString("location",null)
                sharedPrefEditor.apply()

                sharedPref=myActivity.getSharedPreferences("departement",Context.MODE_PRIVATE)
                sharedPrefEditor=sharedPref.edit()
                sharedPrefEditor.putString("departement",null)
                sharedPrefEditor.apply()


                isClicked==true
                holder.view.cardListItemWorkOrder.setCardBackgroundColor(Color.WHITE)
                isClicked==false
            }
        }

        )


    }


    class Holder(val view: View) : RecyclerView.ViewHolder(view)





}