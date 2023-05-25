package com.example.appskasir

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.appskasir.Database.CafeDatabase
import com.example.appskasir.Database.Constant
import com.example.appskasir.Database.Meja
import com.example.appskasir.Database.Menu
import com.example.appskasir.databinding.ActivityEditMenuBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuEditActivity : AppCompatActivity() {

    lateinit var db:CafeDatabase

    private var Id: Int = 0

    private lateinit var binding : ActivityEditMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = CafeDatabase.getInstance(applicationContext)
        setupView()
        setupListener()
    }

    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when(intentType){
            Constant.TYPE_CREATE -> {
                binding.newTask.visibility = View.GONE
                binding.linear1.visibility = View.GONE
                binding.editTask.visibility = View.GONE
                binding.linear2.visibility = View.GONE
                binding.newTable.visibility = View.GONE
                binding.linear3.visibility = View.GONE

                binding.btnAddMenu.setOnClickListener {
                    binding.btnAddMenu.visibility = View.GONE
                    binding.btnAddMeja.visibility = View.GONE
                    binding.btnUpdate.visibility = View.GONE
                    binding.newTask.visibility = View.VISIBLE
                    binding.linear1.visibility = View.VISIBLE
                }

                binding.btnAddMeja.setOnClickListener {
                    binding.btnAddMenu.visibility = View.GONE
                    binding.btnAddMeja.visibility = View.GONE
                    binding.newTable.visibility = View.VISIBLE
                    binding.linear3.visibility = View.VISIBLE
                }
            }
            Constant.TYPE_READ -> {
                binding.newTask.visibility = View.GONE
                binding.editTask.visibility = View.GONE
                binding.linear1.visibility = View.GONE
                binding.btnAddMenu.visibility = View.GONE
                binding.btnAddMeja.visibility = View.GONE
                binding.newTable.visibility = View.GONE
                binding.linear3.visibility = View.GONE

                getMenu()

            }
            Constant.TYPE_UPDATE -> {
                binding.btnSave.visibility = View.GONE
                binding.newTask.visibility = View.GONE
                binding.linear2.visibility = View.GONE
                binding.btnAddMenu.visibility = View.GONE
                binding.btnAddMeja.visibility = View.GONE
                binding.newTable.visibility = View.GONE
                binding.linear3.visibility = View.GONE

                getMenu()

            }
        }
    }

    private fun setupListener() {
//        Menu
        binding.btnSave.setOnClickListener {
            val nameInput = binding.edtName.text.toString()
            val descInput = binding.edtDesc.text.toString()
            val priceInput = binding.edtPrice.text.toString()

            if (nameInput.isEmpty() || descInput.isEmpty() || priceInput.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    db.cafeDao().addMenu(
                        Menu(
                            0,
                            nameInput,
                            descInput,
                            priceInput
                        )
                    )
                    finish()
                }
            }
        }

        binding.btnUpdate.setOnClickListener {
            val nameInput = binding.edtName.text.toString()
            val descInput = binding.edtDesc.text.toString()
            val priceInput = binding.edtPrice.text.toString()

            if (nameInput.isEmpty() || descInput.isEmpty() || priceInput.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    db.cafeDao().updateMenu(
                        Menu(
                            Id,
                            nameInput,
                            descInput,
                            priceInput
                        )
                    )
                    finish()
                }
            }
        }

//        Meja
        binding.btnSaveMeja.setOnClickListener {
            val nameInput = binding.edtNameMeja.text.toString()

            if (nameInput.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    db.cafeDao().addMeja(
                        Meja(
                            0,
                            nameInput
                        )
                    )
                    finish()
                }
            }
        }
    }

    fun getMenu(){
        Id = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val menu = db.cafeDao().getMenu(Id)[0]
            binding.viewTitle.setText(menu.name)
            binding.viewDesc.setText(menu.desc)
            binding.viewPrice.setText("Rp " + menu.price)

            binding.edtName.setText(menu.name)
            binding.edtDesc.setText(menu.desc)
            binding.edtPrice.setText(menu.price)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}