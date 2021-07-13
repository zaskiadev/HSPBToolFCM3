package id.kotlin.hspbtool.adapter
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import id.kotlin.hspbtool.R
import id.kotlin.hspbtool.WorkOrderViewDetailActivity
import id.kotlin.hspbtool.domain.WorkOrderView
import kotlinx.android.synthetic.main.list_card_work_order_list.view.*


/*class AdapterCardListViewWorkOrderListView(private val list:List<WorkOrderView>,Con) : RecyclerView.Adapter<AdapterCardListViewWorkOrderListView.Holder>(){*/

class AdapterCardListViewWorkOrderListView(private val list:List<WorkOrderView>,context:Context) : RecyclerView.Adapter<AdapterCardListViewWorkOrderListView.Holder>(){
private val mContext=context



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.list_card_work_order_list,parent,false))
    }

    override fun getItemCount(): Int = list?.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
      //  var isClicked:Boolean=false;
        holder.view.work_order_list_wo_code.text = list?.get(position)?.code_wo
        holder.view.work_order_list_user.text = list?.get(position)?.user_create
        holder.view.work_order_list_date_finish.text = list?.get(position)?.dateFinish
        holder.view.work_order_list_date_start.text = list?.get(position)?.dateStart

        holder.view.work_order_list_departement.text = list?.get(position)?.departement
        holder.view.work_order_list_wo_priority.text = list?.get(position)?.skala
        holder.view.work_order_list_wo_status.text = list?.get(position)?.status
        holder.view.work_order_list_wo_type.text = list?.get(position)?.jenis_wo

        holder.view.work_order_list_wo_code_detail.text=list?.get(position).code_wo_detail

        /*holder.view.cardListItemWorkOrder.setOnClickListener(View.OnClickListener {

                v->
            *//*Snackbar.make(
                view,
                "Clicked element $name",
                Snackbar.LENGTH_LONG
            ).show()*//*
            val myActivity = v.context as Activity
                var sharedPref:SharedPreferences
                sharedPref=myActivity.getSharedPreferences("WOCodeFromList",Context.MODE_PRIVATE)
                var sharedPrefEditor=sharedPref.edit()
                sharedPrefEditor.putString("WOCodeFromList",list?.get(position)?.code_wo)
                sharedPrefEditor.apply()

            val intent = Intent (myActivity, WorkOrderViewDetailActivity::class.java)
            myActivity.finish()
            myActivity.startActivity(intent)


        }

        )*/


        holder.view.setOnClickListener(View.OnClickListener {

            val myActivity =mContext as Activity


            var sharedPref:SharedPreferences
            sharedPref=myActivity.getSharedPreferences("WOCodeFromList",Context.MODE_PRIVATE)
            var sharedPrefEditor=sharedPref.edit()
            sharedPrefEditor.putString("WOCodeFromList",list?.get(position)?.code_wo)
            sharedPrefEditor.apply()

            sharedPref=myActivity.getSharedPreferences("WOCodeDetailFromList",Context.MODE_PRIVATE)
            sharedPrefEditor=sharedPref.edit()
            sharedPrefEditor.putString("WOCodeDetailFromList",list?.get(position)?.code_wo_detail)
            sharedPrefEditor.apply()

            val intent = Intent(mContext, WorkOrderViewDetailActivity::class.java)
            mContext!!.startActivity(intent)


        })


    }


    class Holder(val view: View) : RecyclerView.ViewHolder(view)





}