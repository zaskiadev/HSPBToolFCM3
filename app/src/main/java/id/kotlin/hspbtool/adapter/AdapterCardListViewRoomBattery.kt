package id.kotlin.hspbtool.adapter
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.kotlin.hspbtool.domain.DataAdapterHotelInformation
import kotlinx.android.synthetic.main.list.view.*
import id.kotlin.hspbtool.R
import id.kotlin.hspbtool.domain.RoomBatteryView
import kotlinx.android.synthetic.main.list_card_battery.view.*

class AdapterCardListViewRoomBattery(private val list:List<RoomBatteryView>) : RecyclerView.Adapter<AdapterCardListViewRoomBattery.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.list_card_battery,parent,false))
    }

    override fun getItemCount(): Int = list?.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.view.roomNumberList.text = list?.get(position)?.room_number
        holder.view.lastChangeBattery.text = list?.get(position)?.last_change_battery
        holder.view.nextChangeBattery.text = list?.get(position)?.next_change_battery
        holder.view.averageCardUsed.text = list?.get(position)?.AverageCardUsed.toString()

    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

}