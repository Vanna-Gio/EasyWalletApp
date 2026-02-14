package com.example.easywallet.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.easywallet.viewModel.auth.ChangePasswordViewModel
import androidx.compose.material.icons.filled.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(
    navController: NavController,
    viewModel: ChangePasswordViewModel
) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var showCurrentPassword by remember { mutableStateOf(false) }
    var showNewPassword by remember { mutableStateOf(false) }
    var showConfirmNewPassword by remember { mutableStateOf(false) }
    var generalMessage by remember { mutableStateOf("") } // For success or general error

    var currentPasswordError by remember { mutableStateOf("") }
    var newPasswordError by remember { mutableStateOf("") }
    var confirmNewPasswordError by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Change Password") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Change Your Password",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Enter your current and new passwords.",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                if (generalMessage.isNotEmpty()) {
                    Text(
                        text = generalMessage,
                        color = if ("success" in generalMessage.lowercase()) MaterialTheme.colorScheme.primary else Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .fillMaxWidth()
                    )
                }

                // Current Password Field
                OutlinedTextField(
                    value = currentPassword,
                    onValueChange = {
                        currentPassword = it
                        currentPasswordError = ""
                    },
                    label = { Text("Current Password") },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = "Current Password")
                    },
                    trailingIcon = {
                        IconButton(onClick = { showCurrentPassword = !showCurrentPassword }) {
                            Icon(
                                imageVector = if (showCurrentPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "Toggle Current Password"
                            )
                        }
                    },
                    visualTransformation = if (showCurrentPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    isError = currentPasswordError.isNotEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(12.dp)
                )
                if (currentPasswordError.isNotEmpty()) {
                    Text(
                        text = currentPasswordError,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                }

                // New Password Field
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = {
                        newPassword = it
                        newPasswordError = ""
                    },
                    label = { Text("New Password") },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = "New Password")
                    },
                    trailingIcon = {
                        IconButton(onClick = { showNewPassword = !showNewPassword }) {
                            Icon(
                                imageVector = if (showNewPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "Toggle New Password"
                            )
                        }
                    },
                    visualTransformation = if (showNewPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    isError = newPasswordError.isNotEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(12.dp)
                )
                if (newPasswordError.isNotEmpty()) {
                    Text(
                        text = newPasswordError,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                }

                // Confirm New Password Field
                OutlinedTextField(
                    value = confirmNewPassword,
                    onValueChange = {
                        confirmNewPassword = it
                        confirmNewPasswordError = ""
                    },
                    label = { Text("Confirm New Password") },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = "Confirm New Password")
                    },
                    trailingIcon = {
                        IconButton(onClick = { showConfirmNewPassword = !showConfirmNewPassword }) {
                            Icon(
                                imageVector = if (showConfirmNewPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "Toggle Confirm New Password"
                            )
                        }
                    },
                    visualTransformation = if (showConfirmNewPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    isError = confirmNewPasswordError.isNotEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(12.dp)
                )
                if (confirmNewPasswordError.isNotEmpty()) {
                    Text(
                        text = confirmNewPasswordError,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        currentPasswordError = ""
                        newPasswordError = ""
                        confirmNewPasswordError = ""
                        generalMessage = ""

                        var isValid = true

                        if (currentPassword.isEmpty()) {
                            currentPasswordError = "Current password cannot be empty"
                            isValid = false
                        }
                        if (newPassword.length < 6) {
                            newPasswordError = "New password must be at least 6 characters"
                            isValid = false
                        }
                        if (newPassword != confirmNewPassword) {
                            confirmNewPasswordError = "New passwords do not match"
                            isValid = false
                        }

                        if (isValid) {
                            isLoading = true
                            viewModel.changePassword(currentPassword, newPassword) { success, message ->
                                isLoading = false
                                if (success) {
                                    generalMessage = "Password changed successfully!"
                                    navController.popBackStack() // Go back to profile screen
                                } else {
                                    generalMessage = message ?: "Failed to change password."
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Text(
                            text = "Change Password",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}
