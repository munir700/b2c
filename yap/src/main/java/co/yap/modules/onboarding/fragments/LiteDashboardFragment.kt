package co.yap.modules.onboarding.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.modules.kyc.activities.DocumentsDashboardActivity
import co.yap.modules.onboarding.activities.LiteDashboardActivity
import co.yap.modules.onboarding.interfaces.ILiteDashboard
import co.yap.modules.onboarding.viewmodels.LiteDashboardViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.biometric.BiometricUtil
import kotlinx.android.synthetic.main.fragment_lite_dashboard.*


class LiteDashboardFragment : BaseBindingFragment<ILiteDashboard.ViewModel>() {

    private lateinit var sharedPreferenceManager: SharedPreferenceManager

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = co.yap.R.layout.fragment_lite_dashboard

    override val viewModel: ILiteDashboard.ViewModel
        get() = ViewModelProviders.of(this).get(LiteDashboardViewModel::class.java)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.clickEvent.observe(this, observer)
        sharedPreferenceManager = SharedPreferenceManager(context as LiteDashboardActivity)

        if (BiometricUtil.isFingerprintSupported
            && BiometricUtil.isHardwareSupported(context as LiteDashboardActivity)
            && BiometricUtil.isPermissionGranted(context as LiteDashboardActivity)
            && BiometricUtil.isFingerprintAvailable(context as LiteDashboardActivity)
        ) {
            val isTouchIdEnabled: Boolean =
                sharedPreferenceManager.getValueBoolien(SharedPreferenceManager.KEY_TOUCH_ID_ENABLED, false)
            swTouchId.isChecked = isTouchIdEnabled
            swTouchId.visibility = View.VISIBLE

            swTouchId.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    sharedPreferenceManager.save(SharedPreferenceManager.KEY_IS_FINGERPRINT_PERMISSION_SHOWN, true)
                    sharedPreferenceManager.save(SharedPreferenceManager.KEY_TOUCH_ID_ENABLED, true)
                } else {
                    sharedPreferenceManager.save(SharedPreferenceManager.KEY_TOUCH_ID_ENABLED, false)
                }
            }
        } else {
            swTouchId.visibility = View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }

    private val observer = Observer<Int> {
        when (it) {
            viewModel.EVENT_LOGOUT_SUCCESS -> doLogout()
            viewModel.EVENT_PRESS_COMPLETE_VERIFICATION -> {
                // TODO: pass the correct name here
                findNavController().navigate(LiteDashboardFragmentDirections.actionLiteDashboardFragmentToDocumentsDashboardActivity("Bilal"))
                activity?.finish()
            }
        }
    }

    private fun doLogout() {
        val isFirstTimeUser: Boolean =
            sharedPreferenceManager.getValueBoolien(SharedPreferenceManager.KEY_IS_FIRST_TIME_USER, false)
        val isFingerprintPermissionShown: Boolean =
            sharedPreferenceManager.getValueBoolien(SharedPreferenceManager.KEY_IS_FINGERPRINT_PERMISSION_SHOWN, false)
        val uuid: String? = sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_APP_UUID)
        // val ACTION = "co.yap.app.OPEN_LOGIN"
        val intent = Intent("co.yap.app.OPEN_LOGIN")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        sharedPreferenceManager.clearSharedPreference()
        sharedPreferenceManager.save(SharedPreferenceManager.KEY_APP_UUID, uuid.toString())
        sharedPreferenceManager.save(
            SharedPreferenceManager.KEY_IS_FINGERPRINT_PERMISSION_SHOWN,
            isFingerprintPermissionShown
        )
        sharedPreferenceManager.save(SharedPreferenceManager.KEY_IS_FIRST_TIME_USER, isFirstTimeUser)
        activity?.finish()
    }

    fun showLogoutDialog() {
        AlertDialog.Builder(context as LiteDashboardActivity)
            .setTitle("Exit")
            .setMessage("Are you sure you want to exit?")
            .setPositiveButton("CONFIRM") { dialog, which ->
                activity?.finish()
            }
            .setNegativeButton("CANCEL") { dialog, which ->

            }
            .show()
    }

    override fun onBackPressed(): Boolean = showLogoutDialog().let { true }

}