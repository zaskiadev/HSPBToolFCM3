package id.kotlin.hspbtool.adapter
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.kotlin.hspbtool.R
import id.kotlin.hspbtool.domain.ShowDataVentaza
import kotlinx.android.synthetic.main.list_card_time_card.view.*

class AdapterCardListViewTapTimeCard(private val list:List<ShowDataVentaza>) : RecyclerView.Adapter<AdapterCardListViewTapTimeCard.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.list_card_time_card,parent,false))
    }

    override fun getItemCount(): Int = list?.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.view.roomNumberListTimeCard.text = list?.get(position)?.room_number
        holder.view.lastTapTimeCard.text = list?.get(position)?.next_date_execute
        holder.view.nextTapTimeCard.text = list?.get(position)?.last_date_execute
        holder.view.lastUpdateBy.text= list?.get(position)?.user
    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

}