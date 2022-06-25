package com.homindolentrahar.moment.features.bill.di

import com.homindolentrahar.moment.features.bill.data.repository.BillRepositoryImpl
import com.homindolentrahar.moment.features.bill.domain.repository.BillRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BillBinding {

    @Binds
    abstract fun bindBillRepository(repositoryImpl: BillRepositoryImpl): BillRepository

}