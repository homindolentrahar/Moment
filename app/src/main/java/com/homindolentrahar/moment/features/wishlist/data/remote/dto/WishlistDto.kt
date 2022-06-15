package com.homindolentrahar.moment.features.wishlist.data.remote.dto

import com.homindolentrahar.moment.features.wishlist.domain.model.Wishlist
import java.time.ZoneOffset

data class WishlistDto(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val current: Double,
    val period: String,
    val imageUrl: String,
    val created_at: Long,
    val updated_at: Long,
) {
    companion object {
        const val COLLECTION = "wishlist"

        fun fromWishlist(wishlist: Wishlist): WishlistDto = WishlistDto(
            id = wishlist.id,
            name = wishlist.name,
            description = wishlist.description,
            price = wishlist.price,
            current = wishlist.current,
            period = wishlist.period,
            imageUrl = wishlist.imageUrl,
            created_at = wishlist.created_at.toEpochSecond(ZoneOffset.UTC),
            updated_at = wishlist.updated_at.toEpochSecond(ZoneOffset.UTC),
        )
    }
}