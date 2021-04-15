package co.yap.billpayments.billcategory

import android.app.Application
import co.yap.billpayments.base.PayBillBaseViewModel
import co.yap.billpayments.billcategory.adapter.BillCategoryAdapter
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent

class BillCategoryViewModel(application: Application) :
    PayBillBaseViewModel<IBillCategory.State>(application), IBillCategory.ViewModel,
    IRepositoryHolder<CustomersRepository> {
    override val repository: CustomersRepository = CustomersRepository
    override val state: IBillCategory.State = BillCategoryState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override var adapter: BillCategoryAdapter = BillCategoryAdapter(mutableListOf())

    override fun handlePressView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
        if (parentViewModel?.billcategories.isNullOrEmpty()) {
            getBillProviders()
        } else {
            parentViewModel?.billcategories?.let { adapter.setList(it) }
            state.dataPopulated.set(true)
        }
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(Translator.getString(context, Strings.screen_add_bill_toolbar_title))
    }

    override fun getBillProviders() {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            val response = repository.getBillProviders()
            launch {
                when (response) {
                    is RetroApiResponse.Success -> {
                        state.viewState.value = false
                        parentViewModel?.billcategories =
                            response.data.billProviders as ArrayList
                        parentViewModel?.billcategories?.let { adapter.setList(it) }
                        state.dataPopulated.set(true)
                    }
                    is RetroApiResponse.Error -> {
                        state.viewState.value = false
                        showDialogWithCancel(response.error.message)
                    }
                }
            }
        }
    }
}
