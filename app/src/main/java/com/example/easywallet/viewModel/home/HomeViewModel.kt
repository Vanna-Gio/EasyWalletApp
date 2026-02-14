package com.example.easywallet.viewModel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easywallet.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val _userData = MutableStateFlow<User?>(null)
    val userData: StateFlow<User?> = _userData

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loadUserData() {
        _isLoading.value = true
        _errorMessage.value = null
        val uid = auth.currentUser?.uid
        if (uid == null) {
            _errorMessage.value = "User not logged in."
            _isLoading.value = false
            return
        }

        viewModelScope.launch {
            db.collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener { doc ->
                    val user = doc.toObject(User::class.java)
                    _userData.value = user
                    _isLoading.value = false
                }
                .addOnFailureListener { e ->
                    _errorMessage.value = e.message
                    _isLoading.value = false
                }
        }
    }

    fun logout() {
        auth.signOut()
    }
}