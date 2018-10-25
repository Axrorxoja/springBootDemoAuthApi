package com.example.demo

import com.example.demo.common.ErrorCodes
import com.example.demo.controller.IAuthController
import com.example.demo.models.requests.ForgotRequest
import com.example.demo.models.requests.ResetPasswordRequest
import com.example.demo.models.requests.SignInRequest
import com.example.demo.models.requests.SignUpRequest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest(classes = [DemoApplication::class])
class DemoApplicationTests {
    @Autowired
    private lateinit var authController: IAuthController

    @Test
    fun testWithEmptyDataSignUp() {
        val emptyRequest = SignUpRequest()
        val res = authController.signUp(emptyRequest)
        Assert.assertNotNull(res.error)
        Assert.assertNull(res.success)
        Assert.assertEquals(res.error, ErrorCodes.EMPTY_DATA)
    }

    @Test
    fun testCreatedUserIdIncrementation() {
        authController.userList.clear()
        val reqSignUp1 = createSignUpReq()
        val resSignUp1 = authController.signUp(reqSignUp1)
        val reqSignUp2 = createSignUpReq2()
        val resSignUp2 = authController.signUp(reqSignUp2)

        Assert.assertNull(resSignUp1.error)
        Assert.assertNotNull(resSignUp1.success)
        Assert.assertNull(resSignUp1.error)
        Assert.assertNotNull(resSignUp2.success)
        Assert.assertTrue(resSignUp2.success!!.userId > resSignUp1.success!!.userId)

    }

    @Test
    fun testWithExistingUser() {
        authController.userList.clear()
        val successReq = createSignUpReq()
        var res = authController.signUp(successReq)
        Assert.assertNull(res.error)
        Assert.assertNotNull(res.success)
        println("userId${res.success!!.userId}")
        Assert.assertTrue(res.success!!.userId >= 0)


        res = authController.signUp(successReq)
        Assert.assertNotNull(res.error)
        Assert.assertNull(res.success)
        Assert.assertEquals(res.error, ErrorCodes.USER_WITH_SUCH_LOGIN_EXIST)
    }

    private fun createSignUpReq() = SignUpRequest(
            "testLogin",
            "testPassword",
            "firstName",
            "lastName"
    )

    private fun createSignUpReq2() = SignUpRequest(
            "testAnotherLogin",
            "testPassword",
            "firstName",
            "lastName"
    )

    @Test
    fun testSignIn() {
        authController.userList.clear()
        val signUp = createSignUpReq()
        authController.signUp(signUp)
        val signIn = SignInRequest(signUp.login, signUp.password)
        val signInRes = authController.signIn(signIn)
        Assert.assertNull(signInRes.error)
        Assert.assertNotNull(signInRes.success)
        Assert.assertTrue(signInRes.success!!.userId >= 0)
    }

    @Test
    fun testEmptySignIn() {
        val req = SignInRequest()
        val res = authController.signIn(req)
        Assert.assertNotNull(res.error)
        Assert.assertNull(res.success)
        Assert.assertEquals(res.error, ErrorCodes.EMPTY_DATA)
    }

    @Test
    fun testFailSignIn() {
        authController.userList.clear()
        val reqSignUp = createSignUpReq()
        authController.signUp(reqSignUp)
        val reqSignIn = SignInRequest("${reqSignUp.login} another", reqSignUp.password)
        val res = authController.signIn(reqSignIn)
        Assert.assertNotNull(res.error)
        Assert.assertNull(res.success)
        Assert.assertEquals(res.error, ErrorCodes.USER_NOT_FOUND)
    }

    @Test
    fun testEmptyForgot() {
        val req = ForgotRequest()
        val res = authController.forget(req)
        Assert.assertNotNull(res.error)
        Assert.assertNull(res.success)
        Assert.assertEquals(res.error, ErrorCodes.EMPTY_DATA)
    }

    @Test
    fun testFailForgot() {
        authController.userList.clear()
        val reqSignUp = createSignUpReq()
        authController.signUp(reqSignUp)
        val forgotReq = ForgotRequest("${reqSignUp.login} another", reqSignUp.firstName, reqSignUp.lastName)
        val res = authController.forget(forgotReq)
        Assert.assertNotNull(res.error)
        Assert.assertNull(res.success)
        Assert.assertEquals(res.error, ErrorCodes.USER_NOT_FOUND)
    }

    @Test
    fun testSuccessForgot() {
        authController.userList.clear()
        val reqSignUp = createSignUpReq()
        authController.signUp(reqSignUp)
        val forgotReq = ForgotRequest(reqSignUp.login, reqSignUp.firstName, reqSignUp.lastName)
        val res = authController.forget(forgotReq)
        Assert.assertNotNull(res.success)
        Assert.assertNull(res.error)
        Assert.assertTrue(res.success!!.authCode != -1)
    }

    @Test
    fun testEmptyReset() {
        val req = ResetPasswordRequest()
        val res = authController.resetPassword(req)
        Assert.assertNotNull(res.error)
        Assert.assertNull(res.success)
        Assert.assertEquals(res.error, ErrorCodes.EMPTY_DATA)
    }

    @Test
    fun testFailReset() {
        authController.userList.clear()
        val reqSignUp = createSignUpReq()
        authController.signUp(reqSignUp)
        val forgotReq = ForgotRequest(reqSignUp.login, reqSignUp.firstName, reqSignUp.lastName)
        val res = authController.forget(forgotReq)
        val reqReset = ResetPasswordRequest(res.success!!.authCode + 1, "some password")
        val resReset = authController.resetPassword(reqReset)
        Assert.assertNotNull(resReset.error)
        Assert.assertNull(resReset.success)
        Assert.assertEquals(resReset.error, ErrorCodes.USER_WITH_SUCH_AUTH_CODE_NOT_FOUND)
    }

    @Test
    fun testSuccessReset() {
        authController.userList.clear()
        val reqSignUp = createSignUpReq()
        authController.signUp(reqSignUp)
        val forgotReq = ForgotRequest(reqSignUp.login, reqSignUp.firstName, reqSignUp.lastName)
        val res = authController.forget(forgotReq)
        val reqReset = ResetPasswordRequest(res.success!!.authCode, "newPassword")
        val resReset = authController.resetPassword(reqReset)
        Assert.assertNotNull(resReset.success)
        Assert.assertNull(resReset.error)
        Assert.assertTrue(resReset.success!!.userId >= 0)

        val reqSignIn = SignInRequest(reqSignUp.login,"newPassword")
        val resSignIn = authController.signIn(reqSignIn)
        Assert.assertNotNull(resSignIn.success)
        Assert.assertNull(resSignIn.error)
        Assert.assertTrue(resSignIn.success!!.userId>=0)
    }

}
