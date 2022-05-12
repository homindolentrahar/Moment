package com.homindolentrahar.moment.features.auth.presentation.sign_in

data class SignInState(
    val loading: Boolean = false,
    val error: String = "",
)
