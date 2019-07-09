package co.yap.modules.onboarding.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.interfaces.IEmail
import co.yap.modules.onboarding.viewmodels.EmailViewModel


class EmailFragment : OnboardingChildFragment<IEmail.ViewModel>() {

    override fun getBindingVariable(): Int = BR.emailViewModel
    override fun getLayoutId(): Int = R.layout.fragment_email

    override val viewModel: IEmail.ViewModel
        get() = ViewModelProviders.of(this).get(EmailViewModel::class.java)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.nextButtonPressEvent.observe(this, nextButtonObserver)
    }

    private val nextButtonObserver = Observer<Boolean> { navigate(R.id.congratulationsFragment) }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.nextButtonPressEvent.removeObserver(nextButtonObserver)
    }

}