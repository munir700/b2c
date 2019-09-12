package co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.interfaces.IFundActions
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.states.FundActionsState
import co.yap.yapcore.BaseViewModel

open class FundActionsViewModel (application: Application) : BaseViewModel<IFundActions.State>(application), IFundActions.ViewModel {
    override val state: FundActionsState = FundActionsState()


}