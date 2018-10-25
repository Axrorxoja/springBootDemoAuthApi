package com.example.demo.common

enum class ErrorCodes(val message: String,
                      val code: Int) {

    EMPTY_DATA("data is empty", 0),
    //sign in errors
    PASSWORD_OR_LOGIN_INCORRECT("password or login incorrect", 1),

    //sign up errors,
    VALIDATION_ERROR("validation error", 2),
    USER_WITH_SUCH_LOGIN_EXIST("user with such a login exists", 3),

    //forgot error
    USER_NOT_FOUND("user with such data not found ", 4),

    //reset errors
    USER_WITH_SUCH_AUTH_CODE_NOT_FOUND("user with such auth code not found ", 5),
}
