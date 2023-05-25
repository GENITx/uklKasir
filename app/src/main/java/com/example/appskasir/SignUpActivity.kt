package com.example.appskasir

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appskasir.Adapter.AdapterMenu
import com.example.appskasir.Adapter.AdapterUser
import com.example.appskasir.Database.CafeDatabase
import com.example.appskasir.Database.Constant
import com.example.appskasir.Database.Menu
import com.example.appskasir.Database.User
import com.example.appskasir.databinding.ActivitySignUpBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpActivity : AppCompatActivity() {

    lateinit var db: CafeDatabase

    private lateinit var binding : ActivitySignUpBinding

    lateinit var adapterUser: AdapterUser

    private var Id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        db = CafeDatabase.getInstance(applicationContext)
        setContentView(binding.root)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setupListener()
        setupRecycleView()
    }

    override fun onStart() {
        super.onStart()
        loadRV()
    }

    fun loadRV(){
        CoroutineScope(Dispatchers.IO).launch {
            val users = db.cafeDao().getUser()
            Log.d("MainActivity", "dbResponse: $users")
            withContext(Dispatchers.Main){
                adapterUser.setData(users)
            }
        }
    }

    fun intentEdit(todoId: Int, intentType: Int){
        startActivity(
            Intent(applicationContext, UserEditActivity::class.java)
                .putExtra("intent_id", todoId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun setupRecycleView() {
        adapterUser = AdapterUser(arrayListOf(), object : AdapterUser.OnAdapterListener {
            override fun onRead(user: User) {
                intentEdit(user.id, Constant.TYPE_READ)
            }

            override fun onUpdate(user: User) {
                intentEdit(user.id, Constant.TYPE_UPDATE)
            }

            override fun onDelete(user: User) {
                deleteDialog(user)
            }

        })
        binding.listUser.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = adapterUser
        }
    }

    private fun deleteDialog(user: User){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Confirmation")
            setMessage("Sure to remove \"${user.name}\"?" )
            setNegativeButton("Cancle") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Remove") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.cafeDao().deleteUser(user)
                    loadRV()
                }
            }
        }
        alertDialog.show()
    }

    private fun setupListener() {
        binding.btnSaveUser.setOnClickListener {
            val nameInput = binding.edtName.text.toString()
            val emailInput = binding.edtEmail.text.toString()
            val passInput = binding.edtPass.text.toString()

            if (nameInput.isEmpty() || emailInput.isEmpty() || passInput.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    db.cafeDao().addUser(
                        User(
                            0,
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
