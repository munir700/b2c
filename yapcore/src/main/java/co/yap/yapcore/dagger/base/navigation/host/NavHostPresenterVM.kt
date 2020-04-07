package co.yap.yapcore.dagger.base.navigation.host

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class NavHostPresenterVM @Inject constructor(override val state: NavHostPresenterState) :
    DaggerBaseViewModel<INavHostPresenter.State>(), INavHostPresenter.ViewModel {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {

    }
}