package co.yap.household.onboarding.existing

import android.os.Bundle
import androidx.lifecycle.Observer
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHhonBoardingExistingBinding
import co.yap.modules.dashboard.main.activities.YapDashboardActivity
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.enums.AccountStatus
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.toCamelCase

class HHOnBoardingExistingFragment :
    BaseNavViewModelFragment<FragmentHhonBoardingExistingBinding, IHHOnBoardingExisting.State, HHOnBoardingExistingVM>() {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_hhon_boarding_existing
    override fun toolBarVisibility() = false
    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        viewModel.clickEvent.observe(this, Observer { onClick(it) })
    }

    private fun onClick(id: Int) {
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
                    launchActivity<YapDashboardActivity>(clearPrevious = true)
                }
            }
        }
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }
}
