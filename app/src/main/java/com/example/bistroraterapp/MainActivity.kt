package com.example.bistroraterapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bistroraterapp.adapter.DishAdapter
import com.example.bistroraterapp.data.Bistro
import com.example.bistroraterapp.data.DishDatabase
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var db: DishDatabase
    private lateinit var dishAdapter: DishAdapter
    private var currentBistroId: Int = -1 // This will be updated once a Bistro is saved

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = DishDatabase.getDatabase(this)

        // UI elements
        val nameInput = findViewById<EditText>(R.id.editTextName)
        val addressInput = findViewById<EditText>(R.id.editTextAddress)
        val saveButton = findViewById<Button>(R.id.buttonSaveBistro)
        val rateButton = findViewById<Button>(R.id.buttonRate)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerDishes)

        // Set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        dishAdapter = DishAdapter { dish ->
            val intent = Intent(this, RateDishActivity::class.java)
            intent.putExtra("dishId", dish.id)
            intent.putExtra("bistroId", dish.bistroId)
            startActivity(intent)
        }
        recyclerView.adapter = dishAdapter

        // Save Bistro button
        saveButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val address = addressInput.text.toString().trim()

            if (name.isBlank() || address.isBlank()) {
                Toast.makeText(this, "Please enter both name and address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newBistro = Bistro(name = name, address = address)

            lifecycleScope.launch {
                try {
                    val newId = db.bistroDao().insertBistro(newBistro).toInt()
                    currentBistroId = newId
                    observeDishes(newId)
                    Toast.makeText(this@MainActivity, "Bistro saved!", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, "Error saving bistro: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

        // Rate Dishes button
        rateButton.setOnClickListener {
            if (currentBistroId != -1) {
                val intent = Intent(this, RateDishActivity::class.java)
                intent.putExtra("bistroId", currentBistroId)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please save a Bistro before rating dishes", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeDishes(bistroId: Int) {
        db.dishDao().getDishesForBistro(bistroId).observe(this) { dishes ->
            dishAdapter.submitList(dishes)
        }
    }
}
