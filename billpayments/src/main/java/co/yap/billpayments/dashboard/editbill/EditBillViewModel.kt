package co.yap.billpayments.dashboard.editbill

import android.app.Application
import android.view.View
import co.yap.billpayments.addbiller.addbillerdetail.adapter.AddBillerDetailAdapter
import co.yap.billpayments.addbiller.addbillerdetail.composer.AddBillerDetailInputComposer
import co.yap.billpayments.base.PayBillBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.billpayment.BillerInputDetails
import co.yap.networking.customers.responsedtos.billpayment.IoCatalogModel
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.interfaces.OnItemClickListener

class EditBillViewModel(application: Application) :
    PayBillBaseViewModel<IEditBill.State>(application),
    IEditBill.ViewModel, IRepositoryHolder<CustomersRepository> {
    override val repository: CustomersRepository = CustomersRepository
    override val state: IEditBill.State = EditBillState()
    override var adapter: AddBillerDetailAdapter = AddBillerDetailAdapter(mutableListOf())
    override val addBillerDetailItemComposer: AddBillerDetailInputComposer =
        AddBillerDetailInputComposer()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var billerDetailsResponse: BillerInputDetails? = null
    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
        adapter.setItemListener(listener)
    }

    override fun onResume() {
        super.onResume()
        toggleToolBarVisibility(true)
        toggleRightIconVisibility(false)
        state.screenTitle.set(getString(Strings.screen_edit_bill_title_text))
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            validate()
        }
    }

    private fun validate() {
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
        state.valid.set(isValid)
    }

    override fun getEditBillDetails(billerId: String) {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            val response = repository.getBillerInputDetails(billerId = billerId)
            launch {
                when (response) {
                    is RetroApiResponse.Success -> {
                        state.viewState.value = false
                        response.data.billerInputsData?.ioCatalogs?.let {
                            val list =
                                addBillerDetailItemComposer.compose(it as ArrayList<IoCatalogModel>)
                            adapter.setList(list)
                            billerDetailsResponse = response.data.billerInputsData
                        }
                    }

                    is RetroApiResponse.Error -> {
                        state.viewState.value = false
                        showToast(response.error.message)
                    }
                }
            }
        }
    }
}
