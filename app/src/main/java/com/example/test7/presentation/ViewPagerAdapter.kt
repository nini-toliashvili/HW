package com.example.homework15

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test7.data.CardDataModel
import com.example.test7.databinding.CardItemBinding

class ViewPagerAdapter(private val cards: List<CardDataModel>) :
    ListAdapter<CardDataModel, ViewPagerAdapter.ViewPagerViewHolder>(DiffUtilCallback()){

    inner class ViewPagerViewHolder (private val binding: CardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: CardDataModel, position: Int) {
            Glide.with(binding.root.context)
                .load(item.cover)
                .into(binding.card)

            binding.locationName.text = item.title
            binding.title.text = item.title
            binding.reactCount.text = item.reaction_count.toString()
            binding.price.text = item.price
            binding.rate.rating = item.rate ?: 1f

        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<CardDataModel>() {
        override fun areItemsTheSame(oldItem: CardDataModel, newItem: CardDataModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CardDataModel, newItem: CardDataModel): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        return ViewPagerViewHolder(
            CardItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val currentCard = cards[position]
        holder.onBind(currentCard, position)
    }

    override fun getItemCount(): Int {
        return cards.size
    }


}