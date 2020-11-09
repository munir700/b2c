package co.yap.modules.dashboard.yapit.sendmoney.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.translation.Strings
import co.yap.yapcore.BaseViewModel

class SendMoneyMainViewModel(application: Application) :
    BaseViewModel<ISendMoneyMain.State>(application),
    ISendMoneyMain.ViewModel {

    override var RecentTransfers: MutableLiveData<Beneficiary> = MutableLiveData()
    override val state: SendMoneyMainState = SendMoneyMainState()
    override fun onCreate() {
        super.onCreate()
        state.toolbarTitle = getString(Strings.common_send_money)
    }
}