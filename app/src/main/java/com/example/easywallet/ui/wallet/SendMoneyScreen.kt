package com.example.easywallet.ui.wallet

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.easywallet.viewModel.wallet.WalletViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendMoneyScreen(navController: NavController) {

    val walletViewModel: WalletViewModel = viewModel()

    var receiverId by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Send Money")},
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                "Send Money",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = receiverId,
                onValueChange = { receiverId = it },
                label = { Text("Receiver UID") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {

                    walletViewModel.sendMoney(
                        receiverId,
                        amount.toDoubleOrNull() ?: 0.0
                    ) { success, error ->

                        message =
                            if (success) "Transfer Success"
                            else error ?: "Failed"
                    }

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Send")
            }

            Spacer(Modifier.height(16.dp))

            if (message.isNotEmpty()) {
                Text(message)
            }
        }
    }
}
