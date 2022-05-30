package co.yap.modules.dashboard.store.young.sendmoney.success

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.hilt.base.viewmodel.HiltBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class YoungSendMoneySuccessVM @Inject constructor(override val state: YoungSendMoneySuccessState) :
    HiltBaseViewModel<IYoungSendMoneySuccess.State>(), IYoungSendMoneySuccess.ViewModel {
    override fun handleOnClick(id: Int) {
    }

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }
}