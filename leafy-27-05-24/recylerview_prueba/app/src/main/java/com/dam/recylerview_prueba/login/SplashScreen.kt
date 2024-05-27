package com.dam.recylerview_prueba.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.dam.recylerview_prueba.R

class splashScreen : AppCompatActivity() {
    private lateinit var foto: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        foto = findViewById(R.id.foto)
        foto.alpha = 0f
        foto.animate().setDuration(1500).alpha(1f).withEndAction{
           val i = Intent(this, SignInActivity::class.java)
            startActivity(i)

            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}