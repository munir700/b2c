package co.yap.billpayments.paybills

import android.app.Application
import co.yap.billpayments.base.PayBillBaseViewModel

class PayBillsViewModel(application: Application) :
    PayBillBaseViewModel<IPayBills.State>(application),
    IPayBills.ViewModel {
    override val state: IPayBills.State = PayBillsState()

    override fun onResume() {
        super.onResume()
        setToolBarTitle("Pay bills")
        toggleToolBarVisibility(true)
        parentViewModel?.state?.leftIconVisibility?.set(true)
    }
}