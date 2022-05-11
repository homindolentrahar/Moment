package com.homindolentrahar.moment.features.auth.presentation.sign_in

import com.homindolentrahar.moment.features.auth.domain.model.AuthUser

data class SignInState(
    val loading: Boolean = false,
    val error: String = "",
)
