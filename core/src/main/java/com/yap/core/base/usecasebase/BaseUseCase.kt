package com.yap.core.base.usecasebase

abstract class BaseUseCase<Q : BaseUseCase.RequestValues, P : BaseUseCase.ResponseValue, L : BaseUseCase.ResponseError> {

    abstract fun executeUseCase(
        requestValues: Q?,
        responseCallback: UseCaseCallback<P, L>?
    )

    /**
     * Data passed to a request.
     */
    interface RequestValues

    /**
     * Data received from a request.
     */
    interface ResponseValue

    /**
     * Error received from a request.
     */
    interface ResponseError

}