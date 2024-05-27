package com.dam.recylerview_prueba

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dam.recylerview_prueba.databinding.FragmentUpdateBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class UpdateFragment : Fragment() {

    private var _binding : FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private var calendar: Calendar = Calendar.getInstance()
    private val args : com.dam.recylerview_prueba.UpdateFragmentArgs by navArgs()

    private lateinit var firebaseRef: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)

        firebaseRef = FirebaseDatabase.getInstance().getReference("plantas")
        binding.apply {
            edtUpdateName.setText(args.name)
            edtUpdatePlace.setText(args.place)
            btnUpdate.setOnClickListener {
                updateData()
                findNavController().navigate(R.id.action_updateFragment_to_homeFragment)
            }
        }

        val datePicker = DatePickerDialog.OnDateSetListener{ view, year, month, dayofMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayofMonth)
            updateLable(calendar)
        }


        binding.campana.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, month, dayofMonth ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayofMonth)


                    val notificationDate = calendar.timeInMillis
                    val nombreplanta = binding.edtUpdateName.text
                    scheduleNotification(notificationDate, nombreplanta)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

    return binding.root

    }

    private fun updateData() {
        val name = binding.edtUpdateName.text.toString()
        val place = binding.edtUpdatePlace.text.toString()

        val plants = Planta(args.id, name, place)

        firebaseRef.child(args.id).setValue(plants)
            .addOnCompleteListener {
                Toast.makeText(context, "Actualizado", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error al actualizar ${it.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    private fun updateLable(calendar: Calendar) {
   val formato = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(formato, Locale.getDefault())

    }
    private fun scheduleNotification(notificationDate: Long, nombreplanta: Editable) {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(requireContext(), NotificationReceiver::class.java)
        intent.putExtra("message", "¡$nombreplanta tiene sed!")

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.set(AlarmManager.RTC_WAKEUP, notificationDate, pendingIntent)
        Log.d("Notificacion", "Entra")
    }




}


