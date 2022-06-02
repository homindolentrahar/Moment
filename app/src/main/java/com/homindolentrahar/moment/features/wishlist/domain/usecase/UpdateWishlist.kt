package com.homindolentrahar.moment.features.wishlist.domain.usecase

import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.wishlist.domain.model.Wishlist
import com.homindolentrahar.moment.features.wishlist.domain.repository.WishlistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateWishlist @Inject constructor(
    private val repository: WishlistRepository
) {
    suspend operator fun invoke(id: String, wishlist: Wishlist): Flow<Resource<Unit>> =
        repository.updateWishlist(id, wishlist)
}