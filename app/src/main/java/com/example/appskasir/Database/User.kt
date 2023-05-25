package com.example.appskasir.Database

import androidx.room.*

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val email: String,
    val password: String
)
