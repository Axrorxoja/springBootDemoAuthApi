package com.example.demo.entity

import com.example.demo.models.requests.ForgotRequest
import com.example.demo.models.requests.SignInRequest
import com.example.demo.models.requests.SignUpRequest

class AppUser(
        val login: String = "",
        var password: String = "",
        val firstName: String = "",
        val lastName: String = ""
) {
    fun checkLoginAndPassword(req: SignInRequest): Boolean {
        return login == req.login && password == req.password
    }

    fun checkLogin(req: SignUpRequest): Boolean {
        return login == req.login
    }

    fun checkFieldsIsEqual(req: ForgotRequest): Boolean {
        return login == req.login &&
                firstName == req.firstName &&
                lastName == req.lastName
    }

    val id: Long = idCounter++

    constructor(request: SignUpRequest) : this(
            request.login,
            request.password,
            request.firstName,
            request.lastName
    )

    companion object {
        private var idCounter: Long = 0
    }
}