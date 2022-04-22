package co.yap.modules.onboarding.fragments

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentNameBinding
import co.yap.modules.onboarding.interfaces.IName
import co.yap.modules.onboarding.viewmodels.NameViewModel
import co.yap.yapcore.firebase.FirebaseEvent
import co.yap.yapcore.firebase.trackEventWithScreenName
import co.yap.yapcore.leanplum.SignupEvents
import co.yap.yapcore.leanplum.trackEvent

class NameFragment : OnboardingChildFragment<FragmentNameBinding, IName.ViewModel>(), IName.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_name

    override val viewModel: NameViewModel by viewModels()
    //  get() = ViewModelProvider(this).get(NameViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.nextButtonPressEvent.observe(this, nextButtonObserver)
        viewModel.state.firstNameError.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrBlank()) {
                viewDataBinding.etFirstName.settingUIForError(it)
            } else {
                viewDataBinding.etFirstName.settingUIForNormal()
            }
        })

        viewModel.state.lastNameError.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrBlank()) {
                viewDataBinding.etLastName.settingUIForError(it)
            } else {
                viewDataBinding.etLastName.settingUIForNormal()
            }
        })
    }

    override fun onDestroyView() {
        viewModel.nextButtonPressEvent.removeObservers(this)
        super.onDestroyView()
    }

    private val nextButtonObserver = Observer<Boolean> {
        trackEvent(
            SignupEvents.SIGN_UP_NAME.type,
            viewModel.state.firstName.trim() + " " + viewModel.state.firstName.trim()
        )
        trackEventWithScreenName(FirebaseEvent.SIGNUP_NAME)
        navigate(R.id.emailFragment)
    }
}