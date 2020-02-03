package co.yap.modules.onboarding.fragments

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.activities.CreatePasscodeActivity
import co.yap.modules.onboarding.constants.Constants
import co.yap.modules.onboarding.interfaces.IPhoneVerification
import co.yap.modules.onboarding.viewmodels.PhoneVerificationViewModel
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.extentions.trackEvent
import co.yap.yapcore.leanplum.TrackEvents


class PhoneVerificationFragment : OnboardingChildFragment<IPhoneVerification.ViewModel>(), IPhoneVerification.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_phone_verification

    override val viewModel: IPhoneVerification.ViewModel
        get() = ViewModelProviders.of(this).get(PhoneVerificationViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setObservers()
    }
    override fun setObservers() {
        viewModel.nextButtonPressEvent.observe(this, Observer {
            startActivityForResult(
                context?.let { CreatePasscodeActivity.newIntent(it) },
                Constants.REQUEST_CODE_CREATE_PASSCODE
            )
        })
    }

    override fun onDestroyView() {
        viewModel.nextButtonPressEvent.removeObservers(this)
        super.onDestroyView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constants.REQUEST_CODE_CREATE_PASSCODE) {
            if (null != data) {
                trackEvent(TrackEvents.OTP_CODE_ENTERED)
                viewModel.setPasscode(data.getStringExtra("PASSCODE"))
                findNavController().navigate(R.id.action_phoneVerificationFragment_to_nameFragment)

            }
        }
    }
}
