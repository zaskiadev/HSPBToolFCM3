package id.kotlin.hspbtool.item

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import id.kotlin.hspbtool.R
import id.kotlin.hspbtool.ViewDetailPromoActivity
import id.kotlin.hspbtool.WorkOrderViewDetailActivity
import id.kotlin.hspbtool.adapter.BannerAdapter
import id.kotlin.hspbtool.domain.BannerPromo
import kotlinx.android.synthetic.main.item_carousel_banner.view.*

interface BannerListener {
    fun onSeeAllPromoClick()
    fun onBannerClick(promo:List<BannerPromo>,positionBanner: Int)
}

class BannerCarouselItem(private val banners: MutableList<BannerPromo>,
                         private val supportFragmentManager: FragmentManager,
                         private val listener: BannerListener,context:Context) : Item() {
    private val mContext=context


    override fun bind(viewHolder: ViewHolder, position: Int) {
        val viewPagerAdapter = BannerAdapter(supportFragmentManager, banners)
        viewHolder.itemView.viewPagerBanner.adapter = viewPagerAdapter
        viewHolder.itemView.indicator.setViewPager(viewHolder.itemView.viewPagerBanner)
        Log.e("Position dari View Holder : ",""+position)
       /* viewHolder.itemView.viewPagerBanner.setOnClickListener() {
            listener.onSeeAllPromoClick()
        }
*/


        viewHolder.itemView.viewPagerBanner.setOnClickListener{
         /*   val myActivity =mContext as Activity

            var sharedPref : SharedPreferences
            sharedPref=myActivity.getSharedPreferences("PromoNumber", Context.MODE_PRIVATE)
            var sharedPrefEditor= sharedPref.edit()

            sharedPrefEditor.putString("PromoNumber",banners?.get(position).name)
            sharedPrefEditor.apply()


            sharedPref=myActivity.getSharedPreferences("imagePromoDetail", Context.MODE_PRIVATE)
            sharedPrefEditor= sharedPref.edit()

            sharedPrefEditor.putString("imagePromoDetail",banners?.get(position).image)
            sharedPrefEditor.apply()

            val intent = Intent(mContext, ViewDetailPromoActivity::class.java)
            mContext!!.startActivity(intent)*/

            listener.onBannerClick(banners,position)



        }

        viewHolder.itemView.btnSemuaPromo.setOnClickListener {

            listener.onBannerClick(banners,position)
        }
        viewHolder.itemView.setOnClickListener{


            listener.onBannerClick(banners,position)



        }

        /*viewHolder.itemView.indicator.setOnClickListener {
            listener.onBannerClick(banners,position)
        }*/
    }

    override fun getLayout(): Int = R.layout.item_carousel_banner
}