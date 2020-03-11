package co.yap.modules.dashboard.yapit.topup.topupamount.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.viewmodels.FundActionsViewModel
import co.yap.networking.customers.models.Session
import co.yap.networking.customers.responsedtos.beneficiary.TopUpCard
import co.yap.networking.customers.responsedtos.beneficiary.TopUpTransactionModel
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.Check3DEnrollmentSessionRequest
import co.yap.networking.transactions.requestdtos.CreateSessionRequest
import co.yap.networking.transactions.requestdtos.Order
import co.yap.networking.transactions.requestdtos.RemittanceFeeRequest
import co.yap.translation.Strings
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.TransactionProductCode
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.managers.MyUserManager
import kotlinx.coroutines.delay

class TopUpCardFundsViewModel(application: Application) : FundActionsViewModel(application) {
    private val transactionsRepository: TransactionsRepository = TransactionsRepository
    override val htmlLiveData: MutableLiveData<String> = MutableLiveData()
    override val topUpTransactionModelLiveData: MutableLiveData<TopUpTransactionModel>? =
        MutableLiveData()
    private lateinit var topupCrad: TopUpCard
    private var secureId: String? = null
    private var orderId: String? = null
    override fun initateVM(item: TopUpCard) {
        topupCrad = item
        state.cardInfo.set(item)

        state.toolBarHeader = getString(Strings.screen_topup_transfer_display_text_screen_title)
        state.enterAmountHeading =
            getString(Strings.screen_topup_transfer_display_text_amount_title)
        state.currencyType = getString(Strings.common_text_currency_type)
        getFundTransferLimits(Constants.TOP_UP_VIA_CARD)
        getFundTransferDenominations(Constants.TOP_UP_VIA_CARD)
        getTransactionFee()
        state.availableBalanceGuide =
            getString(Strings.screen_topup_transfer_display_text_available_balance)
                .format(
                    state.currencyType,
                    MyUserManager.cardBalance.value?.availableBalance.toString()
                        .toFormattedCurrency()
                )
        state.buttonTitle = getString(Strings.screen_topup_funds_display_button_text)
    }

    override fun buttonClickEvent(id: Int) {
        if (state.checkValidityForAddTopUpFromExternalCard() == "") {
            clickEvent.postValue(id)
        } else {
            errorEvent.postValue(id)
        }
    }

    private fun getTransactionFee() {
        launch {
            state.loading = true
            when (val response = transactionsRepository.getTransactionFeeWithProductCode(
                TransactionProductCode.TOP_UP_VIA_CARD.pCode, RemittanceFeeRequest()
            )) {
                is RetroApiResponse.Success -> {
                    if (response.data.data?.feeType == Constants.FEE_TYPE_FLAT) {
                        val feeAmount = response.data.data?.tierRateDTOList?.get(0)?.feeAmount
                        val VATAmount = response.data.data?.tierRateDTOList?.get(0)?.vatAmount
                        state.transactionFee =
                            feeAmount?.plus(VATAmount ?: 0.0).toString().toFormattedCurrency() ?: ""
                        clickEvent.postValue(Constants.CARD_FEE)
                    }
                    //Commented because QA said to remove "No fee" text.
                    /* if (state.transactionFee.toDouble() == 0.0) {
                         state.transactionFee =
                             getString(Strings.screen_topup_transfer_display_text_transaction_no_fee)
                     }*/
                }
                is RetroApiResponse.Error -> {
                    state.errorDescription = response.error.message
                    errorEvent.call()
                }
            }
            state.loading = false
        }
    }

    override fun createTransactionSession() {
        launch {
            state.loading = true
            when (val response = transactionsRepository.createTransactionSession(
                CreateSessionRequest(Order(state.currencyType, state.amount.toString()))
            )) {
                is RetroApiResponse.Success -> {
                    orderId = response.data.data.order.id
                    response.data.data.session.id?.let {
                        check3DEnrollmentSessionRequest(
                            it,
                            response.data.data.order.id
                        )
                    }
                }
                is RetroApiResponse.Error -> {
                    //state.toast = response.error.message
                    state.errorDescription = response.error.message
                    errorEvent.call()
                }
            }
            //  state.loading = false
        }
    }

    private fun check3DEnrollmentSessionRequest(
        sessionId: String,
        orderId: String
    ) {
        launch {
            when (val response = transactionsRepository.check3DEnrollmentSession(
                Check3DEnrollmentSessionRequest(
                    topupCrad.id?.toIntOrNull(),
                    Order(state.currencyType, state.amount.toString()),
                    Session(sessionId)
                )
            )) {
                is RetroApiResponse.Success -> {
                    secureId = response.data.data._3DSecureId
                    htmlLiveData.value =
                        response.data.data._3DSecure.authenticationRedirect.simple?.htmlBodyContent?.let { it }
//                    htmlLiveData.value =
//                        response.data.data._3DSecure.authenticationRedirect.simple?.htmlBodyContent
//                            ?: ""
                    // state.toast = response.data.data.secure3dId
                    //clickEvent.postValue(100)
                }
                is RetroApiResponse.Error -> {
                    state.errorDescription = response.error.message
                    errorEvent.call()
//                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }

    override fun startPooling(showLoader: Boolean) {
        launch {
            if (showLoader)
                state.loading = true
            when (val response = transactionsRepository.secureIdPooling(secureId.toString())) {
                is RetroApiResponse.Success -> {
                    // topUpTransactionModelLiveData?.value = TopUpTransactionModel(orderId, state.currencyType, state.amount, topupCrad.id?.toInt(), secureId)
                    //clickEvent.postValue(100)
                    //temporary
                    when {
                        response.data.data == null -> {
                            delay(3000)
                            startPooling(false)
                        }
                        response.data.data == "N" -> {
                            state.toast = "unable to verify"
                            state.loading = false
                        }
                        response.data.data == "Y" -> {
                            topUpTransactionModelLiveData?.value = TopUpTransactionModel(
                                orderId,
                                state.currencyType,
                                state.amount,
                                topupCrad.id?.toInt(),
                                secureId
                            )
                            state.loading = false
                        }
                    }
                }
                is RetroApiResponse.Error -> {
                    state.errorDescription = response.error.message
                    errorEvent.call()
                }
            }
            state.loading = false
        }
    }

}