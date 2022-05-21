package com.homindolentrahar.moment.features.wishlist.data.remote.dto

import com.homindolentrahar.moment.features.wishlist.domain.model.WishlistSaving

data class WishlistSavingDto(
    val id: String,
    val amount: Double,
    val timestamp: Long
) {
    companion object {
        const val COLLECTION = "savings"

        fun fromWishlistSaving(saving: WishlistSaving): WishlistSaving = WishlistSaving(
            id = saving.id,
            amount = saving.amount,
            timestamp = saving.timestamp
        )
    }
}
