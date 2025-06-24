package com.example.udemy_android_template.ui.siginup

interface SignUpView {
    fun onSignUpLoading()
    fun onSignUpSuccess()
    fun onSignUpFailure()
    //fun onSignUpFailure(code: Int, message: String)
}