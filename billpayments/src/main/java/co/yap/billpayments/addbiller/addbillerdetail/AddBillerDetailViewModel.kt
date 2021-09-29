package co.yap.billpayments.addbiller.addbillerdetail

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import co.yap.billpayments.R
import co.yap.billpayments.addbiller.addbillerdetail.adapter.AddBillerDetailAdapter
import co.yap.billpayments.addbiller.addbillerdetail.composer.AddBillerDetailInputComposer
import co.yap.billpayments.addbiller.base.AddBillBaseViewModel
import co.yap.billpayments.utils.enums.BillCategory
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.models.BillerInputData
import co.yap.networking.customers.requestdtos.AddBillerInformationRequest
import co.yap.networking.customers.responsedtos.billpayment.IoCatalogModel
import co.yap.networking.customers.responsedtos.billpayment.SkuCatalogs
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.interfaces.OnItemClickListener

class AddBillerDetailViewModel(application: Application) :
    AddBillBaseViewModel<IAddBillerDetail.State>(application), IAddBillerDetail.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository = CustomersRepository
    override val state: IAddBillerDetail.State = AddBillerDetailState()
    override var adapterAdd: AddBillerDetailAdapter = AddBillerDetailAdapter(mutableListOf())
    override val addBillerDetailItemComposer: AddBillerDetailInputComposer =
        AddBillerDetailInputComposer()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val billerDetailsResponse: MutableLiveData<SkuCatalogs> = MutableLiveData()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
        getBillerDetails(parentViewModel?.selectedBillerCatalog?.billerID ?: "")
        adapterAdd.setItemListener(listener)
    }

    override fun onResume() {
        super.onResume()
        toggleToolBarVisibility(true)
        state.screenTitle.set(
            getScreenTitle(
                BillCategory.valueOf(
                    parentViewModel?.selectedBillProvider?.categoryType ?: ""
                )
            )
        )
    }

    override fun getScreenTitle(billCategory: BillCategory?): String {
        return if (billCategory == BillCategory.CREDIT_CARD) {
            getString(Strings.screen_biller_detail_title_text_credit_card)
        } else {
            getString(Strings.screen_biller_detail_title_text_enter_you_account_details)
        }
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            when (view.id) {
                R.id.etUserInput -> {
                    if (validate()) {
                        state.valid.set(true)
                    } else {
                        state.valid.set(false)
                    }
                }
            }
        }
    }

    private fun validate(): Boolean {
        var isValid = false
        for (field in adapterAdd.getDataList()) {
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

    override fun getBillerDetails(billerId: String) {
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
                            adapterAdd.setList(list)
                            billerDetailsResponse.value = response.data.billerInputsData
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

    override fun addBiller(
        billerInformationRequest: AddBillerInformationRequest,
        success: (ViewBillModel?) -> Unit
    ) {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            val response = repository.addBiller(billerInformation = billerInformationRequest)
            launch {
                when (response) {
                    is RetroApiResponse.Success -> {
                        state.viewState.value = false
                        success.invoke(response.data.viewBillModel)
                    }
                    is RetroApiResponse.Error -> {
                        state.viewState.value = false
                        showToast(response.error.message)
                    }
                }
            }
        }
    }


    override fun getBillerInformationRequest(billerInformation: SkuCatalogs?): AddBillerInformationRequest {
        val inputsData = ArrayList<BillerInputData>()
        adapterAdd.getDataList().forEachIndexed { index, inputData ->
            inputsData.add(
                BillerInputData(
                    key = inputData.lable ?: "",
                    value = inputData.value?.get() ?: ""
                )
            )
        }
        return AddBillerInformationRequest(
            billerID = billerInformation?.billerID ?: "",
            skuId = billerInformation?.skuId ?: "",
            billNickName = state.nickNameValue.get() ?: "",
            inputsData = inputsData
        )
    }
}
