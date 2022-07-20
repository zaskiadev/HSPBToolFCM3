package id.kotlin.hspbtool.adapter
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import id.kotlin.hspbtool.domain.DataAdapterHotelInformation
import kotlinx.android.synthetic.main.list.view.*
import id.kotlin.hspbtool.R
import id.kotlin.hspbtool.domain.ListPromoView
import id.kotlin.hspbtool.domain.RoomBatteryView
import kotlinx.android.synthetic.main.list_card_battery.view.*
import kotlinx.android.synthetic.main.list_card_list_promo.view.*

class AdapterCardListViewListPromo(private val list:List<ListPromoView>) : RecyclerView.Adapter<AdapterCardListViewListPromo.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.list_card_list_promo,parent,false))
    }

    override fun getItemCount(): Int = list?.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

       // Log.e("test image promo : ",list?.get(position).image_promo)
        if(list?.get(position).image_promo!=null)
        {
            Picasso.get().load(list?.get(position).image_promo).into(holder.view.imageListPromo)
        }

        holder.view.namaPromo.text = list?.get(position)?.nama_promo
        holder.view.detailPromo.text = list?.get(position)?.detail_promo


    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

}