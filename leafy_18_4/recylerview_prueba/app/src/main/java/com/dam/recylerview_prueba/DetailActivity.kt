package com.dam.recylerview_prueba


import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity



class DetailActivity : AppCompatActivity() {
    var detailDesc: TextView? = null
    var detailTitle: TextView? = null
    var detailImage: ImageView? = null
    var detailLang: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        detailDesc = findViewById(R.id.detailDesc)
        detailTitle = findViewById(R.id.detailTitle)
        detailImage = findViewById(R.id.detailImage)
        detailLang = findViewById(R.id.detailLang)
        val bundle = intent.extras
        if (bundle != null) {
            detailDesc?.setText(bundle.getInt("Desc"))
            detailImage?.setImageResource(bundle.getInt("Image"))
            detailTitle?.setText(bundle.getString("Title"))
            detailLang?.setText(bundle.getString("Lang"))

        }

    }

}