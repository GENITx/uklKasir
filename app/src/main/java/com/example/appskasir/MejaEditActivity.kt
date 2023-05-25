package com.example.appskasir

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.appskasir.Database.CafeDatabase
import com.example.appskasir.Database.Constant
import com.example.appskasir.Database.Meja
import com.example.appskasir.databinding.ActivityMejaEditBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MejaEditActivity : AppCompatActivity() {
    lateinit var db: CafeDatabase
    private var Id: Int = 0
    private lateinit var binding : ActivityMejaEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMejaEditBinding.inflate(layoutInflater)
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
                getMeja()
            }
        }
    }

    private fun setupListener() {
        binding.btnUpdateMeja.setOnClickListener {
            val nameInput = binding.edtNameMeja.text.toString()
            if (nameInput.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    db.cafeDao().updateMeja(
                        Meja(
                            Id,
                            nameInput
                        )
                    )
                    finish()
                }
            }
        }
    }

    fun getMeja(){
        Id = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val meja = db.cafeDao().getMeja(Id)[0]
            binding.edtNameMeja.setText(meja.name)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}