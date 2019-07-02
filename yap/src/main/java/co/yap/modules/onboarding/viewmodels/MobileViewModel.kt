package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.R
import co.yap.modules.onboarding.interfaces.IMobile
import co.yap.modules.onboarding.states.MobileState
import co.yap.yapcore.BaseViewModel

class MobileViewModel(application: Application) : BaseViewModel<IMobile.State>(application), IMobile.ViewModel {

    override val state: MobileState = MobileState()


    override fun validateMobileNumber(phoneNumber: String): Boolean? {
        if (!phoneNumber.trim().equals("")) {
            val input = phoneNumber.trim().replace("+", "")
            val regex = "[0-9]+"
            if (!input.matches(regex.toRegex())) {

                /* enable core button
                 set normal UI*/
                state.valid = false
                state.mobileError = getString(R.string.screen_phone_number_display_text_error)
                return false
            }
        }
        if (phoneNumber.trim().equals("")) {

            /* disable core button
             set error UI*/
            state.valid = false
            state.drawbleRight = null
            state.mobileError = getString(R.string.screen_phone_number_display_text_error)
            return false
        }
        state.valid = true
        state.mobileError = ""
        state.drawbleRight = context!!.resources.getDrawable(co.yap.yapcore.R.drawable.path)

        return true
    }


    override fun handlePressOnNext() {

        state.valid = false

    }
}