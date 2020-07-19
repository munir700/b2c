package co.yap.household.onboard.onboarding.invalideid

import android.content.Intent
import android.content.Intent.ACTION_DIAL
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.household.BR
import co.yap.household.R
import co.yap.yapcore.AdjustEvents.Companion.trackAdjustPlatformEvent
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.adjust.AdjustEvents
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.SharedPreferenceManager
import kotlinx.android.synthetic.main.activity_eidnot_accepted.*
@Deprecated("")
class InvalidEIDFragment : BaseBindingFragment<IInvalidEIDSuccess.ViewModel>(), IFragmentHolder {

    override fun getBindingVariable(): Int {
        return BR.invalidEIDSuccessViewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_eidnot_accepted
    }

    override val viewModel: IInvalidEIDSuccess.ViewModel
        get() = ViewModelProviders.of(this).get(InvalidEIDSuccessViewModel::class.java)

    /*companion object {
        const val BUNDLE_DATA = "bundle_data"
        const val EXISTING_USER = "existingYapUser"
        const val USER_INFO = "user_info"
        fun getIntent(context: Context, bundle: Bundle = Bundle()): Intent {
            val intent = Intent(context, InvalidEIDFragment::class.java)
            intent.putExtra(BUNDLE_DATA, bundle)
            return intent
        }
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObservers()
        trackAdjustPlatformEvent(AdjustEvents.ONBOARDING_NEW_HH_USER_EID_DECLINED.type)
    }

    private fun addObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
    }

    private val clickObserver = Observer<Int> {
        when (it) {
            R.id.btnEID_NotAccept -> {
                super.onBackPressed()
            }
        }
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
}
