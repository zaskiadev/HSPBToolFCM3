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

class AccountingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accounting)
        val dataImportSales = listOf(
            DataAdapterHotelInformation(HeadTitle = "GOP",
                BodyData = "Tolak ukur suatu perusahaan yang berhubungan dengan keuangan, untuk memastikan perusahaan tersebut dapat berjalan dengan baik atau tidak"),

            DataAdapterHotelInformation(HeadTitle = "ACCOUNT RECEIVABLE (AR)",
                BodyData = "Merupakan catatan transaksi yang menjadi dasar kita menerima uang. Dalam bahasa sehari-hari, account receivable juga dikenal dengan istilah piutang usaha"+
            ". Account Receivable merupakan jenis transaksi yang merupakan pengertian penagihan kepada konsumen yang telah berutang"),

            DataAdapterHotelInformation(
                HeadTitle = "ACCOUNT PAYABLE (AP)",
                BodyData = "Dapat diartikan sebagai kewajiban pembayaran dari sebuah perusahaan kepada pihak lain (supplier maupun vendor)"+
                        " yang harus segera dipenuhi dalam jangka waktu yang telah di tentukan oleh pihak lain yang diajak bekerjasama oleh"+
                        " perusahaan tersebut"
            ),

            DataAdapterHotelInformation(HeadTitle = "EXPENSE (BEBAN)",
                BodyData = "Merupakan suatu penurunan pada nilai ekonomi sebagai kas keluar atau aktiva yang berkurang."+
                        " Expense ini dianggap sebagai kewajiban karena dapat menyebabkan nilai ekuitas menurun."+
                        " Biasanya juga, dianggap sebagai pengorbanan yang sudah terjadi."
            ),
            DataAdapterHotelInformation(
                HeadTitle = "COST (BIAYA)",
                BodyData = "Biaya merupakan pengeluaran yang dilakukan untuk memproduksi barang maupun jasa agar mendapatkan "+
                        "keuntungan atau mendapatkan manfaat yang memiliki nilai ekonomis di masa depan."
            ),
            DataAdapterHotelInformation(
                HeadTitle = "REVENUE",
                BodyData = "Jumlah uang yang diterima oleh perusahaan atau organisasi dari kegiatan aktivitasnya seperti "+
                        "penjualan produk dan atau jasa kepada pelanggan."
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