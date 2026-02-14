package com.example.easywallet.data

import com.google.firebase.Timestamp

data class Transaction(
    val id: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val amount: Double = 0.0,
    val timestamp: Timestamp? = null

)