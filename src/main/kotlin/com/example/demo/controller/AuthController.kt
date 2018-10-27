package com.example.demo.controller

import com.example.demo.common.ErrorCodes
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
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/")
class AuthController : IAuthController {
    override val userList = mutableListOf<AppUser>()
    override val authHash: HashMap<Int, Long> by lazy { hashMapOf<Int, Long>() }
    private val autoCodeGenerator: Random  by lazy { Random() }

    @PostMapping("sign_up")
    override fun signUp(@RequestBody req: SignUpRequest)
            : BaseData<SignUpResponse> {

        if (req.isEmpty()) return BaseData(ErrorCodes.EMPTY_DATA)

        val baseData: BaseData<SignUpResponse>
        val temporalUserList = userList.filter { it.checkLogin(req) }
        baseData = if (temporalUserList.isEmpty()) {
            val user = AppUser(req)
            userList.add(user)
            BaseData(success = SignUpResponse(user.id))
        } else {
            BaseData(ErrorCodes.USER_WITH_SUCH_LOGIN_EXIST)
        }
        return baseData
    }

    @PostMapping("sign_in")
    override fun signIn(@RequestBody req: SignInRequest)
            : BaseData<SignInResponse> {
        if (req.isEmpty()) return BaseData(ErrorCodes.EMPTY_DATA)

        val baseData: BaseData<SignInResponse>
        val temporalList = userList.filter { it.checkLoginAndPassword(req) }
        baseData = if (temporalList.isEmpty()) {
            BaseData(ErrorCodes.USER_NOT_FOUND)
        } else {
            val user = temporalList[0]
            BaseData(success = SignInResponse(user.id))
        }
        return baseData
    }

    @PostMapping("forgot")
    override fun forget(@RequestBody req: ForgotRequest)
            : BaseData<ForgotResponse> {
        if (req.isEmpty()) return BaseData(ErrorCodes.EMPTY_DATA)

        val baseData: BaseData<ForgotResponse>
        val temporalList = userList.filter { it.checkFieldsIsEqual(req) }
        baseData = if (temporalList.isEmpty()) {
            BaseData(ErrorCodes.USER_NOT_FOUND)
        } else {
            val user = temporalList[0]
            val authCode = autoCodeGenerator.nextInt()
            authHash[authCode] = user.id
            BaseData(success = ForgotResponse(authCode))
        }
        return baseData
    }

    @PostMapping("reset")
    override fun resetPassword(@RequestBody req: ResetPasswordRequest)
            : BaseData<ResetPasswordResponse> {
        if (req.isEmpty()) return BaseData(ErrorCodes.EMPTY_DATA)

        val baseData: BaseData<ResetPasswordResponse>
        val userId = authHash[req.code]
        baseData = if (userId != null) {
            authHash.remove(req.code)
            val item = userList.find { it.id == userId }
            if (item != null) {
                item.password = req.password
                BaseData(success = ResetPasswordResponse(userId))
            } else {
                BaseData(ErrorCodes.USER_NOT_FOUND)
            }
        } else {
            BaseData(ErrorCodes.USER_WITH_SUCH_AUTH_CODE_NOT_FOUND)
        }
        return baseData
    }

}