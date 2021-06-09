package id.kotlin.hspbtool

import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.kotlin.hspbtool.adapter.AdapterCardList
import id.kotlin.hspbtool.domain.DataAdapterHotelInformation

class FBProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_f_b_product)
        val dataImportSales = listOf(
                DataAdapterHotelInformation(HeadTitle = "MENU DAN PENYAJIAN MAKANAN",
                        BodyData = "1. Ala Carte\n"+
                                    "Susunan menu, dimana setiap makanan sudah di sertai dengan harga\n\n"+
                "2. Buffet\n"+
                "Cara penyajian makanan, dimana makanan sudah di susun di meja dan tamu dapat mengambil sendiri sesuai keinginan\n\n"+
                "3. Tabble Manner\n"+
                "Suatu susunan makanan lengkap terdiri dari Appetizer, Soup, Main Course sampai Dessert dalam satu harga yang sudah di siapkan"),
                DataAdapterHotelInformation(HeadTitle = "METODE OF COOKING",
                        BodyData = "1. Boiling\n" +
                                "Memasak dengan menggunakan air dengan suhu 100 derajat celcius\n\n" +
                                "2. Steaming\n" +
                                "Memasak dengan menggunakan uap panas \n\n" +
                                "3. Frying \n" +
                                "Memasak menggunakan minyak\n\n" +
                                "4. Blancing\n" +
                                "Memasak menggunakan air mendidih dengan waktu yang singkat\n\n"+
                                "5. Poaching\n"+
                                "Memasak menggunakan air dengan suhu 70\u2103 sampai 80\u2103 \n\n"+
                                "6. Simmering\n"
                                +"Memasak dengan air yang menggunakan api kecil dalam waktu yang lama\n\n"+
                                "7. Stewing\n"+
                                "Memasak dengan kaldu/sauce dalam waktu yang lama\n\n"+
                                "8. Sautering\n"+
                                "Memasak dengan sedikit minyak")
        )
        setContentView(R.layout.activity_f_b_service)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title

        val mRecyclerView2: RecyclerView = findViewById(R.id.mRecyclerView)

        mRecyclerView2.setHasFixedSize(true)
        mRecyclerView2.layoutManager = LinearLayoutManager(this)

        val adapter = AdapterCardList(dataImportSales)
        adapter.notifyDataSetChanged()

        //tampilkan data dalam recycler view
        mRecyclerView2.adapter = adapter
    }
}