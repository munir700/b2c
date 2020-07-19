package co.yap.household.onboarding.existingsuccess

import android.os.Bundle
import androidx.lifecycle.Observer
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHhonBoardingExistingSuccessBinding
import co.yap.modules.dashboard.main.activities.YapDashboardActivity
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.extentions.launchActivity


class HHOnBoardingExistingSuccessFragment :
    BaseNavViewModelFragment<FragmentHhonBoardingExistingSuccessBinding, IHHOnBoardingExistingSuccess.State, HHOnBoardingExistingSuccessVM>() {

    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_hhon_boarding_existing_success
    override fun toolBarVisibility() = false
    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        viewModel.clickEvent.observe(this, Observer { onClick(it) })
    }

    private fun onClick(id: Int) {
        when (id) {
            R.id.btnCompleteVerification -> navigateForwardWithAnimation(
                HHOnBoardingExistingSuccessFragmentDirections.toHHOnBoardingMobileFragment(),
                arguments,
                null
            )
            R.id.tvSkip -> launchActivity<YapDashboardActivity>(clearPrevious = true) { }
        }
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }
}
