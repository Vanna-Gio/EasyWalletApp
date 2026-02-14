package com.example.easywallet.repository

import com.example.easywallet.data.Transaction
import com.google.firebase.firestore.FirebaseFirestore

class WalletRepository {
    private val db = FirebaseFirestore.getInstance()

    fun sendMoney(
        senderId: String,
        receiverId: String,
        amount: Double,
        onResult: (Boolean, String?) -> Unit
    ){
        val senderRef = db.collection("users").document(senderId)
        val receiverRef = db.collection("users").document(receiverId)
        db.runTransaction { transaction ->
            val senderSnap = transaction.get(senderRef)
            val receiverSnap = transaction.get(receiverRef)

            if (!receiverSnap.exists()) {
                throw Exception("Receiver does not exist")
            }

            val senderBalance =
                senderSnap.getDouble("balance") ?: 0.0

            if (senderBalance < amount) {
                throw Exception("Not enough balance")
            }

            //update balance
            transaction.update(
                senderRef,
                "balance",
                senderBalance - amount
            )

            val receiverBalance =
                receiverSnap.getDouble("balance") ?: 0.0

            transaction.update(
                receiverRef,
                "balance",
                receiverBalance + amount
            )

            // Save transaction
            val txId = db.collection("transactions").document().id

            val tx = Transaction(
                id = txId,
                senderId = senderId,
                receiverId = receiverId,
                amount = amount
            )

            db.collection("transactions")
                .document(txId)
                .set(tx)
        }.addOnSuccessListener {
            onResult(true, null)
        }.addOnFailureListener {
            onResult(false, it.message)
        }
    }
}