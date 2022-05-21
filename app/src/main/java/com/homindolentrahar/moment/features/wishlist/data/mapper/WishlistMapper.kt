package com.homindolentrahar.moment.features.wishlist.data.mapper

import com.homindolentrahar.moment.features.wishlist.data.remote.dto.WishlistDto
import com.homindolentrahar.moment.features.wishlist.data.remote.dto.WishlistSavingDto
import com.homindolentrahar.moment.features.wishlist.domain.model.Wishlist
import com.homindolentrahar.moment.features.wishlist.domain.model.WishlistSaving
import java.time.LocalDateTime
import java.time.ZoneOffset

fun WishlistDto.toWishlist(): Wishlist = Wishlist(
    id = id,
    name = name,
    description = description,
    price = price,
    current = current,
    _period = period,
    imageUrl = imageUrl
)

fun WishlistSavingDto.toWishlistSaving(): WishlistSaving = WishlistSaving(
    id = id,
    amount = amount,
    timestamp = LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC)
)