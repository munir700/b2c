package co.yap.household.onboarding.existing

import androidx.fragment.app.viewModels
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHhonBoardingExistingBinding
import co.yap.modules.dashboard.main.activities.YapDashboardActivity

import co.yap.yapcore.enums.AccountStatus
import co.yap.yapcore.enums.YAPThemes
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.switchTheme
import co.yap.yapcore.helpers.extentions.toCamelCase
import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HHOnBoardingExistingFragment :
    BaseNavViewModelFragmentV2<FragmentHhonBoardingExistingBinding, IHHOnBoardingExisting.State, HHOnBoardingExistingVM>() {
    override fun getBindingVariable() = BR.viewModel

    override val viewModel: HHOnBoardingExistingVM by viewModels()

    override fun getLayoutId() = R.layout.fragment_hhon_boarding_existing
    override fun toolBarVisibility() = false

    override fun onClick(id: Int) {
        when (id) {
            R.id.btnAccepted -> viewModel.subAccountInvitationStatus((AccountStatus.INVITE_ACCEPTED.name.toCamelCase())) {
                it?.let {
                    navigateForward(
                        HHOnBoardingExistingFragmentDirections.toHHOnBoardingExistingSuccessFragment(),
                        arguments
                    )
                }
            }
            R.id.tvDecline -> viewModel.subAccountInvitationStatus((AccountStatus.INVITE_DECLINED.name.toCamelCase())) {
                it?.let {
                    context.switchTheme(YAPThemes.CORE())
                    launchActivity<YapDashboardActivity>(clearPrevious = true)
                }
            }
        }
    }
}
