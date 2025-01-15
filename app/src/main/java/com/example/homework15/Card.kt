package com.example.homework15

import java.util.UUID

data class Card (val id: UUID, val cardholderName:String, val cardNumber: String, val expirationDate:String, val CVV: Int, val isVisa:Boolean ){
}