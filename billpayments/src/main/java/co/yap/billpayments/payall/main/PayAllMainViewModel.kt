package co.yap.billpayments.payall.main

import android.app.Application
import co.yap.yapcore.BaseViewModel

class PayAllMainViewModel(application: Application) :
    BaseViewModel<IPayAllMain.State>(application),
    IPayAllMain.ViewModel {

    override val state: IPayAllMain.State = PayAllMainState()
}
