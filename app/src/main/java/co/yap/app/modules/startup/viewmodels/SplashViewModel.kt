package co.yap.app.modules.startup.viewmodels

import android.app.Application
import co.yap.app.main.MainChildViewModel
import co.yap.app.modules.startup.interfaces.ISplash
import co.yap.app.modules.startup.states.SplashState
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.AppUpdate
import co.yap.networking.customers.responsedtos.sendmoney.Country
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.messages.MessagesApi
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.responsedtos.DownTime
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.constants.Constants.KEY_COUNTRIES_LIST
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.extentions.listToJson

class SplashViewModel(application: Application) : MainChildViewModel<ISplash.State>(application),
    ISplash.ViewModel,
    IRepositoryHolder<AuthRepository> {

    override val state: SplashState = SplashState()

    override val repository: AuthRepository = AuthRepository
    private val messagesApi: MessagesApi = MessagesRepository
    private val customersRepository: CustomersRepository = CustomersRepository

    override val splashComplete: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override var appUpdate: SingleLiveEvent<AppUpdate?> = SingleLiveEvent()
    override var countriesList: ArrayList<Country> = arrayListOf()
    override fun onCreate() {
        super.onCreate()
        loadCookies()
    }

    /**
     *  APi will return @see [DownTime] object if partner bank or processor is down
     *  Show alert to user if backend, partner bank or processor is down
     * */
    private fun getDownTime(errorMessage: String) {
        launch {
            when (val response = messagesApi.getDownTime()) {
                is RetroApiResponse.Success -> {
                    if (response.data.data?.isDown == true) {
                        state.downTime.value = response.data.data
                    } else {
                        state.downTime.value = DownTime(errorMessage, false)
                    }

                }
                is RetroApiResponse.Error -> {
                    state.downTime.value = DownTime(errorMessage, false)
                }
            }
        }
    }

    fun loadCookies() {
        launch {
            when (val response = repository.getCSRFToken()) {
                is RetroApiResponse.Success -> splashComplete.value = true
                is RetroApiResponse.Error -> {
                    getDownTime(if (response.error.statusCode == 504) "Sorry, that doesn't look right.Please try again in sometime." else response.error.message)
                }
            }
        }
    }

    override fun getAppUpdate() {
        launch {
            when (val response = customersRepository.appUpdate()) {
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
                    showToast(response.error.message)
                }
            }
        }
    }

    override fun getAppConfigurations() {
        launch {
            val countries = launchAsync {
                customersRepository.getAppCountries()
            }.await()
            val appUpdates = launchAsync {
                customersRepository.appUpdate()
            }.await()
            when {
                countries is RetroApiResponse.Success && appUpdates is RetroApiResponse.Success -> {
                    countries.data.data?.let {
                        countriesList = ArrayList(it)
//                        countriesList.add(
//                            Country(
//                                isoCountryCode2Digit = "PK",
//                                name = "Pakistan",
//                                isoCountryCode3Digit = "PAK"
//                            )
//                        )
//                        countriesList.add(
//                            Country(
//                                isoCountryCode2Digit = "GH",
//                                name = "Ghana",
//                                isoCountryCode3Digit = "GHA"
//                            )
//                        )
                        SharedPreferenceManager.getInstance(context)
                            .save(KEY_COUNTRIES_LIST, countriesList.listToJson<Country>() ?: "")
                    }
                    appUpdates.data.data?.let {
                        if (it.isNotEmpty()) {
                            appUpdate.value = it[0]
                        } else {
                            appUpdate.value = null
                        }
                    }
                }
            }
            //responses(countries, appUpdates)
        }
    }
}