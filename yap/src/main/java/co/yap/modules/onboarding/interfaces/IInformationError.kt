package co.yap.modules.onboarding.interfaces

import android.graphics.drawable.Drawable
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

interface IInformationError {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handlePressOnGoToDashboard()
        var countryName: String
    }

    interface State : IBase.State {
        var errorTitle: String
        var errorImage: Drawable
        var errorGuide: String
        var buttonTitle: String
    }
}