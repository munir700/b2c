package co.yap.billpayments.payall.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.billpayments.R
import co.yap.billpayments.payall.base.PayAllBaseViewModel
import co.yap.billpayments.payall.payallsuccess.adapter.PaidBill
import co.yap.billpayments.payall.payallsuccess.adapter.PayAllBillsAdapter
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.PayAllRequest
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.BillPaymentStatus
import co.yap.yapcore.helpers.extentions.parseToDouble
import co.yap.yapcore.helpers.extentions.parseToInt
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.managers.SessionManager

class PayAllViewModel(application: Application) :
    PayAllBaseViewModel<IPayAll.State>(application),
    IPayAll.ViewModel, IRepositoryHolder<TransactionsRepository> {
    override val state: IPayAll.State = PayAllState()
    override var errorEvent: MutableLiveData<String> = MutableLiveData()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var adapter: PayAllBillsAdapter = PayAllBillsAdapter(mutableListOf())
    override val repository: TransactionsRepository = TransactionsRepository
    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_pay_all_toolbar_title))
        toggleRightIconVisibility(true)
    }

    override fun populateData() {
        state.allBillsToBePaid = parentViewModel?.allBills?.value?.map {
            PaidBill(
                billerID = it.billerInfo?.billerID,
                skuID = it.skuId,
                billAmount = it.billAmountDue,
                customerBillUuid = it.customerUUID,
                paymentInfo = it.paymentInfo,
                billerCategory = it.billerInfo?.categoryId,
                billerName = it.billerInfo?.billerName,
                billData = it.inputsData,
                logo = it.billerInfo?.logo,
                billerType = it.billerInfo?.billerType
            )
        }
        adapter.setList(state.allBillsToBePaid ?: arrayListOf())
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

    override fun isBalanceAvailable(enterAmount: Double): Boolean {
        val availableBalance = SessionManager.cardBalance.value?.availableBalance?.toDoubleOrNull()
        return if (availableBalance != null) {
            (availableBalance >= enterAmount)
        } else false
    }

    fun showBalanceNotAvailableError(enterAmount: String) {
        val des = Translator.getString(
            context,
            Strings.common_display_text_available_balance_error_without_fee
        ).format(enterAmount.toFormattedCurrency())
        errorEvent?.value = des
    }

    override fun payAllBills(
        payAllRequest: ArrayList<PayAllRequest>,
        success: () -> Unit
    ) {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            val response = repository.payAllBills(payAllRequest)
            launch {
                when (response) {
                    is RetroApiResponse.Success -> {
                        state.viewState.value = false
                        parentViewModel?.paidBills!!.clear()
                        parentViewModel?.paidBills =
                            (state.allBillsToBePaid as List<PaidBill>).toMutableList()
                        if (parentViewModel?.paidBills!!.size > 0 && response.data.data!!.size > 0)
                            for (i in parentViewModel?.paidBills!!.indices) {
                                for (j in response.data.data!!.indices) {
                                    if (parentViewModel?.paidBills!![i].billerID == response.data.data!![j]!!.billerID) {

                                        if (response.data.data!![j]!!.status.equals(
                                                BillPaymentStatus.PAID.title
                                            )
                                        ) {
                                            parentViewModel?.paidBills!![i].paymentStatus =
                                                BillPaymentStatus.PAIDTITLE.title

                                        } else if (response.data.data!![j]!!.status.equals(
                                                BillPaymentStatus.IN_PROGRESS.title
                                            )
                                        ) {
                                            parentViewModel?.paidBills!![i].paymentStatus =
                                                BillPaymentStatus.IN_PROGRESSTITLE.title
                                        } else {
                                            parentViewModel?.paidBills!![i].paymentStatus =
                                                BillPaymentStatus.FAILEDTITLE.title
                                        }
                                    }
                                }
                            }
                        success.invoke()
                    }
                    is RetroApiResponse.Error -> {
                        showToast(response.error.message)
                        state.viewState.value = false
                    }
                }
            }
        }
    }

    override fun getPayAllBillsRequest(): ArrayList<PayAllRequest> {
        val payAllRequestDataList = ArrayList<PayAllRequest>()
        adapter.getDataList().forEach { payAllBills ->
            payAllRequestDataList.add(
                PayAllRequest(
                    billerID = payAllBills.billerID ?: "",
                    skuID = payAllBills.skuID ?: "",
                    billAmount = payAllBills.billAmount.parseToDouble(),
                    customerBillUuid = payAllBills.customerBillUuid ?: "",
                    paymentInfo = payAllBills.paymentInfo,
                    billerCategory = payAllBills.billerCategory!!.parseToInt(),
                    billerName = payAllBills.billerName ?: "",
                    billData = payAllBills.billData
                )
            )
        }
        return payAllRequestDataList
    }

}