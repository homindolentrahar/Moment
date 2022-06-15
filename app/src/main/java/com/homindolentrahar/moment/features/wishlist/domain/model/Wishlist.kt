package com.homindolentrahar.moment.features.wishlist.domain.model

import java.time.LocalDateTime

enum class WishlistStatus(name: String) {
    ALL("all"),
    PROGRESS("progess"),
    REACHED("reached")
}

data class Wishlist(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val current: Double,
    private val _period: String,
    val imageUrl: String,
    val created_at: LocalDateTime,
    val updated_at: LocalDateTime
) {
    val period: String
        get() = _period.replaceFirstChar { it.uppercase() }
}