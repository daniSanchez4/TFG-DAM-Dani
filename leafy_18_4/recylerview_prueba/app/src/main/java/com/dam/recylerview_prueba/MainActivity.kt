package com.dam.recylerview_prueba

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale


class MainActivity : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    var dataList: MutableList<DataClass>? = null
    var adapter: MyAdapter? = null
    var androidData: DataClass? = null
    var searchView: SearchView? = null
    private lateinit var arrow: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arrow = findViewById(R.id.arrow)

        arrow.setOnClickListener {
            val intent2 = Intent(this, prueba_ui_ux::class.java)
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
        androidData = DataClass("Cactus", R.string.camera, "Bajo", R.drawable.cactus)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData =
            DataClass("Pothos", R.string.recyclerview, "Medio", R.drawable.pothos)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Ficus", R.string.date, "Difícil", R.drawable.ficus)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Monstera", R.string.edit, "Kotlin", R.drawable.monstera)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Lengua de suegra", R.string.rating, "Java", R.drawable.suegra)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Aloe Vera", R.string.rating, "Java", R.drawable.aloe)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Cinta", R.string.rating, "Java", R.drawable.cinta)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Palmera de salón", R.string.rating, "Java", R.drawable.palmerasalon)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Suculenta", R.string.rating, "Java", R.drawable.suculenta)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Helecho", R.string.rating, "Java", R.drawable.helecho)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Pilea", R.string.rating, "Java", R.drawable.pilea)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Orquídea", R.string.rating, "Java", R.drawable.orquidea)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Bambú", R.string.rating, "Java", R.drawable.bambusuerte)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Drácena", R.string.rating, "Java", R.drawable.dracena)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Crotón", R.string.rating, "Java", R.drawable.croton)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Calathea", R.string.rating, "Java", R.drawable.calathea)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Hiedra", R.string.rating, "Java", R.drawable.hiedra)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Filodendro", R.string.rating, "Java", R.drawable.filodendro)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Crisantemo", R.string.rating, "Java", R.drawable.crisantemo)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Violeta", R.string.rating, "Java", R.drawable.violeta)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Fresia", R.string.rating, "Java", R.drawable.fresia)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Geranio", R.string.rating, "Java", R.drawable.geranio)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Lirio de paz", R.string.rating, "Java", R.drawable.lirio)
        (dataList as ArrayList<DataClass>).add(androidData!!)
        androidData = DataClass("Azalea", R.string.rating, "Java", R.drawable.azalea)
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