package id.kotlin.hspbtool.adapter
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.kotlin.hspbtool.R
import id.kotlin.hspbtool.domain.ShowDataACMaintenanceRoom
import kotlinx.android.synthetic.main.list_card_ac.view.*
import kotlinx.android.synthetic.main.list_card_time_card.view.*

class AdapterCardListViewDataMaintenanceAc(private val list:List<ShowDataACMaintenanceRoom>) : RecyclerView.Adapter<AdapterCardListViewDataMaintenanceAc.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.list_card_ac,parent,false))
    }

    override fun getItemCount(): Int = list?.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.view.roomNumberListAC.text = list?.get(position)?.room_number
        holder.view.dateExecuteAC.text = list?.get(position)?.last_date_execute
        holder.view.userExecuteAC.text= list?.get(position)?.user
        holder.view.isCleaningAC.text=list?.get(position)?.isCleaning
        holder.view.isTuningFilterAC.text=list?.get(position)?.isTuningFilter
        holder.view.isBlowerAC.text=list?.get(position)?.isBlower
        holder.view.isCoilEvavoratorAC.text=list?.get(position)?.isCoilEvavorator
        holder.view.isVacumDrainAC.text=list?.get(position)?.isVacumDrain
        holder.view.isCheckingDuctingConnectionAC.text=list?.get(position)?.isCheckingDuctingConnection
        holder.view.remarkAC.text=list?.get(position)?.remark
    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

}