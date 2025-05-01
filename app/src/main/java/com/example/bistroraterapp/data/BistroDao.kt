package com.example.bistroraterapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BistroDao {
    @Insert
    suspend fun insertBistro(bistro: Bistro): Long

    @Query("SELECT * FROM Bistro")
    fun getAllBistros(): LiveData<List<Bistro>>
}
