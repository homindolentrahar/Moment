package com.homindolentrahar.moment.features.auth.util

sealed class AuthFailures<T>(val value: String) {
    class InvalidEmail<T>(value: String) : AuthFailures<T>(value)
    class ShortPassword<T>(value: String) : AuthFailures<T>(value)
    class InvalidPassword<T>(value: String) : AuthFailures<T>(value)
}
