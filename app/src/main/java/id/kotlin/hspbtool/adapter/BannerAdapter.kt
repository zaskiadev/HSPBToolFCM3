package id.kotlin.hspbtool.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import id.kotlin.hspbtool.BannerFragment
import id.kotlin.hspbtool.domain.BannerPromo

class BannerAdapter(fragmentManager: FragmentManager,
                    private val banners: List<BannerPromo>) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return BannerFragment.newInstance(banners[position].image)
    }

    override fun getCount(): Int = banners.size


}