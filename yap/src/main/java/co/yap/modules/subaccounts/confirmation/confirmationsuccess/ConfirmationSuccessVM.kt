package co.yap.modules.subaccounts.confirmation.confirmationsuccess

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class ConfirmationSuccessVM @Inject constructor(override val state: IConfirmationSuccess.State) :
    DaggerBaseViewModel<IConfirmationSuccess.State>(), IConfirmationSuccess.ViewModel{
    override val clickEvent: SingleClickEvent= SingleClickEvent()

    override fun handlePressOnClick(id: Int) {
        clickEvent.postValue(id)
    }

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }
}