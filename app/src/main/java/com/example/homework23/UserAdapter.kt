package com.example.homework23

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework20.datamodel.User
import com.example.homework23.data.room.UserEntity
import com.example.homework23.databinding.PageItemBinding

class UserAdapter : PagingDataAdapter<UserEntity, UserAdapter.UserViewHolder>(DIFF_CALLBACK) {

    inner class UserViewHolder(private val binding: PageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserEntity) {
            Glide.with(binding.root.context)
                .load(user.avatar)
                .into(binding.image)

            binding.email.text = user.email
            binding.firstname.text = user.firstName
            binding.lastname.text = user.lastName
        }
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = getItem(position)

        currentUser?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            PageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }



    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserEntity>() {

            override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}