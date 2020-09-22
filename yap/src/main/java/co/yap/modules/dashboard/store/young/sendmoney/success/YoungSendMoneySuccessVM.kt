package co.yap.modules.dashboard.store.young.sendmoney.success

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class YoungSendMoneySuccessVM @Inject constructor(override val state: IYoungSendMoneySuccess.State) :
    DaggerBaseViewModel<IYoungSendMoneySuccess.State>(), IYoungSendMoneySuccess.ViewModel {
    override fun handleOnClick(id: Int) {
    }

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }
}