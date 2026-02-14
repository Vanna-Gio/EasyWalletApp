package com.example.easywallet.ui.wallet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.easywallet.viewModel.wallet.HistoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavController) {

    val vm: HistoryViewModel = viewModel()
    val history by vm.history.collectAsState()

    LaunchedEffect(Unit) {
        vm.loadHistory()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Transaction History") })
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            items(history) { tx ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text("From: ${tx.senderId}")
                        Text("To: ${tx.receiverId}")
                        Text("Amount: $${tx.amount}")
                    }
                }
            }
        }
    }
}
