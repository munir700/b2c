package co.yap.household.onboard.otherscreens

import android.content.Intent
import android.os.Bundle
import co.yap.household.R
import co.yap.modules.onboarding.activities.LiteDashboardActivity
import co.yap.widgets.CoreButton
import co.yap.yapcore.AdjustEvents.Companion.trackAdjustPlatformEvent
import co.yap.yapcore.adjust.AdjustEvents
import co.yap.yapcore.defaults.DefaultActivity

class KycSuccessActivity : DefaultActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kyc_success)
        findViewById<CoreButton>(R.id.btnTopUp).setOnClickListener {
            trackAdjustPlatformEvent(AdjustEvents.KYC_END.type)
            startActivity(Intent(this, LiteDashboardActivity::class.java))
        }
    }

}
