package com.homindolentrahar.moment.features.wishlist.data.mapper

import com.homindolentrahar.moment.features.wishlist.data.remote.dto.WishlistDto
import com.homindolentrahar.moment.features.wishlist.data.remote.dto.WishlistSavingDto
import com.homindolentrahar.moment.features.wishlist.domain.model.Wishlist
import com.homindolentrahar.moment.features.wishlist.domain.model.WishlistSaving
import java.time.LocalDateTime
import java.time.ZoneOffset

fun WishlistDto.toDocumentSnapshot(): Map<String, Any> =
    hashMapOf(
        "id" to id,
        "name" to name,
        "description" to description,
        "price" to price,
        "current" to current,
        "period" to period,
        "imageUrl" to imageUrl
    )

fun WishlistDto.toWishlist(): Wishlist = Wishlist(
    id = id,
    name = name,
    description = description,
    price = price,
    current = current,
    _period = period,
    imageUrl = imageUrl,
    created_at = LocalDateTime.ofEpochSecond(created_at, 0, ZoneOffset.UTC),
    updated_at = LocalDateTime.ofEpochSecond(updated_at, 0, ZoneOffset.UTC)
)

fun WishlistSavingDto.toDocumentSnapshot(): Map<String, Any> = hashMapOf(
    "id" to id,
    "amount" to amount,
    "timestamp" to timestamp
)

fun WishlistSavingDto.toWishlistSaving(): WishlistSaving = WishlistSaving(
    id = id,
    amount = amount,
    timestamp = LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC)
)