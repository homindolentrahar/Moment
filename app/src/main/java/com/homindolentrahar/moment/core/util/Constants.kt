package com.homindolentrahar.moment.core.util

import com.homindolentrahar.moment.R

object Constants {
    const val USERS_COLLECTION = "users"
    const val TRANSACTIONS_COLLECTION = "transactions"
    const val DATA_COLLECTION = "data"

    val categoryIcons: Map<String, Int> = hashMapOf(
        "Food" to R.drawable.food,
        "Transport" to R.drawable.transport,
        "Fashion" to R.drawable.fashion,
        "Education" to R.drawable.education,
        "Beauty" to R.drawable.beauty,
        "Groceries" to R.drawable.groceries,
        "Health" to R.drawable.health,
        "Household" to R.drawable.household,
        "Work" to R.drawable.work,
        "Investment" to R.drawable.investment,
        "Sales" to R.drawable.sales,
        "Gift" to R.drawable.gift
    )
}