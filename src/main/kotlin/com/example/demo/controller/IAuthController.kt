package com.example.demo.controller

import com.example.demo.entity.AppUser
import com.example.demo.entity.BaseData
import com.example.demo.models.requests.ForgotRequest
import com.example.demo.models.requests.ResetPasswordRequest
import com.example.demo.models.requests.SignInRequest
import com.example.demo.models.requests.SignUpRequest
import com.example.demo.models.responses.ForgotResponse
import com.example.demo.models.responses.ResetPasswordResponse
import com.example.demo.models.responses.SignInResponse
import com.example.demo.models.responses.SignUpResponse
import java.util.*

interface IAuthController {
    val userList: MutableList<AppUser>
    val authHash: HashMap<Int, Long>

    fun signIn(req: SignInRequest): BaseData<SignInResponse>

    fun signUp(req: SignUpRequest): BaseData<SignUpResponse>

    fun forget(req: ForgotRequest): BaseData<ForgotResponse>

    fun resetPassword(req: ResetPasswordRequest): BaseData<ResetPasswordResponse>
}