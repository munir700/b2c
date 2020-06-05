package co.yap.modules.location.tax

import android.app.Application
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class TaxInfoViewModel(application: Application) :
    BaseViewModel<ITaxInfo.State>(application),
    ITaxInfo.ViewModel, IRepositoryHolder<CustomersRepository> {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: ITaxInfo.State = TaxInfoState()
    override var taxInfoList: MutableList<TaxModel> = mutableListOf()
    override var taxInfoAdaptor: TaxInfoAdaptor = TaxInfoAdaptor(taxInfoList)
    override val repository: CustomersRepository = CustomersRepository

    override fun onCreate() {
        super.onCreate()
        getReasonsList()
    }

    override fun handleOnPressView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun getReasonsList() {
        launch {
            state.loading = true
            when (val response = repository.getTaxReasons()) {
                is RetroApiResponse.Success -> {
                    val reasons = response.data.reasons
                    val options = arrayListOf<String>("Yes", "No")
                    createModel(1, reasons, options)
                    state.loading = false
                }

                is RetroApiResponse.Error -> {
                    val reasons = arrayListOf<String>(
                        "The country does not issue a TIN",
                        "Unable to obtain TIN",
                        "No TIN required"
                    )
                    val options = arrayListOf<String>("Yes", "No")
                    createModel(1, reasons, options)
                    state.loading = false
                    state.toast = response.error.message
                }
            }
        }
    }

    private fun createModel(
        taxRowNumber: Int,
        reasons: ArrayList<String>,
        options: ArrayList<String>
    ) {
        taxInfoList.add(TaxModel(reasons = reasons, options = options, taxRowNumber = taxRowNumber))
        taxInfoAdaptor.notifyDataSetChanged()
    }
}