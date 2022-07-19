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
import kotlinx.android.synthetic.main.hotel_information_test.rvVentazaRecord

class VentazaRecordData : AppCompatActivity(), ProductListener {

    // declare adapter from groupadapter
    private var groupAdapter = GroupAdapter<ViewHolder>()
    //val rvMain: RecyclerView = findViewById(R.id.rvMain)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventaza_record)



        val products = listOf(
                Product(name = "Add Record Ventaza",
                        img = R.drawable.add_icon),
                Product(name = "View Record Ventaza",
                        img = R.drawable.view_icon),
                Product(name = "Maintenance Ventaza Device",
                        img = R.drawable.add_icon),
            Product(name = "Log Ventaza",
                img = R.drawable.log_icon),

        )



        rvVentazaRecord.apply {
            layoutManager = LinearLayoutManager(this@VentazaRecordData)
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
            "Add Record Ventaza" ->
            {val intent = Intent (this@VentazaRecordData,AddRecordActivityVentaza::class.java)
            startActivity(intent)}
            "View Record Ventaza" ->
            {
                val intent = Intent (this@VentazaRecordData,VentazaFilterShowData::class.java)
                    startActivity(intent)
            }
            "Log Ventaza" ->
            {
                val intent = Intent (this@VentazaRecordData,LogRecordActivity::class.java)
                startActivity(intent)
            }
            "Maintenance Ventaza Device" ->
            {
                val intent = Intent (this@VentazaRecordData,AddRecordActivityVentazaMaintenance::class.java)
                startActivity(intent)
            }
        }

        //Toast.makeText(this, "clicked ${product.name}", Toast.LENGTH_SHORT).show()
    }


}
