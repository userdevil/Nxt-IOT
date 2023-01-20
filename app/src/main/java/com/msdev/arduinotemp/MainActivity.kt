package com.msdev.arduinotemp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var TextView: TextView
    private lateinit var TxtView: TextView
    private lateinit var TxView: TextView
    private lateinit var TView:TextView
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var dbref: DatabaseReference
    private lateinit var pr:String
    private lateinit var UID:String
    private lateinit var user:User
    private lateinit var imgview:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = ""
        supportActionBar?.show()
        pr = ""
        mAuth = FirebaseAuth.getInstance()
        TextView = findViewById(R.id.textView)
        TxtView = findViewById(R.id.txtView)
        TxView = findViewById(R.id.tv)
        TView = findViewById(R.id.textView4)
        imgview = findViewById(R.id.imageView3)
        firebaseDatabase = FirebaseDatabase.getInstance()
        UID = mAuth.currentUser?.uid.toString()
        dbref = FirebaseDatabase.getInstance().reference.child("UserData")
        val mail = mAuth.currentUser?.email.toString()
        Toast.makeText(
            this,
            "LogedIn As $mail",
            Toast.LENGTH_LONG
        ).show()
        if (UID.isNotEmpty()){
            getData()
        }


    }

    private fun getData(){
        dbref.child("$UID/reading").addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()) {
                    user = dataSnapshot.getValue(User::class.java)!!
                    //val jm = dataSnapshot.child("TEMPERATURE").value.toString()
                    //pr = dataSnapshot.child("PRESSURE").value.toString()
                    TextView.text = user.Temp + "°C"
                    TxtView.text = user.Humd + "%"
                    TxView.text = user.Pres + ""
                    pr = user.Pres.toString()
                }

                      if (pr.isNotEmpty()){
                          TxView.visibility = View.VISIBLE
                          imgview.visibility = View.VISIBLE
                          TView.visibility = View.VISIBLE
                      }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@MainActivity,"Database Error",Toast.LENGTH_LONG).show()
            }
        })
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.itemId


        if (id == R.id.action_two) {
            mAuth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            return true
        }
        if (id == R.id.action_three) {
            val user = mAuth.currentUser
            user!!.delete()
                .addOnCompleteListener { task ->
                   if (task.isSuccessful){
                       val intent = Intent(this, RegisterActivity::class.java)
                       startActivity(intent)
                   }
                }
            return true
        }
        if (id == R.id.action_fore) {
            val intent = Intent(this, Example::class.java)
            startActivity(intent)
            return true
        }
        if (id == R.id.action_five) {
            val intent = Intent(this, ControlActivity::class.java)
            startActivity(intent)
            return true
        }
        if (id == R.id.action_six) {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Coding With MS")
                var shareMessage = "Let me recommend you this application\n"
                shareMessage = """${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}""".trimIndent()
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                startActivity(Intent.createChooser(shareIntent, "choose one"))
            } catch (e: Exception) {
                //e.toString();
            }
            Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}