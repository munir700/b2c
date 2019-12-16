package co.yap.modules.dashboard.store.household.onboarding.states

import co.yap.modules.dashboard.store.household.onboarding.interfaces.IHouseHoldUserInfo
import co.yap.yapcore.BaseState
import co.yap.yapcore.SingleLiveEvent

class HouseHoldUserInfoStates : BaseState(), IHouseHoldUserInfo.State {
    override var firstName: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var lastName: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var emailAddress: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var valid: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override val backButtonPressEvent: SingleLiveEvent<Boolean>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
}