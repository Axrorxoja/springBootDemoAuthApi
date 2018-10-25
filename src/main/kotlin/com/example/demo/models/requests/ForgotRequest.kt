package com.example.demo.models.requests

import com.example.demo.models.global.ICheck

class ForgotRequest(
        val login: String = "",
        val firstName: String = "",
        val lastName: String = ""
) : ICheck {
    override fun isEmpty(): Boolean {
        if (login.isNotEmpty() &&
                firstName.isNotEmpty() &&
                lastName.isNotEmpty()
        ) return false
        return true
    }
}