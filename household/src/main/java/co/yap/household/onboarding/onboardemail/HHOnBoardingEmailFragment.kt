package co.yap.household.onboarding.onboardemail

import androidx.fragment.app.viewModels
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHhonBoardingEmailBinding
import co.yap.yapcore.constants.Constants

import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HHOnBoardingEmailFragment :
    BaseNavViewModelFragmentV2<FragmentHhonBoardingEmailBinding, IHHOnBoardingEmail.State, HHOnBoardingEmailVM>() {

    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_hhon_boarding_email
    override fun setHomeAsUpIndicator() = R.drawable.ic_back
    override val viewModel: HHOnBoardingEmailVM by viewModels()

    override fun setDisplayHomeAsUpEnabled() = false

    override fun onClick(id: Int) {
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
}
