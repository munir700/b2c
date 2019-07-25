package co.yap.modules.kyc.interfaces

import android.widget.TextView
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

interface IAddressSelection {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
//        val nextButtonPressEvent: SingleLiveEvent<Boolean>
        fun handlePressOnNext()
        fun handlePressOnSelectLocation()
        fun onEditorActionListener(): TextView.OnEditorActionListener
    }

    interface State : IBase.State {
        var headingTitle: String
        var subHeadingTitle: String
        var addressField: String
        var landmarkField: String
        var locationBtnText: String
        var valid: Boolean


    }
}