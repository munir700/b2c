package co.yap.modules.dashboard.main.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.app.YAPApplication
import co.yap.modules.dashboard.main.interfaces.IYapDashboard
import co.yap.modules.dashboard.main.states.YapDashBoardState
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.responsedtos.CardBalance
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.Utils.formateIbanString
import co.yap.yapcore.leanplum.trackerId
import co.yap.yapcore.managers.MyUserManager

class YapDashBoardViewModel(application: Application) :
    BaseViewModel<IYapDashboard.State>(application), IYapDashboard.ViewModel {

    override val getAccountBalanceSuccess: MutableLiveData<Boolean> = MutableLiveData()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: YapDashBoardState = YapDashBoardState()
    private val customerRepository: CustomersRepository = CustomersRepository
    private val cardsRepository: CardsRepository = CardsRepository
    override val showUnverifedscreen: MutableLiveData<Boolean> = MutableLiveData()
    override val accountInfo: MutableLiveData<AccountInfo>? = MutableLiveData()

    override fun handlePressOnNavigationItem(id: Int) {
        clickEvent.setValue(id)
    }

    override fun copyAccountInfoToClipboard() {
        val info = "Account: ${state.accountNo}\nIBAN: ${state.ibanNo}"
        Utils.copyToClipboard(context, info)
        state.toast = "Copied to clipboard"
    }

    override fun onCreate() {
        super.onCreate()
        getAccountBalanceRequest()
        updateVersion()
    }

    private fun updateVersion() {
        state.appVersion.set(
            String.format(
                "Version %s (%s)",
                YAPApplication.appInfo?.version_name,
                YAPApplication.appInfo?.version_code
            )
        )
    }

    override fun onResume() {
        super.onResume()
        populateState()
    }

    private fun populateState() {
        MyUserManager.user?.let { it ->
            it.accountNo?.let { state.accountNo = it }
            it.iban?.let { state.ibanNo = formateIbanString(it) ?: "" }
            state.fullName = it.currentCustomer.getFullName()
            state.firstName = it.currentCustomer.firstName
            state.userNameImage.set(it.currentCustomer.getPicture() ?: "")
        }
    }

    override fun getAccountInfo() {
        launch {
            when (val response = customerRepository.getAccountInfo()) {
                is RetroApiResponse.Success -> {
                    MyUserManager.user = response.data.data[0]
                    accountInfo?.value = response.data.data[0]
                    trackerId(MyUserManager.user?.uuid)
                    populateState()
                    if (MyUserManager.user?.currentCustomer?.isEmailVerified.equals("N", true)) {
                        showUnverifedscreen.value = true
                    }
                }

                is RetroApiResponse.Error -> state.toast = response.error.message
            }
        }
    }

    override fun getAccountBalanceRequest() {
        launch {
            when (val response = cardsRepository.getAccountBalanceRequest()) {
                is RetroApiResponse.Success -> {
                    MyUserManager.cardBalance.value =
                        CardBalance(availableBalance = response.data.data?.availableBalance.toString())
                    getAccountInfo()
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    getAccountInfo()
                }
            }
        }
    }
}