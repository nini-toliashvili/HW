package com.example.myapplication

import kotlinx.serialization.Serializable
import java.net.URL

@Serializable
data class chatDataDto (
    val id:Int,
    val owner: String,
    val imageLink: URL,
    val lastMessage: String,
    val lastActive: String,
    val unreadMessages: Int,
    val isTyping: Boolean,
    val lastMessageType: MessageType
) {

    enum class MessageType {
        TEXT, VOICE, FILE
    }
}

