package com.homindolentrahar.moment.features.wishlist.domain.repository

import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.wishlist.domain.model.Wishlist
import com.homindolentrahar.moment.features.wishlist.domain.model.WishlistSaving
import kotlinx.coroutines.flow.Flow

interface WishlistRepository {
    suspend fun getAllWishlist(): Flow<Resource<List<Wishlist>>>

    suspend fun getSingleWishlist(id: String): Flow<Resource<Wishlist>>

    suspend fun getWishlistSavings(wishlistId: String): Flow<Resource<List<WishlistSaving>>>

    suspend fun getSingleSaving(id: String, wishlistId: String): Flow<Resource<WishlistSaving>>

    suspend fun saveWishlist(wishlist: Wishlist): Flow<Resource<Unit>>

    suspend fun saveSaving(wishlistId: String, saving: WishlistSaving): Flow<Resource<Unit>>

    suspend fun updateWishlist(id: String, wishlist: Wishlist): Flow<Resource<Unit>>

    suspend fun updateSaving(
        wishlistId: String,
        savingId: String,
        saving: WishlistSaving
    ): Flow<Resource<Unit>>

    suspend fun removeWishlist(wishlistId: String, savingId: String): Flow<Resource<Unit>>

    suspend fun removeSaving(wishlistId: String, savingId: String): Flow<Resource<Unit>>
}