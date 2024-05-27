package com.dam.recylerview_prueba

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dam.recylerview_prueba.activity.MainActivity
import com.dam.recylerview_prueba.adapter.RvPlantasAdapter
import com.dam.recylerview_prueba.databinding.FragmentHomeBinding
import com.dam.recylerview_prueba.activity.prueba_ui_ux
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Locale


class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var plantList: ArrayList<Planta>
    private lateinit var firebaseRef : DatabaseReference



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=FragmentHomeBinding.inflate(inflater, container, false)

        binding.arrow.setOnClickListener {
            val intent2 = Intent(requireContext(), prueba_ui_ux::class.java)
            startActivity(intent2)
        }

        binding.cardView.setOnClickListener {
            val intent2 = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent2)
        }






        val recyclerView = binding.rvPlants
        val animation = AnimationUtils.loadAnimation(context, R.anim.animacion_rv)
        recyclerView.startAnimation(animation)

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }
        firebaseRef = FirebaseDatabase.getInstance().getReference("plantas")
        plantList = arrayListOf()
        fetchData()

        binding.rvPlants.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
        }
        return binding.root






    }

    private fun fetchData() {
        firebaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               plantList.clear()
                if(snapshot.exists()){
                    for (plantSnap in snapshot.children){
                        val plants = plantSnap.getValue(Planta::class.java)
                        plantList.add(plants!!)
                    }
                }
                val rvAdapter = RvPlantasAdapter(plantList)
                binding.rvPlants.adapter = rvAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "error: $error", Toast.LENGTH_SHORT).show()
            }

        })
    }













}