package com.dam.recylerview_prueba

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.dam.recylerview_prueba.databinding.FragmentAddBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class AddFragment : Fragment() {

    private var _binding : FragmentAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseReference: DatabaseReference
    private lateinit var storageRef: StorageReference
    private var uri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=FragmentAddBinding.inflate(inflater, container, false)
        firebaseReference = FirebaseDatabase.getInstance().getReference("plantas")
        storageRef = FirebaseStorage.getInstance().getReference("Images")

        binding.btnAdd.setOnClickListener {
            saveData()
        }

        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){
            binding.imgAdd.setImageURI(it)
            if (it != null){
                uri = it
            }
        }

        binding.btnPickImage.setOnClickListener {
            pickImage.launch("image/*")
        }

        return binding.root
    }

    private fun saveData() {
        val name = binding.editTextText.text.toString()
        val place = binding.editTextText2.text.toString()

        if (name.isEmpty()) binding.editTextText.error = "Obligatorio"
        if (place.isEmpty()) binding.editTextText2.error = "Obligatorio"

        val plantId = firebaseReference.push().key!!
        var plants : Planta
        if (name.isEmpty() || place.isEmpty()) {
            Toast.makeText(context, "Los campos son obligatorios", Toast.LENGTH_SHORT).show()
        } else {

            uri?.let {
                storageRef.child(plantId).putFile(it)
                    .addOnSuccessListener {task ->
                        task.metadata!!.reference!!.downloadUrl
                            .addOnSuccessListener { url ->
                                Toast.makeText(context, "Foto añadida", Toast.LENGTH_SHORT).show()
                                val imgUrl = url.toString()

                                plants = Planta(plantId, name, place, imgUrl)

                                firebaseReference.child(plantId).setValue(plants)
                                    .addOnCompleteListener {
                                        Toast.makeText(context, "añadido", Toast.LENGTH_SHORT).show()
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(context, "error al añadir ${it.message}", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                            }
                    }
            }
        }

    }
}