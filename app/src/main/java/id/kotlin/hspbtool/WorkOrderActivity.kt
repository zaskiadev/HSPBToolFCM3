package id.kotlin.hspbtool

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build.*
import android.os.Bundle
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
import java.util.regex.Matcher
import java.util.regex.Pattern


typealias LumaListener = (luma: Double) -> Unit

class WorkOrderActivity : AppCompatActivity() {
    private lateinit var cameraExecutor: ExecutorService
    private val REQUEST_PICK_IMAGE = 2
    var ivImage: ImageView? = null

    // var txtUri:TextView?=null
    var imageUri: Uri? = null
    var filePath: String? = ""
    var codeWoFromSelect: String = ""
    var nextCodeWo: String = ""
    var codeWoDetailFromSelect: String = ""
    var nextCodeWoDetail: String = ""
    var codeMaintenanceSelect: String = ""
    var nextCodeMaintenance: String = ""
    var serverURL: String = "http://103.84.233.186:8742/hspb_tool/upload_workorder_image.php"
    var serverUploadDirectoryPath: String = "http://103.84.233.186:8742/hspb_tool/image_work_order"
    val client = OkHttpClient()
    var codeBarang: String? = ""
    var namaBarang: String? = ""
    var Location: String? = ""
    var departement: String? = ""
    val arrayDepartement = resources.getStringArray(R.array.work_order_departement)
    val adapterWorkOrderDepartement =
        ArrayAdapter(this, R.layout.spinner_item_work_order, arrayDepartement)


    var chooseFrom: String? = "Gallery"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_order)
        val arrayWorkOrderType = resources.getStringArray(R.array.work_order_type)
        val spinnerWorkOrderType = findViewById<Spinner>(R.id.spinnerWorkOrderType)
        val arrayPriority = resources.getStringArray(R.array.work_order_priority)
        val spinnerWorkOrderPriority = findViewById<Spinner>(R.id.spinnerPriority)

        val spinnerWorkOrderDepartement = findViewById<Spinner>(R.id.spinnerDepartement)
        var textSpinnerDepartement = ""
        var textSpinnerWorkOderType = ""
        var textSpinnerWorkOrderPriority = ""
        val buttonChooseGallery = findViewById<Button>(R.id.buttonChooseFromGallery)
        val buttonTakePhoto = findViewById<Button>(R.id.buttonTakeAgainPictureWorkOrder)
        val buttonSendWO = findViewById<Button>(R.id.buttonUseThisPicture)
        val buttonSearchIteme = findViewById<Button>(R.id.buttonSearchItemWorkOrder)
        this.ivImage = findViewById(R.id.imageTakedWorkOrder)


        val adapterWorkOrderType = ArrayAdapter(
            this,
            R.layout.spinner_item_work_order, arrayWorkOrderType
        )
        spinnerWorkOrderType.adapter = adapterWorkOrderType

        val adapterWorkOrderPriority = ArrayAdapter(
            this,
            R.layout.spinner_item_work_order, arrayPriority
        )
        spinnerWorkOrderPriority.adapter = adapterWorkOrderPriority

        spinnerWorkOrderDepartement.adapter = adapterWorkOrderDepartement



        spinnerWorkOrderType.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                textSpinnerWorkOderType = arrayWorkOrderType[position]


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        spinnerWorkOrderPriority.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                textSpinnerWorkOrderPriority = arrayPriority[position]


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        spinnerWorkOrderDepartement.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                textSpinnerDepartement = arrayDepartement[position]


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
            chooseFrom = "Photo"
        }

        buttonSearchIteme.setOnClickListener {
            val sharedPref: SharedPreferences
            sharedPref = getSharedPreferences("itemName", Context.MODE_PRIVATE)
            val sharedPrefEditor = sharedPref.edit()
            sharedPrefEditor.putString("itemName", editTextWorkOrderItemName.text.toString())
            sharedPrefEditor.apply()
            val intent = Intent(this@WorkOrderActivity, ViewSearchItemForWorkOrder::class.java)
            startActivity(intent)
        }


        buttonSendWO.setOnClickListener {


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
        }


    }

    fun checkSharedPreferences() {
        var sharedPref = getSharedPreferences("chooseFoto", Context.MODE_PRIVATE)
        chooseFrom = sharedPref.getString("chooseFoto", null)

        if (chooseFrom == "Photo") {
            sharedPref = getSharedPreferences("url", Context.MODE_PRIVATE)
            var url: String = sharedPref.getString("url", null).toString()


            if (url != null) {
                imageUri = Uri.parse(url)
                ivImage!!.setImageURI(imageUri)

                // txtUri!!.text=imageUri.toString()
            }
        }

        sharedPref = getSharedPreferences("intentFrom", Context.MODE_PRIVATE)
        var intentFrom = sharedPref.getString("intentFrom", null)
        var stringItem: String = ""
        if (intentFrom == "itemSelectWorkOrder") {

            sharedPref = getSharedPreferences("codeBarang", Context.MODE_PRIVATE)
            codeBarang = sharedPref.getString("codebarang", null)

            sharedPref = getSharedPreferences("namaBarang", Context.MODE_PRIVATE)
            namaBarang = sharedPref.getString("namaBarang", null)

            sharedPref = getSharedPreferences("location", Context.MODE_PRIVATE)
            Location = sharedPref.getString("location", null)

            sharedPref = getSharedPreferences("departement", Context.MODE_PRIVATE)
            departement = sharedPref.getString("departement", null)

            editTextWorkOrderItemName.setText(namaBarang)
            editTextLocationWorkOrder.setText(Location)


            spinnerDepartement.setSelection(((ArrayAdapter)spinnerDepartement.getAdapter()).getPosition("Value"))
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
    }

    fun checkState() {

    }


    fun clearSharedPref() {

    }

    private fun openGallery() {

        if (VERSION.SDK_INT >= VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED
            ) {
                //permission denied
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                //show popup to request runtime permission
                requestPermissions(permissions, PERMISSION_CODE);
            } else {
                pickImageFromGallery()
            }
        } else {
            //system OS is < Marshmallow
            //Intent to pick image
            pickImageFromGallery()
        }
    }

    private fun openCamera() {
        val intent = Intent(this@WorkOrderActivity, CameraXActivity::class.java)
        startActivity(intent)
        //this@WorkOrderActivity.finish()
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 2

        //Permission code
        private val PERMISSION_CODE = 1001
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup granted
                    pickImageFromGallery()
                } else {
                    //permission from popup denied

                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            val sharedPref: SharedPreferences
            sharedPref = getSharedPreferences("chooseFoto", Context.MODE_PRIVATE)
            val sharedPrefEditor = sharedPref.edit()
            sharedPrefEditor.putString("chooseFoto", "Gallery")
            sharedPrefEditor.apply()
            ivImage!!.setImageURI(data?.data)
            imageUri = data?.data
            val uriPathHelper = URIPathHelper()
            filePath = uriPathHelper.getPath(this, data?.data!!)
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
        Thread {
            val mimeType = getMimeType(sourceFile)
            if (mimeType == null) {

                return@Thread
            }
            val fileName: String =
                if (uploadedFileName == null) sourceFile.name else uploadedFileName


            val requestBody: RequestBody =
                MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart(
                        "uploaded_file",
                        fileName,
                        sourceFile.asRequestBody(mimeType.toMediaTypeOrNull())
                    )
                    .build()

            val request: Request = Request.Builder().url(serverURL).post(requestBody).build()

            val response: Response = client.newCall(request).execute()

            if (response.isSuccessful) {
//                    Toast.makeText(applicationContext, "Data Upload Success", Toast.length_LONG).show()
            } else {
                //   Toast.makeText(applicationContext, "Data Upload Failed", Toast.length_LONG).show()
            }


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


    fun getcodeWo() {
        AndroidNetworking.get(ApiEndPoint.get_code_wo)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {


                    val jsonArray = response?.optJSONArray("result")

                    if (jsonArray?.length() == 0) {
                        //loading.dismiss()

                    }

                    for (i in 0 until jsonArray?.length()!!) {


                        val jsonObject = jsonArray?.optJSONObject(i)

                        codeWoFromSelect = jsonObject.getString("code_wo")

                    }

                }

                override fun onError(anError: ANError?) {

                    // Log.d("ONERROR",anError?.errorDetail?.toString())

                }
            })

        var data: Int = 0
        var p: Pattern = Pattern.compile("\\d+")
        var m: Matcher = p.matcher(codeWoFromSelect)
        if (m.matches()) {
            data = m.group().toInt() + 1
        }

        if (data.toString().length == 1) {
            nextCodeWo = "W000" + data
        } else if (data.toString().length == 2) {
            nextCodeWo = "W00" + data
        } else if (data.toString().length == 3) {
            nextCodeWo = "W0" + data
        } else if (data.toString().length == 4) {
            nextCodeWo = "W" + data
        }


    }

    fun getcodeWoDetail() {
        AndroidNetworking.get(ApiEndPoint.get_code_wo_detail)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {


                    val jsonArray = response?.optJSONArray("result")

                    if (jsonArray?.length() == 0) {
                        //loading.dismiss()

                    }

                    for (i in 0 until jsonArray?.length()!!) {


                        val jsonObject = jsonArray?.optJSONObject(i)

                        codeWoDetailFromSelect = jsonObject.getString("code_wo_detail")

                    }

                }

                override fun onError(anError: ANError?) {

                    // Log.d("ONERROR",anError?.errorDetail?.toString())

                }
            })

        var data: Int = 0
        var p: Pattern = Pattern.compile("\\d+")
        var m: Matcher = p.matcher(codeWoFromSelect)
        if (m.matches()) {
            data = m.group().toInt() + 1
        }

        if (data.toString().length == 1) {
            nextCodeWoDetail = "WD0000000" + data;
        } else if (data.toString().length == 2) {
            nextCodeWoDetail = "WD000000" + data;
        } else if (data.toString().length == 3) {
            nextCodeWoDetail = "WD00000" + data;
        } else if (data.toString().length == 4) {
            nextCodeWoDetail = "WD0000" + data;
        } else if (data.toString().length == 5) {
            nextCodeWoDetail = "WD000" + data;
        } else if (data.toString().length == 6) {
            nextCodeWoDetail = "WD00" + data;
        } else if (data.toString().length == 7) {
            nextCodeWoDetail = "WD0" + data;
        } else if (data.toString().length == 8) {
            nextCodeWoDetail = "WD" + data;
        }
    }

    fun GetDataMaintenance() {
        AndroidNetworking.get(ApiEndPoint.get_code_maintenance)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {


                    val jsonArray = response?.optJSONArray("result")

                    if (jsonArray?.length() == 0) {
                        //loading.dismiss()

                    }

                    for (i in 0 until jsonArray?.length()!!) {


                        val jsonObject = jsonArray?.optJSONObject(i)

                        codeWoDetailFromSelect = jsonObject.getString("code_maintenance")

                    }

                }

                override fun onError(anError: ANError?) {

                    // Log.d("ONERROR",anError?.errorDetail?.toString())

                }
            })

        var data: Int = 0
        var p: Pattern = Pattern.compile("\\d+")
        var m: Matcher = p.matcher(codeWoFromSelect)
        if (m.matches()) {
            data = m.group().toInt() + 1
        }

        if (data.toString().length == 1) {
            nextCodeMaintenance = "WM0000000" + data;
        } else if (data.toString().length == 2) {
            nextCodeMaintenance = "WM000000" + data;
        } else if (data.toString().length == 3) {
            nextCodeMaintenance = "WM00000" + data;
        } else if (data.toString().length == 4) {
            nextCodeMaintenance = "WM0000" + data;
        } else if (data.toString().length == 5) {
            nextCodeMaintenance = "WM000" + data;
        } else if (data.toString().length == 6) {
            nextCodeMaintenance = "WM00" + data;
        } else if (data.toString().length == 7) {
            nextCodeMaintenance = "WM0" + data;
        } else if (data.toString().length == 8) {
            nextCodeMaintenance = "WM" + data;
        }
    }

    fun GetDataBarang() {

    }

    fun GetDataID() {

    }

    fun sendWO() {

    }


}