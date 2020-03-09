package co.yap.household.onboard.onboarding.householdsuccess

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.onboard.onboarding.main.OnBoardingHouseHoldActivity
import co.yap.yapcore.BaseBindingFragment

class HouseHoldSuccessFragment : BaseBindingFragment<IHouseHoldSuccess.ViewModel>() {
     lateinit var EXISTING_USER:String

    override fun getBindingVariable(): Int {
        return BR.houseHoldSuccessViewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_onboarding_house_hold_success
    }

    override val viewModel: IHouseHoldSuccess.ViewModel
        get() = ViewModelProviders.of(this).get(HouseholdSuccessViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            EXISTING_USER = it.getString(OnBoardingHouseHoldActivity.EXISTING_USER, "")
        }
        addObservers()
    }

    private fun addObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
    }

    private val clickObserver = Observer<Int> {
        when (it) {
            R.id.btnCompleteVerification -> {
                val bundle = Bundle()
                bundle.putBoolean(OnBoardingHouseHoldActivity.EXISTING_USER, false)
                startActivity(OnBoardingHouseHoldActivity.getIntent(requireContext(), bundle))
            }

            R.id.tvSkipAndLater -> {
//                toast("Skip And Later")
            }
        }
    }


}
