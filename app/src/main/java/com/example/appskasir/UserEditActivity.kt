package com.example.appskasir

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.appskasir.Database.CafeDatabase
import com.example.appskasir.Database.Constant
import com.example.appskasir.Database.User
import com.example.appskasir.databinding.ActivityUserEditBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserEditActivity : AppCompatActivity() {
    lateinit var db: CafeDatabase
    private var Id: Int = 0
    private lateinit var binding : ActivityUserEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = CafeDatabase.getInstance(applicationContext)
        setupView()
        setupListener()
    }

    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when(intentType){
            Constant.TYPE_UPDATE -> {
                getUser()
                binding.linear2.visibility = View.GONE
                binding.viewTitle.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                getUser()
                binding.linear1.visibility = View.GONE
            }
        }
    }

    private fun setupListener() {
        binding.btnUpdateUser.setOnClickListener {
            val nameInput = binding.edtName.text.toString()
            val emailInput = binding.edtEmail.text.toString()
            val passInput = binding.edtPass.text.toString()
            if (nameInput.isEmpty() || emailInput.isEmpty() || passInput.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    db.cafeDao().updateUser(
                        User(
                            Id,
                            nameInput,
                            emailInput,
                            passInput
                        )
                    )
                    finish()
                }
            }
        }
    }

    fun getUser(){
        Id = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val user = db.cafeDao().getUser(Id)[0]

            binding.viewTitle.setText(user.name)
            binding.viewEmail.setText(user.email)
//            binding.viewPass.setText(user.password)

            binding.edtName.setText(user.name)
            binding.edtEmail.setText(user.email)
            binding.edtPass.setText(user.password)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}