package com.example.homework15

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework15.databinding.CardItemBinding

class ViewPagerAdapter(private val cards: List<Card>,
                       private val listener: OnItemClickListener) :
ListAdapter<Card, ViewPagerAdapter.ViewPagerViewHolder>(DiffUtilCallback()){

    inner class ViewPagerViewHolder (private val binding: CardItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Card, position: Int) {
            if (item.isVisa) {
                binding.logo.setImageResource(R.drawable.visa)
                binding.card.setImageResource(R.drawable.visa_background)
            }
            binding.cardNumber.setText(item.cardNumber)
            binding.holderName.setText(item.cardholderName)
            binding.expirationDate.setText(item.expirationDate)

            binding.card.setOnLongClickListener{

                listener.onItemClick(position)
                return@setOnLongClickListener true
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerAdapter.ViewPagerViewHolder {
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