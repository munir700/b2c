package co.yap.modules.yapit.sendmoney.home.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.yap.modules.yapit.sendmoney.home.interfaces.ISendMoneyHome
import co.yap.modules.yapit.sendmoney.home.states.SendMoneyHomeState
import co.yap.modules.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.helpers.PagingState


class SendMoneyHomeScreenViewModel(application: Application) :
    SendMoneyBaseViewModel<ISendMoneyHome.State>(application), ISendMoneyHome.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository = CustomersRepository

    override val state: SendMoneyHomeState = SendMoneyHomeState()

    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override var pagingState: MutableLiveData<PagingState> = MutableLiveData()

    override val yapBeneficiaryLiveData: MutableLiveData<List<Beneficiary>> = MutableLiveData()

    override fun handlePressOnBackButton() {
    }

    override fun handlePressOnAddNow(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override var allBeneficiariesList: List<Beneficiary> = arrayListOf()
    override var recentBeneficiariesList: List<Beneficiary> = arrayListOf()

    override val backButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_send_money_display_text_title))
        toggleAddButtonVisibility(true)
//      requestAllBeneficiaries()
    }


    override fun getState(): LiveData<PagingState> {
        return pagingState
    }

    override fun requestAllBeneficiaries() {
        launch {
            state.loading = true
            when (val response = repository.getAllBeneficiaries()) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    state.toast = response.data.toString()
                    allBeneficiariesList = response.data.data
                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message

                }
            }
        }
    }

    fun requestRecentBeneficiaries() {
        launch {
            state.loading = true
            when (val response = repository.getRecentBeneficiaries()) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    state.toast = response.data.toString()
                    recentBeneficiariesList = response.data.data
                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message

                }
            }
        }
    }
}