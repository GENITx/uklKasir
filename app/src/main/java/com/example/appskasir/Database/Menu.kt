package com.example.appskasir.Database

import androidx.room.*

@Entity
data class Menu (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val desc: String,
    val price: String
)