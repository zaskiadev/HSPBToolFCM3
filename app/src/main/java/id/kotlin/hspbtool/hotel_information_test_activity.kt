package id.kotlin.hspbtool

//import androidx.appcompat.widget.Toolbar
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import id.kotlin.hspbtool.domain.BannerPromo
import id.kotlin.hspbtool.domain.Hero
import id.kotlin.hspbtool.domain.Product
import id.kotlin.hspbtool.item.BannerCarouselItem
import id.kotlin.hspbtool.item.BannerListener
import id.kotlin.hspbtool.item.ProductCategoryItem
import id.kotlin.hspbtool.item.ProductItem
import id.kotlin.hspbtool.item.ProductListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.hotel_information_test.rvVentazaRecord

class hotel_information_test_activity : AppCompatActivity(), HeroListener, BannerListener, ProductListener {

    // declare adapter from groupadapter
    private var groupAdapter = GroupAdapter<ViewHolder>()
    //val rvMain: RecyclerView = findViewById(R.id.rvMain)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hotel_information_test)

        val promos = listOf(
                BannerPromo(name = "Promo 1",
                        image = "http://103.84.233.186:8742/hspb_tool/image_promo/promo1.jpg "),
                BannerPromo(
                        name = "Promo 2",
                        image = "http://103.84.233.186:8742/hspb_tool/image_promo/promo2.jpg"
                ),
                BannerPromo(name = "Promo 3",
                        image = "http://103.84.233.186:8742/hspb_tool/image_promo/promo3.jpg"),
                BannerPromo(
                        name = "Promo 4",
                        image = "http://103.84.233.186:8742/hspb_tool/image_promo/promo4.jpg"
                ),
                BannerPromo(name = "Promo 5",
                        image = "http://103.84.233.186:8742/hspb_tool/image_promo/promo5.jpg"),
                BannerPromo(
                        name = "Promo 6",
                        image = "http://103.84.233.186:8742/hspb_tool/image_promo/promo6.jpg"
                )
        )

        val products = listOf(
                Product(name = "Sales And Marketing",
                        img = R.drawable.sales_icon),
                Product(name = "Front Office",
                        img = R.drawable.front_office_icon),
                Product(name = "F&B Service",
                        img = R.drawable.f_b_s_icon),
                Product(name = "F&B Product",
                        img = R.drawable.f_b_product_icon),
                Product(name = "Engineering",
                        img = R.drawable.engineering_icon),
            Product(name = "Housekeeping",
                img = R.drawable.hk_icon),
            Product(name = "HRD",
                img = R.drawable.hr_icon),
                Product(name = "Accounting",
                        img = R.drawable.accounting_icon)
        )



        rvVentazaRecord.apply {
            layoutManager = LinearLayoutManager(this@hotel_information_test_activity)
            adapter = groupAdapter
        }

        // declare banner carousel
        val bannerCarouselItem = BannerCarouselItem(promos, supportFragmentManager, this)
        groupAdapter.add(bannerCarouselItem)

        Section().apply {
            add(makeProductCategory(products))
            groupAdapter.add(this)
        }
    }

    private fun makeProductCategory(products: List<Product>): ProductCategoryItem {
        val productItemGroupAdapter = GroupAdapter<ViewHolder>()
        products.map {
            productItemGroupAdapter.add(ProductItem(it, this))
        }
        return ProductCategoryItem(productItemGroupAdapter)
    }

    override fun onProductClicked(product: Product) {
        when(product.name)
        {
            "Sales And Marketing" ->
            {val intent = Intent (this@hotel_information_test_activity,SalesMarketingActivity::class.java)
            startActivity(intent)}
            "Front Office" ->
            {
                val intent = Intent (this@hotel_information_test_activity,FrontOfficeActivity::class.java)
                    startActivity(intent)
            }
            "F&B Service" ->
            {
                val intent = Intent (this@hotel_information_test_activity,FBServiceActivity::class.java)
                startActivity(intent)
            }
            "F&B Product" ->
            {
                val intent = Intent (this@hotel_information_test_activity,FBProductActivity::class.java)
                startActivity(intent)
            }
            "Engineering" ->
            {
                val intent = Intent (this@hotel_information_test_activity,EngineeringActivity::class.java)
                startActivity(intent)
            }
            "Housekeeping" ->
            {
                val intent = Intent (this@hotel_information_test_activity,HKActivity::class.java)
                startActivity(intent)
            }
            "HRD" ->
            {
                val intent = Intent (this@hotel_information_test_activity,HRDActivity::class.java)
                startActivity(intent)
            }
            "Accounting" ->
            {
                val intent = Intent (this@hotel_information_test_activity,AccountingActivity::class.java)
                startActivity(intent)
            }



        }

        //Toast.makeText(this, "clicked ${product.name}", Toast.LENGTH_SHORT).show()
    }

    override fun onSeeAllPromoClick() {
        Toast.makeText(this, "see all promo", Toast.LENGTH_SHORT).show()
    }

    override fun onBannerClick(promo: BannerPromo) {
    }

    override fun onHeroClick(hero: Hero) {
        Toast.makeText(this, "hero clicked ${hero.name}", Toast.LENGTH_SHORT).show()
    }
}
