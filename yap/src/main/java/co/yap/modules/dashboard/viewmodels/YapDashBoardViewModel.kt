package co.yap.modules.dashboard.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.interfaces.IYapDashboard
import co.yap.modules.dashboard.states.YapDashBoardState
import co.yap.networking.cards.CardsRepository
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.managers.MyUserManager

class YapDashBoardViewModel(application: Application) :
    BaseViewModel<IYapDashboard.State>(application), IYapDashboard.ViewModel {


    override val getAccountInfoSuccess: MutableLiveData<Boolean> = MutableLiveData()
    override val getAccountBalanceSuccess: MutableLiveData<Boolean> = MutableLiveData()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: YapDashBoardState = YapDashBoardState()
    private val customerRepository: CustomersRepository = CustomersRepository
    private val cardsRepository: CardsRepository = CardsRepository

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
        getAccountInfo()
    }

    override fun onResume() {
        super.onResume()
        populateState()
    }

    private fun populateState() {
        MyUserManager.user?.let {
            state.accountNo = MyUserManager.user!!.accountNo
            state.ibanNo = MyUserManager.user!!.iban
            state.fullName =
                MyUserManager.user!!.customer.firstName + " " + MyUserManager.user!!.customer.lastName
            state.firstName = MyUserManager.user!!.customer.firstName
        }
    }

    override fun getAccountInfo() {
        launch {
            state.loading = true
            when (val response = customerRepository.getAccountInfo()) {
                is RetroApiResponse.Success -> {
                    MyUserManager.user = response.data.data[0]
                    getAccountInfoSuccess.value = true
                    populateState()
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }
    }

    override fun getAccountBalanceRequest() {
        launch {
            when (val response = cardsRepository.getAccountBalanceRequest()) {
                is RetroApiResponse.Success -> {
                    state.availableBalance = response.data.data.availableBalance.toString()
                    getAccountBalanceSuccess.value = true
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
        }
    }


}