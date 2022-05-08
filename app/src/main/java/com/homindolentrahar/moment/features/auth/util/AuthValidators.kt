package com.homindolentrahar.moment.features.auth.util

import arrow.core.Either


fun validateEmailAddress(input: String): Either<AuthFailures<String>, String> {
    return if (android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
        Either.Right(input)
    } else {
        Either.Left(AuthFailures.InvalidEmail(input))
    }
}

fun validatePassword(input: String): Either<AuthFailures<String>, String> {
    val passwordPattern =
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\\\S+\$).{4,}\$\n"

    return if (input.length < 6) {
        Either.Left(AuthFailures.ShortPassword(input))
    } else if (!Regex(passwordPattern).matches(input)) {
        Either.Left(AuthFailures.InvalidPassword(input))
    } else {
        Either.Right(input)
    }
}