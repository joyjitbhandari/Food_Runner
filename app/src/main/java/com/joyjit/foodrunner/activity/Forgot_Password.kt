package com.joyjit.foodrunner.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.joyjit.foodrunner.R

lateinit var butNext:Button
lateinit var etmobilenumber:EditText
lateinit var etemailaddress:EditText
lateinit var txtBackButton: TextView

class Forgot_Password : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot__password)

        butNext = findViewById(R.id.butNext)
        etmobilenumber = findViewById(R.id.etmobilenumber)
        etemailaddress = findViewById(R.id.etemailaddress)
        title = "Forgot Password"
        txtBackButton = findViewById(R.id.txtBackButton)
        txtBackButton.setOnClickListener {
            val intent = Intent(this@Forgot_Password, Login::class.java)
            startActivity(intent)
            finish()
        }

        butNext.setOnClickListener{
            val mobile = etmobilenumber.text.toString()
            val email = etemailaddress.text.toString()
            if(mobile.isNotBlank()&&email.isNotBlank()) {
                Toast.makeText(
                    this@Forgot_Password,
                    "OTP sent to your MobileNumber & EmailAddress",
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(this@Forgot_Password, Login::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this@Forgot_Password,"Fill the boxes",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}