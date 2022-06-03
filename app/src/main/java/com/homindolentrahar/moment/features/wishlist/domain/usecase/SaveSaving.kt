package com.homindolentrahar.moment.features.wishlist.domain.usecase

import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.wishlist.domain.model.WishlistSaving
import com.homindolentrahar.moment.features.wishlist.domain.repository.WishlistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveSaving @Inject constructor(
    private val repository: WishlistRepository
) {
    suspend operator fun invoke(wishlistId: String, saving: WishlistSaving): Flow<Resource<Unit>> =
        repository.saveSaving(wishlistId, saving)
}