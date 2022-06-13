package com.homindolentrahar.moment.features.wishlist.domain.usecase

import com.homindolentrahar.moment.features.wishlist.domain.repository.WishlistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoveSaving @Inject constructor(
    private val repository: WishlistRepository
) {
    suspend operator fun invoke(wishlistId: String, savingId: String): Flow<Unit> = flow {
        emit(repository.removeSaving(wishlistId, savingId))
    }
}