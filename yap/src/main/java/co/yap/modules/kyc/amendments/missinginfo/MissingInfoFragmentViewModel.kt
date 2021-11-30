package co.yap.modules.kyc.amendments.missinginfo

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.managers.SessionManager

class MissingInfoFragmentViewModel(application: Application) :
    BaseViewModel<IMissingInfo.State>(application), IMissingInfo.ViewModel,
    IRepositoryHolder<CustomersRepository> {
    override val adapter = ObservableField<MissingInfoAdapter>()
    override val state: IMissingInfo.State = MissingInfoState()
    override val repository: CustomersRepository = CustomersRepository
    override val onClickEvent: MutableLiveData<Int> = MutableLiveData()
    override val missingInfoMap: MutableLiveData<HashMap<String?, List<String>?>> =
        MutableLiveData(hashMapOf())

    override fun onCreate() {
        super.onCreate()
        getMissingInfoItems()
    }

    override fun getMissingInfoItems() {
        launch {
            state.loading = true
            when (val response =
                repository.getMissingInfoList(SessionManager.user?.uuid ?: "")) {
                is RetroApiResponse.Success -> {
                    val list: MutableList<String> = mutableListOf()
                    val map = HashMap<String?, List<String>?>()
                    response.data.data?.forEach {
                        if (it.amendments?.isNotEmpty() == true) {
                            map[it.sectionName] = it.amendments
                            list.addAll(it.amendments ?: emptyList())
                        }
                    }
                    missingInfoMap.value = map
                    adapter.get()?.setData(list)
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }
        }
    }

    override fun handlePressView(id: Int) {
        onClickEvent.value = id
    }
}