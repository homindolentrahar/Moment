package com.homindolentrahar.moment.features.auth.di

import com.homindolentrahar.moment.features.auth.data.repository.AuthRepositoryImpl
import com.homindolentrahar.moment.features.auth.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthBinding {

    @Binds
    abstract fun bindAuthRepository(repositoryImpl: AuthRepositoryImpl): AuthRepository

}