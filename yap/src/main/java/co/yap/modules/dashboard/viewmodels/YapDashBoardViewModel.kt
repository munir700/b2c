package co.yap.modules.dashboard.viewmodels

import android.app.Application
import co.yap.modules.dashboard.interfaces.IYapDashboard
import co.yap.modules.dashboard.states.YapDashBoardState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.managers.MyUserManager

class YapDashBoardViewModel(application: Application) :
    BaseViewModel<IYapDashboard.State>(application), IYapDashboard.ViewModel {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: YapDashBoardState = YapDashBoardState()
    private val customerRepository: CustomersRepository = CustomersRepository

    override fun handlePressOnNavigationItem(id: Int) {
        clickEvent.setValue(id)
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
            state.accountNo=MyUserManager.user!!.accountNo
            state.ibanNo=MyUserManager.user!!.iban
            state.fullName=MyUserManager.user!!.customer.firstName +" " + MyUserManager.user!!.customer.firstName
        }
    }

    override fun getAccountInfo() {
        launch {
            state.loading = true
            when (val response = customerRepository.getAccountInfo()) {
                is RetroApiResponse.Success -> {
                    MyUserManager.user = response.data.data[0]
                    populateState()
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }
    }
}