package com.example.easywallet.repository

import com.example.easywallet.data.Transaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HistoryRepository {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun getMyTransactions(onResult: (List<Transaction>) -> Unit) {
        val uid = auth.currentUser?.uid ?: return

        db.collection("transactions")
            .whereEqualTo("senderId", uid)
            .get()
            .addOnSuccessListener { sent ->

                db.collection("transactions")
                    .whereEqualTo("receiverId", uid)
                    .get()
                    .addOnSuccessListener { received ->
                        val list =
                            sent.toObjects(Transaction::class.java) +
                                    received.toObjects(Transaction::class.java)
                        onResult(list.sortedByDescending {
                            it.timestamp
                        })
                    }
            }
    }
}