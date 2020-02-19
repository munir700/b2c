package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import co.yap.modules.dashboard.yapit.sendmoney.activities.BeneficiaryCashTransferActivity
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.ITransferSuccess
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states.TransferSuccessState
import co.yap.modules.dashboard.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.cards.responsedtos.CardBalance
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.managers.MyUserManager

class TransferSuccessViewModel(application: Application) :
    SendMoneyBaseViewModel<ITransferSuccess.State>(application), ITransferSuccess.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository = CustomersRepository

    override val state: TransferSuccessState = TransferSuccessState()

    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var updatedCardBalanceEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnButtonClick(id: Int) {
        clickEvent.setValue(id)
    }

    override fun getAccountBalanceRequest() {
        launch {
            state.loading = true
            when (val response = MyUserManager.repository.getAccountBalanceRequest()) {
                is RetroApiResponse.Success -> {
                    MyUserManager.cardBalance.value =
                        (CardBalance(availableBalance = response.data.data?.availableBalance.toString()))
                    updatedCardBalanceEvent.call()
                   // kotlinx.coroutines.delay(1000)
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                }
            }
        }
    }

    override val backButtonPressEvent: SingleClickEvent = SingleClickEvent()

    override fun onResume() {
        super.onResume()
        toggleToolBarVisibility(false)
    }
}