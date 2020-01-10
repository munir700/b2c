package co.yap.household.onboarding.onboarding.activities

import android.content.Intent
import android.content.Intent.ACTION_DIAL
import android.net.Uri
import android.os.Bundle
import co.yap.household.R
import co.yap.translation.Strings
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultActivity
import co.yap.yapcore.helpers.toast
import kotlinx.android.synthetic.main.activity_eidnot_accepted.*


class EIDNotAcceptedActivity : DefaultActivity(), IFragmentHolder {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eidnot_accepted)
        setMessage()
        setClickerListner()
        setClickerListner()
    }


    /*
    * In this function call user Number.
    * */
    private fun userCall() {
        val userNumber = tvEID_NotAcceptNumber.text.toString()
        if (userNumber.isNullOrBlank()) {
            return
        } else {
            val intent = Intent(ACTION_DIAL)
            intent.data = Uri.parse("tel:$userNumber")
            startActivity(intent)
        }

    }

    /*
    * In this function set onClicker listener.
    * */
    private fun setClickerListner() {
        btnEID_NotAccept.setOnClickListener {
            toast("Click Button")
        }

        tvEID_NotAcceptNumber.setOnClickListener {
            userCall()
        }
    }


    /*
    * In this function set dynamic message.
    * */
    private fun setMessage() {
        val message =
            getString(Strings.screen_house_hold_existing_yap_message).format(
                "Mirza Adil"
            )

        tvEID_NotAcceptMessage.text = message
    }
}
