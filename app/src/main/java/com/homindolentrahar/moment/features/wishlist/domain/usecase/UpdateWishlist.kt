package com.homindolentrahar.moment.features.wishlist.domain.usecase

import com.homindolentrahar.moment.features.wishlist.domain.model.Wishlist
import com.homindolentrahar.moment.features.wishlist.domain.repository.WishlistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateWishlist @Inject constructor(
    private val repository: WishlistRepository
) {
    suspend operator fun invoke(id: String, wishlist: Wishlist): Flow<Unit> = flow {
        emit(repository.updateWishlist(id, wishlist))
    }
}