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
import com.example.easywallet.ui.auth.ForgotPasswordScreen
import com.example.easywallet.ui.profile.ChangePasswordScreen
import com.example.easywallet.ui.profile.ProfileScreen
import com.example.easywallet.viewModel.Profile.ProfileViewModel
import com.example.easywallet.viewModel.auth.AuthViewModel
import com.example.easywallet.viewModel.auth.ChangePasswordViewModel
import com.example.easywallet.viewModel.auth.ForgotPasswordViewModel
import com.example.easywallet.viewModel.home.HomeViewModel
import com.example.easywallet.viewModel.wallet.HistoryViewModel
import com.example.easywallet.viewModel.wallet.WalletViewModel

@Composable
fun EasyWalletNavGraph() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val homeViewModel: HomeViewModel = viewModel()
    val profileViewModel: ProfileViewModel = viewModel()
    val walletViewModel: WalletViewModel = viewModel()
    val historyViewModel: HistoryViewModel = viewModel()
    val forgotPasswordViewModel: ForgotPasswordViewModel = viewModel()
    val changePasswordViewModel: ChangePasswordViewModel = viewModel()

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
        composable("forgotPassword") {
            ForgotPasswordScreen(navController, forgotPasswordViewModel)
        }
        composable("changePassword") {
            ChangePasswordScreen(navController, changePasswordViewModel)
        }
        composable("home") {
            HomeScreen(navController, homeViewModel)
        }
        composable("send") {
            SendMoneyScreen(navController, walletViewModel)
        }
        composable("history") {
            HistoryScreen(navController, historyViewModel)
        }
        composable("myqr") {
            MyQRScreen(navController)
        }
        composable("profile") {
            ProfileScreen(navController, profileViewModel)
        }
    }
}