package co.yap.modules.dashboard.main.interfaces

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.AccountType


interface IYapDashboard {

    interface State : IBase.State {
        var accountType: AccountType
        var fullName: String
        var firstName: String?
        var accountNo: String
        var ibanNo: String
        var availableBalance: String
        var userNameImage: ObservableField<String>
        var appVersion: ObservableField<String>

    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnNavigationItem(id: Int)
        fun copyAccountInfoToClipboard()
        val showUnverifedscreen: MutableLiveData<Boolean>
    }

    interface View : IBase.View<ViewModel> {
        fun closeDrawer()
        fun openDrawer()
        fun toggleDrawer()
        fun enableDrawerSwipe(enable: Boolean)
    }
}