package com.dam.recylerview_prueba.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dam.recylerview_prueba.DataClass
import com.dam.recylerview_prueba.adapter.MyAdapter
import com.dam.recylerview_prueba.R
import com.dam.recylerview_prueba.plantnet.post_prueba
import java.util.Locale


class MainActivity : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    var dataList: MutableList<DataClass>? = null
    var adapter: MyAdapter? = null
    var androidData: DataClass? = null
    var searchView: SearchView? = null
    private lateinit var arrow: ImageButton
    private lateinit var card: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arrow = findViewById(R.id.arrow)
        card = findViewById(R.id.cardView)


        arrow.setOnClickListener {
            val intent2 = Intent(this, prueba_ui_ux::class.java)
            startActivity(intent2)
        }

        card.setOnClickListener {
            val intent2 = Intent(this, post_prueba::class.java)
            startActivity(intent2)
        }


        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.search)
        searchView?.clearFocus()
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchList(newText)
                return true
            }
        })

        val gridLayoutManager = GridLayoutManager(this@MainActivity, 1)
        recyclerView?.setLayoutManager(gridLayoutManager)

        dataList = ArrayList()
        androidData = DataClass("Cactus", R.string.cactus, "Bajo", R.drawable.cactus)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData =
            DataClass("Pothos", R.string.pothos, "Medio", R.drawable.pothos)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Ficus", R.string.ficus, "Difícil", R.drawable.ficus)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Monstera", R.string.monstera, "Kotlin", R.drawable.monstera)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Lengua de suegra", R.string.lengua, "Java", R.drawable.suegra)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Aloe Vera", R.string.aloe, "Java", R.drawable.aloe)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Cinta", R.string.cinta, "Java", R.drawable.cinta)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Palmera de salón", R.string.palmera, "Java", R.drawable.palmerasalon)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Suculenta", R.string.suculenta, "Java", R.drawable.suculenta)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Helecho", R.string.helecho, "Java", R.drawable.helecho)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Pilea", R.string.pilea, "Java", R.drawable.pilea)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Orquídea", R.string.orquidea, "Java", R.drawable.orquidea)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Bambú", R.string.bambu, "Java", R.drawable.bambusuerte)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Drácena", R.string.dracena, "Java", R.drawable.dracena)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Crotón", R.string.croton ,"Java", R.drawable.croton)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Calathea", R.string.calathea, "Java", R.drawable.calathea)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Hiedra", R.string.hiedra, "Java", R.drawable.hiedra)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Filodendro", R.string.filodendro, "Java", R.drawable.filodendro)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Crisantemo", R.string.crisantemo, "Java", R.drawable.crisantemo)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Violeta", R.string.violeta, "Java", R.drawable.violeta)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Fresia", R.string.fresia, "Java", R.drawable.fresia)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Geranio", R.string.geranio, "Java", R.drawable.geranio)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Lirio de paz", R.string.lirio, "Java", R.drawable.lirio)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Azalea", R.string.azalea, "Java", R.drawable.azalea)
        (dataList as ArrayList<DataClass>).add(androidData!!)

        adapter = MyAdapter(this@MainActivity, dataList as ArrayList<DataClass>)
        recyclerView?.setAdapter(adapter)
    }

        private fun searchList(text: String) {
        val dataSearchList: MutableList<DataClass> = ArrayList()
        for (data in dataList!!) {
            if (data.dataTitle.lowercase(Locale.getDefault())
                    .contains(text.lowercase(Locale.getDefault()))
            ) {
                dataSearchList.add(data)
            }
        }
        if (dataSearchList.isEmpty()) {
            Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show()
        } else {
            adapter!!.setSearchList(dataSearchList)
        }
    }



}