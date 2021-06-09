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

class FBServiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataImportSales = listOf(
                DataAdapterHotelInformation(HeadTitle = "EXTENSION PHONE NUMBER",
                        BodyData = "Extension Number Kembang Sepatoe Restaurant adalah 6"),
                DataAdapterHotelInformation(HeadTitle = "JAM BUKA KEMBANG SEPATORE RESTAURNT",
                        BodyData = "1. Sarapan \n" +
                                "06.00 - 10.00 (Weekday) \n" +
                                "06.00 - 10.30 (Weekend) \n\n" +
                                "2. Makan Siang \n" +
                                "12.00 - 14.30 (Weekday) \n" +
                                "12.00 - 15.00 (Weekend)\n\n" +
                                "3. Makan Malam\n" +
                                "18.00 - 22.00"),
                DataAdapterHotelInformation(
                        HeadTitle = "HARGA BUFFEE KEMBANG SEPATOE RESTAURANT",
                        BodyData = "1. Harga Buffee Breakfast\n" +
                                "Dewasa = Rp. 179.000 net\n" +
                                "Anak 6-11 Tahun = Rp. 100.000 net\n\n" +
                                "2. Harga Buffe Lunch dan Dinner\n" +
                                "Dewasa = Rp. 235.000 net \n" +
                                "Anak 6-11 Tahun = Rp. 118.000 net"),
                DataAdapterHotelInformation(HeadTitle = "JENIS - JENIS BREAKFAST",
                        BodyData = "1. Continental Breakfast\n" +
                                "- Coffee, tea, hot chocolate atau susu \n" +
                                "- Jus \n" +
                                "- Yogurt \n" +
                                "- Cereal \n" +
                                "- Cold Cut dan Cheese \n" +
                                "- Croisant, brioche, toast/french toast, roti \n" +
                                "- Jam, marmalade, butter atau coklat \n\n" +
                                "2. English Breakfast \n" +
                                "- Coffee, tea, hot chocolate atau susu \n" +
                                "- Jus \n" +
                                "- Yogurt \n" +
                                "- Fruit/ buah \n" +
                                "- Cereal, oatmeal, porridge \n" +
                                "- Macam-macam telor dengan toast, sosis, ham, bacon, grill tomat, jamur, baked bean, kentang \n" +
                                "- Jam, marmalade, butter atau coklat \n\n" +
                                "3. American Breakfast \n" +
                                "- Coffee, tea (caffenaited atau decaffeinated), hot chocolate atau susu \n" +
                                "- Jus \n" +
                                "- Yogurt \n" +
                                "- Fruit/ buah \n" +
                                "- Salmon dengan bagel dan cream cheese \n" +
                                "- Wafel, pancake, donat \n" +
                                "- Macam-macam telor dengan bacon, ham, kentang, dll \n" +
                                "- Muffin, croissant, brioche, toast, roti\n" +
                                "- Jam, maramalade, butter atau coklat\n\n" +
                                "4. Indonesian Breakfast \n" +
                                "- Coffee, tea, hot chocolate atau susu \n" +
                                "- Jus \n" +
                                "- Yogurt \n" +
                                "- Fruit/ buah \n" +
                                "- Pilihan makanan indonesia (Nasi Goreng, Mie Goreng atau Bubur Ayam"),
                DataAdapterHotelInformation(
                        HeadTitle = "JENIS JENIS TELUR DIPAGI HARI",
                        BodyData = "1. Boiled Egg\n" +
                                "Boiled egg terdapat 3 pilihan yaitu di rebus soft (3menit), medium (5menit), hard (8menit) akan di sajikan dengan egg stand\n\n" +
                                "2. Scramble Egg\n" +
                                "Telur acak dengan pilihan disajikan dengan kentang, sosis, dll\n\n" +
                                "3. Fried Egg \n" +
                                "Ada beberapa pilihan memasaknya\n" +
                                "- Fried Egg sunny side up dengan kuning telur yang masih mentah\n" +
                                "- Fried Egg sunny side down/turn over telur yang sudah di balik mata kuningnya ikut termasak. Ada pilihan turn over soft, medium, well done\n" +
                                "4. Omellete \n" +
                                "Telur dadar dengan banyak pilihan untuk isinya seperti cheese, tomat, onion, dll \n\n" +
                                "5. Poached Egg\n" +
                                "Telur yang di rebus dengan sedikit air asam tanpa kulit telurnya. Biasanya disajikan dengan toast\n\n"+
                                "6. Egg Benedict\n"+
                                "Poached Egg yang diletakkan diatas 2 lembar english muffin, canadian ham/bacon dengan saos Hollandaise (kuning telur dan butter)"),
                        DataAdapterHotelInformation(
                        HeadTitle = "NAMA NAMA MEETING ROOM",
                BodyData = "1. Azalea 1-3\n" +
                        "2. Kembang Sepatoe 1 dan 2\n" +
                        "3. Edelweis Ballroom 1-4\n" +
                        "4. Raflesia 1-6"
                        ),
                DataAdapterHotelInformation(
                        HeadTitle = "JENIS SETUP DI BANQUET EVENT",
                        BodyData = "1. Classroom\n" +
                                "2. Round Table\n" +
                                "3. Theatre Style\n" +
                                "4. U-Shape\n" +
                                "5. V-Shape\n"+
                                "6. Block Table\n"+
                                "7. Board Room Style\n\n"+
                                "Ukuran single stage di Banquet = 1.2 x 2.4 dengan tinggi 40cm atau 60cm\n"+
                                "Kepanjangan dari BEO adalah Banquet Event Order"

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