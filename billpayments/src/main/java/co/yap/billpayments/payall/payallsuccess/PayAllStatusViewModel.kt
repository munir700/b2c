package co.yap.billpayments.payall.payallsuccess

import android.app.Application
import co.yap.billpayments.payall.base.PayAllBaseViewModel
import co.yap.billpayments.payall.payallsuccess.adapter.PayAllBillsAdapter
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.billpayments.utils.enums.BillPaymentStatus
import co.yap.yapcore.helpers.extentions.parseToDouble
import co.yap.yapcore.helpers.extentions.toFormattedCurrency

class PayAllStatusViewModel(application: Application) :
    PayAllBaseViewModel<IPayAllStatus.State>(application),
    IPayAllStatus.ViewModel {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: IPayAllStatus.State = PayAllStatusState()
    override var adapter: PayAllBillsAdapter = PayAllBillsAdapter(mutableListOf())

    override fun onCreate() {
        super.onCreate()
        populateData()
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

            in 1 until parentViewModel?.paidBills?.size!! -> {
                getString(Strings.screen_pay_all_success_toolbar_title_transfer_status)
            }

            parentViewModel?.paidBills?.size!! -> {
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

            in 1 until parentViewModel?.paidBills?.size!! -> {
                state.screenTitle.set(getString(Strings.screen_pay_all_success_title_partial_paid))
            }

            parentViewModel?.paidBills?.size!! -> {
                state.screenTitle.set(getString(Strings.screen_pay_all_success_title_all_paid))
            }
            else -> {
                state.screenTitle.set("")
            }
        }
    }

    override fun getSuccessfullyPaidBills(): Int? {
        return parentViewModel?.paidBills?.count { it.paymentStatus.equals(BillPaymentStatus.PAIDTITLE.title) }
    }

    private fun populateData() {
        getSuccessfullyPaidBills()?.let { state.billsPaid.set(it) }
        setToolBarTitle(getToolbarTitle())
        setScreenTitle()
        parentViewModel?.paidBills?.let { adapter.setList(it) }
        state.viewState.value = false
        if (state.billsPaid.get() != 0) {
            state.paidAmount.set(
                parentViewModel?.paidBills?.filter { it.paymentStatus.equals(BillPaymentStatus.PAIDTITLE.title) }
                    ?.sumByDouble { it.billAmount?.parseToDouble() as Double }.toString()
                    .toFormattedCurrency(showCurrency = true, withComma = true)
            )
        } else
            state.paidAmount.set(
                parentViewModel?.paidBills?.sumByDouble { it.billAmount?.parseToDouble() as Double }
                    .toString()
                    .toFormattedCurrency(showCurrency = true, withComma = true)
            )
    }
}
