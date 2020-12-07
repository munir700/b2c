package co.yap.modules.dashboard.addreceipt

import android.app.Application
import co.yap.yapcore.BaseViewModel

class AddTransactionReceiptViewModel(application: Application) :
    BaseViewModel<IAddTransactionReceipt.State>(application), IAddTransactionReceipt.ViewModel{
    override val state = AddTransactionReceiptState()
}