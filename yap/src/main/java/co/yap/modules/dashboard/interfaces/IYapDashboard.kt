package co.yap.modules.dashboard.interfaces

import co.yap.modules.onboarding.enums.AccountType
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent


interface IYapDashboard {

    interface State : IBase.State {
        var accountType: AccountType
        var fullName: String
        var accountNo: String
        var ibanNo: String
    }
    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnNavigationItem(id: Int)
        fun copyAccountInfoToClipboard()
        fun getAccountInfo()
        fun getDebitCards()
    }
    interface View : IBase.View<ViewModel> {
        fun closeDrawer()
        fun openDrawer()
        fun toggleDrawer()
        fun enableDrawerSwipe(enable: Boolean)
    }
}