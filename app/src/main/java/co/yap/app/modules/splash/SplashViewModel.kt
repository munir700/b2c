package co.yap.app.modules.splash

import android.app.Application
import android.os.Handler
import android.os.Looper
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent

class SplashViewModel(application: Application) : BaseViewModel<ISplash.State>(application), ISplash.ViewModel,
    IRepositoryHolder<AuthRepository> {

    override val state: SplashState = SplashState()
    override val repository: AuthRepository = AuthRepository

    override val splashComplete: SingleLiveEvent<Boolean> = SingleLiveEvent()

    override fun onCreate() {
        super.onCreate()
        loadCookies()
    }

    private fun loadCookies() {
        launch {
            when (val response = repository.getCSRFToken()) {
                is RetroApiResponse.Success -> splashComplete.value = true
                is RetroApiResponse.Error -> state.toast = if(response.error.statusCode == 504) "" else response.error.message
            }
        }
    }

}