package com.homindolentrahar.moment.features.auth.util

import arrow.core.Either
import com.homindolentrahar.moment.core.util.ValueFailures


interface AuthValueObjects<T> {
    val value: Either<ValueFailures<T>, T>

    fun isValid(): Boolean = value.isRight()
}

class EmailAddress(private val input: String) : AuthValueObjects<String> {
    override val value: Either<ValueFailures<String>, String>
        get() = validateEmailAddress(input)
}

class Password(private val input: String) : AuthValueObjects<String> {
    override val value: Either<ValueFailures<String>, String>
        get() = validatePassword(input)
}