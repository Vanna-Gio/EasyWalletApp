package com.example.easywallet.viewModel.wallet

import androidx.lifecycle.ViewModel
import com.example.easywallet.data.Transaction
import com.example.easywallet.repository.HistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HistoryViewModel : ViewModel() {

    private val repo = HistoryRepository()

    private val _history = MutableStateFlow<List<Transaction>>(emptyList())
    val history: StateFlow<List<Transaction>> = _history

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loadHistory() {
        _isLoading.value = true
        _errorMessage.value = null
        repo.getMyTransactions { transactions, error ->
            _isLoading.value = false
            if (error != null) {
                _errorMessage.value = error
            } else {
                _history.value = transactions
            }
        }
    }
}