package id.kotlin.hspbtool.item

import androidx.fragment.app.FragmentManager
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import id.kotlin.hspbtool.R
import id.kotlin.hspbtool.adapter.BannerAdapter
import id.kotlin.hspbtool.domain.BannerPromo
import kotlinx.android.synthetic.main.item_carousel_banner.view.btnSemuaPromo
import kotlinx.android.synthetic.main.item_carousel_banner.view.indicator
import kotlinx.android.synthetic.main.item_carousel_banner.view.viewPagerBanner

interface BannerListener {
    fun onSeeAllPromoClick()
    fun onBannerClick(promo: BannerPromo)
}

class BannerCarouselItem(private val banners: List<BannerPromo>,
                         private val supportFragmentManager: FragmentManager,
                         private val listener: BannerListener) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val viewPagerAdapter = BannerAdapter(supportFragmentManager, banners)
        viewHolder.itemView.viewPagerBanner.adapter = viewPagerAdapter
        viewHolder.itemView.indicator.setViewPager(viewHolder.itemView.viewPagerBanner)

        viewHolder.itemView.btnSemuaPromo.setOnClickListener {
            listener.onSeeAllPromoClick()
        }
    }

    override fun getLayout(): Int = R.layout.item_carousel_banner
}