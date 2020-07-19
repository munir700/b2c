package co.yap.household.onboarding.onboardemail

import android.os.Bundle
import androidx.lifecycle.Observer
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHhonBoardingEmailBinding
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment


class HHOnBoardingEmailFragment :
    BaseNavViewModelFragment<FragmentHhonBoardingEmailBinding, IHHOnBoardingEmail.State, HHOnBoardingEmailVM>() {

    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_hhon_boarding_email
    override fun setHomeAsUpIndicator() = R.drawable.ic_back
    override fun setDisplayHomeAsUpEnabled() = false
    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        viewModel.clickEvent.observe(this, Observer { onClick(it) })
    }

    private fun onClick(id: Int) {
        when (id) {
            R.id.btnNext -> viewModel.verifyHouseholdEmail {
                if (it) {
                    arguments?.putInt(Constants.INDEX, 100)
                    navigateForward(
                        HHOnBoardingEmailFragmentDirections.toHHOnBoardingSuccessFragment(),
                        arguments
                    )
                }
            }
        }
    }
    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }
}
