package com.example.bistroraterapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Bistro::class, Dish::class], version = 1)
abstract class DishDatabase : RoomDatabase() {

    abstract fun bistroDao(): BistroDao
    abstract fun dishDao(): DishDao

    companion object {
        @Volatile private var INSTANCE: DishDatabase? = null

        fun getDatabase(context: Context): DishDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    DishDatabase::class.java,
                    "dish_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
