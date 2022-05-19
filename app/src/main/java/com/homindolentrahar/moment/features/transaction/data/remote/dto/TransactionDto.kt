package com.homindolentrahar.moment.features.transaction.data.remote.dto

data class TransactionDto(
    val id: String,
    val name: String,
    val desc: String,
    val type: String,
    val category: Map<String, Any>,
    val account: Map<String, Any>,
    val timestamp: Long
) {
    companion object {
        const val COLLECTION = "transactions"
    }
}
