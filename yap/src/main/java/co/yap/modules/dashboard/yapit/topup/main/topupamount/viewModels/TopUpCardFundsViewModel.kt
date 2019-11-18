package co.yap.modules.dashboard.yapit.topup.main.topupamount.viewModels

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
import co.yap.translation.Strings
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.managers.MyUserManager
import kotlinx.coroutines.delay

class TopUpCardFundsViewModel(application: Application) : FundActionsViewModel(application) {
    private val transactionsRepository: TransactionsRepository = TransactionsRepository
    override val htmlLiveData: MutableLiveData<String> = MutableLiveData()
    override val topUpTransactionModelLiveData: MutableLiveData<TopUpTransactionModel>? = MutableLiveData()
    private lateinit var topupCrad: TopUpCard
    private var secureId: String? = null
    private var orderId: String? = null
    override fun initateVM(item: TopUpCard) {
        topupCrad = item
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
                    Utils.getFormattedCurrency(MyUserManager.cardBalance.value?.availableBalance.toString())
                )
        state.buttonTitle = getString(Strings.screen_topup_funds_display_button_text)
    }

    private fun getTransactionFee() {
        launch {
            state.loading = true
            when (val response = transactionsRepository.getTransactionFee(
                Constants.TOP_UP_VIA_CARD
            )) {
                is RetroApiResponse.Success -> {
                    state.transactionFee = response.data.data
                    if (state.transactionFee.toDouble() == 0.0) {
                        state.transactionFee =
                            getString(Strings.screen_topup_transfer_display_text_transaction_no_fee)
                    }
                    clickEvent.postValue(Constants.CARD_FEE)
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
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
                    check3DEnrollmentSessionRequest(
                        response.data.data.session.id,
                        response.data.data.order.id
                    )
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }

    private fun check3DEnrollmentSessionRequest(
        sessionId: String,
        orderId: String
    ) {
        launch {
            state.loading = true
            when (val response = transactionsRepository.check3DEnrollmentSession(
                Check3DEnrollmentSessionRequest(
                    topupCrad.id?.toInt()!!,
                    Order(state.currencyType, state.amount.toString()),
                    Session(sessionId)
                )
            )) {
                is RetroApiResponse.Success -> {
                    secureId = response.data.data.`3DSecureId`
                    htmlLiveData.value =
                        response.data.data.`3DSecure`.authenticationRedirect.simple.htmlBodyContent
                    // state.toast = response.data.data.secure3dId
                    //clickEvent.postValue(100)
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
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
                            topUpTransactionModelLiveData?.value = TopUpTransactionModel(orderId, state.currencyType, state.amount, topupCrad.id?.toInt(), secureId)
                            state.loading = false
                        }
                    }
                    // state.toast = response.data.data.secure3dId
                    //clickEvent.postValue(100)
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }

}