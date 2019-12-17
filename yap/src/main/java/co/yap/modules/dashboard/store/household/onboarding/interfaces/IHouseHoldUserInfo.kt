package co.yap.modules.dashboard.store.household.onboarding.interfaces

import android.graphics.drawable.Drawable
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHouseHoldUserInfo {

    interface State : IBase.State {
        var firstName: String
        var lastName: String
        var emailAddress: String
        var valid: Boolean
        //

        var dummyStrings: Array<String>
        var firstNameError: String
        var lastNameError: String
        var drawbleRight: Drawable?
        var drawbleRightLastName: Drawable?
        var drawbleRightFirstName: Drawable?


        var email: String
        var emailError: String

        //textwatcher
        var cursorPlacement: Boolean
        var refreshField: Boolean
        var setSelection: Int
        var handleBackPress: Int
        var emailTitle: String
        var emailVerificationTitle: String
        var emailBtnTitle: String
        var twoWayTextWatcher: String
        var deactivateField: Boolean

        var verificationCompleted: Boolean

    }

    interface ViewModel : IBase.ViewModel<State> {

        val clickEvent: SingleClickEvent
        fun handlePressOnNext(id: Int)
        fun handlePressOnBackButton()
//        val backButtonPressEvent: SingleLiveEvent<Boolean>

    }

    interface View : IBase.View<ViewModel>

}