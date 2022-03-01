package com.yap.core.base.usecasebase

interface UseCaseCallback<R, E> {
    fun onSuccess(response: R)
    fun onError(error: E)
}
