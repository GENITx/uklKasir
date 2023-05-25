package com.example.appskasir.Database

import androidx.room.*

@Entity
data class Meja (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
)
