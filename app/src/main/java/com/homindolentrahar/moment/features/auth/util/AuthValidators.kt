package com.homindolentrahar.moment.features.auth.util

import arrow.core.Either
import com.homindolentrahar.moment.core.util.ValueFailures


fun validateEmailAddress(input: String): Either<ValueFailures<String>, String> {
    return if (android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
        Either.Right(input)
    } else {
        Either.Left(ValueFailures.InvalidEmail(input))
    }
}

fun validatePassword(input: String): Either<ValueFailures<String>, String> {
    val passwordPattern =
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\\\S+\$).{4,}\$\n"

    return if (input.length < 6) {
        Either.Left(ValueFailures.ShortPassword(input))
    } else if (!Regex(passwordPattern).matches(input)) {
        Either.Left(ValueFailures.InvalidPassword(input))
    } else {
        Either.Right(input)
    }
}