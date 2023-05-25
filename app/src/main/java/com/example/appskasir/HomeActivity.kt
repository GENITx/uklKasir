package com.example.appskasir

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appskasir.Adapter.AdapterMenu
import com.example.appskasir.Adapter.AdapterTable
import com.example.appskasir.Database.*
import com.example.appskasir.databinding.ActivityHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : AppCompatActivity() {

    private lateinit var txtHello: TextView
    private var userName: String? = null

    private lateinit var binding : ActivityHomeBinding

    lateinit var adapterMenu: AdapterMenu
    lateinit var adapterTable: AdapterTable

    lateinit var db: CafeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = CafeDatabase.getInstance(applicationContext)
        setupListener()
        setupRecyclerView()

        txtHello = findViewById(R.id.tampil)

        val extras = intent.extras
        userName = extras?.getString("name")
        txtHello.text = "Welcome, $userName"
    }

    override fun onStart() {
        super.onStart()
        loadRV()
    }

    fun loadRV(){
        CoroutineScope(Dispatchers.IO).launch {
            val menus = db.cafeDao().getMenu()
            val tables = db.cafeDao().getMeja()
            Log.d("MainActivity", "dbResponse: $menus")
            withContext(Dispatchers.Main){
                adapterMenu.setData(menus)
                adapterTable.setData(tables)
            }
        }
    }

    private fun setupListener() {
        binding.buttonCreate.setOnClickListener {
            intentEdit(0,Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(todoId: Int, intentType: Int){
        startActivity(
            Intent(applicationContext, MenuEditActivity::class.java)
                .putExtra("intent_id", todoId)
                .putExtra("intent_type", intentType)
        )
    }

    fun intentEditMeja(todoId: Int, intentType: Int){
        startActivity(
            Intent(applicationContext, MejaEditActivity::class.java)
                .putExtra("intent_id", todoId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun setupRecyclerView() {
        adapterMenu = AdapterMenu(arrayListOf(), object : AdapterMenu.OnAdapterListener {
            override fun onRead(menu: Menu) {
                intentEdit(menu.id, Constant.TYPE_READ)
            }

            override fun onUpdate(menu: Menu) {
                intentEdit(menu.id, Constant.TYPE_UPDATE)
            }

            override fun onDelete(menu: Menu) {
                deleteDialog(menu)
            }

        })
        binding.listMenu.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = adapterMenu
        }

        adapterTable = AdapterTable(arrayListOf(), object : AdapterTable.OnAdapterListener {
            override fun onUpdate(meja: Meja) {
                intentEditMeja(meja.id, Constant.TYPE_UPDATE)
            }

            override fun onDelete(meja: Meja) {
                deleteDialog(meja)
            }

        })
        binding.listMeja.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = adapterTable
        }
    }

    private fun deleteDialog(menu: Menu){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Confirmation")
            setMessage("Sure to remove \"${menu.name}\"?" )
            setNegativeButton("Cancle") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Remove") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.cafeDao().deleteMenu(menu)
                    loadRV()
                }
            }
        }
        alertDialog.show()
    }

    private fun deleteDialog(meja: Meja){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Confirmation")
            setMessage("Sure to remove \"${meja.name}\"?" )
            setNegativeButton("Cancle") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Remove") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.cafeDao().deleteMeja(meja)
                    loadRV()
                }
            }
        }
        alertDialog.show()
    }
}