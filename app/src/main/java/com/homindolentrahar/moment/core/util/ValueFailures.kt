package com.homindolentrahar.moment.core.util

sealed class ValueFailures<T>(val value: String) {
    class InvalidEmail<T>(value: String) : ValueFailures<T>(value)
    class ShortPassword<T>(value: String) : ValueFailures<T>(value)
    class InvalidPassword<T>(value: String) : ValueFailures<T>(value)
    class InvalidFullName<T>(value: String) : ValueFailures<T>(value)
}