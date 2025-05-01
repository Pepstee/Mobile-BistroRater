package com.example.bistroraterapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DishDao {
    @Insert
    suspend fun insertDish(dish: Dish)

    @Update
    suspend fun updateDish(dish: Dish)

    @Delete
    suspend fun deleteDish(dish: Dish)

    @Query("SELECT * FROM Dish WHERE bistroId = :bistroId")
    fun getDishesForBistro(bistroId: Int): LiveData<List<Dish>>
}
