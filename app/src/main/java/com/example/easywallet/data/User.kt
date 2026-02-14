package com.example.easywallet.data

import java.security.Timestamp

data class User (
    val uid: String ="",
    val name: String = "",
    val email: String = "",
    val balance: Double = 0.0,
    val phoneNumber: String = "",
    val profileImageUrl: String? = null
)