package co.yap.billpayments.paybill.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.yapcore.BaseViewModel

class PayBillMainViewModel(application: Application) :
    BaseViewModel<IPayBillMain.State>(application),
    IPayBillMain.ViewModel {

    override val state: IPayBillMain.State = PayBillMainState()
    override var errorEvent: MutableLiveData<String> = MutableLiveData()
    override val billModel: MutableLiveData<ViewBillModel> = MutableLiveData()

}