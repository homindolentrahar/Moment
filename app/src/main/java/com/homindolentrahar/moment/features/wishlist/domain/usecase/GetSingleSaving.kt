package com.homindolentrahar.moment.features.wishlist.domain.usecase

import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.wishlist.domain.model.WishlistSaving
import com.homindolentrahar.moment.features.wishlist.domain.repository.WishlistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSingleSaving @Inject constructor(
    private val repository: WishlistRepository
) {
    suspend operator fun invoke(
        savingId: String,
        wishlistId: String
    ): Flow<Resource<WishlistSaving>> =
        repository.getSingleSaving(savingId, wishlistId)
}