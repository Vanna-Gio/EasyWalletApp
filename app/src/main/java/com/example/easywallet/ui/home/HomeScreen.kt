package com.example.easywallet.ui.home

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
import com.example.easywallet.viewModel.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel) {

    val user by homeViewModel.userData.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.loadUserData()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("EasyWallet Dashboard") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Welcome, ${user?.name ?: ""}",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text("Wallet Balance")

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = "$ ${user?.balance ?: 0.0}",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    navController.navigate("send")
                }
            ) {
                Text("Send Money")
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    navController.navigate("history")
                }
            ) {
                Text("Transaction History")
            }

            Button(
                onClick = {
                    navController.navigate("myqr")
                }
            ) {
                Text("My QR")
            }
            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    navController.navigate("profile")
                }
            ) {
                Text("Profile")
            }
        }
    }
}