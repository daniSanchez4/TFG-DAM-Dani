package com.dam.recylerview_prueba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.compose.LottieAnimation
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar

class tiempo : AppCompatActivity() {

    private lateinit var naview: BottomNavigationView
    private lateinit var tvtemp: TextView
    private lateinit var tviento: TextView
    private lateinit var tvhum: TextView
    private lateinit var temperaturaActual: String
    private lateinit var humedad: String
    private lateinit var viento: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiempo)

        tvtemp = findViewById(R.id.textoTemp)
        tviento = findViewById(R.id.textoViento)
        tvhum = findViewById(R.id.textohumedad)
        naview = findViewById(R.id.nav_view)
        naview.selectedItemId = R.id.sol
        naview.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    // Agrega aquí cualquier otra acción que desees realizar
                    true // Devuelve true para indicar que el clic fue manejado
                }
                R.id.flor -> {

                    val intent2 = Intent(this, Crud::class.java)
                    startActivity(intent2)

                    // Agrega aquí cualquier otra acción que desees realizar
                    true // Devuelve true para indicar que el clic fue manejado
                }
                // Otros casos para otros elementos del menú
                else -> false
            }
        }

    }

    override fun onResume() {
        super.onResume()
        llamadaApi()
    }

    private fun llamadaApi() {
        val url = "https://www.el-tiempo.net/api/json/v2/provincias/30/municipios/30030"
        val client = HttpClient(Android)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d("Respuesta", "entra")
                val response = client.get(url)
                val jsonresponse = response.bodyAsText()
                val jsondata = jsonresponse
                val jsonObject = JSONObject(jsondata)
                val stateSky = jsonObject.getJSONObject("stateSky")
                val descripcionStateSky = stateSky.getString("description")
                temperaturaActual = jsonObject.getString("temperatura_actual")
                val temperaturas = jsonObject.getJSONObject("temperaturas")
                val temperaturaMaxima = temperaturas.getString("max")
                val temperaturaMinima = temperaturas.getString("min")
                humedad = jsonObject.getString("humedad")
                viento = jsonObject.getString("viento")
                val precipitacion = jsonObject.getString("precipitacion")
                Log.d("key", temperaturaActual)

                temp(temperaturaActual, viento, humedad)

            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("error", "jodete")
            }
        }
    }

    private fun temp(temperaturaActual: String, viento: String, humedad: String) {
        runOnUiThread {
            var tempint = temperaturaActual.toInt()
            Log.d("temp", tempint.toString())
            var lottieView: LottieAnimationView = findViewById(R.id.animacionTemperatura)
            var animacionResource = R.raw.sunny



            // Obtén solo la hora en formato de 24 horas
            val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

            val horaLimite = 19

            if (currentHour >= horaLimite) {
                    animacionResource = R.raw.night
                } else {
                    if (tempint < 20) {
                        animacionResource = R.raw.cloudy
                    }else animacionResource = R.raw.sunny
                }

                lottieView.setAnimation(animacionResource)
                lottieView.playAnimation()
                tvtemp.text = "Temperatura actual: $tempint" + "º"
                tviento.text = "$viento" + " km/h"
                tvhum.text = "Humedad: $humedad" + "%"

            }
        }
    }
