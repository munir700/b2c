package co.yap.modules.kyc.amendments.missinginfo.ui

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.kyc.amendments.missinginfo.interfaces.IMissingInfo
import co.yap.modules.kyc.amendments.missinginfo.states.MissingInfoState
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.missinginfo.MissingInfoRepository
import co.yap.networking.missinginfo.requestdtos.GetMissingInfoListRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel

class MissingInfoFragmentViewModel(application: Application) :
    BaseViewModel<IMissingInfo.State>(application), IMissingInfo.ViewModel,
    IRepositoryHolder<MissingInfoRepository> {

    override val state: IMissingInfo.State = MissingInfoState(application = application)

    override val repository: MissingInfoRepository = MissingInfoRepository
    override val onClickEvent: MutableLiveData<Int> = MutableLiveData()
    override val missingInfoItems: MutableLiveData<ArrayList<String>> = MutableLiveData()

    override fun getMissingInfoItems() {
        launch {
            state.loading = true
            when (val response = repository.getMissingInfoList(GetMissingInfoListRequest(""))) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        if (it.isNotEmpty()) {
                            missingInfoItems.value = response.data.data
                        }
                    }
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