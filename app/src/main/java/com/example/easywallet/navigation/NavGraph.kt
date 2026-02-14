package com.example.easywallet.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.easywallet.ui.auth.LoginScreen
import com.example.easywallet.ui.auth.RegisterScreen
import com.example.easywallet.ui.home.HomeScreen
import com.example.easywallet.ui.wallet.HistoryScreen
import com.example.easywallet.ui.wallet.MyQRScreen
import com.example.easywallet.ui.wallet.SendMoneyScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.easywallet.ui.profile.ProfileScreen
import com.example.easywallet.viewModel.Profile.ProfileViewModel
import com.example.easywallet.viewModel.auth.AuthViewModel
import com.example.easywallet.viewModel.home.HomeViewModel

@Composable
fun EasyWalletNavGraph() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val homeViewModel: HomeViewModel = viewModel()
    val profileViewModel: ProfileViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "login"
    ){
        composable("login") {
            LoginScreen(navController, authViewModel)
        }
        composable("register") {
            RegisterScreen(navController, authViewModel)
        }
        composable("home") {
            HomeScreen(navController, homeViewModel)
        }
        composable("send") {
            SendMoneyScreen(navController)
        }
        composable("history") {
            HistoryScreen(navController)
        }
        composable("myqr") {
            MyQRScreen(navController)
        }
        composable("profile") {
            ProfileScreen(navController, profileViewModel)
        }
    }
}