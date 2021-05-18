package co.yap.billpayments.payall.home

import android.app.Application
import co.yap.billpayments.R
import co.yap.billpayments.payall.base.PayAllBaseViewModel
import co.yap.billpayments.payall.home.adapter.OverlappingLogoAdapter
import co.yap.networking.customers.responsedtos.billpayment.BillerInfoModel
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.managers.SessionManager

class PayAllViewModel(application: Application) :
    PayAllBaseViewModel<IPayAll.State>(application),
    IPayAll.ViewModel {
    override val state: IPayAll.State
        get() = PayAllState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var adapter: OverlappingLogoAdapter = OverlappingLogoAdapter(mutableListOf())

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_pay_all_toolbar_title))
        toggleRightIconVisibility(true)
    }

    override fun populateData() {
        adapter.setList(parentViewModel?.allBills?.value?.map { viewBillModel -> viewBillModel.billerInfo } as List<BillerInfoModel>)
        state.billerNames.set(parentViewModel?.allBills?.value?.joinToString(
            separator = " , "
        ) { viewBillModel -> viewBillModel.billerInfo?.billerName.toString() } ?: "")
        state.availableBalanceString.set(getAvailableBalance())
    }

    override fun handleOnPressView(id: Int) {
        clickEvent.setValue(id)
    }

    private fun getAvailableBalance(): CharSequence {
        return context.resources.getText(
            getString(Strings.screen_cash_transfer_display_text_available_balance),
            context.color(
                R.color.colorPrimaryDark,
                SessionManager.cardBalance.value?.availableBalance?.toFormattedCurrency(
                    showCurrency = true
                )
                    ?: ""
            )
        )
    }
}