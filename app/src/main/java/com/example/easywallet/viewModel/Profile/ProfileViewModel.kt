package com.example.easywallet.viewModel.Profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easywallet.data.User
import com.example.easywallet.repository.AuthRepository
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    fun fetchUser() {
        viewModelScope.launch {
            authRepository.getCurrentUser {
                _user.postValue(it)
            }
        }
    }

    fun logout() {
        authRepository.logout()
    }
}
