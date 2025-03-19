package com.example.homework25.presentation.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework25.databinding.CategoryItemBinding
import com.example.homework25.presentation.model.CategoryUI

class CategoryListAdapter : ListAdapter<CategoryUI, CategoryListAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    inner class CategoryViewHolder(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(category: CategoryUI) {
                binding.apply {
                    categoryName.text = category.name
                    val parentCount = if (category.depth > 0) ".".repeat(category.depth) else ""
                    dots.text = parentCount
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CategoryDiffCallback : DiffUtil.ItemCallback<CategoryUI>() {
        override fun areItemsTheSame(oldItem: CategoryUI, newItem: CategoryUI): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CategoryUI, newItem: CategoryUI): Boolean {
            return oldItem == newItem
        }
    }
}