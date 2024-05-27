package com.dam.recylerview_prueba.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dam.recylerview_prueba.Planta
import com.dam.recylerview_prueba.databinding.RvPlantItemBinding
import com.dam.recylerview_prueba.HomeFragmentDirections
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class RvPlantasAdapter(private val plantList: ArrayList<Planta>) : RecyclerView.Adapter<RvPlantasAdapter.ViewHolder>(){

    class ViewHolder(val binding : RvPlantItemBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(RvPlantItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
       return plantList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = plantList[position]
        holder.apply {
            binding.apply {
                tvNameItem.text = currentItem.name
                tvPlaceItem.text = currentItem.place
                tvIdItem.text = currentItem.id
                Picasso.get().load(currentItem.imgUri).into(imgItem)

                rvContainer.setOnClickListener {

                    val action = HomeFragmentDirections.actionHomeFragmentToUpdateFragment(
                        currentItem.id.toString(),
                        currentItem.name.toString(),
                        currentItem.place.toString()
                    )
                    findNavController(holder.itemView).navigate(action)
                }

                rvContainer.setOnLongClickListener{
                    MaterialAlertDialogBuilder(holder.itemView.context)
                        .setTitle("Eliminar planta")
                        .setMessage("¿Estás seguro de querer eliminar esta planta?")
                        .setPositiveButton("Eliminar"){_,_ ->
                            val firebaseRef = FirebaseDatabase.getInstance().getReference("plantas")
                            firebaseRef.child(currentItem.id.toString()).removeValue()
                                .addOnSuccessListener {
                                    Toast.makeText(holder.itemView.context, "Eliminada", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener{error ->
                                    Toast.makeText(holder.itemView.context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
                        .setNegativeButton("Cancelar"){_,_ ->
                            Toast.makeText(holder.itemView.context, "Cancelado", Toast.LENGTH_SHORT).show()
                        }
                        .show()

                    return@setOnLongClickListener true
                }
            }
        }
    }


}