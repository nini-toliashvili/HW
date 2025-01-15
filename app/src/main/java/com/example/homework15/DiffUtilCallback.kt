package com.example.homework15

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback: DiffUtil.ItemCallback<Card> (){
    override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem == newItem
    }
}