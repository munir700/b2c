package co.yap.modules.dashboard.transaction.totalpurchases

import android.app.Application
import co.yap.yapcore.BaseViewModel

class TotalPurchasesViewModel(application: Application) :
    BaseViewModel<ITotalPurchases.State>(application), ITotalPurchases.ViewModel {
    override val state: ITotalPurchases.State = TotalPurchaseState()
}