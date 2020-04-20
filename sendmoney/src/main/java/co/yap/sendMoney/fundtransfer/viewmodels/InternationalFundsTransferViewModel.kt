package co.yap.sendMoney.fundtransfer.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.RemittanceFeeRequest
import co.yap.networking.transactions.requestdtos.RxListRequest
import co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList
import co.yap.networking.transactions.responsedtos.transaction.FxRateResponse
import co.yap.networking.transactions.responsedtos.transaction.RemittanceFeeResponse
import co.yap.sendMoney.fundtransfer.interfaces.IInternationalFundsTransfer
import co.yap.sendMoney.fundtransfer.states.InternationalFundsTransferState
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.FeeType
import co.yap.yapcore.helpers.extentions.parseToDouble
import java.util.*
import kotlin.collections.ArrayList

class InternationalFundsTransferViewModel(application: Application) :
    BeneficiaryFundTransferBaseViewModel<IInternationalFundsTransfer.State>(application),
    IInternationalFundsTransfer.ViewModel,
    IRepositoryHolder<CustomersRepository> {
    private var mTransactionsRepository: TransactionsRepository = TransactionsRepository
    override val repository: CustomersRepository = CustomersRepository
    override val state: InternationalFundsTransferState =
        InternationalFundsTransferState(
            application
        )
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var isAPIFailed: MutableLiveData<Boolean> = MutableLiveData()
    override var transactionData: ArrayList<InternationalFundsTransferReasonList.ReasonList> =
        ArrayList()
    override val populateSpinnerData: MutableLiveData<ArrayList<InternationalFundsTransferReasonList.ReasonList>> =
        MutableLiveData()
    override var reasonPosition: Int = 0
    override var fxRateResponse: MutableLiveData<FxRateResponse.Data> = MutableLiveData()
    override var transactionFeeResponse: MutableLiveData<RemittanceFeeResponse.RemittanceFee> =
        MutableLiveData()
    override var feeTiers: List<RemittanceFeeResponse.RemittanceFee.TierRateDTO> = arrayListOf()

    override fun handlePressOnButton(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
        parentViewModel?.state?.toolBarTitle = getString(Strings.screen_funds_toolbar_header)
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_international_funds_transfer_display_text_title))
    }

    override fun getTransactionFeeInternational(productCode: String?) {
        launch {
            val remittanceFeeRequestBody =
                RemittanceFeeRequest(parentViewModel?.beneficiary?.value?.country, "")
            when (val response =
                mTransactionsRepository.getTransactionFeeWithProductCode(
                    productCode,
                    remittanceFeeRequestBody
                )) {
                is RetroApiResponse.Success -> {
                    transactionFeeResponse.value = response.data.data
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    isAPIFailed.value = true
                    state.toast = response.error.message
                }
            }
        }
    }

    override fun getTransactionInternationalfxList(productCode: String?) {
        launch {
            state.loading = true
            val rxListBody = RxListRequest(parentViewModel?.beneficiary?.value?.id.toString())

            when (val response =
                mTransactionsRepository.getTransactionInternationalRXList(
                    productCode,
                    rxListBody
                )) {
                is RetroApiResponse.Success -> {
                    fxRateResponse.value = response.data.data
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    isAPIFailed.value = true
                    state.toast = response.error.message
                }
            }
        }
    }

    override fun getReasonList(productCode: String?) {
        launch {
            transactionData.clear()
            //            state.loading = true
            when (val response =
                mTransactionsRepository.getTransactionInternationalReasonList(productCode)) {
                is RetroApiResponse.Success -> {
                    if (response.data.data.isNullOrEmpty()) return@launch
                    response.data.data?.let {
                        transactionData.addAll(it.map { item ->
                            InternationalFundsTransferReasonList.ReasonList(
                                code = item.code ?: "",
                                reason = item.reason ?: ""
                            )
                        })
                    }
                    populateSpinnerData.value = transactionData
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    isAPIFailed.value = true
                }
            }
        }
    }

    override fun getMoneyTransferLimits(productCode: String?) {
        launch {
            when (val response = mTransactionsRepository.getFundTransferLimits(productCode)) {
                is RetroApiResponse.Success -> {
                    state.maxLimit = response.data.data?.maxLimit?.toDouble() ?: 0.00
                    state.minLimit = response.data.data?.minLimit?.toDouble() ?: 0.00
                    getCountryLimits()
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    isAPIFailed.value = true
                }
            }
        }

    }

    override fun getCountryLimits() {
        launch {
            when (val response = repository.getCountryTransactionLimits(
                parentViewModel?.beneficiary?.value?.country ?: "",
                parentViewModel?.beneficiary?.value?.currency ?: ""
            )) {
                is RetroApiResponse.Success -> {
                    if (response.data.data?.toDouble() ?: 0.0 > 0.0) {
                        state.maxLimit = response.data.data?.toDouble()
                        if (response.data.data?.toDouble() ?: 0.0 < state.minLimit?.toDouble() ?: 0.0) {
                            state.minLimit = 1.0
                        }
                    }
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    isAPIFailed.value = true
                }
            }
        }

    }

    override fun getTransactionThresholds() {
        launch {
            when (val response = mTransactionsRepository.getTransactionThresholds()) {
                is RetroApiResponse.Success -> {
                    parentViewModel?.transactionThreshold?.value = response.data.data
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    isAPIFailed.value = true
                }
            }
        }
    }

    fun getFeeFromTier(enterAmount: String?): String? {
        return if (!enterAmount.isNullOrBlank()) {
            val fee = feeTiers.filter { item ->
                item.amountFrom ?: 0.0 <= enterAmount.parseToDouble() && item.amountTo ?: 0.0 >= enterAmount.parseToDouble()
            }
            fee[0].feeAmount?.plus(fee[0].vatAmount ?: 0.0).toString()
        } else {
            null
        }
    }

    fun getTotalAmountWithFee(): Double {
        return (when (transactionFeeResponse.value?.feeType) {
            FeeType.TIER.name -> {
                val transferFee = getFeeFromTier(state.etInputAmount)
                state.etInputAmount?.toDoubleOrNull() ?: 0.0.plus(
                    transferFee?.toDoubleOrNull() ?: 0.0
                )
            }
            FeeType.FLAT.name -> {
                val transferFee = transactionFeeResponse.value?.tierRateDTOList?.get(0)
                    ?.feeAmount?.plus(
                    transactionFeeResponse.value?.tierRateDTOList?.get(0)?.vatAmount ?: 0.0
                )
                state.etInputAmount?.toDoubleOrNull() ?: 0.0.plus(transferFee ?: 0.0)
            }
            else -> {
                0.00
            }
        })
    }

    fun setDestinationAmount() {
        if (!state.etInputAmount.isNullOrBlank()) {
            val totalDestinationAmount = state.etInputAmount?.toDoubleOrNull()
                ?.div(parentViewModel?.transferData?.value?.rate?.toDoubleOrNull() ?: 0.0)

            state.etOutputAmount = String.format(
                Locale.getDefault(),
                "%.02f",
                totalDestinationAmount
            )
        } else {
            state.etOutputAmount = ""
        }
    }
}
