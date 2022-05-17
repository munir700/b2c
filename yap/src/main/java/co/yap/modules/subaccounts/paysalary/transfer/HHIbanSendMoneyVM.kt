package co.yap.modules.subaccounts.paysalary.transfer

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.networking.customers.household.CustomersHHRepository
import co.yap.networking.customers.household.requestdtos.SchedulePayment
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.household.TransactionsHHApi
import co.yap.networking.transactions.household.TransactionsHHRepository
import co.yap.networking.transactions.household.requestdtos.IbanSendMoneyRequest
import co.yap.widgets.State
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.extentions.parseToDouble
import co.yap.yapcore.helpers.livedata.GetAccountBalanceLiveData
import co.yap.yapcore.helpers.showTextUpdatedAbleSnackBar
import co.yap.yapcore.helpers.spannables.underline
import co.yap.yapcore.helpers.validation.IValidator
import co.yap.yapcore.helpers.validation.Validator
import co.yap.yapcore.hilt.base.viewmodel.HiltBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HHIbanSendMoneyVM @Inject constructor(
    override val state: HHIbanSendMoneyState
) :
    HiltBaseViewModel<IHHIbanSendMoney.State>(), IHHIbanSendMoney.ViewModel, IValidator {
    override var transactionsRepository: TransactionsHHApi = TransactionsHHRepository
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override var validator: Validator? = Validator(null)
    override fun handleOnClick(id: Int) {
    }

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
        extras?.let { state.subAccount.value = it.getParcelable(SubAccount::class.java.simpleName) }
    }

    override fun onAmountChange(
        amount: CharSequence, start: Int, before: Int,
        count: Int
    ) {
        if (amount.parseToDouble() > GetAccountBalanceLiveData.cardBalance.value?.availableBalance.parseToDouble()) {
            context.showTextUpdatedAbleSnackBar(
                msg = "Looks like it’s time to Top Up! Please top up your account to continue with this transaction. ${underline(
                    "Top Up here!"
                )}", marginTop = 0, clickListener = View.OnClickListener { })
        } else {
            cancelAllSnackBar()
        }
    }

    override fun ibanSendMoney(request: IbanSendMoneyRequest, apiResponse: ((Boolean) -> Unit?)?) {
        launch {
            publishState(State.loading(null))

            when (val response =
                transactionsRepository.ibanSendMoney(request)) {
                is RetroApiResponse.Success -> {
                    publishState(State.success(null))
                    apiResponse?.invoke(true)
                    //trackEvent(HHUserActivityEvents.HH_EXPENSE_TRANSFERRED.type)
                }
                is RetroApiResponse.Error -> {
                    apiResponse?.invoke(false)
                    state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                    publishState(State.empty(response.error.message))
                }
            }
        }
    }

    override fun getSchedulePayment(uuid: String?, apiResponse: ((SchedulePayment?) -> Unit?)?) {
        launch {
            state.loading = true
            when (val response =
                CustomersHHRepository.getSchedulePayment(uuid, "Salary")) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    response.data.data?.let {
                        val schedulePayment =
                            it.find { s -> s.isRecurring == true && s.recurringInterval?.isNotEmpty()!! }
                        apiResponse?.invoke(schedulePayment)
                    } ?: apiResponse?.invoke(null)
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    apiResponse?.invoke(null)
                }
            }
        }
    }
}

//TODO  Add this line in onSuccess of expense transfer API call. trackEvent(HHUserActivityEvents.HH_EXPENSE_TRANSFERRED.type)