package com.homindolentrahar.moment.features.wishlist.domain.model

import java.time.LocalDateTime

data class WishlistSaving(
    val id: String,
    val amount: Double,
    val timestamp: LocalDateTime
)
