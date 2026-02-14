package com.example.easywallet.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.easywallet.ui.auth.LoginScreen
import com.example.easywallet.ui.auth.RegisterScreen
import com.example.easywallet.ui.home.HomeScreen
import com.example.easywallet.ui.wallet.SendMoneyScreen

@Composable
fun EasyWalletNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ){
        composable("login") {
            LoginScreen(navController)
        }
        composable("register") {
            RegisterScreen(navController)
        }
        composable("home") {
            HomeScreen(navController)
        }
        composable("send") {
            SendMoneyScreen(navController)
        }
    }
}