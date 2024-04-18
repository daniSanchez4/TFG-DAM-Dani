package com.dam.recylerview_prueba

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream


class post_prueba : AppCompatActivity() {

    private lateinit var btnChange: Button

    private lateinit var btnUpload: Button
    private lateinit var imageUri: Uri
    private lateinit var name: TextView
    private lateinit var sname: TextView
    private lateinit var titulo: TextView
    private lateinit var render: ImageView
    private lateinit var labelname: TextView
    private lateinit var labelcientifico: TextView


    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()){
        imageUri = it!!

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_prueba)
        setup()

    }

    private fun setup(){
        btnChange = findViewById(R.id.botonazo)
        btnChange.setOnClickListener { contract.launch("image/*") }
        btnUpload = findViewById(R.id.btnUpload)
        btnUpload.isEnabled = false
        btnUpload.setOnClickListener { upload() }
        name = findViewById(R.id.name)
        sname = findViewById(R.id.scientific)
        labelname = findViewById(R.id.labelnombre)
        labelcientifico = findViewById(R.id.labelcientifico)
        titulo = findViewById(R.id.titulo)
        render = findViewById(R.id.tia)
        animateViewWithSlideUp(titulo, 1000)
        animateImageView(render)
        animateView(btnChange)
        animateView(btnUpload)
        animateView(sname)
        animateView(name)
        animateView(labelname)
        animateView(labelcientifico)
    }

    private fun upload(){
        val filesDir = applicationContext.filesDir
        val file = File(filesDir, "image.png")

        val inputStream = contentResolver.openInputStream(imageUri)
        inputStream?.use { input ->
            val outputStream = FileOutputStream(file)
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())

        Log.d("funciona", "Nombre del archivo: ${file.name}")
        Log.d("funciona", "Tamaño del archivo: ${file.length()} bytes")
        Log.d("funciona", "Tipo de contenido: image/*")

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val part = MultipartBody.Part.createFormData("images", file.name, requestBody)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://my-api.plantnet.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(UploadService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            Log.d("funciona", "entra")

            val responseBody: ResponseBody = retrofit.uploadImage(part)
            val jsonString = responseBody.string()
            Log.d("funciona", "Contenido JSON de la respuesta: $jsonString")
            val (scientificName, firstCommonName) = obtenerNombres(jsonString)

            println("scientificName: $scientificName")
            println("firstCommonName: $firstCommonName")

            name.text = "$firstCommonName"
            sname.text = "$scientificName"

        }
    }


    fun obtenerNombres(jsonString: String): Pair<String, String?> {
        var scientificName: String? = null
        var firstCommonName: String? = null

        val jsonObject = JsonParser.parseString(jsonString).asJsonObject

        val resultadosArray: JsonArray? = jsonObject.getAsJsonArray("results")

        resultadosArray?.get(0)?.let { result ->
            val resultObject = result.asJsonObject
            val speciesObject = resultObject.getAsJsonObject("species")
            scientificName = speciesObject.get("scientificName").asString

            val commonNamesArray: JsonArray? = speciesObject.getAsJsonArray("commonNames")
            firstCommonName = commonNamesArray?.get(0)?.asString
        }

        return Pair(scientificName ?: "", firstCommonName)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {

                else -> {
                    imageUri = data?.data!!
                    btnUpload.isEnabled = true
                }
            }
        }
    }

    fun animateViewWithSlideUp(view: View, duration: Long) {

        view.alpha = 0f
        view.translationY = 100f

        val fadeInAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        fadeInAnimator.duration = duration

        val translateYAnimator = ObjectAnimator.ofFloat(view, "translationY", 100f, 0f)
        translateYAnimator.duration = duration

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(fadeInAnimator, translateYAnimator)

        animatorSet.start()
    }

    private fun animateImageView(imageView: ImageView) {

        imageView.scaleX = 0.5f
        imageView.scaleY = 0.5f

        val scaleXAnimator = ObjectAnimator.ofFloat(imageView, "scaleX", 1f)
        val scaleYAnimator = ObjectAnimator.ofFloat(imageView, "scaleY", 1f)

        scaleXAnimator.duration = 1000
        scaleYAnimator.duration = 1000

        scaleXAnimator.start()
        scaleYAnimator.start()
    }

    private fun animateView(view: View) {
        view.alpha = 0f
        val animator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        animator.duration = 1100
        animator.start()
    }
}