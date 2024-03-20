package com.dam.recylerview_prueba

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.utils.io.errors.IOException
import java.io.File


class post_prueba : AppCompatActivity() {
    private lateinit var boton: Button
    private val IMAGE1 = "../data/image_1.jpeg"
    val ORGAN1 = "flower"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_prueba)



    }
}