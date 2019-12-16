package co.yap.modules.dashboard.store.household.onboarding.interfaces

import android.graphics.drawable.Drawable
import android.widget.EditText
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHouseHoldUserContact {

    interface State : IBase.State {
        var mobileNumber: String
        var confirmMobileNumber: String
         var valid: Boolean

//
        var countryCode:String
        var mobile: String
        var drawbleRight: Drawable?
        var background: Drawable?
        var mobileNoLength: Int
        var etMobileNumber: EditText?
         var errorMessage:String

    }

    interface ViewModel : IBase.ViewModel<State> {

        val clickEvent: SingleClickEvent
        fun handlePressOnAdd(id: Int)
        fun handlePressOnBackButton()
//        val backButtonPressEvent: SingleLiveEvent<Boolean>
    }

    interface View : IBase.View<ViewModel>

}