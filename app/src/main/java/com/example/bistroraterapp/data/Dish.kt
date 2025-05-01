package com.example.bistroraterapp.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Bistro::class,
        parentColumns = ["id"],
        childColumns = ["bistroId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Dish(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val bistroId: Int,
    val name: String,
    val type: String,
    val rating: Int
)
