package id.kotlin.hspbtool

//import androidx.appcompat.widget.Toolbar
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
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
import id.kotlin.hspbtool.adapter.AdaptarCardListViewLogRecordMaintenanceVentaza
import id.kotlin.hspbtool.domain.LogRecordMaintenanceVentaza
import kotlinx.android.synthetic.main.activity_log_record.*
import kotlinx.android.synthetic.main.hotel_information_test.rvVentazaRecord
import org.json.JSONObject
import java.io.Console
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class hotel_information_test_activity : AppCompatActivity(), HeroListener, BannerListener, ProductListener {

    // declare adapter from groupadapter
    private var groupAdapter = GroupAdapter<ViewHolder>()
    //val rvMain: RecyclerView = findViewById(R.id.rvMain)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hotel_information_test)

        val test:MutableList<BannerPromo> = mutableListOf()
        val bannerCarouselItem = BannerCarouselItem(test, supportFragmentManager, this, this@hotel_information_test_activity)
        groupAdapter.add(bannerCarouselItem)

        AndroidNetworking.post(ApiEndPoint.list_banner)
           // .addBodyParameter("filter_by", textSpinnerSelected)
            //.addBodyParameter("room_number", editTextTextRoomNumberShowLog.text.toString())
            .setPriority(Priority.IMMEDIATE)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {



                    val jsonArray = response?.optJSONArray("result")

                    if (jsonArray?.length() == 0) {
                        //loading.dismiss()

                    }
                    var index=0
                    for (i in 0 until jsonArray?.length()!!) {

                        var user: String?;
                        user = "";
                        val jsonObject = jsonArray?.optJSONObject(i)

                        try {
                            if (jsonObject.getString("user") != "") {
                                user = jsonObject.getString("user")
                            }

                        } catch (e: Exception) {

                        }

                    var namaPromo : String
                    var ImagePromo : String
                    namaPromo=jsonObject.getString("nama_promo").replace("_"," ")

                        ImagePromo="http://103.84.233.186:8742/hspb_tool/image_promo/"+jsonObject.getString("nama_promo")+
                                "_"+
                                jsonObject.getInt("bulan")+"_"+jsonObject.getInt("tahun")+".jpg"


                        //Log.e("Image Promo : ",ImagePromo)
                        //Log.e("Nama Promo : ", namaPromo)
                        test.add(index,BannerPromo(namaPromo,ImagePromo))
                        //index++




                        if (jsonArray?.length() - 1 == i) {

                        }


                    }

                Log.e("Jumlah test di JSON : ",""+test.size)
                }

                override fun onError(anError: ANError?) {

                    // Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Koneksi Error",Toast.LENGTH_LONG).show()


                }
            })

        /*val promos = listOf(
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
        )*/
        Log.e("Jumlah Test di luar: ",""+test.size)
        //test.add(0,
        //BannerPromo(name="",image="")
          //  )


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

    override fun onBannerClick(banners: List<BannerPromo>,positionBanner:Int) {

        Log.d("Position : ",positionBanner.toString())
        Log.d("Jumlah List Banner Promo : ",""+banners?.size)

        var sharedPref : SharedPreferences
        sharedPref=getSharedPreferences("PromoNumber", Context.MODE_PRIVATE)
        var sharedPrefEditor= sharedPref.edit()

        sharedPrefEditor.putString("PromoNumber",banners?.get(positionBanner).name)
        sharedPrefEditor.apply()


        sharedPref=getSharedPreferences("imagePromoDetail", Context.MODE_PRIVATE)
        sharedPrefEditor= sharedPref.edit()

        sharedPrefEditor.putString("imagePromoDetail",banners?.get(positionBanner).image)
        sharedPrefEditor.apply()
        val intent2 = Intent (this@hotel_information_test_activity,ViewDetailPromoActivity::class.java)
        startActivity(intent2)

        /*when(promo.name) {
            "Promo 1" -> {
                Toast.makeText(applicationContext, "Promo 1", Toast.LENGTH_SHORT).show()

                val intent =
                    Intent(this@hotel_information_test_activity, ViewDetailPromoActivity::class.java)

                var sharedPref : SharedPreferences
                sharedPref=getSharedPreferences("PromoNumber", Context.MODE_PRIVATE)
                var sharedPrefEditor= sharedPref.edit()

                sharedPrefEditor.putString("PromoNumber","Promo 1")
                sharedPrefEditor.apply()


                startActivity(intent)
            }
            "Promo 2" -> {
                val intent =
                    Intent(this@hotel_information_test_activity, ViewDetailPromoActivity::class.java)
                var sharedPref : SharedPreferences
                sharedPref=getSharedPreferences("PromoNumber", Context.MODE_PRIVATE)
                var sharedPrefEditor= sharedPref.edit()

                sharedPrefEditor.putString("PromoNumber","Promo 2")
                sharedPrefEditor.apply()

                startActivity(intent)
            }
            "Promo 3" -> {
                val intent =
                    Intent(this@hotel_information_test_activity, ViewDetailPromoActivity::class.java)
                var sharedPref : SharedPreferences
                sharedPref=getSharedPreferences("PromoNumber", Context.MODE_PRIVATE)
                var sharedPrefEditor= sharedPref.edit()

                sharedPrefEditor.putString("PromoNumber","Promo 3")
                sharedPrefEditor.apply()
                startActivity(intent)
            }
            "Promo 4" -> {
                val intent =
                    Intent(this@hotel_information_test_activity, ViewDetailPromoActivity::class.java)
                var sharedPref : SharedPreferences
                sharedPref=getSharedPreferences("PromoNumber", Context.MODE_PRIVATE)
                var sharedPrefEditor= sharedPref.edit()

                sharedPrefEditor.putString("PromoNumber","Promo 4")
                sharedPrefEditor.apply()

                startActivity(intent)
            }
            "Promo 5" -> {
                val intent =
                    Intent(this@hotel_information_test_activity, ViewDetailPromoActivity::class.java)
                var sharedPref : SharedPreferences
                sharedPref=getSharedPreferences("PromoNumber", Context.MODE_PRIVATE)
                var sharedPrefEditor= sharedPref.edit()

                sharedPrefEditor.putString("PromoNumber","Promo 5")
                sharedPrefEditor.apply()

                startActivity(intent)
            }
            "Promo 6" -> {
                val intent = Intent(this@hotel_information_test_activity, ViewDetailPromoActivity::class.java)

                var sharedPref : SharedPreferences
                sharedPref=getSharedPreferences("PromoNumber", Context.MODE_PRIVATE)
                var sharedPrefEditor= sharedPref.edit()

                sharedPrefEditor.putString("PromoNumber","Promo 6")
                sharedPrefEditor.apply()
                startActivity(intent)
            }

        }*/
    }

    override fun onHeroClick(hero: Hero) {
        Toast.makeText(this, "hero clicked ${hero.name}", Toast.LENGTH_SHORT).show()
    }
}
