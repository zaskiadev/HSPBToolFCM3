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
import id.kotlin.hspbtool.item.BannerListener
import id.kotlin.hspbtool.item.ProductCategoryItem
import id.kotlin.hspbtool.item.ProductItem
import id.kotlin.hspbtool.item.ProductListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.hotel_information_test.rvVentazaRecord

class EmployeInformationActivity : AppCompatActivity(), ProductListener {

    // declare adapter from groupadapter
    private var groupAdapter = GroupAdapter<ViewHolder>()
    //val rvMain: RecyclerView = findViewById(R.id.rvMain)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employe_information)

//        val promos = listOf(
//                BannerPromo(name = "Promo 1",
//                        image = "https://i.ibb.co/Nrf6y83/promo1.jpg "),
//                BannerPromo(
//                        name = "Promo 2",
//                        image = "https://i.ibb.co/3mY4jx9/promo2.jpg"
//                ),
//                BannerPromo(name = "Promo 3",
//                        image = "https://i.ibb.co/Px5PMbk/promo3.jpg"),
//                BannerPromo(
//                        name = "Promo 4",
//                        image = "https://i.ibb.co/n3G0fKH/promo4.jpg"
//                ),
//                BannerPromo(name = "Promo 5",
//                        image = "https://i.ibb.co/RzDcRmK/promo5.jpg"),
//                BannerPromo(
//                        name = "Promo 6",
//                        image = "https://i.ibb.co/wzdjsY2/promo6.jpg"
//                )
//        )

        val products = listOf(
                Product(name = "Memo",
                        img = R.drawable.sales_icon),
                Product(name = "Training",
                        img = R.drawable.front_office_icon),
                Product(name = "Data Keterlambatan",
                        img = R.drawable.front_office_icon),

        )



        rvVentazaRecord.apply {
            layoutManager = LinearLayoutManager(this@EmployeInformationActivity)
            adapter = groupAdapter
        }

        // declare banner carousel
//        val bannerCarouselItem = BannerCarouselItem(promos, supportFragmentManager, this)
//        groupAdapter.add(bannerCarouselItem)

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
            {val intent = Intent (this@EmployeInformationActivity,SalesMarketingActivity::class.java)
                startActivity(intent)}
            "Front Office" ->
            {
                val intent = Intent (this@EmployeInformationActivity,FrontOfficeActivity::class.java)
                startActivity(intent)
            }
            "F&B Service" ->
            {
                val intent = Intent (this@EmployeInformationActivity,FBServiceActivity::class.java)
                startActivity(intent)
            }
            "F&B Product" ->
            {
                val intent = Intent (this@EmployeInformationActivity,FBProductActivity::class.java)
                startActivity(intent)
            }
            "Engineering" ->
            {
                val intent = Intent (this@EmployeInformationActivity,EngineeringActivity::class.java)
                startActivity(intent)
            }
            "Housekeeping" ->
            {
                val intent = Intent (this@EmployeInformationActivity,HKActivity::class.java)
                startActivity(intent)
            }
            "HRD" ->
            {
                val intent = Intent (this@EmployeInformationActivity,HRDActivity::class.java)
                startActivity(intent)
            }
            "Accounting" ->
            {
                val intent = Intent (this@EmployeInformationActivity,AccountingActivity::class.java)
                startActivity(intent)
            }



        }

        //Toast.makeText(this, "clicked ${product.name}", Toast.LENGTH_SHORT).show()
    }


}
