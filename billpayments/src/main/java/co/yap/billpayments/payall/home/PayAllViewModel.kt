package co.yap.billpayments.payall.home

import android.app.Application
import co.yap.billpayments.R
import co.yap.billpayments.payall.base.PayAllBaseViewModel
import co.yap.billpayments.payall.home.adapter.OverlappingLogoAdapter
import co.yap.networking.customers.responsedtos.billpayment.BillerInfoModel
import co.yap.networking.transactions.responsedtos.billpayment.PaidBill
import co.yap.translation.Strings
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.extentions.getJsonDataFromAsset
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.managers.SessionManager
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class PayAllViewModel(application: Application) :
    PayAllBaseViewModel<IPayAll.State>(application),
    IPayAll.ViewModel {
    override val state: IPayAll.State = PayAllState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var adapter: OverlappingLogoAdapter = OverlappingLogoAdapter(mutableListOf())

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_pay_all_toolbar_title))
        toggleRightIconVisibility(true)
    }

    override fun populateData() {
        adapter.setList(parentViewModel?.allBills?.value?.map { viewBillModel -> viewBillModel.billerInfo } as List<BillerInfoModel>)
        state.billerNames.set(getBillerNames())
        state.availableBalanceString.set(getAvailableBalance())
    }

    private fun getBillerNames(): String {
        var cancatenatedString = parentViewModel?.allBills?.value?.joinToString(
            separator = ", "
        ) { it.billerInfo?.billerName.toString() } ?: ""
        return cancatenatedString
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

    override fun readFromFile(): List<PaidBill> {
        val gson = GsonBuilder().create()
        return gson.fromJson<List<PaidBill>>(
            context.getJsonDataFromAsset(
                "jsons/pay_all_bills.json"
            ), object : TypeToken<List<PaidBill>>() {}.type
        )
    }

    override fun payAllBills(success: () -> Unit) {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            launch {
                parentViewModel?.paidBills = readFromFile().toMutableList()
                success.invoke()

            }
        }
    }
}