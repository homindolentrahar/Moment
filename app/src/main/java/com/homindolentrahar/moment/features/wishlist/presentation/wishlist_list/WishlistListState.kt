package com.homindolentrahar.moment.features.wishlist.presentation.wishlist_list

import com.homindolentrahar.moment.features.wishlist.domain.model.Wishlist

data class WishlistListState(
    val wishlist: List<Wishlist> = emptyList(),
    val loading: Boolean = false,
    val error: String = ""
)
