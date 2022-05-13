package co.yap.yapcore.dagger.base.navigation.host

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.hilt.base.viewmodel.HiltBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavHostPresenterVM @Inject constructor(override val state: NavHostPresenterState):
    HiltBaseViewModel<INavHostPresenter.State>(), INavHostPresenter.ViewModel {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {

    }

    override fun handleOnClick(id: Int) {
    }
}