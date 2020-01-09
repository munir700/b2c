package co.yap.household.onboarding.onboarding.activities

import android.os.Bundle
import co.yap.household.R
import co.yap.household.onboarding.OnboardingHouseHoldActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultActivity
import kotlinx.android.synthetic.main.activity_on_boarding_existing_yap.*

class OnBoardingExistingYapActivity : DefaultActivity(), IFragmentHolder {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding_existing_yap)
        btnAccept.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean(OnboardingHouseHoldActivity.EXISTING_USER, true)
            startActivity(OnboardingHouseHoldActivity.getIntent(this, bundle))
        }
    }
}
