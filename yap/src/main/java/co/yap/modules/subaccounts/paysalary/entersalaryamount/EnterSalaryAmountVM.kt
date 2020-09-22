package co.yap.modules.subaccounts.paysalary.entersalaryamount

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import co.yap.R
import co.yap.networking.customers.household.responsedtos.SalaryTransaction
import co.yap.networking.customers.household.responsedtos.SubAccount
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsApi
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.requestdtos.PaySalaryNowRequest
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.helpers.cancelAllSnackBar
import co.yap.yapcore.helpers.extentions.parseToDouble
import co.yap.yapcore.helpers.livedata.GetAccountBalanceLiveData
import co.yap.yapcore.helpers.showTextUpdatedAbleSnackBar
import co.yap.yapcore.helpers.spannables.underline
import co.yap.yapcore.helpers.validation.IValidator
import co.yap.yapcore.helpers.validation.Validator
import javax.inject.Inject

class EnterSalaryAmountVM @Inject constructor(
    override var state: IEnterSalaryAmount.State,
    override var validator: Validator?
) :
    DaggerBaseViewModel<IEnterSalaryAmount.State>(), IEnterSalaryAmount.ViewModel, IValidator {
    private val repository: TransactionsApi = TransactionsRepository
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun fetchExtras(extras: Bundle?) {
        super.fetchExtras(extras)
        extras?.let {
            state.subAccount.value = it.getParcelable(SubAccount::class.java.simpleName)
            state.lastTransaction?.value = it.getParcelable(SalaryTransaction::class.simpleName)
        }
    }

    override fun paySalaryNow(request: PaySalaryNowRequest) {
        launch {
            state.loading = true
            when (val response = repository.paySalaryNow(request)) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    clickEvent?.postValue(GO_TO_CONFIRMATION)
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                }
            }
        }
    }

    override fun onAmountChange(
        amount: CharSequence, start: Int, before: Int,
        count: Int
    ) {
        if (amount.parseToDouble() > GetAccountBalanceLiveData.cardBalance.value?.availableBalance.parseToDouble()) {
            context.showTextUpdatedAbleSnackBar(
                msg = "Looks like it’s time to Top Up! Please top up your account to continue with this transaction. ${underline(
                    "Top Up here!"
                )}", clickListener = View.OnClickListener { })
        } else {
            cancelAllSnackBar()
        }
    }

    override fun handlePressOnView(id: Int) {
        handleOnClick(id)
    }

    override fun handleOnClick(id: Int) {
        when (id) {
            R.id.tvLastUsedAmount -> {
                state.amount.value = state.lastTransaction?.value?.amount
            }
            else -> {
                if (state.isRecurring.value == false) {
                    paySalaryNow(
                        PaySalaryNowRequest(
                            amount = state.amount.value,
                            receiverUUID = state.subAccount.value?.accountUuid,
                            beneficiaryName = state.subAccount.value?.getFullName()
                        )
                    )
                } else {
                    clickEvent?.postValue(GO_TO_RECURING)
                }
            }
        }
    }
}
