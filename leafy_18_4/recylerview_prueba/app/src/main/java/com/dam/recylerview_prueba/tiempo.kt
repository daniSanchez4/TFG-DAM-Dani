package com.dam.recylerview_prueba

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
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
import java.util.Locale

class tiempo : AppCompatActivity() {


    private lateinit var tvtemp: TextView
    private lateinit var tviento: TextView
    private lateinit var tvhum: TextView
    private lateinit var temperaturaActual: String
    private lateinit var humedad: String
    private lateinit var viento: String
    private lateinit var titulo: TextView
    private lateinit var sol: ImageView
    private lateinit var textoTemp: TextView
    private lateinit var card: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiempo)

        tvtemp = findViewById(R.id.textoTemp)
        tviento = findViewById(R.id.textoViento)
        tvhum = findViewById(R.id.textohumedad)
        titulo = findViewById(R.id.titulo)
        sol = findViewById(R.id.sol)
        textoTemp = findViewById(R.id.textoTemp)
        card = findViewById(R.id.card)


        val infoFecha = obtenerInfoFecha()
        titulo.text = infoFecha
        animateViewWithSlideUp(titulo, 1000)
        animateImageView(sol)
        animateView(textoTemp)
        animateViewWithSlideUp(card, 1000)
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

            //var animacionResource = R.raw.sunny

            val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

            val horaLimite = 19
/*
            if (currentHour >= horaLimite) {
                    animacionResource = R.raw.night
                } else {
                    if (tempint < 20) {
                        animacionResource = R.raw.cloudy
                    }else animacionResource = R.raw.sunny
                }
*/
               // lottieView.setAnimation(animacionResource)

                tvtemp.text = "$tempint" + "º"
                tviento.text = "$viento" + " km/h"
                tvhum.text = "$humedad" + "%"

            }
        }
    fun obtenerInfoFecha(): String {
        // Obtener una instancia de Calendar
        val calendario = Calendar.getInstance()

        // Obtener el día de la semana como un número, donde Domingo es 1 y Sábado es 7
        val diaSemana = calendario.get(Calendar.DAY_OF_WEEK)

        // Obtener el número del día del mes
        val diaMes = calendario.get(Calendar.DAY_OF_MONTH)

        // Obtener el nombre del mes
        val formatoMes = SimpleDateFormat("MMMM", Locale("es", "ES"))
        val nombreMes = formatoMes.format(calendario.time)



        // Convertir el número del día de la semana a un nombre de día
        val nombreDia = when (diaSemana) {
            Calendar.SUNDAY -> "Domingo"
            Calendar.MONDAY -> "Lunes"
            Calendar.TUESDAY -> "Martes"
            Calendar.WEDNESDAY -> "Miércoles"
            Calendar.THURSDAY -> "Jueves"
            Calendar.FRIDAY -> "Viernes"
            Calendar.SATURDAY -> "Sábado"
            else -> ""
        }

        return "$nombreDia, $diaMes $nombreMes"
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
        animator.duration = 1000
        animator.start()
    }



    }
