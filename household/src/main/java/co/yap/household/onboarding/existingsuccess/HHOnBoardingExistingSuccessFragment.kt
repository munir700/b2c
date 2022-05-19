package co.yap.household.onboarding.existingsuccess

import androidx.fragment.app.viewModels
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHhonBoardingExistingSuccessBinding
import co.yap.modules.dashboard.main.activities.YapDashboardActivity
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.enums.YAPThemes
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.switchTheme
import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HHOnBoardingExistingSuccessFragment :
    BaseNavViewModelFragmentV2<FragmentHhonBoardingExistingSuccessBinding, IHHOnBoardingExistingSuccess.State, HHOnBoardingExistingSuccessVM>() {

    override fun getBindingVariable() = BR.viewModel
    override val viewModel: HHOnBoardingExistingSuccessVM by viewModels()

    override fun getLayoutId() = R.layout.fragment_hhon_boarding_existing_success
    override fun toolBarVisibility() = false

    override fun onClick(id: Int) {
        when (id) {
            R.id.btnCompleteVerification -> navigateForwardWithAnimation(
                HHOnBoardingExistingSuccessFragmentDirections.toHHOnBoardingMobileFragment(),
                arguments,
                null
            )
            R.id.tvSkip -> {
                context.switchTheme(YAPThemes.CORE())
                launchActivity<YapDashboardActivity>(clearPrevious = true) { }
            }
        }
    }
}
