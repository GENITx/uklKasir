package com.example.appskasir

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import com.example.appskasir.Database.CafeDatabase
import com.example.appskasir.Database.User
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var email: TextInputEditText
    private lateinit var btnSend: Button
    private lateinit var btnSignUp: Button

    private lateinit var PassInput: TextInputEditText
    private lateinit var ShowPass: CheckBox

    lateinit var db: CafeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = CafeDatabase.getInstance(applicationContext)

        email = findViewById(R.id.input_email)
        btnSend = findViewById(R.id.btn_login)
        btnSignUp = findViewById(R.id.btn_signUp)
        PassInput = findViewById(R.id.pass)

//        LOGIN BUTTON (IDK)
        btnSend.setOnClickListener {
            if(email.text.toString().isNotEmpty() && PassInput.text.toString().isNotEmpty()){
                var list: List<User> = db.cafeDao().login(email.text.toString(), PassInput.text.toString())
                if(list.size > 0){
                    val moveIntent = Intent(this, HomeActivity::class.java)
                    val name = list[0].name
                    val id_user = list[0].id
                    moveIntent.putExtra("name", name)
                    moveIntent.putExtra("id_user", id_user)
                    startActivity(moveIntent)
                }
                else{
                    Toast.makeText(applicationContext, "User not found", Toast.LENGTH_SHORT).show()
                }
            }
        }

//        SignUp
        btnSignUp.setOnClickListener {
            try {
                val i = Intent(this, SignUpActivity::class.java)
                startActivity(i)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "ERROR, TRY AGAIN!", Toast.LENGTH_SHORT).show()
            }
        }

//        CHECKBOX
//        ShowPass.setOnClickListener {
//            if (ShowPass.isChecked) {
//                PassInput.transformationMethod = HideReturnsTransformationMethod.getInstance()
//            } else {
//                PassInput.transformationMethod = PasswordTransformationMethod.getInstance()
//            }
//        }
    }
}