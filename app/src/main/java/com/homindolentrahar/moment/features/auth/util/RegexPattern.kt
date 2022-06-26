package com.homindolentrahar.moment.features.auth.util

import java.util.regex.Pattern

object RegexPattern {
    val NAME_PATTERN = Pattern.compile(
        "^(?=.*[a-z]).{3,}$"
    )
    val PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"
    )
}