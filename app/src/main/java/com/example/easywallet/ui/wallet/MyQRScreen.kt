package com.example.easywallet.ui.wallet

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    val bitmap = remember {
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
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Text("My QR Code")

            Spacer(Modifier.height(16.dp))

            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null
            )
        }
    }
}
