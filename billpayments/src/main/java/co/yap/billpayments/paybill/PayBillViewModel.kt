package co.yap.billpayments.paybill

import android.app.Application
import co.yap.billpayments.R
import co.yap.billpayments.paybill.base.PayBillMainBaseViewModel
import co.yap.billpayments.paybill.enum.PaymentScheduleType
import co.yap.networking.coreitems.CoreBottomSheetData
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.PayBillRequest
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.extentions.parseToDouble
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.managers.SessionManager

class PayBillViewModel(application: Application) :
    PayBillMainBaseViewModel<IPayBill.State>(application),
    IPayBill.ViewModel, IRepositoryHolder<TransactionsRepository> {

    override val repository: TransactionsRepository = TransactionsRepository
    override val state: IPayBill.State = PayBillState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override fun handlePressView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
        state.availableBalanceString.set(getAvailableBalance())
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_pay_bill_text_title))
        toggleRightIconVisibility(true)
        state.billReferences.set(getBillReferences())

    }

    private fun getBillReferences(): String {
        return parentViewModel?.billModel?.value?.inputsData?.joinToString(
            separator = " | "
        ) { billerInputData -> billerInputData.value.toString() } ?: ""
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

    override fun payBill(payBillRequest: PayBillRequest, success: () -> Unit) {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            val response = repository.payBill(payBillRequest)
            launch {
                when (response) {
                    is RetroApiResponse.Success -> {
                        state.viewState.value = false
                        success.invoke()
                    }
                    is RetroApiResponse.Error -> {
                        state.viewState.value = false
                        showToast(response.error.message)
                    }
                }
            }
        }
    }

    override fun composeWeekDaysList(listData: List<String>): MutableList<CoreBottomSheetData> {
        val list: MutableList<CoreBottomSheetData> = arrayListOf()
        listData.forEach { weekDay ->
            list.add(
                CoreBottomSheetData(
                    content = weekDay,
                    subTitle = weekDay,
                    sheetImage = null
                )
            )
        }
        return list
    }

    override fun setMinMaxLimitForPostPaid(viewBillModel: ViewBillModel) {
        val billerSku = viewBillModel.billerInfo?.skuInfos?.first()
        if (billerSku?.isExcessPayment == true && billerSku.isPartialPayment) {
            state.minLimit.set(billerSku.minAmount ?: 0.0)
            state.maxLimit.set(billerSku.maxAmount ?: 0.0)
        } else if (billerSku?.isExcessPayment == true) {
            state.maxLimit.set(billerSku.maxAmount ?: 0.0)
            state.minLimit.set(viewBillModel.totalAmountDue.parseToDouble())
        } else if (billerSku?.isPartialPayment == true) {
            state.minLimit.set(billerSku.minAmount ?: 0.0)
            state.maxLimit.set(viewBillModel.totalAmountDue.parseToDouble())
        }else{
            state.minLimit.set(billerSku?.minAmount ?: 0.0)
            state.maxLimit.set(billerSku?.maxAmount ?: 0.0)
        }
    }

    override fun updateAutoPaySelection(
        isWeek: Boolean,
        isMonth: Boolean,
        paymentScheduleType: PaymentScheduleType
    ) {
        state.autoPaymentScheduleTypeWeek.set(isWeek)
        state.autoPaymentScheduleTypeMonth.set(isMonth)
        state.autoPaymentScheduleType.set(paymentScheduleType.name)
    }

    fun checkOnTextChangeValidation(enterAmount: Double) {
        when {
            !isBalanceAvailable(enterAmount) -> {
                state.valid.set(false)
                showBalanceNotAvailableError()
            }

            enterAmount < state.minLimit.get() ?: 0.0 -> {
                state.valid.set(false)
            }

            enterAmount > state.maxLimit.get() ?: 0.0 -> {
                showUpperLowerLimitError()
                state.valid.set(false)
            }

            else -> {
                cancelAllSnackBar()
                state.valid.set(true)
            }
        }
    }

    private fun showUpperLowerLimitError() {
        val des = Translator.getString(
            context,
            Strings.common_display_text_min_max_limit_error_transaction,
            state.minLimit.get().toString().toFormattedCurrency(),
            state.maxLimit.get().toString().toFormattedCurrency()
        )
        parentViewModel?.errorEvent?.value = des
    }

    private fun isBalanceAvailable(enterAmount: Double): Boolean {
        val availableBalance =
            SessionManager.cardBalance.value?.availableBalance?.toDoubleOrNull()
        return if (availableBalance != null) {
            (availableBalance >= enterAmount)
        } else
            false
    }

    private fun showBalanceNotAvailableError() {
        val des = Translator.getString(
            context,
            Strings.common_display_text_available_balance_error_without_fee
        ).format(state.amount.toFormattedCurrency())
        parentViewModel?.errorEvent?.value = des
    }

    override fun getPayBillRequest(billModel: ViewBillModel?, billAmount: String): PayBillRequest {
        return PayBillRequest(
            billerId = billModel?.billerID ?: "",
            skuId = billModel?.skuId ?: "",
            billAmount = state.amount,
            customerBillUuid = billModel?.uuid ?: "",
            paymentInfo = billModel?.paymentInfo,
            billerCategory = billModel?.billerInfo?.categoryId ?: "",
            biller_name = billModel?.billerInfo?.billerName ?: "",
            billData = billModel?.inputsData

        )


    }
}