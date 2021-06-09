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

class HKActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_h_k)
        val dataImportSales = listOf(
                DataAdapterHotelInformation(HeadTitle = "POOL AND GYM OPERATION",
                        BodyData = "Selama pandemi pool dan gym buka jam 06.00 am , tutup 06.00 pm"),
                DataAdapterHotelInformation(HeadTitle = "LAUNDRY PACKAGES",
                        BodyData = "Laundry Package Rp 76.000 per 5 pcs (semua item kecuali dry clean item)")

                

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