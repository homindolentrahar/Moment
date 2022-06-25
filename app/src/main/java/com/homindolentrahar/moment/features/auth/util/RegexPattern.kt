package com.homindolentrahar.moment.features.auth.util

import java.util.regex.Pattern

object RegexPattern {
    val PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$"
    )
}