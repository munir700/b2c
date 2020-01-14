package co.yap.household.onboarding.onboarding.fragments

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.onboarding.fragments.OnboardingChildFragment
import co.yap.household.onboarding.onboarding.interfaces.IEmail
import co.yap.household.onboarding.onboarding.viewmodels.PassCodeViewModel
import kotlinx.android.synthetic.main.passcode.*

class TempPassCodeFragment : OnboardingChildFragment<IEmail.ViewModel>() {

    private val windowSize: Rect = Rect() // to hold the size of the visible window

    override fun getBindingVariable(): Int = BR.passCodeViewModel

    override fun getLayoutId(): Int = R.layout.passcode

    override val viewModel: IEmail.ViewModel
        get() = ViewModelProviders.of(this).get(PassCodeViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        containerPassCode.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_passCodeFragment_to_emailHouseHoldFragment)
        })
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        viewModel.animationStartEvent.removeObservers(this)
        super.onDestroyView()
    }


    override fun onBackPressed(): Boolean = true
}


