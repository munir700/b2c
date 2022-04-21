package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.topup.topupamount

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseViewModel
import co.yap.networking.leanteach.LeanTechRepository
import co.yap.networking.leanteach.requestdtos.GetPaymentIntentIdModel
import co.yap.networking.leanteach.responsedtos.accountlistmodel.LeanCustomerAccounts
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.helpers.extentions.toast
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText

class TopupAmountViewModel(application: Application) :
    AddMoneyBaseViewModel<ITopupAmount.State>(application), ITopupAmount.ViewModel {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override var customerId: String = ""
    override var paymentIntentId: MutableLiveData<String> = MutableLiveData("")
    override var leanCustomerAccounts: LeanCustomerAccounts = LeanCustomerAccounts()
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

    override fun setAvailableBalance() {
        //Hard coded currency used, to be replaced by the model currency fetched from api
        state.availableBalance.value = context.resources.getText(
            getString(Strings.common_display_text_available_balance),
            context.color(
                R.color.colorPrimaryDark,
                "2000".toFormattedCurrency()
            )
        )
    }

    override fun getPaymentIntentId() {
        var model = GetPaymentIntentIdModel(20.00, "AED", customerId)
        launch {
            when (val response = leanTechRepository.getPaymentIntentId(model)) {
                is RetroApiResponse.Success -> {
                    response.data.data?.paymentIntentId?.let {
                        paymentIntentId.postValue(it)
                    }
                }
                is RetroApiResponse.Error -> {
                    toast(context, response.error.message)
                }
            }
        }
    }
}