package co.yap.household.onboarding.onboarding.activities

import android.os.Bundle
import co.yap.household.R
import co.yap.household.onboarding.OnboardingHouseHoldActivity
import co.yap.translation.Strings
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultActivity
import co.yap.yapcore.helpers.toast
import kotlinx.android.synthetic.main.activity_on_boarding_existing_yap.*

class OnBoardingExistingYapActivity : DefaultActivity(), IFragmentHolder {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding_existing_yap)
        setTitleName()
    }

    /*
    * In this function show title message.
    * */

    private fun setTitleName() {
        val successMessage =
            getString(Strings.screen_house_hold_onBoarding_existing_title_display_text).format(
                "Mirza", "Adil"
            )
        tvTitleKYCSuccess.text = successMessage


        btnAccept.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean(OnboardingHouseHoldActivity.EXISTING_USER, true)
            startActivity(OnboardingHouseHoldActivity.getIntent(this, bundle))
        }


        tvOnBoardingExistingDeclineRequest.setOnClickListener {
            toast("Decline request")
        }
    }
}
