package com.homindolentrahar.moment.features.auth.util

import arrow.core.Either


interface AuthValueObjects<T> {
    val value: Either<AuthFailures<T>, T>
}

class EmailAddress(private val input: String) : AuthValueObjects<String> {
    override val value: Either<AuthFailures<String>, String>
        get() = validateEmailAddress(input)
}

class Password(private val input: String) : AuthValueObjects<String> {
    override val value: Either<AuthFailures<String>, String>
        get() = validatePassword(input)
}