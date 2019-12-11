package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IInternationalFundsTransfer
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states.InternationalFundsTransferState
import co.yap.modules.dashboard.yapit.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.responsedtos.InternationalFundsTransferReasonList
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.Utils

class InternationalFundsTransferViewModel(application: Application) :
    SendMoneyBaseViewModel<IInternationalFundsTransfer.State>(application),
    IInternationalFundsTransfer.ViewModel,
    IRepositoryHolder<CustomersRepository> {


    var mTransactionsRepository: TransactionsRepository = TransactionsRepository

    override val repository: CustomersRepository = CustomersRepository
    override val state: InternationalFundsTransferState = InternationalFundsTransferState(application)
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var transactionData: MutableLiveData<List<InternationalFundsTransferReasonList.ReasonList>> =
        MutableLiveData()

    override fun handlePressOnNext(id: Int) {
        clickEvent.setValue(id)
    }


    override fun onCreate() {
        super.onCreate()

        getTransactionFeeInternational()
        getTransactionInternationalReasonList()
    }


    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_international_funds_transfer_display_text_title))
        //toggleAddButtonVisibility(false)
    }


    /*
    * In this function get Transaction Fee.
    * */

    fun getTransactionFeeInternational() {
        launch {
            state.loading = true
            when (val response = mTransactionsRepository.getTransactionFeeWithProductCode("P001")) {
                is RetroApiResponse.Success -> {
                    state.transferFee =
                        getString(Strings.screen_international_funds_transfer_display_text_fee).format(
                            "AED",
                            "50.00"
                        )

                    state.transferFeeSpannable =
                        Utils.getSppnableStringForAmount(context, state.transferFee, "AED", "50.00")

                }

                is RetroApiResponse.Error -> {
                    state.loading = false
//                    state.toast = response.error.message
                    state.transferFee =
                        getString(Strings.screen_international_funds_transfer_display_text_fee).format(
                            "AED",
                            "50.00"
                        )

                    state.transferFeeSpannable =
                        Utils.getSppnableStringForAmount(context, state.transferFee, "AED", "50.00")
                }
            }
            state.loading = false
        }
    }


    fun getTransactionInternationalReasonList() {
        launch {
            state.loading = true
            when (val response =
                mTransactionsRepository.getTransactionInternationalReasonList("P012")) {
                is RetroApiResponse.Success -> {
                    if (response.data.data.isNullOrEmpty()) {
                        return@launch
                    } else {
                        transactionData.value = response.data.data
                    }
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                    transactionData.value = null
                }
            }
            state.loading = false
        }
    }
}