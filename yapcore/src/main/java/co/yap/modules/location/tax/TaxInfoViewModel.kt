package co.yap.modules.location.tax

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.R
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.interfaces.OnItemClickListener

class TaxInfoViewModel(application: Application) :
    BaseViewModel<ITaxInfo.State>(application),
    ITaxInfo.ViewModel, IRepositoryHolder<CustomersRepository> {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: ITaxInfo.State = TaxInfoState()
    override var taxInfoList: MutableList<TaxModel> = mutableListOf()
    override var taxInfoAdaptor: TaxInfoAdaptor = TaxInfoAdaptor(taxInfoList)
    override val repository: CustomersRepository = CustomersRepository
    override var reasonsList: ArrayList<String> = arrayListOf(
        "The country does not issue a TIN",
        "Unable to obtain TIN",
        "No TIN required"
    )
    override var options = arrayListOf("Yes", "No")

    override
    fun onCreate() {
        super.onCreate()
        getReasonsList()
        setupRecycleView()
    }

    private fun setupRecycleView() {
        taxInfoAdaptor.allowFullItemClickListener = true
        taxInfoAdaptor.setItemListener(listener)
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            when (view.id) {
                R.id.ivCross -> {
                    val index = taxInfoList.indexOf(data as TaxModel)
                    taxInfoList.removeAt(index)
                    taxInfoAdaptor.notifyItemRemoved(index)
                    taxInfoList.last().canAddMore.set(true)
                    //taxInfoAdaptor.notifyItemChanged(taxInfoList.size - 1)
                }
                R.id.lyAddCountry -> {
                    createModel(reasonsList, options)
                }
            }
        }
    }

    override fun handleOnPressView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun getReasonsList() {
        launch {
            state.loading = true
            when (val response = repository.getTaxReasons()) {
                is RetroApiResponse.Success -> {
                    reasonsList = response.data.reasons
                    createModel(reasonsList, options)
                    state.loading = false
                }

                is RetroApiResponse.Error -> {
                    createModel(reasonsList, options)
                    state.loading = false
                    state.toast = response.error.message
                }
            }
        }
    }

    override fun createModel(
        reasons: ArrayList<String>,
        options: ArrayList<String>
    ) {
        taxInfoList.add(
            TaxModel(
                reasons = reasons,
                options = options,
                canAddMore = ObservableField(taxInfoList.size in 0..1),
                taxRowNumber = ObservableField(taxInfoList.isNotEmpty())
            )
        )
        taxInfoAdaptor.notifyItemInserted(taxInfoList.size)
    }
}