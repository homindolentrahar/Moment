package com.homindolentrahar.moment.features.wishlist.presentation.wishlist_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homindolentrahar.moment.features.wishlist.domain.model.Wishlist
import com.homindolentrahar.moment.features.wishlist.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class WishlistListViewModel @Inject constructor(
    private val getMonthlyWishlist: GetMonthlyWishlist,
    private val getHighestWishlist: GetHighestWishlist,
    private val getLowestWishlist: GetLowestWishlist,
    private val saveWishlist: SaveWishlist,
) : ViewModel() {

    private val _state = MutableStateFlow(WishlistListState())
    val state: StateFlow<WishlistListState>
        get() = _state

    init {
        allWishlist()
    }


    fun allWishlist(date: LocalDateTime = LocalDateTime.now()) {
        viewModelScope.launch {
            getMonthlyWishlist(date)
                .zip(getHighestWishlist()) { monthly, highest ->
                    hashMapOf(
                        "monthly" to monthly,
                        "highest" to highest,
                    )
                }
                .zip(getLowestWishlist()) { pair, lowest ->
                    hashMapOf(
                        pair.map { it.toPair() }.first(),
                        pair.map { it.toPair() }.last(),
                        "lowest" to lowest,
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
                        error = error.localizedMessage!!.toString(),
                        loading = false
                    )
                }
                .collect { data ->
                    _state.value = _state.value.copy(
                        error = "",
                        wishlist = data["wishlist"] as List<Wishlist>,
                        highest = data["highest"] as Wishlist,
                        lowest = data["lowest"] as Wishlist,
                        loading = false,
                    )
                }
        }
    }

    fun save(wishlist: Wishlist) {
        viewModelScope.launch {
            saveWishlist(wishlist)
                .onStart {
                    _state.value = _state.value.copy(
                        error = "",
                        loading = true,
                    )
                }
                .catch { error ->
                    _state.value = _state.value.copy(
                        error = error.localizedMessage!!.toString(),
                        loading = false,
                    )
                }
                .collect {
                    _state.value = _state.value.copy(
                        error = "",
                        loading = false,
                    )
                }
        }
    }
}