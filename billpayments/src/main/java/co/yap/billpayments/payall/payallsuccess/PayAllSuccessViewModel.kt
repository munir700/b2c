package co.yap.billpayments.payall.payallsuccess

import android.app.Application
import co.yap.billpayments.payall.base.PayAllBaseViewModel
import co.yap.billpayments.payall.payallsuccess.adapter.PayAllSuccessAdapter
import co.yap.networking.transactions.responsedtos.billpayment.PaidBill
import co.yap.translation.Strings
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.extentions.getJsonDataFromAsset
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class PayAllSuccessViewModel(application: Application) :
    PayAllBaseViewModel<IPayAllSuccess.State>(application),
    IPayAllSuccess.ViewModel {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: IPayAllSuccess.State = PayAllSuccessState()
    override var paidBillList: MutableList<PaidBill> = mutableListOf()
    override var adapter: PayAllSuccessAdapter = PayAllSuccessAdapter(mutableListOf())

    override fun onCreate() {
        super.onCreate()
        PayAllBills()
    }

    override fun onResume() {
        super.onResume()
        toggleRightIconVisibility(false)
    }

    override fun handleOnPressView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun getToolbarTitle(): String {
        return when (state.billsPaid.get()) {
            0 -> {
                getString(Strings.screen_pay_all_success_toolbar_title_transfer_unsuccessful)
            }

            in 1 until paidBillList.size -> {
                getString(Strings.screen_pay_all_success_toolbar_title_transfer_status)
            }

            paidBillList.size -> {
                getString(Strings.screen_pay_all_success_toolbar_title)
            }
            else -> {
                ""
            }
        }
    }

    override fun setScreenTitle() {
        return when (state.billsPaid.get()) {
            0 -> {
                state.screenTitle.set(getString(Strings.screen_pay_all_success_title_all_decline))
            }

            in 1 until paidBillList.size -> {
                state.screenTitle.set(getString(Strings.screen_pay_all_success_title_partial_paid))
            }

            paidBillList.size -> {
                state.screenTitle.set(getString(Strings.screen_pay_all_success_title_all_paid))
            }
            else -> {
                state.screenTitle.set("")
            }
        }
    }

    private fun readFromFile(): List<PaidBill> {
        val gson = GsonBuilder().create()
        return gson.fromJson<List<PaidBill>>(
            context.getJsonDataFromAsset(
                "jsons/pay_all_bills.json"
            ), object : TypeToken<List<PaidBill>>() {}.type
        )
    }

    override fun getSuccessfullyPaidBills(): Int {
        return paidBillList.count { it.PaymentStatus.equals("Paid") }
    }

    private fun PayAllBills() {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            launch {
                paidBillList = readFromFile().toMutableList()
                state.billsPaid.set(getSuccessfullyPaidBills())
                setToolBarTitle(getToolbarTitle())
                setScreenTitle()
                adapter.setList(paidBillList)
                state.viewState.value = false
                if (state.billsPaid.get() != 0)
                    state.paidAmount.set(
                        paidBillList.filter { it.PaymentStatus.equals("Paid") }
                            .sumBy { it.amount?.toInt() as Int }.toString()
                            .toFormattedCurrency(showCurrency = true, withComma = true)
                    )
                else
                    state.paidAmount.set(
                        paidBillList.sumBy { it.amount?.toInt() as Int }.toString()
                            .toFormattedCurrency(showCurrency = true, withComma = true)
                    )
            }
        }
    }
}
