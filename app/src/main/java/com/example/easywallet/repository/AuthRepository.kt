package com.example.easywallet.repository

import com.example.easywallet.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun login(
        email: String,
        password: String,
        // onResult callback to report success or failure
        onResult: (Boolean, String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    fun register(
        name: String,
        email: String,
        password: String,
        phoneNumber: String,
        onResult: (Boolean, String?) -> Unit
    ) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    val uid = auth.currentUser?.uid ?: ""

                    val user = User(
                        uid = uid,
                        name = name,
                        email = email,
                        balance = 0.0,
                        phoneNumber = phoneNumber
                    )

                    db.collection("users")
                        .document(uid)
                        .set(user)
                        .addOnSuccessListener {
                            onResult(true, null)
                        }
                        .addOnFailureListener {
                            onResult(false, it.message)
                        }

                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }
    fun getCurrentUser(onResult: (User?) -> Unit) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            db.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val user = document.toObject(User::class.java)
                        onResult(user)
                    } else {
                        onResult(null)
                    }
                }
                .addOnFailureListener {
                    onResult(null)
                }
        } else {
            onResult(null)
        }
    }

    fun logout() {
        auth.signOut()
    }

    fun sendPasswordResetEmail(email: String, onResult: (Boolean, String?) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    fun changePassword(currentPassword: String, newPassword: String, onResult: (Boolean, String?) -> Unit) {
        val user = auth.currentUser
        if (user != null && user.email != null) {
            val credential = com.google.firebase.auth.EmailAuthProvider.getCredential(user.email!!, currentPassword)
            user.reauthenticate(credential)
                .addOnCompleteListener { reauthTask ->
                    if (reauthTask.isSuccessful) {
                        user.updatePassword(newPassword)
                            .addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    onResult(true, null)
                                } else {
                                    onResult(false, updateTask.exception?.message)
                                }
                            }
                    } else {
                        onResult(false, reauthTask.exception?.message)
                    }
                }
        } else {
            onResult(false, "User not logged in or email not available.")
        }
    }
}