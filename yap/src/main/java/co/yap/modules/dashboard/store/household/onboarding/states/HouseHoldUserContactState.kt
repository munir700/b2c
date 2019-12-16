package co.yap.modules.dashboard.store.household.onboarding.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.dashboard.store.household.onboarding.interfaces.IBaseOnboarding
import co.yap.modules.dashboard.store.household.onboarding.interfaces.IHouseHoldUserContact
import co.yap.yapcore.BaseState

class HouseHoldUserContactState  : BaseState(), IHouseHoldUserContact.State {

//    @get:Bindable
//    override var tootlBarTitle: String = ""
//        set(value) {
//            field = value
//            notifyPropertyChanged(BR.tootlBarTitle)
//
//        }
//
//    @get:Bindable
//    override var tootlBarVisibility: Int = 0x00000000
//        set(value) {
//            field = value
//            notifyPropertyChanged(BR.tootlBarVisibility)
//
//        }
    override var mobileNumber: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var confirmMobileNumber: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var valid: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
}