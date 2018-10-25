package com.example.demo.models.requests

import com.example.demo.models.global.BaseRequest

class SignInRequest(
        login: String = "",
        password: String = ""
) : BaseRequest(login, password) {
    override fun isEmpty(): Boolean {
        if (login.isNotEmpty() &&
                password.isNotEmpty()
        ) return false
        return true
    }
}