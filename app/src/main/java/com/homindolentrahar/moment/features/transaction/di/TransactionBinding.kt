package com.homindolentrahar.moment.features.transaction.di

import com.homindolentrahar.moment.features.transaction.data.repository.TransactionRepositoryImpl
import com.homindolentrahar.moment.features.transaction.domain.repository.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TransactionBinding {

    @Binds
    abstract fun bindTransactionRepository(repositoryImpl: TransactionRepositoryImpl): TransactionRepository

}