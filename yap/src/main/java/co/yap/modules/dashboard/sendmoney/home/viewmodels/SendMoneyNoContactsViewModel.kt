package co.yap.modules.dashboard.sendmoney.home.viewmodels

import android.app.Application
import co.yap.modules.dashboard.sendmoney.home.interfaces.ISendMoneyHome
import co.yap.modules.dashboard.sendmoney.home.states.SendMoneyHome
import co.yap.modules.dashboard.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent


class SendMoneyNoContactsViewModel(application: Application) :
    SendMoneyBaseViewModel<ISendMoneyHome.State>(application), ISendMoneyHome.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository = CustomersRepository

    override val state: SendMoneyHome = SendMoneyHome()

    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnBackButton() {
    }

    override fun handlePressOnTickButton() {

    }

    override val backButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()


    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_send_money_display_text_title))

    }
}