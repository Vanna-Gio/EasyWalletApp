package com.example.easywallet.ui.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.easywallet.viewModel.Profile.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel) {
    val user by viewModel.user.observeAsState()
    var isLoggingOut by remember { mutableStateOf(false) }

    val isUploading by viewModel.isUploading.collectAsState()
    val uploadError by viewModel.uploadError.collectAsState()
    val uploadSuccessUrl by viewModel.uploadSuccessUrl.collectAsState()

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            selectedImageUri = uri
            uri?.let { viewModel.uploadProfileImage(it) }
        }
    )

    LaunchedEffect(Unit) {
        viewModel.fetchUser()
    }

    LaunchedEffect(uploadSuccessUrl) {
        if (uploadSuccessUrl != null) {
            // Optionally show a toast or message
            // Toast.makeText(context, "Profile image updated!", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top // Changed to Top for better layout with image
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Profile Picture
            Box(modifier = Modifier.size(120.dp), contentAlignment = Alignment.Center) {
                AsyncImage(
                    model = user?.profileImageUrl ?: "https://via.placeholder.com/120", // Placeholder if no image
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .clickable { pickImageLauncher.launch("image/*") },
                    contentScale = ContentScale.Crop
                )
                if (isUploading) {
                    CircularProgressIndicator(modifier = Modifier.size(40.dp))
                } else {
                    Icon(
                        imageVector = Icons.Default.AddAPhoto,
                        contentDescription = "Change Profile Picture",
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.BottomEnd)
                            .clickable { pickImageLauncher.launch("image/*") }
                    )
                }
            }

            if (uploadError != null) {
                Text(
                    text = "Upload Error: ${uploadError}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            user?.let { actualUser ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Name: ${actualUser.name}", style = MaterialTheme.typography.headlineMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Email: ${actualUser.email}", style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        navController.navigate("changePassword")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Change Password")
                }
                Spacer(modifier = Modifier.height(16.dp)) // Add spacing between buttons
                Button(
                    onClick = {
                        isLoggingOut = true
                        viewModel.logout()
                        navController.navigate("login") {
                            popUpTo("home") { inclusive = true }
                        }
                    },
                    enabled = !isLoggingOut
                ) {
                    if (isLoggingOut) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(24.dp))
                    } else {
                        Text(text = "Logout")
                    }
                }
            } ?: run {
                CircularProgressIndicator()
            }
        }
    }
}
