package co.yap.billpayments.addBill.billerdetail

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import co.yap.billpayments.addBill.base.AddBillBaseViewModel
import co.yap.billpayments.addBill.billerdetail.adapter.BillerDetailAdapter
import co.yap.billpayments.addBill.billerdetail.composer.BillerDetailInputComposer
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.models.BillerInputData
import co.yap.networking.customers.requestdtos.AddBillerInformationRequest
import co.yap.networking.customers.responsedtos.billpayment.BillerDetailResponse
import co.yap.networking.customers.responsedtos.billpayment.BillerInputDetails
import co.yap.networking.customers.responsedtos.billpayment.IoCatalogModel
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.BillCategory
import co.yap.yapcore.helpers.extentions.getJsonDataFromAsset
import co.yap.yapcore.interfaces.OnItemClickListener
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class BillerDetailViewModel(application: Application) :
    AddBillBaseViewModel<IBillerDetail.State>(application), IBillerDetail.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository = CustomersRepository
    override val state: IBillerDetail.State = BillerDetailState()
    override var adapter: BillerDetailAdapter = BillerDetailAdapter(mutableListOf())
    override val billerDetailItemComposer: BillerDetailInputComposer = BillerDetailInputComposer()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val billerDetailsResponse: MutableLiveData<BillerInputDetails> = MutableLiveData()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
        getBillerDetails(parentViewModel?.selectedBillerCatalog?.billerID ?: "")
        adapter.setItemListener(listener)
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

    override fun readBillerDetailsFromFile(): BillerDetailResponse {
        val gson = GsonBuilder().create()
        return gson.fromJson<BillerDetailResponse>(
            context.getJsonDataFromAsset(
                "jsons/biller_details.json"
            ), object : TypeToken<BillerDetailResponse>() {}.type
        )
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
                                billerDetailItemComposer.compose(it as ArrayList<IoCatalogModel>)
                            adapter.setList(list)
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
        success: () -> Unit
    ) {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            val response = repository.addBiller(billerInformation = billerInformationRequest)
            launch {
                when (response) {
                    is RetroApiResponse.Success -> {
                        state.viewState.value = false
                        success.invoke()
                    }
                    is RetroApiResponse.Error -> {
                        state.viewState.value = false
                    }
                }
            }
        }
    }


    override fun getBillerInformationRequest(billerInformation: BillerInputDetails?): AddBillerInformationRequest {
        val inputsData = ArrayList<BillerInputData>()
        adapter.getDataList().forEachIndexed { index, inputData ->
            if (index > 0)
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
            billNickName = adapter.getDataList().first().value?.get() ?: "",
            inputsData = inputsData
        )
    }
}
