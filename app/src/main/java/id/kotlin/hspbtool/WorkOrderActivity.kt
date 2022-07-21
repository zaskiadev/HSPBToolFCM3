package id.kotlin.hspbtool

import android.Manifest
import java.util.Timer
import kotlin.concurrent.schedule
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build.*
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_add_maintenance_ac.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_work_order.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

typealias LumaListener = (luma: Double) -> Unit

class WorkOrderActivity : AppCompatActivity() {
    private lateinit var cameraExecutor: ExecutorService
    private val REQUEST_PICK_IMAGE = 2
    var ivImage: ImageView? = null
    // var txtUri:TextView?=null
    var imageUri: Uri?=null
    var filePath:String?=""
    var codeWoFromSelect:String=""
    var nextCodeWo:String=""
    var codeWoDetailFromSelect:String=""
    var nextCodeWoDetail:String=""
    var codeMaintenanceSelect:String=""
    var nextCodeMaintenance:String=""

    var selectCodeBarang:String=""
    var nextCodeBarang:String=""


    var serverURL: String = "http://103.84.233.186:8742/hspb_tool/upload_workorder_image.php"
    var serverUploadDirectoryPath: String = "http://103.84.233.186:8742/hspb_tool/image_work_order"
    val client = OkHttpClient()
    var codeBarang:String?=""
    var namaBarang:String?=""
    var Location:String?=""
    var departement:String?=""
    var fileName:String=""
    var chooseFrom: String?="Gallery"
    var codeDepartement:String=""
    var intentFrom:String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_order)
        val arrayWorkOrderType = resources.getStringArray(R.array.work_order_type)
        val spinnerWorkOrderType = findViewById<Spinner>(R.id.spinnerWorkOrderType)
        val arrayPriority = resources.getStringArray(R.array.work_order_priority)
        val arrayDepartement=resources.getStringArray(R.array.work_order_departement)
        val spinnerWorkOrderPriority = findViewById<Spinner>(R.id.spinnerPriority)

        val spinnerWorkOrderDepartement = findViewById<Spinner>(R.id.spinnerDepartement)
        var textSpinnerDepartement=""
        var textSpinnerWorkOderType=""
        var textSpinnerWorkOrderPriority=""
        val buttonChooseGallery= findViewById<Button>(R.id.buttonChooseFromGallery)
        val buttonTakePhoto= findViewById<Button>(R.id.buttonTakeAgainPictureWorkOrder)
        val buttonSendWO= findViewById<Button>(R.id.buttonUseThisPicture)
        val buttonSearchIteme= findViewById<Button>(R.id.buttonSearchItemWorkOrder)
        this.ivImage=findViewById(R.id.imageTakedWorkOrder)



        val adapterWorkOrderType = ArrayAdapter(this,
            R.layout.spinner_item_work_order, arrayWorkOrderType)
        spinnerWorkOrderType.adapter = adapterWorkOrderType

        val adapterWorkOrderPriority = ArrayAdapter(this,
            R.layout.spinner_item_work_order, arrayPriority)
        spinnerWorkOrderPriority.adapter = adapterWorkOrderPriority

        spinnerWorkOrderDepartement.adapter=ArrayAdapter(this,R.layout.spinner_item_work_order,arrayDepartement)

        getcodeWo()
        getcodeWoDetail()
        GetDataMaintenance()
        getCodeBarang()
        spinnerWorkOrderType.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                textSpinnerWorkOderType=arrayWorkOrderType[position]


            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        spinnerWorkOrderPriority.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                textSpinnerWorkOrderPriority=arrayPriority[position]


            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        spinnerWorkOrderDepartement.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                textSpinnerDepartement=arrayDepartement[position]


            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        //checkSharedPreferences()
        cameraExecutor = Executors.newSingleThreadExecutor()
        buttonChooseGallery.setOnClickListener {
            openGallery()

        }
        buttonTakePhoto.setOnClickListener {
            openCamera()
            chooseFrom="Photo"
        }

        buttonSearchIteme.setOnClickListener {
            val sharedPref : SharedPreferences
            sharedPref=getSharedPreferences("itemName", Context.MODE_PRIVATE)
            val sharedPrefEditor= sharedPref.edit()
            sharedPrefEditor.putString("itemName",editTextWorkOrderItemName.text.toString())
            sharedPrefEditor.apply()
            val intent = Intent(this@WorkOrderActivity, ViewSearchItemForWorkOrder::class.java)
            startActivity(intent)
        }


        buttonSendWO.setOnClickListener{
            //for sending image



                if (chooseFrom == "Photo") {

                    uploadFile(File(imageUri!!.path))
                    //Toast.makeText(applicationContext,"Send WO Sucess (Image From Photo)", Toast.length_LONG).show()
                    this.finish()
                    chooseFrom = ""

                } else {

                    val REQUEST_EXTERNAL_STORAGE = 1
                    val PERMISSIONS_STORAGE = arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    val permission = ActivityCompat.checkSelfPermission(
                        this@WorkOrderActivity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )

                    if (permission != PackageManager.PERMISSION_GRANTED) {
                        // We don't have permission so prompt the user
                        ActivityCompat.requestPermissions(
                            this@WorkOrderActivity,
                            PERMISSIONS_STORAGE,
                            REQUEST_EXTERNAL_STORAGE
                        )
                    }
                    uploadFileString(filePath!!)
                    chooseFrom = ""
                    //Toast.makeText(applicationContext, "Send WO Success (Image From Gallery)", Toast.length_LONG).show()
                    this.finish()
                }

                //sendWO()

        }



    }

    fun checkSharedPreferences()
    {
        var sharedPref=   getSharedPreferences("chooseFoto", Context.MODE_PRIVATE)
        chooseFrom = sharedPref.getString("chooseFoto",null)

        if(chooseFrom=="Photo") {
            sharedPref = getSharedPreferences("url", Context.MODE_PRIVATE)
            var url: String = sharedPref.getString("url", null).toString()


            if (url != null) {
                imageUri = Uri.parse(url)
                ivImage!!.setImageURI(imageUri)

                // txtUri!!.text=imageUri.toString()
            }
        }

        sharedPref=   getSharedPreferences("intentFrom", Context.MODE_PRIVATE)
        intentFrom = sharedPref.getString("intentFrom",null)
        var stringItem:String=""
        if(intentFrom=="itemSelectWorkOrder") {

            sharedPref = getSharedPreferences("CodeBarang", Context.MODE_PRIVATE)
            codeBarang = sharedPref.getString("CodeBarang", null).toString().replace("Item Code = ","")

            //Log.e("Code barangnya nih kiriman dari shared pref",codeBarang)
            sharedPref = getSharedPreferences("namaBarang", Context.MODE_PRIVATE)
            namaBarang = sharedPref.getString("namaBarang", null)

            sharedPref = getSharedPreferences("location", Context.MODE_PRIVATE)
            Location = sharedPref.getString("location", null)

            sharedPref = getSharedPreferences("departement", Context.MODE_PRIVATE)
            departement = sharedPref.getString("departement", null)

            editTextWorkOrderItemName.setText(namaBarang.toString().replace("Item Name = ",""))
            editTextLocationWorkOrder.setText(Location.toString().replace("Location = ",""))



        }
    }
    override fun onResume() {
        super.onResume()
        checkSharedPreferences()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        clearSharedPref()
    }


    fun clearSharedPref()
    {
        var sharedPref=getSharedPreferences("codeBarang",Context.MODE_PRIVATE)
        var sharedPrefEditor=sharedPref.edit()
        sharedPrefEditor.putString("codeBarang",  null)
        sharedPrefEditor.apply()

        sharedPref=getSharedPreferences("namaBarang",Context.MODE_PRIVATE)
        sharedPrefEditor=sharedPref.edit()
        sharedPrefEditor.putString("namaBarang",null)
        sharedPrefEditor.apply()

        sharedPref=getSharedPreferences("location",Context.MODE_PRIVATE)
        sharedPrefEditor=sharedPref.edit()
        sharedPrefEditor.putString("location",null)
        sharedPrefEditor.apply()

       /* sharedPref=getSharedPreferences("departement",Context.MODE_PRIVATE)
        sharedPrefEditor=sharedPref.edit()
        sharedPrefEditor.putString("departement",null)
        sharedPrefEditor.apply()*/

        sharedPref=getSharedPreferences("chooseFoto",Context.MODE_PRIVATE)
        sharedPrefEditor=sharedPref.edit()
        sharedPrefEditor.putString("chooseFoto",null)
        sharedPrefEditor.apply()


        sharedPref=getSharedPreferences("intentFrom",Context.MODE_PRIVATE)
        sharedPrefEditor=sharedPref.edit()
        sharedPrefEditor.putString("intentFrom",null)
        sharedPrefEditor.apply()

        sharedPref=getSharedPreferences("itemName",Context.MODE_PRIVATE)
        sharedPrefEditor=sharedPref.edit()
        sharedPrefEditor.putString("itemName",null)
        sharedPrefEditor.apply()

        /*sharedPref=getSharedPreferences("codeWoFromSelect",Context.MODE_PRIVATE)
        sharedPrefEditor=sharedPref.edit()
        sharedPrefEditor.putString("codeWoFromSelect",null)
        sharedPrefEditor.apply()


        sharedPref=getSharedPreferences("codeWoDetailFromSelect",Context.MODE_PRIVATE)
        sharedPrefEditor=sharedPref.edit()
        sharedPrefEditor.putString("codeWoDetailFromSelect",null)
        sharedPrefEditor.apply()

        sharedPref=getSharedPreferences("codeMaintenance",Context.MODE_PRIVATE)
        sharedPrefEditor=sharedPref.edit()
        sharedPrefEditor.putString("codeMaintenance",null)
        sharedPrefEditor.apply()

        sharedPref=getSharedPreferences("codeBarangFromSelect",Context.MODE_PRIVATE)
        sharedPrefEditor=sharedPref.edit()
        sharedPrefEditor.putString("codeBarangFromSelect",null)
        sharedPrefEditor.apply()*/




    }

    private fun openGallery() {

        if (VERSION.SDK_INT >= VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED){
                //permission denied
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                //show popup to request runtime permission
                requestPermissions(permissions, PERMISSION_CODE);
            }
            else{
                pickImageFromGallery()
            }
        }
        else{
            //system OS is < Marshmallow
            //Intent to pick image
            pickImageFromGallery()
        }
    }

    private fun openCamera()
    {
        val intent = Intent(this@WorkOrderActivity, CameraXActivity::class.java)
        startActivity(intent)

    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 2
        //Permission code
        private val PERMISSION_CODE = 1001
    }




    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied

                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            val sharedPref : SharedPreferences
            sharedPref=getSharedPreferences("chooseFoto", Context.MODE_PRIVATE)
            val sharedPrefEditor= sharedPref.edit()
            sharedPrefEditor.putString("chooseFoto","Gallery")
            sharedPrefEditor.apply()
            ivImage!!.setImageURI(data?.data)
            imageUri=data?.data
            val uriPathHelper = URIPathHelper()
            filePath= uriPathHelper.getPath(this,data?.data!!)
            //Toast.makeText(applicationContext, "Select Iamge Successfull", Toast.length_LONG).show()
            //txtUri!!.text=data?.data.toString()
        }
    }


    fun pickImageFromGallery() {
        Intent(Intent.ACTION_GET_CONTENT).also { intent ->
            intent.type = "image/*"
            intent.resolveActivity(packageManager)?.also {
                startActivityForResult(intent, REQUEST_PICK_IMAGE)
            }
        }
    }
    fun uploadFileString(sourceFilePath: String, uploadedFileName: String? = null) {
        uploadFile(File(sourceFilePath), uploadedFileName)
    }

    fun uploadFile(sourceFile: File, uploadedFileName: String? = null) {
        fileName = if (uploadedFileName == null)  sourceFile.name else uploadedFileName
        if(intentFrom!="") {

            if (codeBarang != "") {
              //  Log.e("Kode Barangny adalah ",codeBarang)
                sendWO()
            } else {
                sendWoAndCreateDataBarang()
            }
        }
        else
        {

        }
        Thread {
            val mimeType = getMimeType(sourceFile)
            if (mimeType == null) {

                return@Thread
            }



//            val handler = Handler()
  //          handler.postDelayed({ // Do something after 5s = 5000ms
                val requestBody: RequestBody =
                    MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("uploaded_file", fileName,sourceFile.asRequestBody(mimeType.toMediaTypeOrNull()))
                        .build()

                val request: Request = Request.Builder().url(serverURL).post(requestBody).build()

                val response: Response = client.newCall(request).execute()

                if (response.isSuccessful) {
//                    Toast.makeText(applicationContext, "Data Upload Success", Toast.length_LONG).show()
                } else {
                    //   Toast.makeText(applicationContext, "Data Upload Failed", Toast.length_LONG).show()
                }

    //        }, 12000)


        }.start()
    }

    // url = file path or whatever suitable URL you want.
    fun getMimeType(file: File): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(file.path)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }



    fun getcodeWo()
    {
        AndroidNetworking.get(ApiEndPoint.get_code_wo)
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {


                    val jsonArray = response?.optJSONArray("result")


                    if (jsonArray?.length() == 0) {
                        //loading.dismiss()

                    }

                    for (i in 1 until jsonArray?.length()!!) {

                        val jsonObject = jsonArray?.optJSONObject(i)

                        val sharedPref : SharedPreferences
                        sharedPref=getSharedPreferences("CodeWoFromSelect", Context.MODE_PRIVATE)
                        val sharedPrefEditor= sharedPref.edit()
                        val dataSend=jsonObject.getString("code_wo")
                        sharedPrefEditor.putString("CodeWoFromSelect",dataSend)
                        sharedPrefEditor.apply()


                        }

                    }

                override fun onError(anError: ANError?) {

                    Log.e("ONERROR",anError?.errorDetail.toString())

                }
            })





        val timer = Timer("schedule", true);

        timer.schedule(5000) {
            val sharedPref : SharedPreferences
            sharedPref=   getSharedPreferences("CodeWoFromSelect", Context.MODE_PRIVATE)
            codeWoFromSelect= sharedPref.getString("CodeWoFromSelect","Belum Login").toString()
            Log.e("CodeWoFromSelect",codeWoFromSelect)
            var data= codeWoFromSelect.filter { it.isDigit() }.toInt()+1

            if(data.toString().length==1)
            {
                nextCodeWo="W000"+data
            }
            else if(data.toString().length==2)
            {
                nextCodeWo="W00"+data
            }
            else if(data.toString().length==3)
            {
                nextCodeWo="W0"+data
            }
            else if(data.toString().length==4)
            {
                nextCodeWo="W"+data
            }

        }


    }

    fun getcodeWoDetail()
    {
        AndroidNetworking.get(ApiEndPoint.get_code_wo_detail)
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {


                    val jsonArray = response?.optJSONArray("result")

                    if (jsonArray?.length() == 0) {
                        //loading.dismiss()

                    }

                    for (i in 1 until jsonArray?.length()!!) {


                        val jsonObject = jsonArray?.optJSONObject(i)
                        val sharedPref : SharedPreferences
                        sharedPref=getSharedPreferences("codeWoDetailFromSelect", Context.MODE_PRIVATE)
                        val sharedPrefEditor= sharedPref.edit()
                        val codeSend=jsonObject.getString("code_wo_detail")
                        sharedPrefEditor.putString("codeWoDetailFromSelect",codeSend)
                        Log.e("code_wo_detail",jsonObject.getString("code_wo_detail"))
                        sharedPrefEditor.apply()


                    }

                }

                override fun onError(anError: ANError?) {

                    // Log.d("ONERROR",anError?.errorDetail?.toString())

                }
            })

        // create a daemon thread
        val timer = Timer("schedule", true);

// schedule a single event
        timer.schedule(5000) {
            val sharedPref : SharedPreferences
            sharedPref=   getSharedPreferences("codeWoDetailFromSelect", Context.MODE_PRIVATE)
            codeWoDetailFromSelect= sharedPref.getString("codeWoDetailFromSelect","Belum Login").toString()

            Log.e("getCodeWoDetailSelect",codeWoDetailFromSelect)
            var data= codeWoDetailFromSelect.filter { it.isDigit() }.toInt()+1

            if (data.toString().length == 1)
            {
                nextCodeWoDetail = "WD0000000" + data;
            }
            else if (data.toString().length == 2)
            {
                nextCodeWoDetail = "WD000000" + data;
            }
            else if (data.toString().length == 3)
            {
                nextCodeWoDetail = "WD00000" + data;
            }
            else if (data.toString().length == 4)
            {
                nextCodeWoDetail = "WD0000" + data;
            }
            else if (data.toString().length == 5)
            {
                nextCodeWoDetail = "WD000" + data;
            }
            else if (data.toString().length == 6)
            {
                nextCodeWoDetail = "WD00" + data;
            }
            else if (data.toString().length == 7)
            {
                nextCodeWoDetail = "WD0" + data;
            }
            else if (data.toString().length == 8)
            {
                nextCodeWoDetail = "WD" + data;
            }
        }

    }

    fun GetDataMaintenance()
    {
        AndroidNetworking.get(ApiEndPoint.get_code_maintenance)
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {


                    val jsonArray = response?.optJSONArray("result")

                    if (jsonArray?.length() == 0) {
                        //loading.dismiss()

                    }

                    for (i in 1 until jsonArray?.length()!!) {


                        val jsonObject = jsonArray?.optJSONObject(i)

                        val sharedPref : SharedPreferences
                        sharedPref=getSharedPreferences("codeMaintenance", Context.MODE_PRIVATE)
                        val sharedPrefEditor= sharedPref.edit()
                        val codeSend=jsonObject.getString("code_maintenance")
                        sharedPrefEditor.putString("codeMaintenance",codeSend)
                        sharedPrefEditor.apply()
                        Log.e("CodeMaintenance",jsonObject.getString("code_maintenance"))


                    }

                }

                override fun onError(anError: ANError?) {

                    // Log.d("ONERROR",anError?.errorDetail?.toString())

                }
            })

        // create a daemon thread
        val timer = Timer("schedule", true);

// schedule a single event
        timer.schedule(5000) {
            val sharedPref : SharedPreferences
            sharedPref=   getSharedPreferences("codeMaintenance", Context.MODE_PRIVATE)
            codeMaintenanceSelect= sharedPref.getString("codeMaintenance","Belum Login").toString()
            Log.e("getCodeMaintenance",codeMaintenanceSelect)

            var data= codeMaintenanceSelect.filter { it.isDigit() }.toInt()+1

            if (data.toString().length == 1)
            {
                nextCodeMaintenance = "WM0000000" + data;
            }
            else if (data.toString().length == 2)
            {
                nextCodeMaintenance = "WM000000" + data;
            }
            else if (data.toString().length == 3)
            {
                nextCodeMaintenance = "WM00000" + data;
            }
            else if (data.toString().length == 4)
            {
                nextCodeMaintenance = "WM0000" + data;
            }
            else if (data.toString().length == 5)
            {
                nextCodeMaintenance = "WM000" + data;
            }
            else if (data.toString().length == 6)
            {
                nextCodeMaintenance = "WM00" + data;
            }
            else if (data.toString().length == 7)
            {
                nextCodeMaintenance = "WM0" + data;
            }
            else if (data.toString().length == 8)
            {
                nextCodeMaintenance = "WM" + data;
            }
        }

    }


    fun addWorkOrder()
    {

        codeDepartement=spinnerDepartement.selectedItem.toString()
        if(codeDepartement!="")
        {

            if(departement!!.replace("Departement = ","")=="Accounting")
            {
                codeDepartement="D0001"
            }
            else if(departement!!.replace("Departement = ","")=="Housekeeping")
            {
                codeDepartement="D0002"
            }
            else if(departement!!.replace("Departement = ","")=="Front Office")
            {
                codeDepartement="D0003"
            }
            else if(departement!!.replace("Departement = ","")=="F&B Product")
            {
                codeDepartement="D0004"
            }
            else if(departement!!.replace("Departement = ","")=="F&B Service")
            {
                codeDepartement="D0005"
            }
            else if(departement!!.replace("Departement = ","")=="Human Resource")
            {
                codeDepartement="D0006"
            }
            else if(departement!!.replace("Departement = ","")=="Engineering")
            {
                codeDepartement="D0007"
            }
            else if(departement!!.replace("Departement = ","")=="Sales")
            {
                codeDepartement="D0008"
            }

        }
      //  Log.e("code Barang : ",codeBarang)
        val sharedPref : SharedPreferences
        sharedPref=   getSharedPreferences("UserName", Context.MODE_PRIVATE)
        val userLoginName : String = sharedPref.getString("UserName","Belum Login").toString()
        var workOrderType=spinnerWorkOrderType.selectedItem.toString()
        var woRequest=editTextNoteDetailWorkOrder.text.toString()
        var woskala= spinnerPriority.selectedItem.toString()
        AndroidNetworking.post(ApiEndPoint.add_work_order_data)
            .addBodyParameter("code_wo", nextCodeWo)
            .addBodyParameter("code_dept_wo_request",codeDepartement )
            .addBodyParameter("code_wo_detail", nextCodeWoDetail)
            .addBodyParameter("wo_request",woRequest)
            .addBodyParameter("image_location",fileName)
            .addBodyParameter("user_wo_create",userLoginName)
            .addBodyParameter("jenis_wo",workOrderType)
            .addBodyParameter("skala",woskala)
            .addBodyParameter("code_maintenance", nextCodeMaintenance)
            .addBodyParameter("code_barang", codeBarang)
            .addBodyParameter("note", woRequest)
            .setPriority(Priority.MEDIUM)
            .build().getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {

                    Log.e("Add Dengan Search Item","Berhasil add dengan search item")

                }

                override fun onError(anError: ANError?) {
                   //
                    // Toast.makeText(applicationContext, "Koneksi Error", Toast.LENGTH_LONG).show()
                    Log.e("Error JSON",anError.toString())
                }


            })

    }

   /* fun addWorkOrderDetail()
    {
        getcodeWoDetail()
        AndroidNetworking.post(ApiEndPoint.add_work_order_detail)
            .addBodyParameter("code_wo_detail", nextCodeWoDetail)
            .addBodyParameter("code_wo",nextCodeWoDetail )
            .addBodyParameter("wo_request",editTextNoteDetailWorkOrder.text.toString())
            .addBodyParameter("image_location",fileName)
            .setPriority(Priority.MEDIUM)
            .build().getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {




                }

                override fun onError(anError: ANError?) {
                    //Toast.makeText(applicationContext, "Koneksi Error", Toast.LENGTH_LONG).show()
                    Log.e("Error JSON",anError.toString())
                }


            })

    }*/

    /*fun addMaintenance()
    {
        GetDataMaintenance()
        AndroidNetworking.post(ApiEndPoint.add_work_order_maintenance)
            .addBodyParameter("code_wo_detail", nextCodeWoDetail)
            .addBodyParameter("code_wo",nextCodeWoDetail )
            .addBodyParameter("wo_request",editTextNoteDetailWorkOrder.text.toString())
            .addBodyParameter("image_location",fileName)
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

            override fun onResponse(response: JSONObject?) {




            }

            override fun onError(anError: ANError?) {
                //Toast.makeText(applicationContext, "Koneksi Error", Toast.LENGTH_LONG).show()
                Log.e("Error JSON",anError.toString())
            }


        })

    }*/

    fun  sendWO()
    {
        // create a daemon thread


        addWorkOrder()


        Toast.makeText(applicationContext, "Work Order Created Successfully", Toast.LENGTH_LONG).show()
    }


    fun sendWoAndCreateDataBarang()
    {




        codeDepartement=spinnerDepartement.selectedItem.toString()
        if(codeDepartement!="")
        {

            if(codeDepartement=="Accounting")
            {
                codeDepartement="D0001"
            }
            else if(codeDepartement=="Housekeeping")
            {
                codeDepartement="D0002"
            }
            else if(codeDepartement=="Front Office")
            {
                codeDepartement="D0003"
            }
            else if(codeDepartement==="F&B Product")
            {
                codeDepartement="D0004"
            }
            else if(codeDepartement=="F&B Service")
            {
                codeDepartement="D0005"
            }
            else if(codeDepartement=="Human Resource")
            {
                codeDepartement="D0006"
            }
            else if(codeDepartement=="Engineering")
            {
                codeDepartement="D0007"
            }
            else if(codeDepartement=="Sales")
            {
                codeDepartement="D0008"
            }

        }

        val sharedPref : SharedPreferences
        sharedPref=   getSharedPreferences("UserName", Context.MODE_PRIVATE)
        val userLoginName : String = sharedPref.getString("UserName","Belum Login").toString()
        var workOrderType=spinnerWorkOrderType.selectedItem.toString()
        var woRequest=editTextNoteDetailWorkOrder.text.toString()
        var woskala= spinnerPriority.selectedItem.toString()
        var lokasi=editTextLocationWorkOrder.text.toString()
        var nmBarang=editTextWorkOrderItemName.text.toString()
        AndroidNetworking.post(ApiEndPoint.add_work_order_data_and_item)
            .addBodyParameter("code_wo", nextCodeWo)
            .addBodyParameter("code_dept_wo_request",codeDepartement )
            .addBodyParameter("code_wo_detail", nextCodeWoDetail)
            .addBodyParameter("wo_request",woRequest)
            .addBodyParameter("image_location",fileName)
            .addBodyParameter("user_wo_create",userLoginName)
            .addBodyParameter("jenis_wo",workOrderType)
            .addBodyParameter("skala",woskala)
            .addBodyParameter("code_maintenance", nextCodeMaintenance)
            .addBodyParameter("note", woRequest)
            .addBodyParameter("code_barang", nextCodeBarang)
            .addBodyParameter("nama_barang", nmBarang)
            .addBodyParameter("location", lokasi)
            .setPriority(Priority.MEDIUM)
            .build().getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {



                }

                override fun onError(anError: ANError?) {
                    //
                    // Toast.makeText(applicationContext, "Koneksi Error", Toast.LENGTH_LONG).show()
                    Log.e("Error JSON",anError.toString())
                }


            })
        Toast.makeText(applicationContext, "Work Order Created Successfully", Toast.LENGTH_LONG).show()
    }

    fun getCodeBarang()
    {
        AndroidNetworking.get(ApiEndPoint.get_code_barang)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {


                    val jsonArray = response?.optJSONArray("result")

                    if (jsonArray?.length() == 0) {
                        //loading.dismiss()

                    }

                    for (i in 1 until jsonArray?.length()!!) {


                        val jsonObject = jsonArray?.optJSONObject(i)
                        // Log.e("getWO",jsonObject.getString("code_wo"))
                        val sharedPref : SharedPreferences
                        sharedPref=getSharedPreferences("codeBarangFromSelect", Context.MODE_PRIVATE)
                        val sharedPrefEditor= sharedPref.edit()
                        sharedPrefEditor.putString("codeBarangFromSelect",jsonObject.getString("code_barang"))
                        sharedPrefEditor.apply()


                    }

                }

                override fun onError(anError: ANError?) {

                    // Log.d("ONERROR",anError?.errorDetail?.toString())

                }
            })

// create a daemon thread
        val timer = Timer("schedule", true);

// schedule a single event
        timer.schedule(5000) {
            val sharedPref : SharedPreferences
            sharedPref=   getSharedPreferences("codeBarangFromSelect", Context.MODE_PRIVATE)
            selectCodeBarang= sharedPref.getString("codeBarangFromSelect","Belum Login").toString()
            //Log.e("codeWoFromSelect",codeWoFromSelect)


            var data= selectCodeBarang.filter { it.isDigit() }.toInt()+1

            if(data.toString().length==1)
            {
                nextCodeBarang="B000"+data
            }
            else if(data.toString().length==2)
            {
                nextCodeBarang="B00"+data
            }
            else if(data.toString().length==3)
            {
                nextCodeBarang="B0"+data
            }
            else if(data.toString().length==4)
            {
                nextCodeBarang="B"+data
            }
        }

    }

}