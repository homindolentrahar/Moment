package com.homindolentrahar.moment.features.wishlist.domain.usecase

import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.wishlist.domain.model.Wishlist
import com.homindolentrahar.moment.features.wishlist.domain.repository.WishlistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllWishlist @Inject constructor(
    private val repository: WishlistRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Wishlist>>> = repository.getAllWishlist()
}