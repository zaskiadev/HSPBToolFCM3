package id.kotlin.hspbtool

import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import id.kotlin.hspbtool.domain.DataAdapterHotelInformation
import androidx.recyclerview.widget.LinearLayoutManager
import id.kotlin.hspbtool.adapter.AdapterCardList

class SalesMarketingActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataImportSales = listOf(
                DataAdapterHotelInformation(HeadTitle = "PUBLISH RATE ROOM 2021",
                        BodyData = "Deluxe Room = Rp. 1,700,000 \n" +
                                "Premiere Room = Rp. 1,875,000 \n" +
                                "Executive Room = Rp. 1,950,000 \n" +
                                "Junior Suite Room = Rp. 2,200,000 \n" +
                                "Suite Room = Rp. 2,500,000 \n" +
                                "Premiere Suite Room = Rp. 3,050,000"),
                DataAdapterHotelInformation(HeadTitle = "PUBLISH RATE MEETING PACKAGE 2021",
                        BodyData = "HALF DAY MEETING \n" +
                                "RP 395,000/pax (1x snack dan 1x makan) - 4 jam \n\n" +
                                "FULL DAY MEETING \n" +
                                "RP 475,000/pax (2x snack dan 1x makan) - 8 jam \n\n" +
                                "FULL BOARD MEETING \n" +
                                "RP 635,000/pax (2x snack dan 2x makan) - 12 jam \n\n" +
                                "WHOLE DAY MEETING \n" +
                                "RP 695.000/pax (3x snack dan 3x makan) - 12 jam"),
                DataAdapterHotelInformation(
                        HeadTitle = "PUBLISH RATE SOCIAL EVENT 2021",
                        BodyData = "LUNCH/DINNER PACKAGE\n" +
                                "RP 350.000/pax - 4 jam event at function room\n\n" +
                                "AKAD NIKAH PACKAGE\n" +
                                "RP 350,000/pax\n\n" +
                                "INTIMATE WEDDING PACKAGE\n" +
                                "RP 70,000,000.00 for 200 pax"),
                DataAdapterHotelInformation(HeadTitle = "MINIMUM PAX",
                        BodyData = "Kembang Sepatoe 30 pax\n\n"+
                                "Azalea 30 pax (social event)\n\n" +
                                "Edelweis Ballroom 120 pax \n\n" +
                                "Edelweis Ballroom 150 pax (setup new normal)\n\n"+
                                "Raflesia 1-4 10 pax\n\n" +
                                "Raflesia 5 20 pax\n\n" +
                                "Raflesia 6 15 pax")

        )
        setContentView(R.layout.activity_sales_marketing)
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
    /*
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val textViewST : TextView = findViewById(R.id.scrollText)
        textViewST.text="PUBLISH RATE ROOM 2021 \n\nDeluxe Room               Rp. 1,700,000 \n"+
                                                    "Premiere Room            Rp. 1,875,000 \n"+
                                                    "Executive Room           Rp. 1,950,000 \n"+
                                                    "Junior Suite Room        Rp. 2,200,000 \n"+
                                                    "Suite Room               Rp. 2,500,000 \n"+
                                                    "Premiere Suite Room Rp. 3,050,000 \n\n\n"+
                        "PUBLISH RATE MEETING PACKAGE 2021 \n\n"+
                        "HALF DAY MEETING \n"+
                        "RP 395,000/pax (1x snack dan 1x makan) - 4 jam \n\n"+
                        "FULL DAY MEETING \n"+
                        "RP 475,000/pax (2x snack dan 1x makan) - 8 jam \n\n"+
                        "FULL BOARD MEETING \n"+
                        "RP 635,000/pax (2x snack dan 2x makan) - 12 jam \n\n"+
                        "WHOLE DAY MEETING \n"+
                        "RP 695.000/pax (3x snack dan 3x makan) - 12 jam \n\n\n"+
                        "PUBLISH RATE SOCIAL EVENT 2021\n\n"+
                        "LUNCH/DINNER PACKAGE\n"+
                        "RP 350.000/pax - 4 jam event at function room\n\n"+
                        "AKAD NIKAH PACKAGE\n"+
                        "RP 350,000/pax\n\n"+
                        "INTIMATE WEDDING PACKAGE\n"+
                        "RP 70,000,000.00 for 200 pax\n\n\n"+
                        "MINIMUM PAX\n\n"+
                        "Kembang Sepatoe 30 pax\n"+
                        "Azalea 30 pax (social event)\n\n"+
                        "Edelweis Ballroom 120 pax \n"+
                        "Edelweis Ballroom 150 pax (setup new normal) \n\n"+
                        "Raflesia 1-4 10 pax\n"+
                        "Raflesia 5 20 pax\n"+
                        "Raflesia 6 15 pax"

*/



}