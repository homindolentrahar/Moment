package com.homindolentrahar.moment.features.wishlist.presentation.wishlist_list

import androidx.lifecycle.ViewModel
import com.homindolentrahar.moment.features.wishlist.domain.usecase.GetAllWishlist
import com.homindolentrahar.moment.features.wishlist.domain.usecase.SaveWishlist
import com.homindolentrahar.moment.features.wishlist.domain.usecase.UpdateWishlist
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WishlistListViewModel @Inject constructor(
    private val getAllWishlist: GetAllWishlist,
    private val saveWishlist: SaveWishlist,
) : ViewModel() {
}