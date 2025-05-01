package com.example.bistroraterapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Bistro(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val address: String
)
