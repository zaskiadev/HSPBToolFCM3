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

class EngineeringActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_engineering)
        val dataImportSales = listOf(
                DataAdapterHotelInformation(HeadTitle = "GENSET DAN SOLAR",
                        BodyData = "1. Genset\n"+
                "- Genset merk = MAN\n"+
                                "- Jumlah = 2 unit\n"+
                                "- Kapasitas = 500 KVA\n\n"+

                "2. Solar \n" +
                 "- Kapasitas tangki = 12.000 Liter\n"+
                "- Daily tank solar = 1000 liter\n"+
                "- Daya tarik listrik PLN = 1385\n"+
                "- Daya trafo = 1600"),

                DataAdapterHotelInformation(HeadTitle = "LIFT DAN ESKALATOR",
                        BodyData = "1. Guest Lift \n" +
                                "- Merk = Kone \n" +
                                "- Jumlah lift = 2 \n" +
                                "- Kapasitas = 1000 KG \n" +
                                "- P x L x T = 140 x 160 x 240 \n\n" +
                                "2. Lift Ballroom\n" +
                                "- Merk = Kone\n" +
                                "- Jumlah lift = 2 \n"+
                                "- Kapasitas = 1000 KG \n"+
                                "- P x L x T = 140 x 160 x 240 \n\n"+
                                "3. Lift Service\n"+
                                "- Merk = Kone \n" +
                                "- Jumlah = 1 \n"+
                                "- Kapasitas = 1000 KG \n"+
                                "- P x L x T = 200 x 112 x 240\n\n"+
                                "4. Eskalator \n"+
                                "- Merk = Schindler \n"+
                                "- Jumlah = 1\n"+
                                "- Jangkauan = LG Floor ke G Floor"),
                DataAdapterHotelInformation(
                        HeadTitle = "TV, Internet dan Telpon",
                        BodyData = "1. TV\n" +
                                "- Merk = Samsung\n" +
                                "- Provider = Transvision\n" +
                                "- Jumlah channel = 32 ch\n"+
                                "- Sistem TV = MA Digital TV\n\n"+
                                "2. Internet\n" +
                                "- Provider = Maxindo (Office) dan Fibernet (Guest)\n" +
                                "- Kapasitas = Maxindo 30MB dan Fibernet 100MB\n\n"+
                                "3. Telpon\n"+
                                "- Provider = Telkom \n"+
                                "- Sistem Telpon = PABX Transtell "),

                DataAdapterHotelInformation(HeadTitle = "STP dan AIR",
                        BodyData = "1. STP\n" +
                                "Menggunakan sistem extended airation, sebagian hasil pengoalahan air di manfaatkan untuk menyiram tanaman\n\n" +
                                "2. Sumber Air Bersih \n" +
                                "Sumber air bersih menggunakan PDAM Jaya Real Property dengan minimum pemakaian 1500 M\u00B3 \n\n" +
                                "3. Deepweel (Air Tanah) \n" +
                                "Kapasitas Roff Tank 32 M\u00B3 \n\n" +
                                "4. Air Panas \n" +
                                "- Storage tank = 2 unit (6000 liter/unit) \n" +
                                "- Suhu Air 55\u2103 - 70\u2103 \n" +
                                "- Pemanas menggunakan Heat Pump Solarhart type 28kw \n\n"
                               ),
                DataAdapterHotelInformation(
                        HeadTitle = "COOLING SYSTEM",
                        BodyData = "1. Merk Chiller\n" +
                                "Chiller menggunakan merk Frimec dengan kapasitas 150TR\n\n" +
                                "2. Jumlah Unit\n" +
                                "Mempunyai 3 Unit Water Cooling System"
                                ),
                DataAdapterHotelInformation(
                        HeadTitle = "KOLAM RENANG",
                        BodyData = "1. Kedalaman kolam dewasa adalah 120CM\n" +
                                "2. Kedalaman kolam anak anak adalah 60CM\n"
                ),
                DataAdapterHotelInformation(
                            HeadTitle = "LUAS TANAH",
                        BodyData = "Luas tanah HSP Bintaro adalah 4002 m2"

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