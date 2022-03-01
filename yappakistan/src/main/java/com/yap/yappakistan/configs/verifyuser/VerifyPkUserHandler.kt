package com.yap.yappakistan.configs.verifyuser

import com.yap.core.IUserVerifier
import com.yap.core.base.usecasebase.UseCaseCallback
import com.yap.yappakistan.configs.AppConfigurations
import com.yap.yappakistan.networking.microservices.customers.CustomersApi
import com.yap.yappakistan.networking.microservices.customers.CustomersRepository
import com.yap.yappakistan.networking.microservices.customers.CustomersRetroService
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class VerifyPkUserHandler @Inject constructor(private val verifyPkUserUC: VerifyPkUserUC) :
    IUserVerifier {

    override fun verifyUser(
        mobileNo: String,
        completion: (Result<Boolean>) -> Unit
    ) {
        verifyPkUserUC.executeUseCase(
            requestValues = VerifyPkUserUC.RequestValues(mobileNo),
            responseCallback = object :
                UseCaseCallback<VerifyPkUserUC.ResponseValue, VerifyPkUserUC.ErrorValue> {

                override fun onSuccess(response: VerifyPkUserUC.ResponseValue) {
                    completion.invoke(Result.success(response.result))
                }

                override fun onError(error: VerifyPkUserUC.ErrorValue) {
                    completion.invoke(Result.failure(Throwable(error.msg)))
                }
            })
    }
}


class PkUserVerifierFactory {

    fun create(): IUserVerifier {
        val customersService =
            AppConfigurations.getRetrofit()?.createService(CustomersRetroService::class.java)
        val customersApi: CustomersApi = CustomersRepository(customersService!!)
        val verifyPkUserUC = VerifyPkUserUC(customersApi, Dispatchers.IO)

        return VerifyPkUserHandler(verifyPkUserUC)
    }
}