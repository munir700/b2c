package co.yap.billpayments.home

import android.app.Application
import co.yap.yapcore.BaseViewModel

class BillPaymentsViewModel(application: Application) :
    BaseViewModel<IBillPayments.State>(application),
    IBillPayments.ViewModel {
    override val state: IBillPayments.State = BillPaymentsState()
}