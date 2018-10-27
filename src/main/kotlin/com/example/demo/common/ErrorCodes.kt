package com.example.demo.common

object ErrorCodes {
    val EMPTY_DATA = ErrorCode("data is empty", 0)
    //sign in errors
    val PASSWORD_OR_LOGIN_INCORRECT = ErrorCode("password or login incorrect", 1)

    //sign up errors,
    val VALIDATION_ERROR = ErrorCode("validation error", 2)
    val USER_WITH_SUCH_LOGIN_EXIST = ErrorCode("user with such a login exists", 3)

    //forgot error
    val USER_NOT_FOUND = ErrorCode("user with such data not found ", 4)

    //reset errors
    val USER_WITH_SUCH_AUTH_CODE_NOT_FOUND = ErrorCode("user with such auth code not found ", 5)

}