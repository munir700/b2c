package co.yap.sendMoney.base

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.RemittanceFeeRequest
import co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.IBase
import co.yap.yapcore.enums.FeeType
import co.yap.yapcore.helpers.extentions.parseToDouble

abstract class SMFeeViewModel<S : IBase.State>(application: Application) :
    BaseViewModel<S>(application) {
    private val transactionRepository: TransactionsRepository = TransactionsRepository
    var feeTiers: List<RemittanceFeeResponse.RemittanceFee.TierRateDTO> = arrayListOf()
    var isFeeReceived: MutableLiveData<Boolean> = MutableLiveData(false)
    var isAPIFailed: MutableLiveData<Boolean> = MutableLiveData(false)
    var feeType: String = ""
    val updatedFee: MutableLiveData<String> = MutableLiveData("0.0")

    fun getTransferFees(
        productCode: String?,
        beneficiary: Beneficiary?=null
    ) {
        launch {
            when (val response =
                transactionRepository.getTransactionFeeWithProductCode(
                    productCode, RemittanceFeeRequest(beneficiary?.country, "")
                )) {
                is RetroApiResponse.Success -> {
                    feeType = response.data.data?.feeType ?: ""
                    response.data.data?.tierRateDTOList?.let {
                        feeTiers =
                            response.data.data?.tierRateDTOList as ArrayList<RemittanceFeeResponse.RemittanceFee.TierRateDTO>
                        isFeeReceived.value = true
                    }
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
                    isAPIFailed.value = true
                }
            }
        }
    }

    fun updateFees(enterAmount: String) {
        val result = when (feeType) {
            FeeType.FLAT.name -> getFlatFee(enterAmount).toString()
            FeeType.TIER.name -> getFeeFromTier(enterAmount).toString()
            else -> {
                "0.0"
            }
        }
        updatedFee.value = result
    }

     fun getFeeFromTier(enterAmount: String): String? {
        return if (!enterAmount.isBlank()) {
            val fee = feeTiers.filter { item ->
                item.amountFrom ?: 0.0 <= enterAmount.parseToDouble() && item.amountTo ?: 0.0 >= enterAmount.parseToDouble()
            }
            if (fee[0].feeAmount != null && fee[0].vatAmount != null) {
                fee[0].feeAmount?.plus(fee[0].vatAmount ?: 0.0).toString()
            } else {
                calFeeInPercentage(enterAmount)
            }
        } else {
            null
        }
    }

     fun getFlatFee(enterAmount: String): String? {
        return if (feeTiers[0].feeAmount != null && feeTiers[0].vatAmount != null) {
            feeTiers[0].feeAmount?.plus(feeTiers[0].vatAmount ?: 0.0).toString()
        } else {
            return calFeeInPercentage(enterAmount)
        }
    }

     private fun calFeeInPercentage(enterAmount: String): String? {
        val feeAmount =
            enterAmount.parseToDouble() * (feeTiers[0].percentageFee?.parseToDouble()?.div(100)
                ?: 0.0)
        val vatAmount = feeAmount * (feeTiers[0].percentageVat?.parseToDouble()?.div(100) ?: 0.0)
        return (feeAmount + vatAmount).toString()
    }

     fun getFeeInPercentageForTopup(enterAmount: String): String? {
        val feeAmount =
            enterAmount.parseToDouble() * (feeTiers[0].percentageFee?.parseToDouble()?.div(100)
                ?: 0.0)

        val totalFeeAmount =
            feeAmount * (feeTiers[0].percentageFee?.parseToDouble()?.div(100) ?: 0.0)

        val vatAmount =
            totalFeeAmount * (feeTiers[0].percentageVat?.parseToDouble()?.div(100) ?: 0.0)

        return (totalFeeAmount + vatAmount).toString()
    }
}