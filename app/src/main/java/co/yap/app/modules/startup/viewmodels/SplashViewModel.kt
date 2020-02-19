package co.yap.app.modules.startup.viewmodels

import android.app.Application
import co.yap.app.modules.startup.interfaces.ISplash
import co.yap.app.modules.startup.states.SplashState
import co.yap.networking.admin.AdminRepository
import co.yap.networking.admin.responsedtos.AppUpdate
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent

class SplashViewModel(application: Application) : BaseViewModel<ISplash.State>(application),
    ISplash.ViewModel,
    IRepositoryHolder<AuthRepository> {

    override val state: SplashState = SplashState()

    override val repository: AuthRepository = AuthRepository
    private val adminRepository: AdminRepository = AdminRepository

    override val splashComplete: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override var appUpdate: SingleLiveEvent<AppUpdate> = SingleLiveEvent()

    override fun onCreate() {
        super.onCreate()
        loadCookies()
    }

    private fun loadCookies() {
        launch {
            when (val response = repository.getCSRFToken()) {
                is RetroApiResponse.Success -> splashComplete.value = true
                is RetroApiResponse.Error -> state.toast =
                    if (response.error.statusCode == 504) "" else response.error.message
            }
        }
    }

    override fun getAppUpdate() {
        launch {
            when (val response = adminRepository.appUpdate()) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        if (it.isNotEmpty()) {
                            appUpdate.value = it[0]
                        } else {
                            appUpdate.value = null
                        }
                        return@let
                    }
                    appUpdate.value = null
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }
        }
    }

}