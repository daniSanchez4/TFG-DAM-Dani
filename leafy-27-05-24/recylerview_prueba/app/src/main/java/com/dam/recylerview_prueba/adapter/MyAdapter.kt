package com.dam.recylerview_prueba.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.dam.recylerview_prueba.DataClass
import com.dam.recylerview_prueba.R
import com.dam.recylerview_prueba.activity.DetailActivity


class MyAdapter(private val context: Context, private var dataList: List<DataClass>) :
    RecyclerView.Adapter<MyViewHolder>() {
    fun setSearchList(dataSearchList: List<DataClass>) {
        dataList = dataSearchList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.recImage.setImageResource(dataList[position].dataImage)
        holder.recTitle.text = dataList[position].dataTitle
        holder.recDesc.setText(dataList[position].dataDesc)
        holder.recLang.text = dataList[position].dataLang
        holder.recCard.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("Image", dataList[holder.adapterPosition].dataImage)
            intent.putExtra("Title", dataList[holder.adapterPosition].dataTitle)
            intent.putExtra("Desc", dataList[holder.adapterPosition].dataDesc)
            intent.putExtra("Lang", dataList[holder.adapterPosition].dataLang)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var recImage: ImageView
    var recTitle: TextView
    var recDesc: TextView
    var recLang: TextView
    var recCard: CardView

    init {
        recImage = itemView.findViewById(R.id.recImage)
        recTitle = itemView.findViewById(R.id.recTitle)
        recDesc = itemView.findViewById(R.id.recDesc)
        recLang = itemView.findViewById(R.id.recLang)
        recCard = itemView.findViewById(R.id.recCard)
    }
}