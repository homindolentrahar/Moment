package com.homindolentrahar.moment.features.wishlist.presentation.wishlist_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.wishlist.domain.model.Wishlist
import com.homindolentrahar.moment.features.wishlist.domain.model.WishlistSaving
import com.homindolentrahar.moment.features.wishlist.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistDetailViewModel @Inject constructor(
    private val getSingleWishlist: GetSingleWishlist,
    private val getWishlistSavings: GetWishlistSavings,
    private val saveSaving: SaveSaving,
    private val updateWishlist: UpdateWishlist,
    private val updateSaving: SaveSaving,
    private val removeWishlist: RemoveWishlist,
    private val removeSaving: RemoveSaving
) : ViewModel() {

    private val _state = MutableStateFlow(WishlistDetailState())
    val state: StateFlow<WishlistDetailState>
        get() = _state

    fun singleWishlist(wishlistId: String) {
        viewModelScope.launch {
            getSingleWishlist(wishlistId)
                .collect { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                wishlist = null,
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
                                wishlist = resource.data,
                                error = "",
                                loading = false
                            )
                        }
                    }
                }
        }
    }

    fun allSavings(wishlistId: String) {
        viewModelScope.launch {
            getWishlistSavings(wishlistId)
                .collect { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                savings = emptyList(),
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
                                savings = resource.data ?: emptyList(),
                                error = "",
                                loading = false
                            )
                        }
                    }
                }
        }
    }

    fun savingSave(wishlistId: String, saving: WishlistSaving) {
        viewModelScope.launch {
            saveSaving(wishlistId, saving)
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

    fun update(wishlistId: String, wishlist: Wishlist) {
        viewModelScope.launch {
            updateWishlist(wishlistId, wishlist)
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

    fun savingUpdate(wishlistId: String, saving: WishlistSaving) {
        viewModelScope.launch {
            updateSaving(wishlistId, saving)
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

    fun remove(wishlistId: String) {
        viewModelScope.launch {
            removeWishlist(wishlistId)
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

    fun savingRemove(wishlistId: String, savingId: String) {
        viewModelScope.launch {
            removeSaving(wishlistId, savingId)
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