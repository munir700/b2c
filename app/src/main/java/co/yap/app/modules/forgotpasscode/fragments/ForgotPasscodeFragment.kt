package co.yap.app.modules.forgotpasscode.fragments

import androidx.lifecycle.ViewModelProviders
import co.yap.app.modules.forgotpasscode.viewmodels.ForgotPasscodeViewModel
import co.yap.modules.onboarding.fragments.PhoneVerificationFragment
import co.yap.modules.onboarding.interfaces.IPhoneVerification

class ForgotPasscodeFragment : PhoneVerificationFragment() {
    override val viewModel: IPhoneVerification.ViewModel
        get() = ViewModelProviders.of(this).get(ForgotPasscodeViewModel::class.java)

}