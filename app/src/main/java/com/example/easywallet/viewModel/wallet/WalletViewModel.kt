package com.example.easywallet.viewModel.wallet

import androidx.lifecycle.ViewModel
import com.example.easywallet.repository.WalletRepository
import com.google.firebase.auth.FirebaseAuth

class WalletViewModel : ViewModel() {
    private val repo = WalletRepository()
    private val auth = FirebaseAuth.getInstance()

    fun sendMoney(
        receiverId: String,
        amount: Double,
        onResult: (Boolean, String?) -> Unit
    ) {
        val senderId = auth.currentUser?.uid ?: return

        repo.sendMoney(
            senderId,
            receiverId,
            amount,
            onResult
        )
    }
}