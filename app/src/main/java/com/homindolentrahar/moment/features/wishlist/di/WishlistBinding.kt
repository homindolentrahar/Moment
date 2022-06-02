package com.homindolentrahar.moment.features.wishlist.di

import com.homindolentrahar.moment.features.wishlist.data.repository.WishlistRepositoryImpl
import com.homindolentrahar.moment.features.wishlist.domain.repository.WishlistRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class WishlistBinding {

    @Binds
    abstract fun bindWishlistRepository(repositoryImpl: WishlistRepositoryImpl): WishlistRepository

}