package com.homindolentrahar.moment.features.wishlist.domain.usecase

import com.homindolentrahar.moment.features.wishlist.domain.model.Wishlist
import com.homindolentrahar.moment.features.wishlist.domain.repository.WishlistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject

class GetMonthlyWishlist @Inject constructor(
    private val repository: WishlistRepository
) {

    suspend operator fun invoke(date: LocalDateTime = LocalDateTime.now()): Flow<List<Wishlist>> =
        flow {
            val monthlyWishlist = repository.getAllWishlist()
                .filter { it.created_at.month == date.month }

            emit(monthlyWishlist)
        }

}