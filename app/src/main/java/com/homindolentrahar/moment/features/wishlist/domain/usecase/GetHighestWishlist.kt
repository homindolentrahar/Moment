package com.homindolentrahar.moment.features.wishlist.domain.usecase

import com.homindolentrahar.moment.features.wishlist.domain.model.Wishlist
import com.homindolentrahar.moment.features.wishlist.domain.repository.WishlistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject

class GetHighestWishlist @Inject constructor(
    private val repository: WishlistRepository
) {

    suspend operator fun invoke(date: LocalDateTime = LocalDateTime.now()): Flow<Wishlist> = flow {
        val highest = repository.getAllWishlist()
            .filter { it.created_at.month == date.month }
            .maxByOrNull { it.price } ?: repository.getAllWishlist().first()

        emit(highest)
    }

}