package co.yap.app.modules.login.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

interface ISystemPermission {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        var screenType: String
        fun permissonGranted()
        fun permissonNotGranted()
        val permissionGrantedPressEvent: SingleLiveEvent<Boolean>
        val permissionNotGrantedPressEvent: SingleLiveEvent<Boolean>
    }

    interface State : IBase.State {
        var icon: Int
        var title: String
        var termsAndConditionsVisibility: Boolean
        var buttonTitle: String
    }
}