package co.yap.billpayments.dashboard

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.billpayments.dashboard.mybills.adapter.BillModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.billpayment.BillProviderModel
import co.yap.networking.customers.responsedtos.billpayment.BillResponse
import co.yap.networking.customers.responsedtos.billpayment.BillerCatalogModel
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.extentions.getJsonDataFromAsset
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class BillPaymentsViewModel(application: Application) :
    BaseViewModel<IBillPayments.State>(application),
    IBillPayments.ViewModel, IRepositoryHolder<CustomersRepository> {
    override val state: IBillPayments.State = BillPaymentsState()
    override var billerCatalogs: MutableList<BillerCatalogModel> = mutableListOf()
    override var billcategories: MutableList<BillProviderModel> = mutableListOf()
    override var selectedBillerCatalog: BillerCatalogModel? = null
    override var onToolbarClickEvent: SingleClickEvent = SingleClickEvent()
    override var selectedBill: ViewBillModel? = null
    override var billsResponse: MutableLiveData<MutableList<ViewBillModel>>? = MutableLiveData()
    override val repository: CustomersRepository = CustomersRepository
    override fun onToolbarClick(id: Int) {
        onToolbarClickEvent.setValue(id)
    }

    override fun getViewBillsFromJSONFile(): BillResponse {
        val gson = GsonBuilder().create()
        return gson.fromJson<BillResponse>(
            context.getJsonDataFromAsset(
                "jsons/bill_list.json"
            ), object : TypeToken<BillResponse>() {}.type
        )
    }

    override fun getViewBills() {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            val response = repository.getAddedBills()
            launch {
                when (response) {
                    is RetroApiResponse.Success -> {
                        billsResponse?.value?.forEach {
                            it.formattedDueDate = DateUtils.reformatStringDate(
                                it.billDueDate.toString(),
                                DateUtils.SERVER_DATE_FULL_FORMAT,
                                DateUtils.FORMATE_DATE_MONTH_YEAR
                            )
                        }
                        billsResponse?.value = response.data.viewBillList.toMutableList()
                        state.viewState.value = false
                    }

                    is RetroApiResponse.Error -> {
                        showToast(response.error.message)
                        state.viewState.value = false
                    }
                }
            }

        }
    }

}
