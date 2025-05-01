package com.example.bistroraterapp

import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.example.bistroraterapp.data.Dish
import com.example.bistroraterapp.data.DishDatabase
import kotlinx.coroutines.launch

class RateDishActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate_dish)

        val db = DishDatabase.getDatabase(this)

        val dishName = findViewById<EditText>(R.id.editTextDishName)
        val dishType = findViewById<Spinner>(R.id.spinnerDishType)
        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val saveBtn = findViewById<Button>(R.id.buttonSaveDish)

        ArrayAdapter.createFromResource(
            this,
            R.array.dish_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            dishType.adapter = adapter
        }

        val bistroId = intent.getIntExtra("bistroId", -1)
        val dishId = intent.getIntExtra("dishId", -1)

        if (bistroId == -1) {
            Toast.makeText(this, "Missing Bistro ID", Toast.LENGTH_SHORT).show()
            finish() // exit to avoid crash
            return
        }

        saveBtn.setOnClickListener {
            val name = dishName.text.toString().trim()
            val type = dishType.selectedItem.toString()
            val rating = ratingBar.rating.toInt()

            if (name.isBlank()) {
                Toast.makeText(this, "Please enter a dish name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dish = Dish(
                id = if (dishId == -1) 0 else dishId,
                bistroId = bistroId,
                name = name,
                type = type,
                rating = rating
            )

            lifecycleScope.launch {
                if (dishId == -1) {
                    db.dishDao().insertDish(dish)
                } else {
                    db.dishDao().updateDish(dish)
                }
                Toast.makeText(this@RateDishActivity, "Dish saved", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
