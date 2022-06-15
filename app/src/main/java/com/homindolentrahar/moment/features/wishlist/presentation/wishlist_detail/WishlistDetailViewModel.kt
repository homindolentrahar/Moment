package com.homindolentrahar.moment.features.wishlist.presentation.wishlist_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homindolentrahar.moment.features.wishlist.domain.model.Wishlist
import com.homindolentrahar.moment.features.wishlist.domain.model.WishlistSaving
import com.homindolentrahar.moment.features.wishlist.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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
                .zip(getWishlistSavings(wishlistId)) { wishlist, savings ->
                    hashMapOf(
                        "wishlist" to wishlist,
                        "savings" to savings,
                    )
                }
                .onStart {
                    _state.value = _state.value.copy(
                        loading = true,
                        error = "",
                    )
                }
                .catch { error ->
                    _state.value = _state.value.copy(
                        loading = false,
                        error = error.localizedMessage!!.toString(),
                    )
                }
                .collect {
                    _state.value = _state.value.copy(
                        loading = false,
                        error = "",
                        wishlist = it["wishlist"] as Wishlist,
                        savings = it["savings"] as List<WishlistSaving>,
                    )
                }
        }
    }

    fun update(wishlistId: String, wishlist: Wishlist) {
        viewModelScope.launch {
            updateWishlist(wishlistId, wishlist)
                .onStart {
                    _state.value = _state.value.copy(
                        loading = true,
                        error = "",
                    )
                }
                .catch { error ->
                    _state.value = _state.value.copy(
                        loading = false,
                        error = error.localizedMessage!!.toString(),
                    )
                }
                .collect {
                    _state.value = _state.value.copy(
                        loading = false,
                        error = "",
                    )
                }
        }
    }

    fun remove(wishlistId: String) {
        viewModelScope.launch {
            removeWishlist(wishlistId)
                .onStart {
                    _state.value = _state.value.copy(
                        loading = true,
                        error = "",
                    )
                }
                .catch { error ->
                    _state.value = _state.value.copy(
                        loading = false,
                        error = error.localizedMessage!!.toString(),
                    )
                }
                .collect {
                    _state.value = _state.value.copy(
                        loading = false,
                        error = "",
                    )
                }
        }
    }

    fun savingSave(wishlistId: String, saving: WishlistSaving) {
        viewModelScope.launch {
            saveSaving(wishlistId, saving)
                .onStart {
                    _state.value = _state.value.copy(
                        loading = true,
                        error = "",
                    )
                }
                .catch { error ->
                    _state.value = _state.value.copy(
                        loading = false,
                        error = error.localizedMessage!!.toString(),
                    )
                }
                .collect {
                    _state.value = _state.value.copy(
                        loading = false,
                        error = "",
                    )
                }
        }
    }

    fun savingUpdate(wishlistId: String, saving: WishlistSaving) {
        viewModelScope.launch {
            updateSaving(wishlistId, saving)
                .onStart {
                    _state.value = _state.value.copy(
                        loading = true,
                        error = "",
                    )
                }
                .catch { error ->
                    _state.value = _state.value.copy(
                        loading = false,
                        error = error.localizedMessage!!.toString(),
                    )
                }
                .collect {
                    _state.value = _state.value.copy(
                        loading = false,
                        error = "",
                    )
                }
        }
    }

    fun savingRemove(wishlistId: String, savingId: String) {
        viewModelScope.launch {
            removeSaving(wishlistId, savingId)
                .onStart {
                    _state.value = _state.value.copy(
                        loading = true,
                        error = "",
                    )
                }
                .catch { error ->
                    _state.value = _state.value.copy(
                        loading = false,
                        error = error.localizedMessage!!.toString(),
                    )
                }
                .collect {
                    _state.value = _state.value.copy(
                        loading = false,
                        error = "",
                    )
                }
        }
    }

}