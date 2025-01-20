package com.example.myapplication.hmwrk

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.chatDataDto
import com.example.myapplication.databinding.ChatBinding

class ChatsRecyclerview(private val chats : List<chatDataDto>) : RecyclerView.Adapter<ChatsRecyclerview.ChatViewHolder>() {

    inner class ChatViewHolder (private val binding: ChatBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind{

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}