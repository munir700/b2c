package co.yap.billpayments.billdetail.editbill

import android.app.Application
import android.view.View
import co.yap.billpayments.R
import co.yap.billpayments.addbiller.addbillerdetail.adapter.AddBillerDetailAdapter
import co.yap.billpayments.addbiller.addbillerdetail.composer.AddBillerDetailInputComposer
import co.yap.billpayments.billdetail.base.BillDetailBaseViewModel
import co.yap.billpayments.utils.enums.PaymentScheduleType
import co.yap.networking.coreitems.CoreBottomSheetData
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.models.BillerInputData
import co.yap.networking.customers.responsedtos.billpayment.IoCatalogModel
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.requestdtos.EditBillerRequest
import co.yap.translation.Strings
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.interfaces.OnItemClickListener

class EditBillViewModel(application: Application) :
    BillDetailBaseViewModel<IEditBill.State>(application),
    IEditBill.ViewModel, IRepositoryHolder<CustomersRepository> {
    override val repository: CustomersRepository = CustomersRepository
    override val state: IEditBill.State = EditBillState()
    override val addBillerDetailItemComposer: AddBillerDetailInputComposer =
        AddBillerDetailInputComposer()
    override var adapter: AddBillerDetailAdapter = AddBillerDetailAdapter(mutableListOf())
    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onCreate() {
        super.onCreate()
        setEditBillDetails()
        adapter.setItemListener(listener)
    }

    override fun onResume() {
        super.onResume()
        toggleToolBarVisibility(true)
        setToolBarTitle(parentViewModel?.selectedBill?.billerInfo?.billerName.toString())
        toggleRightIconVisibility(false)
        state.screenTitle.set(getString(Strings.screen_edit_bill_title_text))
        state.isAutoPaymentOn.set(parentViewModel?.selectedBill?.autoPayment ?: false)
//        parentViewModel?.selectedBill?.autoPayment = state.isAutoPaymentOn.get()
        state.isBillReminderOn.set(parentViewModel?.selectedBill?.reminderNotification ?: false)
//        parentViewModel?.selectedBill?.reminderNotification = state.isBillReminderOn.get()
        state.totalDays.set(parentViewModel?.selectedBill?.reminderFrequency ?: 3)
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            when (view.id) {
                R.id.etUserInput -> {
                    validation()
                }
            }
        }
    }

    override fun validation() {
        if (state.isAutoPaymentOn.get() != parentViewModel?.selectedBill?.autoPayment ?: false) {
            state.valid.set(true)
        } else if (state.isBillReminderOn.get() != parentViewModel?.selectedBill?.reminderNotification ?: false ||
            state.totalDays.get() != parentViewModel?.selectedBill?.reminderFrequency ?: 3
        ) {
            state.valid.set(true)
        } else if (lengthValidation() && state.nickNameValue.get()?.length ?: 0 > 1) {
            state.valid.set(textChangedValidation())
        } else
            state.valid.set(false)
    }

    override fun textChangedValidation(): Boolean {
        if (state.nickNameValue.get() != parentViewModel?.selectedBill?.billNickName) {
            return true
        }
        adapter.getDataList().forEachIndexed { index, field ->
            if (field.value?.get() != parentViewModel?.selectedBill?.inputsData?.get(index)?.value) {
                return true
            }
        }
        return false
    }

    override fun lengthValidation(): Boolean {
        var isValid = false
        for (field in adapter.getDataList()) {
            if (field.minLength != null &&
                field.minLength!! > field.value?.get()?.length ?: 0
            ) {
                isValid = false
                break
            } else {
                isValid = true
            }
        }
        return isValid
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
        validation()
    }

    override fun setEditBillDetails() {
        val list =
            addBillerDetailItemComposer.compose(parentViewModel?.selectedBill?.billerInfo?.skuInfos?.first()?.ioCatalogs as ArrayList<IoCatalogModel>)
        state.nickNameValue.set(parentViewModel?.selectedBill?.billNickName)
        list.forEach { item ->
            parentViewModel?.selectedBill?.inputsData?.find { it.ioId == item.ioId }
            item.value?.set(parentViewModel?.selectedBill?.inputsData?.find { it.ioId == item.ioId }?.value)
        }
        adapter.setList(list)
    }

    override fun deleteBill(success: () -> Unit) {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            val response = parentViewModel?.selectedBill?.id?.let { repository.deleteBill(it) }
            launch {
                when (response) {
                    is RetroApiResponse.Success -> {
                        state.viewState.value = false
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

    override fun editBill(
        editBillerRequest: EditBillerRequest,
        success: () -> Unit
    ) {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            val response = repository.editBiller(editBillerRequest)
            launch {
                when (response) {
                    is RetroApiResponse.Success -> {
                        state.viewState.value = false
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

    override fun getEditBillerRequest(): EditBillerRequest {
        val inputsData = ArrayList<BillerInputData>()
        adapter.getDataList().forEach { inputData ->
            inputsData.add(
                BillerInputData(
                    key = inputData.lable ?: "",
                    value = inputData.value?.get() ?: ""
                )
            )
        }
        return EditBillerRequest(
            id = Integer.parseInt(parentViewModel?.selectedBill?.id ?: "0"),
            billerID = parentViewModel?.selectedBill?.billerID ?: "",
            skuId = parentViewModel?.selectedBill?.skuId ?: "",
            billNickName = state.nickNameValue.get() ?: "",
            autoPayment = state.isAutoPaymentOn.get(),
            reminderNotification = state.isBillReminderOn.get(),
            reminderFrequency = state.totalDays.get() ?: 3,
            inputsData = inputsData
        )
    }
}
