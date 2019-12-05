package co.yap.modules.dashboard.yapit.sendmoney.home.viewmodels

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.y2y.home.adaptors.RecentTransferAdaptor
import co.yap.modules.dashboard.yapit.sendmoney.home.interfaces.ISendMoneyHome
import co.yap.modules.dashboard.yapit.sendmoney.home.states.SendMoneyHomeState
import co.yap.modules.dashboard.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.beneficiary.RecentBeneficiary
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

    override val allBeneficiariesLiveData: MutableLiveData<List<Beneficiary>> = MutableLiveData()

    override var onDeleteSuccess: MutableLiveData<Int> = MutableLiveData()

    override val recentTransferData: MutableLiveData<List<RecentBeneficiary>> = MutableLiveData()

    override val adapter = ObservableField<RecentTransferAdaptor>()

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
        requestAllBeneficiaries()
    }


    override fun getState(): LiveData<PagingState> {
        return pagingState
    }

//    fun getRecentBeneficiaries() {
//        launch {
//            when (val response = repository.getRecentY2YBeneficiaries()) {
//                is RetroApiResponse.Success -> {
//                    recentTransferData.value = response.data.data
//                }
//                is RetroApiResponse.Error -> state.toast = response.error.message
//            }
//
//        }
//    }


    fun requestAllBeneficiaries() {
        launch {
            state.loading = true
            when (val response = repository.getAllBeneficiaries()) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    allBeneficiariesList = response.data.data
                    allBeneficiariesLiveData.value = allBeneficiariesList

                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message

                }
            }
        }
    }

   override fun requestRecentBeneficiaries() {

       launch {
           when (val response = repository.getRecentY2YBeneficiaries()) {
               is RetroApiResponse.Success -> {
                   recentTransferData.value = response.data.data

               }
               is RetroApiResponse.Error -> state.toast = response.error.message
           }

       }




//        launch {
//            state.loading = true
//            when (val response = repository.getRecentBeneficiaries()) {
//                is RetroApiResponse.Success -> {
//                    state.loading = false
//                    state.toast = response.data.toString()
//                    recentBeneficiariesList = response.data.data
//
//                }
//
//                is RetroApiResponse.Error -> {
//                    state.loading = false
//                    state.toast = response.error.message
//
//                }
//            }
//        }
    }

    override fun requestDeleteBeneficiary(beneficiaryId: Int) {
        launch {
            state.loading = true
            when (val response = repository.deleteBeneficiaryFromList(beneficiaryId.toString())) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    state.toast = response.data.toString()
                    onDeleteSuccess.setValue(111)

                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message

                }
            }
        }
    }



}