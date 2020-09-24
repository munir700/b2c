package co.yap.modules.dashboard.more.profile.intefaces

import android.graphics.Bitmap
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent


interface ISuccess {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnDoneButton()
        fun placesApiCall(photoPlacedId: String)
        val buttonClickEvent: SingleClickEvent
    }

    interface State : IBase.State {
        var topMainHeading: String
        var staticString: String
        var destination: String
        var buttonTitle: String
        var placeBitmap: Bitmap?
    }
}