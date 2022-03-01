package com.yap.core

interface IUserVerifier {
    fun verifyUser(
        mobileNo: String,
        completion: (Result<Boolean>) -> Unit
    )
}