package co.yap.household.onboard.onboarding.newuser

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentOnBoardingNewUserBinding
import co.yap.household.onboard.onboarding.main.OnBoardingHouseHoldActivity
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.extentions.launchActivity

class HHNewUserFragment: BaseNavViewModelFragment<FragmentOnBoardingNewUserBinding, INewHouseHoldUser.State,
    NewHouseholdUserViewModel>() {

    override fun getBindingVariable(): Int = BR.newUserViewModel

    override fun getLayoutId(): Int = R.layout.fragment_on_boarding_new_user

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.clickEvent.observe(this, clickObserver)
    }

    private val clickObserver = Observer<Int> {
        when (it) {
            R.id.btnGetStarted -> {
                launchActivity<OnBoardingHouseHoldActivity> (clearPrevious = true) {
                    putExtra(OnBoardingHouseHoldActivity.USER_INFO, state.accountInfo.value)
                    putExtra(OnBoardingHouseHoldActivity.NEXT_SCREEN, true)
                }
            }
        }
    }
}