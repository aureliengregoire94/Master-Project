package com.project.masterapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth= Firebase.auth
        val registerText: TextView = findViewById(R.id.textView_register_now)
        registerText.setOnClickListener{
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

        val loginButton: Button = findViewById(R.id.button_login)
        loginButton.setOnClickListener{
            perfomLogin()
        }
    }

    private fun perfomLogin() {
        val email: EditText = findViewById(R.id.editText_email_login)
        val password: EditText = findViewById(R.id.editText_password_login)

        // null check on inputs

        if (email.text.isEmpty() || password.text.isEmpty()){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val emailInput = email.text.toString()
        val passwordInput = password.text.toString()

        auth.signInWithEmailAndPassword(emailInput, passwordInput)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, let move to HomeActivity
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(
                        baseContext,
                        "Login successfully.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error occurred: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
    }


}