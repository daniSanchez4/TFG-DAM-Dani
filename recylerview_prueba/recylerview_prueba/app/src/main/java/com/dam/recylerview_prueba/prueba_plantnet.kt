package com.dam.recylerview_prueba


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.readBytes
import io.ktor.util.InternalAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class prueba_plantnet : AppCompatActivity() {

private lateinit var boton: Button
    @OptIn(InternalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prueba_plantnet)

        boton=findViewById(R.id.button2)

        boton.setOnClickListener {  //
        var api_url = "https://my-api.plantnet.org/v2/identify"
        var key = "2b10BaJkdtVCtqIZwYqnQamhu"
        var project = "all"
        var imageURL = "https://media.admagazine.com/photos/6511419d5e83245788937f4a/16:9/w_2560%2Cc_limit/tulipanes-cuidados-historia.jpg"
        var lang = "es"
        var organs = "flower"
        var includeRelatedImages = false

        val encodedURL = URLEncoder.encode(imageURL, StandardCharsets.UTF_8.toString())
  var url = "https://my-api.plantnet.org/v2/identify/all?images=$imageURL&organs=flower&lang=es&include-related-images=False&api-key=2b10BaJkdtVCtqIZwYqnQamhu"
        var URL = "$api_url/$project?" + "images=$encodedURL&" + "organs=$organs&" +
                "lang=$lang&" + "include-related-images=$includeRelatedImages&" + "api_key=$key"

        Log.d("url", URL)

        val client = HttpClient(Android)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d("Respuesta", "entra")
                val response = client.get(url)
                val jsonresponse = response.bodyAsText()
                Log.d("Respuesta",jsonresponse)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("error", "jodete")
            }
        }
    }//
    }
}