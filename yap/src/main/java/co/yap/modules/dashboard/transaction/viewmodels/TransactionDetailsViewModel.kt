package co.yap.modules.dashboard.transaction.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import co.yap.modules.dashboard.transaction.interfaces.ITransactionDetails
import co.yap.modules.dashboard.transaction.states.TransactionDetailsState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import java.text.SimpleDateFormat
import java.util.*


class TransactionDetailsViewModel(application: Application) :
    BaseViewModel<ITransactionDetails.State>(application), ITransactionDetails.ViewModel {
    override val state: TransactionDetailsState = TransactionDetailsState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()

    @SuppressLint("SimpleDateFormat")
    override fun onCreate() {
        super.onCreate()
        val dateString = Calendar.getInstance().time
        val dateFormat=SimpleDateFormat("MMM dd, YYYY ・ HH:mmaa")
        state.toolBarTitle =  dateFormat.format(dateString)
    }

    override fun handlePressOnBackButton(id: Int) {
        clickEvent.postValue(id)
    }

    override fun handlePressOnShareButton(id: Int) {
        clickEvent.postValue(id)
    }
}