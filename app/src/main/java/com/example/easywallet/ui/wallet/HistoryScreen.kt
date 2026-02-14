package com.example.easywallet.ui.wallet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.easywallet.viewModel.wallet.HistoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavController, vm: HistoryViewModel) { // vm passed as parameter

    val history by vm.history.collectAsState()
    val isLoading by vm.isLoading.collectAsState()
    val errorMessage by vm.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        vm.loadHistory()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transaction History") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            "Back"
                        )
                    }
                }
                )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else if (errorMessage != null) {
                Text(
                    text = "Error: ${errorMessage}",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            } else if (history.isEmpty()) {
                Text(
                    text = "No transactions found.",
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(history) { tx ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text("From: ${tx.senderId}", style = MaterialTheme.typography.bodyMedium)
                                Text("To: ${tx.receiverId}", style = MaterialTheme.typography.bodyMedium)
                                Text("Amount: $${tx.amount}", style = MaterialTheme.typography.bodyMedium)
                                Text("Date: ${tx.timestamp?.toDate()}", style = MaterialTheme.typography.bodySmall) // Assuming timestamp is a Firebase Timestamp
                            }
                        }
                    }
                }
            }
        }
    }
}
