package id.kotlin.hspbtool.adapter
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.kotlin.hspbtool.domain.DataAdapterHotelInformation
import kotlinx.android.synthetic.main.list.view.*
import id.kotlin.hspbtool.R
class AdapterCardList(private val list:List<DataAdapterHotelInformation>) : RecyclerView.Adapter<AdapterCardList.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.list,parent,false))
    }

    override fun getItemCount(): Int = list?.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.view.headerList.text = list?.get(position)?.HeadTitle
        holder.view.bodyList.text = list?.get(position)?.BodyData

    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

}