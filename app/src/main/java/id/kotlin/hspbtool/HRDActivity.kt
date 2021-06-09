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

class HRDActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_h_r_d)
        //setSupportActionBar(findViewById(R.id.toolbar))
        val dataImportSales = listOf(
            DataAdapterHotelInformation(HeadTitle = "VISI SANTIKA INDONESIA",
                BodyData = "Menjadi jaringan hotel pilihan utama yang terbesar di Indonesia dan tersebar di Asia Tenggara"),

            DataAdapterHotelInformation(HeadTitle = "MISI SANTIKA INDONESIA",
                BodyData = "Menciptakan nilai lebih bagi stake holders dengan menyajikan produk bermutu, disertai pelayanan proffesional yang ramah dalam mewujudkan 'Sentuhan Indonesia' sebagai citra Santika"),

            DataAdapterHotelInformation(
                HeadTitle = "NILAI - NILAI KOMPAS GRAMEDIA (5C)",
                BodyData = "1. Caring\n" +
                        "2. Credible\n" +
                        "3. Competent\n" +
                        "4. Competitive\n"+
                        "5. Costumer Delight"
                        ),

            DataAdapterHotelInformation(HeadTitle = "NILAI SANTIKA",
                BodyData = "1. Syukur kepada Tuhan Yang Maha Esa\n"+
                        "2. Profesionalisme\n"+
                        "3. Kejujuran\n"+
                        "4. Kedisiplinan\n"+
                        "5. Keterbukaan\n"+
                        "6. Kebersamaan\n"+
                        "7. Tanggung Jawab Sosial"
            ),
            DataAdapterHotelInformation(
                HeadTitle = "ASPEK STANDAR GROOMING",
                BodyData = "1. Kesehatan Diri\n" +
                        "2. Tata Rias Wajah\n" +
                        "3. Tata Rias Rambut\n" +
                        "4. Uniform\n"+
                        "5. Perhiasan\n"+
                        "6. Accesories"
            ),
            DataAdapterHotelInformation(
                HeadTitle = "INDONESIAN HOME DAN BASIC OF SERVICE",
                BodyData = "1. Mengucapkan salam panjalu, memberi salam kepada tamu secara hangat dan tulus\n" +
                        "2. Menggunakan atau memanggil nama tamu\n"+
                        "3. Mengenali dan mencatat kesukaan spesifik tamu\n"+
                        "4. Selalu tersenyum dan memelihara kontak mata dengan tamu\n"+
                        "5. Peduli dengan kebersihan semua area"
            ),
            DataAdapterHotelInformation(
                HeadTitle = "DASAR PENANGANAN KELUHAN TAMU",
                BodyData = "1. Dengarkan\n"+
                        "2. Perlihatkan simpati \n"+
                        "3. Jangan menilai benar atau salah"+
                        "4. Setuju dan minta maaf\n"+
                        "5. Tanyakan pertanyaan-pertanyaan\n"+
                        "6. Mengambil keputusan\n"+
                        "7. Tanggapan tindakan selanjutnya"

            ),
            DataAdapterHotelInformation(
                HeadTitle = "",
                BodyData = "Pendiri Kompas Gramedia = P.K Ojong dan Jacoeb Oetama\n\n"+
                        "CEO Kompas Gramedia = Liliek Oetama\n\n"+
                        "Owner Hotel Santika Premiere Bintaro = Bapak Hutomo Mugi Santoso dan Ibu Suzy Hutomo"

            )

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