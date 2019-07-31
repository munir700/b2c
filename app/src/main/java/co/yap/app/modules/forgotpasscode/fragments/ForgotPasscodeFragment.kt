package co.yap.app.modules.forgotpasscode.fragments

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.app.modules.forgotpasscode.viewmodels.ForgotPasscodeViewModel
import co.yap.modules.onboarding.activities.CreatePasscodeActivity
import co.yap.modules.onboarding.constants.Constants
import co.yap.modules.onboarding.fragments.PhoneVerificationFragment
import co.yap.modules.onboarding.interfaces.IPhoneVerification
import co.yap.modules.onboarding.viewmodels.PhoneVerificationViewModel
import co.yap.yapcore.helpers.SharedPreferenceManager

class ForgotPasscodeFragment : PhoneVerificationFragment() {
    override val viewModel: IPhoneVerification.ViewModel
        get() = ViewModelProviders.of(this).get(ForgotPasscodeViewModel::class.java)


    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_phone_verification

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.nextButtonPressEvent.observe(this, nextButtonObserver)
    }

    override fun onDestroyView() {
        viewModel.nextButtonPressEvent.removeObservers(this)
        super.onDestroyView()
    }

    private val nextButtonObserver = Observer<Boolean> {
        startActivityForResult(
            context?.let { CreatePasscodeActivity.newIntent(it) },
            Constants.REQUEST_CODE_CREATE_PASSCODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constants.REQUEST_CODE_CREATE_PASSCODE) {
            if (null != data) {
                viewModel.setPasscode(data.getStringExtra(SharedPreferenceManager.KEY_PASSCODE))
                findNavController().navigate(R.id.action_phoneVerificationFragment_to_nameFragment)
            }
        }
    }
}