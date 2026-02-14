package com.example.easywallet.repository

import android.net.Uri
import com.example.easywallet.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class ProfileRepository {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    fun uploadProfileImage(
        imageUri: Uri,
        onResult: (Boolean, String?, String?) -> Unit // Boolean for success, String for error, String for downloadUrl
    ) {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            onResult(false, "User not logged in", null)
            return
        }

        val profileImageRef = storage.reference.child("profile_images/$uid/profile.jpg")
        profileImageRef.putFile(imageUri)
            .addOnSuccessListener { uploadTask ->
                uploadTask.storage.downloadUrl.addOnSuccessListener { uri ->
                    val downloadUrl = uri.toString()
                    updateUserProfileImageUrl(uid, downloadUrl) { success, error ->
                        onResult(success, error, downloadUrl)
                    }
                }.addOnFailureListener { e ->
                    onResult(false, e.message, null)
                }
            }
            .addOnFailureListener { e ->
                onResult(false, e.message, null)
            }
    }

    private fun updateUserProfileImageUrl(
        uid: String,
        imageUrl: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        val userRef = db.collection("users").document(uid)
        userRef.update("profileImageUrl", imageUrl)
            .addOnSuccessListener {
                onResult(true, null)
            }
            .addOnFailureListener { e ->
                onResult(false, e.message)
            }
    }
}
