package com.homindolentrahar.moment.features.wishlist.presentation.wishlist_detail

import com.homindolentrahar.moment.features.wishlist.domain.model.Wishlist
import com.homindolentrahar.moment.features.wishlist.domain.model.WishlistSaving

data class WishlistDetailState(
    val wishlist: Wishlist? = null,
    val savings: List<WishlistSaving> = emptyList(),
    val loading: Boolean = false,
    val error: String = ""
)
