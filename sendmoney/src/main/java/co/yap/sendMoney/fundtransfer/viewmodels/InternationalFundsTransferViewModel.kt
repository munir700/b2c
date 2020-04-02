package co.yap.sendMoney.fundtransfer.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.RxListRequest
import co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment
import co.yap.networking.transactions.responsedtos.transaction.FxRateResponse
import co.yap.sendMoney.fundtransfer.interfaces.IInternationalFundsTransfer
import co.yap.sendMoney.fundtransfer.states.InternationalFundsTransferState
import co.yap.sendmoney.R
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.FeeType
import co.yap.yapcore.helpers.extentions.parseToDouble
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.managers.MyUserManager
import java.util.*

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
    override var purposeOfPaymentList: MutableLiveData<ArrayList<PurposeOfPayment>> =
        MutableLiveData()
    override var reasonPosition: Int = 0
    override var fxRateResponse: MutableLiveData<FxRateResponse.Data> = MutableLiveData()
    var purposeCategories: Map<String?, List<PurposeOfPayment>>? = HashMap()

    override fun handlePressOnButton(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
        parentViewModel?.state?.toolBarTitle = getString(Strings.screen_funds_toolbar_header)
        state.availableBalanceString =
            context.resources.getText(
                getString(Strings.screen_cash_transfer_display_text_available_balance),
                context.color(
                    R.color.colorPrimaryDark,
                    "${"AED"} ${MyUserManager.cardBalance.value?.availableBalance?.toFormattedCurrency()}"
                )
            )
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_international_funds_transfer_display_text_title))
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

    override fun getReasonList(productCode: String) {
        state.loading = true
        launch {
            when (val response =
                mTransactionsRepository.getPurposeOfPayment(productCode)) {
                is RetroApiResponse.Success -> {
                    if (!response.data.data.isNullOrEmpty()) {
                        purposeOfPaymentList.value =
                            response.data.data as? ArrayList<PurposeOfPayment>?
                    }
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
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
    override fun processPurposeList(list: ArrayList<PurposeOfPayment>) {
        purposeCategories = list.groupBy { item ->
            item.purposeCategory
        }
    }
    fun updateFees() {
        updateFees(state.etInputAmount.toString())
    }

    fun getTotalAmountWithFee(): Double {
        return (when (feeType) {
            FeeType.TIER.name -> {
                val transferFee = getFeeFromTier(state.etInputAmount.toString())
                state.etInputAmount.parseToDouble().plus(transferFee.parseToDouble())
            }
            FeeType.FLAT.name -> {
                val transferFee = getFlatFee(state.etInputAmount.toString())
                state.etInputAmount.parseToDouble().plus(transferFee.parseToDouble())
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
