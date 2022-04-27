package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.topup.topupamountscreen

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseViewModel
import co.yap.networking.leanteach.LeanTechRepository
import co.yap.networking.leanteach.requestdtos.GetPaymentIntentIdModel
import co.yap.networking.leanteach.responsedtos.accountlistmodel.LeanCustomerAccounts
import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.getValueWithoutComa
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.helpers.extentions.toast
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.managers.SessionManager

class TopupAmountViewModel(application: Application) :
    AddMoneyBaseViewModel<ITopupAmount.State>(application), ITopupAmount.ViewModel {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override var customerId: String? = ""
    override var paymentIntentId: MutableLiveData<String> = MutableLiveData("")
    override var leanCustomerAccounts: LeanCustomerAccounts? = LeanCustomerAccounts()
    override var leanPaymentStatus: MutableLiveData<Boolean> = MutableLiveData(false)
    override var getPaymentIntentModel: GetPaymentIntentIdModel = GetPaymentIntentIdModel()
    override var bankListMainModel: BankListMainModel = BankListMainModel()
    override val state: ITopupAmount.State = TopupAmountState()
    private val leanTechRepository: LeanTechRepository = LeanTechRepository

    override fun handleClickEvent(id: Int) {
        clickEvent.setValue(id)
    }

    override fun denominationAmountValidator(amount: String) {
        val currency = Utils.getFormattedCurrencyWithoutComma(
            amount.replace(
                if (amount.contains("+")) "+" else "-",
                ""
            )
        )
        if (!state.enteredTopUpAmount.value.equals(currency)) {
            state.enteredTopUpAmount.value = currency
        }
    }

    override fun setAvailableBalance(balance: String) {
        state.availableBalance.value = context.resources.getText(
            getString(Strings.common_display_text_available_balance),
            context.color(
                R.color.colorPrimaryDark,
                balance.toFormattedCurrency()
            )
        )
    }

    override fun getPaymentIntentId() {
        state.loading = true
        launch {
            when (val response = leanTechRepository.getPaymentIntentId(getPaymentIntentModel)) {
                is RetroApiResponse.Success -> {
                    response.data.data?.paymentIntentId?.let {
                        paymentIntentId.postValue(it)
                        state.loading = false
                    }
                }
                is RetroApiResponse.Error -> {
                    toast(context, response.error.message)
                    state.loading = false
                }
            }
        }
    }

    override fun getLimitOfAmount() =
        bankListMainModel.transferLimits?.filter { it.currency.equals(SessionManager.getDefaultCurrency()) }
            ?.get(0)

    override fun isMaxMinLimitReached() =
        state.enteredTopUpAmount.value?.let {
            val amount = it.getValueWithoutComa()
            if (amount.isNotBlank() || amount.toDoubleOrNull() ?: 0.0 > 0.0)
                amount.toDoubleOrNull() ?: 0.0 > getLimitOfAmount()?.max?.toDouble() ?: 0.0 || amount.toDoubleOrNull() ?: 0.0 < getLimitOfAmount()?.min?.toDouble() ?: 0.0
            else false
        } ?: false
}