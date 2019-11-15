package co.yap.modules.dashboard.yapit.topup.main.topupamount.viewModels

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.viewmodels.FundActionsViewModel
import co.yap.networking.customers.models.Session
import co.yap.networking.customers.responsedtos.beneficiary.TopUpCard
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.Check3DEnrollmentSessionRequest
import co.yap.networking.transactions.requestdtos.CreateSessionRequest
import co.yap.networking.transactions.requestdtos.Order
import co.yap.translation.Strings
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.managers.MyUserManager
import com.google.android.material.appbar.AppBarLayout
import com.thefinestartist.finestwebview.FinestWebView

class TopUpCardFundsViewModel(application: Application) : FundActionsViewModel(application) {
    private val transactionsRepository: TransactionsRepository = TransactionsRepository
    override val htmlLiveData: MutableLiveData<String> = MutableLiveData()
    private lateinit var topupCrad: TopUpCard
    override fun initateVM(item: TopUpCard) {
        topupCrad = item
        state.toolBarHeader = getString(Strings.screen_topup_transfer_display_text_screen_title)
        state.enterAmountHeading =
            getString(Strings.screen_topup_transfer_display_text_amount_title)
        state.currencyType = getString(Strings.common_text_currency_type)
        getFundTransferLimits(co.yap.modules.others.helper.Constants.ADD_FUNDS_PRODUCT_CODE)
        getFundTransferDenominations(co.yap.modules.others.helper.Constants.ADD_FUNDS_PRODUCT_CODE)
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
                Constants.TOP_UP
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
                    //openFaqsPage(response.data.data.`3DSecure`.authenticationRedirect.simple.htmlBodyContent)
                    htmlLiveData.value =
                        response.data.data.`3DSecure`.authenticationRedirect.simple.htmlBodyContent
                    clickEvent.postValue(100)
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
            state.loading = false
        }
    }

    private fun openFaqsPage(url: String) {
        FinestWebView.Builder(context)
            .updateTitleFromHtml(true)
            .toolbarScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS)
            .gradientDivider(false)
            .dividerHeight(2)
            .titleColor(ContextCompat.getColor(context!!, R.color.colorPrimaryDark))
            .toolbarColorRes(R.color.colorWhite)
            .dividerColorRes(R.color.colorPrimaryDark)
            .iconDefaultColorRes(R.color.colorPrimary)
            .iconDisabledColorRes(R.color.light_grey)
            .iconPressedColorRes(R.color.colorPrimaryDark)
            .progressBarHeight(Utils.convertDpToPx(context!!, 3f))
            .progressBarColorRes(R.color.colorPrimaryDark)
            .backPressToClose(false)
            .webViewUseWideViewPort(true)
            .webViewSupportZoom(true)
            .webViewBuiltInZoomControls(true)
            .setCustomAnimations(
                R.anim.activity_open_enter,
                R.anim.activity_open_exit,
                R.anim.activity_close_enter,
                R.anim.activity_close_exit
            )
            .load(url)
    }
}