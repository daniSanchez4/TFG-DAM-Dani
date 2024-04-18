package com.dam.recylerview_prueba

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.airbnb.lottie.LottieAnimationView

class prueba_ui_ux : AppCompatActivity() {
    private lateinit var jardin: CardView
    private lateinit var temp: CardView
    private lateinit var iden: CardView
    private lateinit var colec: CardView
    private lateinit var tv: TextView
    private lateinit var maceta: ImageView
    private lateinit var nube: ImageView
    private lateinit var label1: TextView
    private lateinit var label2: TextView
    private lateinit var label3: TextView
    private lateinit var label4: TextView
    private lateinit var label5: TextView
    private lateinit var label6: TextView
    private lateinit var label7: TextView
    private lateinit var labelinteres: TextView
    private lateinit var lupa: ImageView
    private lateinit var scaner: LottieAnimationView
    private lateinit var hoja1: ImageView
    private lateinit var hoja2: ImageView
    private lateinit var hoja3: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prueba_ui_ux)

        jardin = findViewById(R.id.cardjardin)
        temp = findViewById(R.id.cardtiempo)
        iden = findViewById(R.id.cardiden)
        colec = findViewById(R.id.cardaprende)
        tv = findViewById(R.id.bv)
        maceta = findViewById(R.id.maceta)
        nube = findViewById(R.id.nube)
        label1 = findViewById(R.id.labeljardin)
        label2 = findViewById(R.id.labeltiempo)
        label3 = findViewById(R.id.labelinteres)
        label4 = findViewById(R.id.labelscaner)
        label5 = findViewById(R.id.sublabelscaner)
        label6 = findViewById(R.id.labelaprende)
        label7 = findViewById(R.id.sublabelaprende)
        lupa= findViewById(R.id.lupa)
        scaner = findViewById(R.id.animacionscaner)
        hoja1 = findViewById(R.id.hoja1)
        hoja2 = findViewById(R.id.hoja2)
        hoja3 = findViewById(R.id.hoja3)
        labelinteres= findViewById(R.id.labelinteres)


        jardin.setOnClickListener {
            val intent2 = Intent(this, Crud::class.java)
            startActivity(intent2)
        }

        temp.setOnClickListener {
            val intent2 = Intent(this, tiempo::class.java)
            startActivity(intent2)
        }

        iden.setOnClickListener {
            val intent2 = Intent(this, post_prueba::class.java)
            startActivity(intent2)
        }

        colec.setOnClickListener {
            val intent2 = Intent(this, MainActivity::class.java)
            startActivity(intent2)
        }

        animateView(jardin)
        animateView(temp)
        animateView(iden)
        animateView(colec)
        animateView(nube)
        animateView(maceta)
        animateView(label1)
        animateView(label2)
        animateView(label3)
        animateView(label4)
        animateView(label5)
        animateView(label6)
        animateView(label7)
        animateView(lupa)
        animateView(scaner)
        animateView(hoja1)
        animateView(hoja2)
        animateView(hoja3)

        animateViewWithSlideUp(tv, 1000)
        animateView(labelinteres)
        animateImageView(hoja1)
        animateImageView(hoja2)
        animateImageView(hoja3)

    }


    private fun animateView(view: View) {
        view.alpha = 0f
        val animator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        animator.duration = 1000
        animator.start()
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

        val scaleXAnimator = ObjectAnimator.ofFloat(imageView, "scaleX", 1.6f)
        val scaleYAnimator = ObjectAnimator.ofFloat(imageView, "scaleY", 1.6f)

        scaleXAnimator.duration = 1000
        scaleYAnimator.duration = 1000

        scaleXAnimator.start()
        scaleYAnimator.start()
    }
}