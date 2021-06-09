package id.kotlin.hspbtool//import androidx.appcompat.widget.Toolbar
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import id.kotlin.hspbtool.domain.BannerPromo
import id.kotlin.hspbtool.domain.Hero
import id.kotlin.hspbtool.domain.Product
import id.kotlin.hspbtool.item.BannerListener
import id.kotlin.hspbtool.item.ProductCategoryItem
import id.kotlin.hspbtool.item.ProductItem
import id.kotlin.hspbtool.item.ProductListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.activity_menu_engineering_record.*
import kotlinx.android.synthetic.main.hotel_information_test.rvVentazaRecord

class MenuEngineeringRecord : AppCompatActivity(), HeroListener, BannerListener, ProductListener {

    // declare adapter from groupadapter
    private var groupAdapter = GroupAdapter<ViewHolder>()
    //val rvMain: RecyclerView = findViewById(R.id.rvMain)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_engineering_record)



        val products = listOf(
                Product(name = "Ventaza Record",
                        img = R.drawable.ventaza),
                Product(name = "AC Record",
                        img = R.drawable.ac)

        )



        rvMenuEngineeringRecord.apply {
            layoutManager = LinearLayoutManager(this@MenuEngineeringRecord)
            adapter = groupAdapter
        }

        // declare banner carousel


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
            "Ventaza Record" ->
            {val intent = Intent (this@MenuEngineeringRecord,VentazaRecordData::class.java)
            startActivity(intent)}
            "AC Record" ->
            {
                val intent = Intent (this@MenuEngineeringRecord,ACRecordData::class.java)
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
