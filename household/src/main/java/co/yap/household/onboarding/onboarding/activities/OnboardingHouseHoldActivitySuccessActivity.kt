package co.yap.household.onboarding.onboarding.activities

import android.os.Bundle
import co.yap.household.R
import co.yap.translation.Strings
import co.yap.yapcore.defaults.DefaultActivity
import kotlinx.android.synthetic.main.activity_eidnot_accepted.*

class OnboardingHouseHoldActivitySuccessActivity : DefaultActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_house_hold_success)

        setMessage()
    }

    /*
     * In this function set dynamic message.
    * */
    private fun setMessage() {
        val message =
            getString(Strings.screen_on_boarding_existing_message).format(
                "Mirza Adil"
            )

        tvEID_NotAcceptMessage.text = message
    }


}
