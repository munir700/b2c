package co.yap.household.onboard.onboarding.existinghousehold

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.onboard.onboarding.main.OnBoardingHouseHoldActivity
import co.yap.household.onboard.onboarding.householdsuccess.HouseHoldSuccessFragment
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.helpers.extentions.toast

class ExistingHouseholdFragment : BaseBindingFragment<IExistingHouseHold.ViewModel>(),
    IFragmentHolder {

    override fun getBindingVariable(): Int {
        return BR.existingHouseHoldViewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_on_boarding_existing_yap
    }

    override val viewModel: IExistingHouseHold.ViewModel
        get() = ViewModelProviders.of(this).get(ExistingHouseholdViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObservers()
    }

    private fun addObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
    }

    private val clickObserver = Observer<Int> {
        when (it) {
            R.id.btnAccept -> {
                val bundle = Bundle()
                bundle.putBoolean(OnBoardingHouseHoldActivity.EXISTING_USER, true)
                startFragment(
                    HouseHoldSuccessFragment::class.java.name,
                    false,
                    bundle,
                    showToolBar = false
                )
            }

            R.id.tvOnBoardingExistingDeclineRequest -> {
                toast("Decline request")
            }
        }
    }
}
