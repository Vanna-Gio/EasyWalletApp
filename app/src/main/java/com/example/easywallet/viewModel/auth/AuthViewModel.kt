package com.example.easywallet.viewModel.auth

import androidx.lifecycle.ViewModel
import com.example.easywallet.repository.AuthRepository

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()

    fun login(
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        repository.login(email, password, onResult)
    }

    fun register(
        name : String,
        email: String,
        password: String,
        phoneNumber: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        repository.register(name, email, password, phoneNumber, onResult)
    }
}