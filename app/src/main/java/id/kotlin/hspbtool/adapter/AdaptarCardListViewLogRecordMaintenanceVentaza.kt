package id.kotlin.hspbtool.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.kotlin.hspbtool.R
import id.kotlin.hspbtool.domain.LogRecordMaintenanceVentaza
import kotlinx.android.synthetic.main.list_card_log_maintenance_ventaza.view.*


class AdaptarCardListViewLogRecordMaintenanceVentaza (private val list:List<LogRecordMaintenanceVentaza>) : RecyclerView.Adapter<AdaptarCardListViewLogRecordMaintenanceVentaza.Holder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.list_card_log_maintenance_ventaza,parent,false))
    }

    override fun getItemCount(): Int = list?.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.view.LogJudulMaintenanceVentaza.text=list?.get(position)?.judul
        holder.view.roomNumberListLogMaintenanceVentaza.text = list?.get(position)?.room_number
        holder.view.dateExecuteLogMaintenanceVentaza.text = list?.get(position)?.date_maintenance
        holder.view.maintenanceTypeVentaza.text = list?.get(position)?.date_maintenance
        holder.view.remarkMaintenanceTypeVentaza.text = list?.get(position)?.remark
        holder.view.userExecuteMaintenanceVentaza.text = list?.get(position)?.user

    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

}