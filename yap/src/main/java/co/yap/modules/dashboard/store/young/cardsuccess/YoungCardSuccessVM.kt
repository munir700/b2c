package co.yap.modules.dashboard.store.young.cardsuccess

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class YoungCardSuccessVM  @Inject constructor(override var state: IYoungCardSuccess.State) :
    DaggerBaseViewModel< IYoungCardSuccess.State>(), IYoungCardSuccess.ViewModel{
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {

    }
}