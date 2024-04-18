package com.dam.recylerview_prueba

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class prueba_plantid : AppCompatActivity() {

    private lateinit var btnChange: Button
    private lateinit var imgView: ImageView
    private lateinit var btnUpload: Button
    private lateinit var imageUri: Uri


    private lateinit var name1: TextView


    private lateinit var url1: TextView



    private val contract =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            imageUri = it!!
            imgView.setImageURI(it)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prueba_plantid)
        setup()

    }

    private fun setup() {
        imgView = findViewById(R.id.imagen)
        btnChange = findViewById(R.id.botonazo)

        btnChange.setOnClickListener { contract.launch("image/*") }

        btnUpload = findViewById(R.id.btnUpload)
        btnUpload.isEnabled = false

        btnUpload.setOnClickListener { upload() }
        name1 = findViewById(R.id.name1)





    }

    private fun upload() {
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

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val multipartBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("images", file.name, requestBody)
            .build()

        val request = Request.Builder()
            .url("https://plant.id/api/v3/health_assessment?details=common_names,url,description&language=es")
            .post(multipartBody)
            .addHeader("Api-Key", "xHdKHCoLzQwZbL669IskRVqT7eFh4u0hvLVyWNMdH3WIY9RejK")
            .build()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: Response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val jsonString = response.body?.string()
                    Log.d("Response", "JSON Response: $jsonString")
                    val nombresYUrls = obtenerNombresYUrls("$jsonString", name1)
                    println("Nombres y URLs:")
                    nombresYUrls.forEachIndexed { index, (name, url) ->
                        println("${index + 1}. Nombre: $name, URL: $url")

                    }
                } else {
                    Log.e("Response", "Unsuccessful HTTP Response: ${response.code}")
                }
            } catch (e: IOException) {
                Log.e("Response", "Exception occurred: ${e.message}")
            }
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

    fun obtenerNombresYUrls(jsonString: String, name1: TextView): List<Pair<String?, String?>> {
        val resultList = mutableListOf<Pair<String?, String?>>()

        val jsonObject = JsonParser.parseString(jsonString).asJsonObject
        val resultObject = jsonObject.getAsJsonObject("result")
        val diseaseObject = resultObject.getAsJsonObject("disease")
        val suggestionsArray = diseaseObject.getAsJsonArray("suggestions")

        val stringBuilder = StringBuilder()

        val size = suggestionsArray.size()
        val maxItems = if (size < 3) size else 3 // Determinar el número máximo de elementos a procesar

        for (i in 0 until maxItems) {
            val suggestion = suggestionsArray[i].asJsonObject
            val name = suggestion.get("name")?.asString
            val details = suggestion.getAsJsonObject("details")
            val url = details.get("url")?.asString

            stringBuilder.append("$name: $url\n")
            resultList.add(name to url)
        }

        // Actualizar el TextView name1 en el hilo principal
        runOnUiThread {
            name1.text = stringBuilder.toString()
        }

        return resultList
    }























    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {

                else -> {
                    imageUri = data?.data!!
                    imgView.setImageURI(imageUri)

                    btnUpload.isEnabled = true
                }
            }
        }
    }


}