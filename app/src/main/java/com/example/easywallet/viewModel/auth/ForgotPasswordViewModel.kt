package com.example.easywallet.viewModel.auth

import androidx.lifecycle.ViewModel
import com.example.easywallet.repository.AuthRepository

class ForgotPasswordViewModel : ViewModel() {
    private val repository = AuthRepository()

    fun sendPasswordResetEmail(email: String, onResult: (Boolean, String?) -> Unit) {
        repository.sendPasswordResetEmail(email, onResult)
    }
}
