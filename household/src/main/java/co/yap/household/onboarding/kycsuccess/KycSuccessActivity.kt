package co.yap.household.onboarding.kycsuccess

import android.content.Intent
import android.os.Bundle
import co.yap.household.R
import co.yap.household.onboarding.dashboard.main.activities.HouseholdDashboardActivity
import co.yap.yapcore.defaults.DefaultActivity
import kotlinx.android.synthetic.main.activity_kyc_success.*

class KycSuccessActivity : DefaultActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kyc_success)

        btnTopUp.setOnClickListener {
            startActivity(Intent(this, HouseholdDashboardActivity::class.java))
        }
    }

}
