package com.homindolentrahar.moment.features.wishlist.data.remote.dto

import com.homindolentrahar.moment.features.wishlist.domain.model.Wishlist

data class WishlistDto(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val current: Double,
    val period: String,
    val imageUrl: String,
) {
    companion object {
        const val COLLECTION = "wishlist"

        fun fromWishlist(wishlist: Wishlist): Wishlist = Wishlist(
            id = wishlist.id,
            name = wishlist.name,
            description = wishlist.description,
            price = wishlist.price,
            current = wishlist.current,
            _period = wishlist.period,
            imageUrl = wishlist.imageUrl
        )
    }
}