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
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import android.widget.Toast



class TakePictureWorkOrderActivity : AppCompatActivity(){



    private lateinit var cameraExecutor: ExecutorService
    private val REQUEST_PICK_IMAGE = 2
    var ivImage: ImageView? = null
    // var txtUri:TextView?=null
    var imageUri: Uri?=null
    var filePath:String?=""
    var serverURL: String = "http://103.84.233.186:8742/hspb_tool/upload_workorder_image.php"
    var serverUploadDirectoryPath: String = "http://103.84.233.186:8742/hspb_tool/image_work_order"
    val client = OkHttpClient()

    var chooseFrom: String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_picture_work_order)

        val buttonTakeAgain :Button = findViewById(R.id.buttonTakeAgainPictureWorkOrder)
        val buttonChooseGallery : Button=findViewById(R.id.buttonChooseFromGallery)
        val buttonUseThisPicture : Button = findViewById(R.id.buttonUseThisPicture)
        this.ivImage=findViewById(R.id.imageTakedWorkOrder)
        ivImage!!.setImageURI(null)
        //this.txtUri=findViewById(R.id.textViewUri)

        checkSharedPreferences()
        cameraExecutor = Executors.newSingleThreadExecutor()
        buttonTakeAgain.setOnClickListener{
            openCamera()
            chooseFrom="Photo"
        }

        buttonChooseGallery.setOnClickListener{
            openGallery()
            chooseFrom="Gallery"
        }

        buttonUseThisPicture.setOnClickListener {
            if(chooseFrom=="Photo") {
                Toast.makeText(applicationContext, imageUri!!.path, Toast.LENGTH_LONG).show()
                uploadFile(File(imageUri!!.path))

            }
            else
            {
                val REQUEST_EXTERNAL_STORAGE = 1
                val PERMISSIONS_STORAGE = arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                val permission = ActivityCompat.checkSelfPermission(
                    this@TakePictureWorkOrderActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )

                if (permission != PackageManager.PERMISSION_GRANTED) {
                    // We don't have permission so prompt the user
                    ActivityCompat.requestPermissions(
                        this@TakePictureWorkOrderActivity,
                        PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE
                    )
                }
                uploadFileString(filePath!!)
            }
        }
    }

    fun checkSharedPreferences()
    {
        var sharedPref : SharedPreferences
        sharedPref=   getSharedPreferences("url", Context.MODE_PRIVATE)
        var url:String = sharedPref.getString("url",null).toString()
        if(url!=null)
        {
            imageUri= Uri.parse(url)
            ivImage!!.setImageURI(imageUri)
            // txtUri!!.text=imageUri.toString()
        }
        sharedPref=   getSharedPreferences("chooseFoto", Context.MODE_PRIVATE)
        chooseFrom = sharedPref.getString("chooseFoto",null)

    }
    override fun onResume() {
        super.onResume()

    }


    private fun openGallery() {
        /*Intent(Intent.ACTION_GET_CONTENT).also { intent ->
            intent.type = "image/*"
            intent.resolveActivity(packageManager)?.also {
                startActivityForResult(intent, REQUEST_PICK_IMAGE)
            }
        }*/


         */
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
        val intent = Intent(this@TakePictureWorkOrderActivity, CameraXActivity::class.java)
        startActivity(intent)
        this@TakePictureWorkOrderActivity.finish()
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 2;
        //Permission code
        private val PERMISSION_CODE = 1001;
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
            ivImage!!.setImageURI(data?.data)
            imageUri=data?.data
            val uriPathHelper = URIPathHelper()
            filePath= uriPathHelper.getPath(this,data?.data!!)

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
            val mimeType = getMimeType(sourceFile);
            if (mimeType == null) {

                return@Thread
            }
            val fileName: String = if (uploadedFileName == null)  sourceFile.name else uploadedFileName


            val requestBody: RequestBody =
                MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("uploaded_file", fileName,sourceFile.asRequestBody(mimeType.toMediaTypeOrNull()))
                    .build()

            val request: Request = Request.Builder().url(serverURL).post(requestBody).build()

            val response: Response = client.newCall(request).execute()

            if (response.isSuccessful) {
//                    Toast.makeText(applicationContext, "Data Upload Success", Toast.LENGTH_LONG).show()
            } else {
                //   Toast.makeText(applicationContext, "Data Upload Failed", Toast.LENGTH_LONG).show()
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




}

