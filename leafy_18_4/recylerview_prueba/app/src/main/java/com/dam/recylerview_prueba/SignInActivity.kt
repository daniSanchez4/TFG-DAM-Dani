package com.dam.recylerview_prueba

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.dam.recylerview_prueba.databinding.ActivitySignInBinding


import com.google.firebase.auth.FirebaseAuth


class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var remember: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPref = getSharedPreferences("prefs", MODE_PRIVATE)
        val editor = sharedPref.edit()

        binding.emailEt.setTextColor(ContextCompat.getColor(this, R.color.black))
        binding.passET.setTextColor(ContextCompat.getColor(this, R.color.black))

        remember = findViewById(R.id.remember_me_chkb)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.textView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()


            if ((email.isNotEmpty()) && pass.isNotEmpty()) {

                val progressDialog = ProgressDialog(this)
                progressDialog.setMessage("Iniciando sesión...")
                progressDialog.show()

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    progressDialog.dismiss()
                    if (it.isSuccessful) {


                        editor.putString("email", email)
                        editor.putString("password", pass)
                        editor.apply()


                        val intent = Intent(this, prueba_ui_ux::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

            } else {
                Toast.makeText(this, "No pueden haber campos vacíos", Toast.LENGTH_SHORT).show()
            }
        }

        remember.setOnClickListener {


            val correo = sharedPref.getString("email", null)
            val con = sharedPref.getString("password", null)

            binding.emailEt.setText(correo)
            binding.passET.setText(con)

        }

        binding.forgotPW.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.dialog_pwforgot, null)
            val userEmail = view.findViewById<EditText>(R.id.editBox)

            builder.setView(view)
            val dialog = builder.create()

            view.findViewById<Button>(R.id.btnReset).setOnClickListener {
                compareEmail(userEmail)
                dialog.dismiss()
            }
            view.findViewById<Button>(R.id.btnCancel).setOnClickListener {
                dialog.dismiss()
            }
            if (dialog.window != null){
                dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
            }
                dialog.show()
        }
    }

    private fun compareEmail(email: EditText){

        if(email.text.toString().isEmpty()){
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
            return
        }
        firebaseAuth.sendPasswordResetEmail(email.text.toString()).addOnCompleteListener { task ->
            if (task.isSuccessful){
                Toast.makeText(this, "Revisa tu correo electrónico", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


