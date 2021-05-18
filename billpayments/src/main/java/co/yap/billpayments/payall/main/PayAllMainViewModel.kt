package co.yap.billpayments.payall.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.yapcore.BaseViewModel

class PayAllMainViewModel(application: Application) :
    BaseViewModel<IPayAllMain.State>(application),
    IPayAllMain.ViewModel {
    override val state: IPayAllMain.State = PayAllMainState()
    override var allBills: MutableLiveData<MutableList<ViewBillModel>> = MutableLiveData()
}
