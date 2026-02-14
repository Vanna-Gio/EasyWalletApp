package com.example.easywallet.ui.wallet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.easywallet.viewModel.wallet.WalletViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendMoneyScreen(navController: NavController, walletViewModel: WalletViewModel) { // WalletViewModel passed as parameter

    var receiverId by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    var receiverIdError by remember { mutableStateOf<String?>(null) }
    var amountError by remember { mutableStateOf<String?>(null) }

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
                onValueChange = {
                    receiverId = it
                    receiverIdError = null
                },
                label = { Text("Receiver UID") },
                isError = receiverIdError != null,
                modifier = Modifier.fillMaxWidth()
            )
            receiverIdError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = amount,
                onValueChange = {
                    amount = it
                    amountError = null
                },
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = amountError != null,
                modifier = Modifier.fillMaxWidth()
            )
            amountError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    receiverIdError = if (receiverId.isBlank()) "Receiver UID cannot be empty" else null
                    val parsedAmount = amount.toDoubleOrNull()
                    amountError = when {
                        amount.isBlank() -> "Amount cannot be empty"
                        parsedAmount == null -> "Invalid amount"
                        parsedAmount <= 0 -> "Amount must be greater than 0"
                        else -> null
                    }

                    if (receiverIdError == null && amountError == null) {
                        isLoading = true
                        walletViewModel.sendMoney(
                            receiverId,
                            parsedAmount!!
                        ) { success, error ->
                            isLoading = false
                            message = if (success) "Transfer Success" else error ?: "Failed"
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(24.dp))
                } else {
                    Text("Send")
                }
            }

            Spacer(Modifier.height(16.dp))

            if (message.isNotEmpty()) {
                Text(
                    message,
                    color = if (message == "Transfer Success") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}