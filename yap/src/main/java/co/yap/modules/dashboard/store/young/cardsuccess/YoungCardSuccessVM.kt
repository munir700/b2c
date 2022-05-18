package co.yap.modules.dashboard.store.young.cardsuccess

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.hilt.base.viewmodel.HiltBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class YoungCardSuccessVM  @Inject constructor(override var state: YoungCardSuccessState) :
    HiltBaseViewModel< IYoungCardSuccess.State>(), IYoungCardSuccess.ViewModel{
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }
    override fun handleOnClick(id: Int) {
    }
}