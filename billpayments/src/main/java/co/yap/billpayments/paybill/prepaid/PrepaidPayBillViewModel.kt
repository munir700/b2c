package co.yap.billpayments.paybill.prepaid

import android.app.Application
import co.yap.billpayments.R
import co.yap.billpayments.paybill.base.PayBillMainBaseViewModel
import co.yap.billpayments.utils.enums.PaymentScheduleType
import co.yap.billpayments.paybill.prepaid.skuadapter.SkuAdapter
import co.yap.billpayments.utils.enums.SkuInfoType
import co.yap.networking.coreitems.CoreBottomSheetData
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.billpayment.SkuCatalogs
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.ApiResponse
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.EditBillerRequest
import co.yap.networking.transactions.requestdtos.PayBillRequest
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.managers.SessionManager

class PrepaidPayBillViewModel(application: Application) :
    PayBillMainBaseViewModel<IPrepaidPayBill.State>(application),
    IPrepaidPayBill.ViewModel, IRepositoryHolder<CustomersRepository> {
    override val repository: CustomersRepository = CustomersRepository
    private val transactionRepository: TransactionsRepository = TransactionsRepository
    override val state: IPrepaidPayBill.State = PrepaidPayBillState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var adapter: SkuAdapter = SkuAdapter(mutableListOf())
    override var selectedSku: SkuCatalogs? = null
    override fun handlePressView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
        state.availableBalanceString.set(getAvailableBalance())
        parentViewModel?.billModel?.value?.billerInfo?.billerName?.let {
            state.isBillTypeDuPrepaid.set(
                it?.contains(
                    "Du Prepaid"
                )
            )
        }
        setSkuInfos(SkuInfoType.Airtime().airtime)
    }

    override fun setSkuInfos(type: String?) {
        if (state.isBillTypeDuPrepaid.get()) {

            if (type.equals(SkuInfoType.Airtime().airtime)) {
                (parentViewModel?.billModel?.value?.billerInfo?.skuInfos?.filter { it.isAirtime })?.let {
                    adapter.setList(
                        it
                    )
                }
            } else {
                (parentViewModel?.billModel?.value?.billerInfo?.skuInfos?.filter { !it.isAirtime })?.let {
                    adapter.setList(
                        it
                    )
                }
            }
        } else {
            parentViewModel?.billModel?.value?.billerInfo?.skuInfos?.let { adapter.setList(it) }
        }
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_pay_bill_text_title))
        toggleRightIconVisibility(true)
        state.customerFullName.set(SessionManager.user?.currentCustomer?.getFullName() ?: "")
        state.customerAccountNumber.set(SessionManager.user?.accountNo ?: "")
        state.isBillReminderOn.set(parentViewModel?.billModel?.value?.reminderNotification ?: false)
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

    private fun fetchParallelAPIResponses(
        payBillRequest: PayBillRequest,
        editBillerRequest: EditBillerRequest,
        responses: (RetroApiResponse<ApiResponse>, RetroApiResponse<ApiResponse>) -> Unit
    ) {
        launch(Dispatcher.Background) {
            val deferredEditBillerResponse = launchAsync {
                repository.editBiller(editBillerRequest)
            }
            val deferredPayBillResponse = launchAsync {
                transactionRepository.payBill(payBillRequest)
            }
            responses(
                deferredEditBillerResponse.await(),
                deferredPayBillResponse.await()
            )
        }
    }

    override fun payBillAndEditBiller(
        payBillRequest: PayBillRequest,
        editBillerRequest: EditBillerRequest,
        success: () -> Unit
    ) {
        state.viewState.postValue(true)
        fetchParallelAPIResponses(
            payBillRequest,
            editBillerRequest
        ) { editBillerResponse, payBillResponse ->
            launch(Dispatcher.Main) {
                when (editBillerResponse) {
                    is RetroApiResponse.Success -> {
                    }
                    is RetroApiResponse.Error -> {
                    }
                }
                when (payBillResponse) {
                    is RetroApiResponse.Success -> {
                        state.viewState.value = false
                        success.invoke()
                    }
                    is RetroApiResponse.Error -> {
                        state.viewState.value = false
                        showToast(payBillResponse.error.message)
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

    override fun setMinMaxLimitForPrepaid(viewBillModel: ViewBillModel) {
        val billerSku = viewBillModel.billerInfo?.skuInfos?.first()
        state.minLimit.set(billerSku?.minAmount ?: 0.0)
        state.maxLimit.set(billerSku?.maxAmount ?: 0.0)
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

    override fun updateReminderSelection(
        isThreedays: Boolean,
        isOneWeek: Boolean,
        isThreeWeek: Boolean,
        totalDays: Int
    ) {
        state.billReminderThreeDays.set(isThreedays)
        state.billReminderOneWeek.set(isOneWeek)
        state.billReminderThreeWeeks.set(isThreeWeek)
        state.totalDays.set(totalDays)

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
            (availableBalance > enterAmount)
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
            notes = state.noteValue.get() ?: "",
            skuId = selectedSku?.skuId ?: "",
            billAmount = state.amount,
            billData = billModel?.inputsData,
            customerBillUuid = billModel?.uuid ?: "",
            billerCategory = billModel?.billerInfo?.categoryId ?: "",
            biller_name = billModel?.billerInfo?.billerName ?: "",
            paymentInfo = null
        )
    }

    override fun getEditBillerRequest(billModel: ViewBillModel?): EditBillerRequest {
        return EditBillerRequest(
            id = Integer.parseInt(billModel?.id ?: "0"),
            billerID = billModel?.billerID ?: "",
            skuId = billModel?.skuId ?: "",
            billNickName = billModel?.billNickName ?: "",
            autoPayment = false,
            reminderNotification = state.isBillReminderOn.get(),
            reminderFrequency = state.totalDays.get() ?: 3,
            inputsData = billModel?.inputsData
        )
    }
}
