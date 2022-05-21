package com.homindolentrahar.moment.features.wishlist.domain.model

data class Wishlist(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val current: Double,
    private val _period: String,
    val imageUrl: String
) {
    val period: String
        get() = _period.replaceFirstChar { it.uppercase() }
}