package co.yap.modules.dashboard.main.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.dashboard.main.interfaces.IYapDashboard
import co.yap.modules.onboarding.enums.AccountType
import co.yap.yapcore.BaseState

class YapDashBoardState : BaseState(), IYapDashboard.State {

    @get:Bindable
    override var accountType: AccountType = AccountType.B2C_ACCOUNT
        set(value) {
            field = value
            notifyPropertyChanged(BR.accountType)
        }

    @get:Bindable
    override var fullName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.fullName)
        }

    @get:Bindable
    override var firstName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstName)
        }


    @get:Bindable
    override var accountNo: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.accountNo)
        }

    @get:Bindable
    override var ibanNo: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.ibanNo)
        }

    @get:Bindable
    override var availableBalance: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.availableBalance)
        }

}