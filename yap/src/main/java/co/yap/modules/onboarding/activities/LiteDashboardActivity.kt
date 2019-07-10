package co.yap.modules.onboarding.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.app.login.BiometricUtil
import co.yap.modules.onboarding.enums.AccountType
import co.yap.modules.onboarding.interfaces.ILiteDashboard
import co.yap.modules.onboarding.viewmodels.LiteDashboardViewModel
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.helpers.SharedPreferenceManager
import kotlinx.android.synthetic.main.activity_lite_dashboard.*


class LiteDashboardActivity : BaseBindingActivity<ILiteDashboard.ViewModel>() {

    private lateinit var sharedPreferenceManager: SharedPreferenceManager

    companion object {

        private val ACCOUNT_TYPE = "account_type"


        fun newIntent(context: Context, accountType: AccountType): Intent {
            val intent = Intent(context, LiteDashboardActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.putExtra(ACCOUNT_TYPE, accountType)
            return intent
        }
    }

    override val viewModel: ILiteDashboard.ViewModel
        get() = ViewModelProviders.of(this).get(LiteDashboardViewModel::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.logoutSuccess.observe(this, logoutSuccessObserver)
        sharedPreferenceManager = SharedPreferenceManager(this@LiteDashboardActivity)

        if (BiometricUtil.isFingerprintSupported
            && BiometricUtil.isHardwareSupported(this@LiteDashboardActivity)
            && BiometricUtil.isPermissionGranted(this@LiteDashboardActivity)
            && BiometricUtil.isFingerprintAvailable(this@LiteDashboardActivity)
        ) {
            val isTouchIdEnabled: Boolean =
                sharedPreferenceManager.getValueBoolien(SharedPreferenceManager.KEY_TOUCH_ID_ENABLED, false)
            swTouchId.isChecked = isTouchIdEnabled
            swTouchId.visibility = View.VISIBLE

            swTouchId.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    sharedPreferenceManager.save(SharedPreferenceManager.KEY_TOUCH_ID_ENABLED, true)
                } else {
                    sharedPreferenceManager.save(SharedPreferenceManager.KEY_TOUCH_ID_ENABLED, false)
                }
            }

        } else {
            swTouchId.visibility = View.INVISIBLE
        }


    }

    private val logoutSuccessObserver = Observer<Boolean> {

        val ACTION = "co.yap.app.OPEN_LOGIN"
        val intent = Intent()
        intent.action = ACTION
        startActivity(intent)
        sharedPreferenceManager.clearSharedPreference()
        finish()
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = co.yap.R.layout.activity_lite_dashboard

    override fun onDestroy() {
        super.onDestroy()
        viewModel.logoutSuccess.removeObserver(logoutSuccessObserver)
    }

}