package com.example.bistroraterapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bistroraterapp.data.Dish
import com.example.bistroraterapp.databinding.ItemDishBinding

class DishAdapter(val onClick: (Dish) -> Unit) :
    ListAdapter<Dish, DishAdapter.DishViewHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<Dish>() {
        override fun areItemsTheSame(oldItem: Dish, newItem: Dish) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Dish, newItem: Dish) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val binding = ItemDishBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DishViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DishViewHolder(private val binding: ItemDishBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dish: Dish) {
            binding.textViewDish.text = "${dish.name} (${dish.type}) - ${dish.rating}/5"
            binding.root.setOnClickListener { onClick(dish) }
        }
    }
}
