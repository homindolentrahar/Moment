package com.homindolentrahar.moment.features.stats.presentation.stats_home

import com.homindolentrahar.moment.features.stats.domain.usecase.GetMonthlyTransactionByType
import com.homindolentrahar.moment.features.stats.domain.usecase.GetTotalAmountPerCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatsHomeViewModel @Inject constructor(
    private val getMonthlyTransactionByType: GetMonthlyTransactionByType,
    private val getTotalAmountPerCategory: GetTotalAmountPerCategory,
) {
}