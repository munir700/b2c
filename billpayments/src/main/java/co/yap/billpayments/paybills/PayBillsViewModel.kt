package co.yap.billpayments.paybills

import android.app.Application
import androidx.databinding.ObservableField
import co.yap.billpayments.base.PayBillBaseViewModel
import co.yap.billpayments.paybills.adapter.DueBill
import co.yap.billpayments.paybills.adapter.DueBillsAdapter
import co.yap.billpayments.paybills.notification.DueBillsNotificationAdapter
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.billpayment.BillProviderModel
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.widgets.State
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.DateUtils.FORMATE_DATE_MONTH_YEAR
import co.yap.yapcore.helpers.DateUtils.SERVER_DATE_FULL_FORMAT
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.managers.SessionManager

class PayBillsViewModel(application: Application) :
    PayBillBaseViewModel<IPayBills.State>(application),
    IPayBills.ViewModel, IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository = CustomersRepository
    override val state: IPayBills.State = PayBillsState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val dueBillsAdapter: DueBillsAdapter = DueBillsAdapter(arrayListOf())
    override val notificationAdapter: DueBillsNotificationAdapter =
        DueBillsNotificationAdapter(context, arrayListOf())
    override var billcategories: ObservableField<MutableList<BillProviderModel>> = ObservableField()

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_bill_payment_text_title))
        toggleToolBarVisibility(true)
        parentViewModel?.state?.leftIconVisibility?.set(true)
    }

    override fun onCreate() {
        super.onCreate()
        if (state.showBillCategory.get()) {
            if (parentViewModel?.billcategories.isNullOrEmpty())
                getBillProviders()
            else
                billcategories.set(parentViewModel?.billcategories)
        } else {
            getBillCategoriesApi()
        }
    }

    override fun handlePressView(id: Int) {
        clickEvent.setValue(id)
    }

    private fun getDueBills(): ArrayList<DueBill> {
        val arr = ArrayList<DueBill>()
        arr.add(
            DueBill(
                logoUrl = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg",
                billerName = "Etisalat",
                billNickName = "My iPhoneX",
                billDueDate = "2021-08-12T06:53:35",
                amount = "250.000",
                currency = "AED"
            )
        )
        arr.add(
            DueBill(
                logoUrl = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg",
                billerName = "Etisalat",
                billNickName = "My iPhoneX",
                billDueDate = "2020-08-12T06:53:35",
                amount = "250.000",
                currency = "AED"
            )
        )
        arr.add(
            DueBill(
                logoUrl = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg",
                billerName = "Etisalat",
                billNickName = "My iPhoneX",
                billDueDate = "2020-08-12T06:53:35",
                amount = "250.000",
                currency = "AED"
            )
        )
        arr.add(
            DueBill(
                logoUrl = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg",
                billerName = "Etisalat",
                billNickName = "My iPhoneX",
                billDueDate = "2021-08-12T06:53:35",
                amount = "250.000",
                currency = "AED"
            )
        )
        return arr
    }

    private fun getBillProviders() {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            val response = repository.getBillProviders()
            launch {
                when (response) {
                    is RetroApiResponse.Success -> {
                        billcategories.set(response.data.billProviders as ArrayList)
                        parentViewModel?.billcategories =
                            billcategories.get() as MutableList<BillProviderModel>
                        state.stateLiveData?.value =
                            if (billcategories.get()
                                    .isNullOrEmpty()
                            ) State.empty("") else State.success(null)
                        state.viewState.value = false
                    }
                    is RetroApiResponse.Error -> {
                        state.viewState.value = false
                        state.stateLiveData?.value = State.error("")
                        showToast(response.error.message)
                    }
                }
            }
        }
    }

    override fun getBillCategoriesApi() {
        launch {
            state.loading = true;
            val list = getDueBills()
            list.addAll(getDueBills())
            dueBillsAdapter.setList(list)
            notificationAdapter.setList(list)
            var total = 0.00;
            dueBillsAdapter.getDataList().forEach {
                total = total.plus(it.amount.toDouble())
                it.billDueDate = DateUtils.reformatStringDate(
                    it.billDueDate,
                    SERVER_DATE_FULL_FORMAT,
                    FORMATE_DATE_MONTH_YEAR
                )
            }
            state.totalDueAmount.set(
                total.toString().toFormattedCurrency(true, SessionManager.getDefaultCurrency())
            )
            state.loading = false
        }
    }
}