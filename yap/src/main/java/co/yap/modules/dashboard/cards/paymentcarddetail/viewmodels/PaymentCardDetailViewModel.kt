package co.yap.modules.dashboard.cards.paymentcarddetail.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.paymentcarddetail.Interfaces.IPaymentCardDetail
import co.yap.modules.dashboard.cards.paymentcarddetail.states.PaymentCardDetailState
import co.yap.modules.dashboard.helpers.transaction.TransactionLogicHelper
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class PaymentCardDetailViewModel(application: Application) :
    BaseViewModel<IPaymentCardDetail.State>(application),
    IPaymentCardDetail.ViewModel {

    override val state: IPaymentCardDetail.State = PaymentCardDetailState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val transactionLogicHelper: TransactionLogicHelper =
        TransactionLogicHelper(context)


    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }


}