package co.yap.modules.dashboard.yapit.y2y.home.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.y2y.home.adaptors.RecentTransferAdaptor
import co.yap.modules.dashboard.yapit.y2y.home.interfaces.IYapToYap
import co.yap.modules.dashboard.yapit.y2y.home.states.YapToYapState
import co.yap.modules.dashboard.yapit.y2y.main.viewmodels.Y2YBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.beneficiary.RecentBeneficiary
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.SingleClickEvent

class YapToYapViewModel(application: Application) : Y2YBaseViewModel<IYapToYap.State>(application),
    IYapToYap.ViewModel, IRepositoryHolder<CustomersRepository> {
    val adapter = ObservableField<RecentTransferAdaptor>()

    override val recentTransferData: MutableLiveData<Boolean> = MutableLiveData()

    override val state: IYapToYap.State = YapToYapState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val repository: CustomersRepository
        get() = CustomersRepository
    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }


    override fun onResume() {
        super.onResume()
        if (parentViewModel?.isSearching?.value != null)
            toggleToolBarVisibility(!parentViewModel?.isSearching?.value!!)
        else {
            toggleToolBarVisibility(false)
        }
        setToolBarTitle("YAP to YAP")
    }

    override fun getRecentBeneficiaries() {
        launch {
            when (val response = repository.getRecentY2YBeneficiaries()) {
                is RetroApiResponse.Success -> {
                    if (response.data.data.size > 0) {
                        adapter.get()?.setData(response.data.data)
                        recentTransferData.value  = true
                    }else{
                        recentTransferData.value  = false
                    }

                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }

        }
    }
}