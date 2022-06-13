package com.homindolentrahar.moment.features.wishlist.domain.usecase

import com.homindolentrahar.moment.features.wishlist.domain.model.WishlistSaving
import com.homindolentrahar.moment.features.wishlist.domain.repository.WishlistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWishlistSavings @Inject constructor(
    private val repository: WishlistRepository
) {
    suspend operator fun invoke(wishlistId: String): Flow<List<WishlistSaving>> = flow {
        emit(repository.getWishlistSavings(wishlistId))
    }
}