package com.homindolentrahar.moment.features.wishlist.presentation.wishlist_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.wishlist.domain.model.Wishlist
import com.homindolentrahar.moment.features.wishlist.domain.usecase.GetAllWishlist
import com.homindolentrahar.moment.features.wishlist.domain.usecase.SaveWishlist
import com.homindolentrahar.moment.features.wishlist.domain.usecase.UpdateWishlist
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistListViewModel @Inject constructor(
    private val getAllWishlist: GetAllWishlist,
    private val saveWishlist: SaveWishlist,
) : ViewModel() {

    private val _state = MutableStateFlow(WishlistListState())
    val state: StateFlow<WishlistListState>
        get() = _state


    fun allWishlist() {
        viewModelScope.launch {
            getAllWishlist()
                .collect { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                wishlist = emptyList(),
                                error = resource.message ?: "Unexpected error",
                                loading = false
                            )
                        }
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(
                                loading = true
                            )
                        }
                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                wishlist = resource.data ?: emptyList(),
                                error = "",
                                loading = false
                            )
                        }
                    }
                }
        }
    }

    fun save(wishlist: Wishlist) {
        viewModelScope.launch {
            saveWishlist(wishlist)
                .collect { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                error = resource.message ?: "Unexpected error",
                                loading = false
                            )
                        }
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(
                                loading = true
                            )
                        }
                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                error = "",
                                loading = false
                            )
                        }
                    }
                }
        }
    }
}