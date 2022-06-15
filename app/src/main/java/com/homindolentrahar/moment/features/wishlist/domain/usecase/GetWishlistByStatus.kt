package com.homindolentrahar.moment.features.wishlist.domain.usecase

import com.homindolentrahar.moment.features.wishlist.domain.model.Wishlist
import com.homindolentrahar.moment.features.wishlist.domain.model.WishlistStatus
import com.homindolentrahar.moment.features.wishlist.domain.repository.WishlistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject

class GetWishlistByStatus @Inject constructor(
    private val repository: WishlistRepository
) {

    suspend operator fun invoke(
        status: WishlistStatus,
        date: LocalDateTime = LocalDateTime.now()
    ): Flow<List<Wishlist>> = flow {
        val filteredWishlist = repository.getAllWishlist()
            .filter { it.created_at.month == date.month }
            .filter {
                when (status) {
                    WishlistStatus.all -> {
                        true
                    }
                    WishlistStatus.progress -> {
                        it.current < it.price
                    }
                    WishlistStatus.reached -> {
                        it.current == it.price
                    }
                }
            }

        emit(filteredWishlist)
    }

}