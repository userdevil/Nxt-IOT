@file:Suppress("DEPRECATION")

package com.msdev.arduinotemp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*

class ControlActivity : AppCompatActivity() {
    companion object {
        private const val REQUEST_CODE_STT = 1
    }
    private lateinit var mAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var dbref: DatabaseReference
    private lateinit var TextView: TextView
    private lateinit var UID:String
    private lateinit var EditText: TextInputEditText
    private lateinit var img_btn:ImageButton
    private lateinit var ImageButton: ImageButton
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)
        supportActionBar?.title = ""
        supportActionBar?.show()

        TextView = findViewById(R.id.et_text_input)
        img_btn = findViewById(R.id.send)
        ImageButton = findViewById(R.id.btn_tts)
        EditText = findViewById(R.id.editText)
        mAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        UID = mAuth.currentUser?.uid.toString()
        dbref = FirebaseDatabase.getInstance().reference.child("UserData")

        ImageButton.setOnClickListener {
            val sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            sttIntent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            sttIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now!")

            try {
                startActivityForResult(sttIntent, REQUEST_CODE_STT)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Toast.makeText(this, "Your device does not support STT.", Toast.LENGTH_LONG).show()
            }
        }

        img_btn.setOnClickListener {
                val input_text = EditText.text.toString()
                WriteData(input_text)
            EditText.text?.clear()
        }


    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_STT -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    result?.let {
                        val recognizedText = it[0]
                        TextView.text = recognizedText
                        if (UID.isNotEmpty()){
                            WriteData(recognizedText)
                        }
                    }
                }
            }
        }
    }

    private fun WriteData(text: String){
        dbref.child("$UID/Control").child("status").setValue(text).addOnCompleteListener {
            Toast.makeText(this,"$text Sent",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener { err ->
            Toast.makeText(this,"Error:${err.message}",Toast.LENGTH_SHORT).show()
        }
    }
}