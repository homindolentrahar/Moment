package com.homindolentrahar.moment.features.wishlist.domain.repository

import com.homindolentrahar.moment.features.wishlist.domain.model.Wishlist
import com.homindolentrahar.moment.features.wishlist.domain.model.WishlistSaving

interface WishlistRepository {
    suspend fun getAllWishlist(): List<Wishlist>

    suspend fun getSingleWishlist(id: String): Wishlist?

    suspend fun getWishlistSavings(wishlistId: String): List<WishlistSaving>

    suspend fun getSingleSaving(id: String, wishlistId: String): WishlistSaving?

    suspend fun saveWishlist(wishlist: Wishlist)

    suspend fun saveSaving(wishlistId: String, saving: WishlistSaving)

    suspend fun updateWishlist(id: String, wishlist: Wishlist)

    suspend fun updateSaving(
        wishlistId: String,
        savingId: String,
        saving: WishlistSaving
    )

    suspend fun removeWishlist(wishlistId: String)

    suspend fun removeSaving(wishlistId: String, savingId: String)
}