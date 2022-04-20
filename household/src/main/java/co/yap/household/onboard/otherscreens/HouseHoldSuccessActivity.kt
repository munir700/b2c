package co.yap.household.onboard.otherscreens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import co.yap.household.R
import co.yap.household.onboard.onboarding.main.OnBoardingHouseHoldActivity
import co.yap.translation.Strings
import co.yap.widgets.CoreButton
import co.yap.yapcore.defaults.DefaultActivity

class HouseHoldSuccessActivity : DefaultActivity() {
    companion object {
        const val BUNDLE_DATA = "bundle_data"
        const val EXISTING_USER = "existingYapUser"
        fun getIntent(context: Context, bundle: Bundle = Bundle()): Intent {
            val intent = Intent(context, HouseHoldSuccessActivity::class.java)
            intent.putExtra(BUNDLE_DATA, bundle)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_house_hold_success)

        setMessage()
        findViewById<CoreButton>(R.id.btnCompleteVerification).setOnClickListener {
            startActivity(
                intent.getBundleExtra(BUNDLE_DATA)?.let { it1 ->
                    OnBoardingHouseHoldActivity.getIntent(
                        this,
                        it1
                    )
                }
            )
        }
    }

    /*
     * In this function set dynamic message.
    * */
    private fun setMessage() {
        val message =
            getString(Strings.screen_on_boarding_existing_message).format(
                "Mirza Adil"
            )

        findViewById<TextView>(R.id.tvOnBoardingExistingMessage)?.text = message
    }


}
