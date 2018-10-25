package com.example.demo.models.requests

import com.example.demo.models.global.BaseRequest

class SignUpRequest(
        login: String = "",
        password: String = "",
        val firstName: String = "",
        val lastName: String = ""
) : BaseRequest(login, password) {

    override fun isEmpty(): Boolean {
        if (login.isNotEmpty() &&
                password.isNotEmpty() &&
                firstName.isNotEmpty() &&
                lastName.isNotEmpty()
        ) return false
        return true
    }
}