package com.example.flo.ui.login


import com.example.flo.NetWork.Result
interface LoginView {
    fun onLoginSuccess(code : Int, result : Result)
    fun onLoginFailure()
}