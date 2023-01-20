package com.msdev.arduinotemp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth


class RegisterActivity : AppCompatActivity() {
   private lateinit var etRegEmail: TextInputEditText
   private lateinit var etRegPassword: TextInputEditText
   private lateinit var tvLoginHere: TextView
   private lateinit var btnRegister: Button
   private lateinit var mAuth: FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.title = ""
        supportActionBar?.show()

        etRegEmail = findViewById(R.id.RegEmail)
        etRegPassword = findViewById(R.id.RegPass)
        tvLoginHere = findViewById(R.id.tvLoginHere)
        btnRegister = findViewById(R.id.btnRegister)
        mAuth = FirebaseAuth.getInstance()

        btnRegister.setOnClickListener {
            createUser()
        }

        tvLoginHere.setOnClickListener {
            startActivity(
                Intent(
                    this@RegisterActivity,
                    LoginActivity::class.java
                )
            )
        }
    }

    private fun createUser() {
        val email = etRegEmail.text.toString()
        val password = etRegPassword.text.toString()
        if (TextUtils.isEmpty(email)) {
            etRegEmail.error = "Email cannot be empty"
            etRegEmail.requestFocus()
        } else if (TextUtils.isEmpty(password)) {
            etRegPassword.error = "Password cannot be empty"
            etRegPassword.requestFocus()
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    user!!.sendEmailVerification().addOnCompleteListener{
                        if (task.isSuccessful){
                            Toast.makeText(
                                this@RegisterActivity,
                                "Verification link is Sent To The Email",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    Toast.makeText(
                        this@RegisterActivity,
                        "Registration Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Registration Error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
