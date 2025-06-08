package com.example.obrasarteapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.buttonGoToRegister)
        val btnForgotPassword = findViewById<Button>(R.id.buttonForgotPassword)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this,
                    getString(R.string.completa_todos_los_campos), Toast.LENGTH_SHORT).show()
            }
        }


        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }


        btnForgotPassword.setOnClickListener {
            val email = etEmail.text.toString()

            if (email.isNotEmpty()) {
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this,
                                getString(R.string.correo_de_recuperaci_n_enviado), Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this,
                                getString(R.string.error, task.exception?.message), Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this,
                    getString(R.string.por_favor_ingresa_tu_correo_electr_nico), Toast.LENGTH_SHORT).show()
            }
        }
    }
}