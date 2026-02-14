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

    fun loadHistory() {

        repo.getMyTransactions {
            _history.value = it
        }
    }
}