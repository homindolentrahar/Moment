package com.homindolentrahar.moment.features.wishlist.domain.usecase

import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.wishlist.domain.repository.WishlistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoveWishlist @Inject constructor(
    private val repository: WishlistRepository
) {
    suspend operator fun invoke(id: String): Flow<Resource<Unit>> =
        repository.removeWishlist(id)
}