package co.yap.modules.dashboard.yapit.sendmoney.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.sendmoney.main.ISendMoneyMain
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.yapcore.BaseViewModel

class SendMoneyMainViewModel(application: Application) :
    BaseViewModel<ISendMoneyMain.State>(application),
    ISendMoneyMain.ViewModel {

    override var RecentTransfers: MutableLiveData<Beneficiary>
        get() = TODO("Not yet implemented")
        set(value) {}
    override val state: ISendMoneyMain.State
        get() = TODO("Not yet implemented")
}