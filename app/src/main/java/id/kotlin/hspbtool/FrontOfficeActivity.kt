package id.kotlin.hspbtool

import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.kotlin.hspbtool.adapter.AdapterCardList
import id.kotlin.hspbtool.domain.DataAdapterHotelInformation

class FrontOfficeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataImportSales = listOf(
                DataAdapterHotelInformation(HeadTitle = "TIPE TIPE KAMAR",
                        BodyData =
                                "1. Deluxe Room, jumlah kamar 120, luas kamar 24m2 \n\n" +
                                "2. Premiere Room, jumlah kamar 30, luas kamar 28m2\n\n" +
                                "3. Executive Room, jumlah kamar, luas kamar 32m2\n\n" +
                                "4. Junior Suite Room, jumlah kamar 5, luas kamar 40m2 \n\n" +
                                "5. Suite Room, jumlah kamar 3, luas kamar 47m2 \n\n" +
                                "6. Premiere Suite Room, jumlah kamar 3, luas kamar 47m2"),
                DataAdapterHotelInformation(HeadTitle = "POINT OF INTEREST HOTEL SURROUNDING",
                        BodyData = "1. Kandank Jurank Doank \n" +
                                "Lokasi di Pondok Sawah, sekitar 20-30 menit dari hotel, menawarankan wisata edukasi untuk anak dan juga wisata alam \n\n"+
                                "2. Taman Wisata Situ Gintung \n" +
                                "Lokasi dapat di tempuh sekitar 30 menit dari hotel, tempat ini menawarkan wisata keindahan alam di tepi danau, terdapat juga fasilitas untuk keluarga, restaurant dan rekreasi outbound dengan tarif yang tidak begitu mahal, wisata ini cocok untuk keluarga \n\n" +
                                "3. Trans Snow Bintaro \n" +
                                "Lokasi di dalam Transpark Bintaro, berjarak hanya 500 meter dari hotel dan dapat di tempuh dengan berjalan kaki. Wisata ini menawarkan hiburan untuk keluarga, terutama anak.Trans snow ini adalah permainan yang di lakukan di atas salju. Di lokasi tersebut ada permainan lainnya juga \n\n" +
                                "4. Taman Jajan Bintaro \n" +
                                "Berlokasi di sekitaran hotel, dapat di tempuh dengan berjalan kaki sekitar 5-7 menit. Di tempat ini dapat di temukan berbagai kuliner dan parkirannya luas \n\n" +
                                "5. Pasar Modern Bintaro \n"+
                                "Berlokasi berada dekat dengan hotel, dapat di tempuh dengan berjalan kaki selama 5 menit. Di pasar modern bintaro akan menjumpai berbagai tenda-tenda yang menawarkan berbagai aneka makanan, terutama makanan khas nusantara")

        )
        setContentView(R.layout.activity_front_office)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title

        val mRecyclerView2: RecyclerView = findViewById(R.id.mRecyclerView)

        mRecyclerView2.setHasFixedSize(true)
        mRecyclerView2.layoutManager = LinearLayoutManager(this)

        val adapter = AdapterCardList(dataImportSales)
        adapter.notifyDataSetChanged()

        //tampilkan data dalam recycler view
        mRecyclerView2.adapter = adapter

        //tampilkan data dalam recycler view
        //mRecyclerView.adapter = adapter;


    }
}