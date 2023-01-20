package com.msdev.arduinotemp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.text.style.BackgroundColorSpan
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.msdev.arduinotemp.R.drawable


class LoginActivity : AppCompatActivity() {
    private lateinit var etLoginEmail: TextInputEditText
    private lateinit var etLoginPassword: TextInputEditText
    private lateinit var tvRegisterHere: TextView
    private lateinit var forget: TextView
    private lateinit var btnLogin: Button
    private lateinit var mAuth: FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.title = ""
        supportActionBar?.show()

        val user = FirebaseAuth.getInstance().currentUser
        forget = findViewById(R.id.forget)
        etLoginEmail = findViewById(R.id.etLoginEmail)
        etLoginPassword = findViewById(R.id.etLoginPass)
        tvRegisterHere = findViewById(R.id.tvRegisterHere)
        btnLogin = findViewById(R.id.btnLogin)
        mAuth = FirebaseAuth.getInstance()

        if(user != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            this.finish()

        }
        btnLogin.setOnClickListener {
            loginUser()
        }
        tvRegisterHere.setOnClickListener {
            startActivity(
                Intent(
                    this@LoginActivity,
                    RegisterActivity::class.java
                )
            )
        }

        forget.setOnClickListener{
            showRecoverPasswordDialog()
        }

    }

    private fun loginUser() {
        val email = etLoginEmail.text.toString()
        val password = etLoginPassword.text.toString()
        if (TextUtils.isEmpty(email)) {
            etLoginEmail.error = "Email cannot be empty"
            etLoginEmail.requestFocus()
        } else if (TextUtils.isEmpty(password)) {
            etLoginPassword.error = "Password cannot be empty"
            etLoginPassword.requestFocus()
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@LoginActivity,
                        "User logged in successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                }else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Login Unsuccessful",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n", "ResourceType")
    private fun showRecoverPasswordDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Recover Password")
        val linearLayout = LinearLayout(this)
        val emailet = EditText(this)

        // write the email using which you registered
        emailet.hint = "Email"
        emailet.minEms = 16
        emailet.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        linearLayout.addView(emailet)
        linearLayout.setPadding(10, 10, 10, 10)
        builder.setView(linearLayout)

        // Click on Recover and a email will be sent to your registered email id
        builder.setPositiveButton(
            "Recover"
        ) { dialog, which ->
            val email = emailet.text.toString().trim { it <= ' ' }
            beginRecovery(email)
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, which ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    private fun beginRecovery(email: String) {
        // calling sendPasswordResetEmail
        // open your email and write the new
        // password and then you can login
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // if isSuccessful then done message will be shown
                // and you can change the password
                Toast.makeText(this@LoginActivity, "Email sent Successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@LoginActivity, "Error Occurred", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this@LoginActivity, "Error Failed", Toast.LENGTH_SHORT).show()
        }
    }
}
