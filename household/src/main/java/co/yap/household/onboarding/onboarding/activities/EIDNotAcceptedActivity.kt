package co.yap.household.onboarding.onboarding.activities

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_DIAL
import android.net.Uri
import android.os.Bundle
import co.yap.household.R
import co.yap.modules.dummy.ActivityNavigator
import co.yap.networking.cards.responsedtos.CardBalance
import co.yap.translation.Strings
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultActivity
import co.yap.yapcore.helpers.AuthUtils
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.activity_eidnot_accepted.*


class EIDNotAcceptedActivity : DefaultActivity(), IFragmentHolder {
    private var mNavigator: ActivityNavigator? = null

    companion object {
        const val BUNDLE_DATA = "bundle_data"
        const val EXISTING_USER = "existingYapUser"
        const val USER_INFO = "user_info"
        fun getIntent(context: Context, bundle: Bundle = Bundle()): Intent {
            val intent = Intent(context, EIDNotAcceptedActivity::class.java)
            intent.putExtra(BUNDLE_DATA, bundle)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eidnot_accepted)
        tvEID_NotAcceptMessage.text =
            getString(Strings.screen_house_hold_existing_yap_message).format(
                MyUserManager.user?.currentCustomer?.getFullName()
            )
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
            doLogout()
        }

//        tvEID_NotAcceptNumber.setOnClickListener {
//            userCall()
//        }
    }

    private fun doLogout() {
        AuthUtils.navigateToHardLogin(this)
        MyUserManager.cardBalance.value = CardBalance()
        MyUserManager.cards.value?.clear()
        MyUserManager.expireUserSession()
        finish()
    }

}
