package com.example.easywallet.viewModel.auth

import androidx.lifecycle.ViewModel
import com.example.easywallet.repository.AuthRepository

class ChangePasswordViewModel : ViewModel() {
    private val repository = AuthRepository()

    fun changePassword(currentPassword: String, newPassword: String, onResult: (Boolean, String?) -> Unit) {
        repository.changePassword(currentPassword, newPassword, onResult)
    }
}
