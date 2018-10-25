package com.example.demo.models.requests

import com.example.demo.models.global.ICheck

class ResetPasswordRequest(
        val code: Int = -1,
        val password: String = ""
) : ICheck {
    override fun isEmpty(): Boolean {
        if (password.isNotEmpty() &&
                code != -1
        ) return false
        return true
    }
}