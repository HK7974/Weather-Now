package com.example.weathernow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.weathernow.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class signup : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.button1.setOnClickListener {
            val intent = Intent(this, signin::class.java)
            startActivity(intent)
        }
        binding.button.setOnClickListener {
            val email = binding.emailtext.text.toString()
            val pass = binding.passwordtext.text.toString()
            val repass = binding.repasswordtext.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && repass.isNotEmpty()) {
                if (pass == repass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, signin::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter the details", Toast.LENGTH_SHORT).show()
            }
        }
    }
}