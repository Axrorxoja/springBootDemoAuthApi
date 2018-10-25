package com.example.demo.models.global

abstract class BaseRequest(
        val login: String = "",
        var password: String = ""
) : ICheck