package com.joyjit.foodrunner.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.joyjit.foodrunner.R

lateinit var etName: EditText
lateinit var butRegister: Button
lateinit var etMobileNun: EditText
lateinit var etNewPassword: EditText
lateinit var etConPassword: EditText
lateinit var txtBack: TextView

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etName = findViewById(R.id.etName)
        butRegister =findViewById(R.id.butRegister)
        etMobileNun = findViewById(R.id.etMobileNum)
        etNewPassword = findViewById(R.id.etNewPassword)
        etConPassword = findViewById(R.id.etConPassword)
        txtBack = findViewById(R.id.txtBack)

        txtBack.setOnClickListener {
            val intent = Intent(this@RegisterActivity, Login::class.java)
            startActivity(intent)
            finish()
        }

        title = "Register Yourself"

        butRegister.setOnClickListener{
            val mobilenum = etMobileNun.text.toString()
            val NewPassword = etNewPassword.text.toString()
            val ConPassword = etConPassword.text.toString()
            val Name = etName.text.toString()

            if(mobilenum.isNotBlank() && NewPassword.isNotBlank() && ConPassword.isNotBlank() && Name.isNotBlank()){
                if (NewPassword==ConPassword){
                    val intent = Intent(this@RegisterActivity, Home_Page::class.java)
                    intent.putExtra("title",Name)
                    startActivity(intent)
                }else{
                    Toast.makeText(this@RegisterActivity,"Password not matched",Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this@RegisterActivity,"Fill all the boxes.",Toast.LENGTH_SHORT).show()
            }

        }
    }
    override fun onPause(){
        super.onPause()
        finish()
    }


}