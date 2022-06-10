package com.homindolentrahar.moment.features.bill.domain.usecase

import com.homindolentrahar.moment.core.util.Resource
import com.homindolentrahar.moment.features.bill.domain.repository.BillRepository
import javax.inject.Inject

class RemoveBill @Inject constructor(
    private val repository: BillRepository
) {
    suspend operator fun invoke(id: String): Resource<Unit> = try {
        Resource.Success(repository.removeBill(id))
    } catch (exception: Exception) {
        Resource.Error(exception.localizedMessage ?: "Unexpected error")
    }
}