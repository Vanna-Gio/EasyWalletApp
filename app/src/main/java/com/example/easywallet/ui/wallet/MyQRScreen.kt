package com.example.easywallet.ui.wallet

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyQRScreen(navController: NavController) {

    val uid = FirebaseAuth.getInstance().currentUser?.uid

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My QR Code")},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack()  }) {
                        Icon( imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back" )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally, // Center horizontally
            verticalArrangement = Arrangement.Center
        ) {
            if (uid == null) {
                Text(
                    text = "Please log in to view your QR code.",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.error
                )
            } else {
                val bitmap = remember(uid) { // Re-generate bitmap only if uid changes
                    val writer = QRCodeWriter()
                    val bitMatrix =
                        writer.encode(uid, BarcodeFormat.QR_CODE, 512, 512)

                    Bitmap.createBitmap(512, 512, Bitmap.Config.RGB_565).apply {
                        for (x in 0 until 512) {
                            for (y in 0 until 512) {
                                setPixel(
                                    x,
                                    y,
                                    if (bitMatrix[x, y])
                                        android.graphics.Color.BLACK
                                    else
                                        android.graphics.Color.WHITE
                                )
                            }
                        }
                    }
                }

                Text(
                    text = "Scan this QR code to receive money",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.height(16.dp))

                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "My QR Code for UID: $uid",
                    modifier = Modifier.size(256.dp) // Make QR code a bit smaller
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Your UID: $uid",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}