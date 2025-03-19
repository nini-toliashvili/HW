package com.example.challenge.presentation.screen.connection

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.challenge.R
import com.example.challenge.databinding.ItemConnectionLayoutBinding
import com.example.challenge.presentation.extension.loadImage
import com.example.challenge.presentation.model.connection.Connection

class ConnectionsRecyclerAdapter :
    ListAdapter<Connection, ConnectionsRecyclerAdapter.ConnectionsViewHolder>(ConnectionsDiffUtil()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ConnectionsViewHolder(
        ItemConnectionLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ConnectionsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ConnectionsViewHolder(private val binding: ItemConnectionLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(model: Connection) {
            binding.apply {
                tvFullName.text = model.fullName
                if (model.avatar.isNullOrEmpty() ) {
                    imvProfile.setImageResource(R.drawable.ic_launcher_background)
                } else {
                    imvProfile.loadImage(model.avatar)
                }
            }
        }
    }

    class ConnectionsDiffUtil : DiffUtil.ItemCallback<Connection>() {
        override fun areItemsTheSame(oldItem: Connection, newItem: Connection): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Connection, newItem: Connection): Boolean {
            return oldItem == newItem
        }
    }
}