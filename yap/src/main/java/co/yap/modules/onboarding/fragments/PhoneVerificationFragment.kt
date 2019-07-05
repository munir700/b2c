package co.yap.modules.onboarding.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.interfaces.IPhoneVerification
import co.yap.modules.onboarding.viewmodels.PhoneVerificationViewModel
import co.yap.yapcore.BaseBindingFragment


class PhoneVerificationFragment : OnboardingChildFragment<IPhoneVerification.ViewModel>(), IPhoneVerification.View {

    override val viewModel: IPhoneVerification.ViewModel
        get() = ViewModelProviders.of(this).get(PhoneVerificationViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_phone_verification

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.nextButtonPressEvent.observe(this, nextButtonObserver)
    }

    private val nextButtonObserver = Observer<Boolean> { navigate(R.id.nameFragment) }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.nextButtonPressEvent.removeObserver(nextButtonObserver)
    }
}
