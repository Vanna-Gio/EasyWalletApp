package com.example.easywallet.viewModel.Profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easywallet.data.User
import com.example.easywallet.repository.AuthRepository
import com.example.easywallet.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val authRepository = AuthRepository()
    private val profileRepository = ProfileRepository()

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    private val _isUploading = MutableStateFlow(false)
    val isUploading: StateFlow<Boolean> = _isUploading

    private val _uploadError = MutableStateFlow<String?>(null)
    val uploadError: StateFlow<String?> = _uploadError

    private val _uploadSuccessUrl = MutableStateFlow<String?>(null)
    val uploadSuccessUrl: StateFlow<String?> = _uploadSuccessUrl


    fun fetchUser() {
        viewModelScope.launch {
            authRepository.getCurrentUser {
                _user.postValue(it)
            }
        }
    }

    fun uploadProfileImage(imageUri: Uri) {
        _isUploading.value = true
        _uploadError.value = null
        _uploadSuccessUrl.value = null

        profileRepository.uploadProfileImage(imageUri) { success, error, downloadUrl ->
            _isUploading.value = false
            if (success && downloadUrl != null) {
                _uploadSuccessUrl.value = downloadUrl
                // Also update the local user LiveData with the new URL
                _user.value = _user.value?.copy(profileImageUrl = downloadUrl)
            } else {
                _uploadError.value = error ?: "Unknown upload error"
            }
        }
    }

    fun logout() {
        authRepository.logout()
    }
}
