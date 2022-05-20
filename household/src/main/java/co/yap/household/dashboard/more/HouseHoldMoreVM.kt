package co.yap.household.dashboard.more

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.modules.dashboard.more.home.models.MoreOption
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.dagger.base.viewmodel.BaseRecyclerAdapterVM
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.hilt.base.viewmodel.BaseRecyclerAdapterVMV2
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HouseHoldMoreVM @Inject constructor(override val state: HouseHoldMoreState) :
    BaseRecyclerAdapterVMV2<MoreOption, IHouseHoldMore.State>(), IHouseHoldMore.ViewModel,
    IRepositoryHolder<AuthRepository> {
    override val repository: AuthRepository = AuthRepository

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun handleOnClick(id: Int) {

    }

    override fun logout(success: () -> Unit) {
        val deviceId: String? =
            SharedPreferenceManager.getInstance(context).getValueString(Constants.KEY_APP_UUID)
        launch {
            state.loading = true
            when (val response = repository.logout(deviceId.toString())) {
                is RetroApiResponse.Success -> {
                    success.invoke()
                    state.loading = true
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
                }
            }
        }
    }
}
