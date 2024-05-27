package com.dam.recylerview_prueba.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.dam.recylerview_prueba.R
import com.dam.recylerview_prueba.databinding.ActivitySignUpBinding
import com.dam.recylerview_prueba.interfaz.Animaciones
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity(), Animaciones {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        animateView(binding.textView)
        animateView(binding.button)
        animateView(binding.confirm)
        animateView(binding.contra)
        animateView(binding.emailEt)
        animateView(binding.passET)
        animateView(binding.correo)
        animateView(binding.confirmPassEt)
        animateViewWithSlideUp(binding.registrate, 1000)
        animateViewWithSlideUp(binding.sublabel, 1300)
        animateViewWithSlideUp(binding.logo, 1000)




        binding.emailEt.setTextColor(ContextCompat.getColor(this, R.color.black))
        binding.passET.setTextColor(ContextCompat.getColor(this, R.color.black))
        binding.confirmPassEt.setTextColor(ContextCompat.getColor(this, R.color.black))
        firebaseAuth = FirebaseAuth.getInstance()

        binding.textView.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()

            if((email.isNotEmpty()) && pass.isNotEmpty() && confirmPass.isNotEmpty()){
                if(pass == confirmPass){

                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if(it.isSuccessful){
                            val intent = Intent(this, SignInActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(this, "No coinciden las contraseñas", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "No pueden haber campos vacíos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}