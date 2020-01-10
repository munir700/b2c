package co.yap.household.onboarding.onboarding.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import co.yap.household.R
import co.yap.household.onboarding.OnboardingHouseHoldActivity
import co.yap.yapcore.defaults.DefaultActivity
import kotlinx.android.synthetic.main.activity_onboarding_house_hold_success.*

class OnboardingHouseHoldActivitySuccessActivity : DefaultActivity() {
    companion object {
        const val BUNDLE_DATA = "bundle_data"
        const val EXISTING_USER = "existingYapUser"
        fun getIntent(context: Context, bundle: Bundle = Bundle()): Intent {
            val intent = Intent(context, OnboardingHouseHoldActivitySuccessActivity::class.java)
            intent.putExtra(BUNDLE_DATA, bundle)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_house_hold_success)
        btnCompleteVerification.setOnClickListener {
            startActivity(OnboardingHouseHoldActivity.getIntent(this, intent.getBundleExtra(BUNDLE_DATA)))
        }
    }
}
