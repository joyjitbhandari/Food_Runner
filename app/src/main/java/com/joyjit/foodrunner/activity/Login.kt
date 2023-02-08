package com.joyjit.foodrunner.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.joyjit.foodrunner.R

class Login : AppCompatActivity() {
    lateinit var txtDonthaveAcc: TextView
    lateinit var txtForgotPassword: TextView
    lateinit var butLogin: Button
    lateinit var etUsername: EditText
    lateinit var etPassword: EditText
    val validusername = "0000"
    val validpassword = "abcd"


    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedPreferences = getSharedPreferences(getString(R.string.Preference_file),MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn",false)

        if (isLoggedIn){
            val intent = Intent(this@Login, Home_Page::class.java)
            startActivity(intent)
            finish()
        }


        title = "Log in"
        txtDonthaveAcc = findViewById(R.id.txtDonthaveAcc)
        txtForgotPassword = findViewById(R.id.txtForgtPassword)
        butLogin = findViewById(R.id.butLogin)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)


        butLogin.setOnClickListener{
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username == validusername && password == validpassword){
                sharedPreferences.edit().putBoolean("isLoggedIn",true).apply()

                val intent = Intent(this@Login, Home_Page::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this@Login, "User Id or Password Incorrect", Toast.LENGTH_LONG).show()
            }
        }

        txtForgotPassword.setOnClickListener(){
            val intent = Intent(this@Login, Forgot_Password::class.java)
            startActivity(intent)
        }

        txtDonthaveAcc.setOnClickListener(){
            val intent = Intent(this@Login, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}