package id.kotlin.hspbtool.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.kotlin.hspbtool.R
import id.kotlin.hspbtool.domain.LogRecordVentazaAll
import kotlinx.android.synthetic.main.listcardlogalldata.view.*

class AdaptarCardListViewLogRecordVentazaAll (private val list:List<LogRecordVentazaAll>) : RecyclerView.Adapter<AdaptarCardListViewLogRecordVentazaAll.Holder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.listcardlogalldata,parent,false))
    }

    override fun getItemCount(): Int = list?.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.view.LogJudul.text=list?.get(position)?.judul
        holder.view.roomNumberListLog.text = list?.get(position)?.room_number
        holder.view.dateExecuteLog.text = list?.get(position)?.last_date_execute
        holder.view.nextExecuteLog.text = list?.get(position)?.next_date_execute
        holder.view.userChangeLog.text = list?.get(position)?.user

    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

}