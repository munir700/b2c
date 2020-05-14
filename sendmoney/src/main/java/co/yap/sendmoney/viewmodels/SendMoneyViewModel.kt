package co.yap.sendmoney.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.countryutils.country.Country
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.sendmoney.interfaces.ISendMoney
import co.yap.sendmoney.states.SendMoneyState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class SendMoneyViewModel(application: Application) :
    BaseViewModel<ISendMoney.State>(application),
    ISendMoney.ViewModel {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override var selectedCountry: MutableLiveData<Country> = MutableLiveData()
    override var beneficiary: MutableLiveData<Beneficiary> = MutableLiveData()
    override val state: SendMoneyState = SendMoneyState()
    override var otpSuccess: MutableLiveData<Boolean> = MutableLiveData()
    override fun handlePressButton(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
        selectedCountry.value = Country()
        beneficiary.value = Beneficiary()
        otpSuccess.value = false
    }
}