package co.yap.modules.onboarding.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.modules.onboarding.constants.Constants
import co.yap.modules.onboarding.interfaces.ILiteDashboard
import co.yap.modules.onboarding.viewmodels.LiteDashboardViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.helpers.AuthUtils
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.biometric.BiometricUtil
import co.yap.yapcore.managers.MyUserManager
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
        sharedPreferenceManager = SharedPreferenceManager(requireContext())

        if (BiometricUtil.isFingerprintSupported
            && BiometricUtil.isHardwareSupported(requireContext())
            && BiometricUtil.isPermissionGranted(requireContext())
            && BiometricUtil.isFingerprintAvailable(requireContext())
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
            viewModel.EVENT_GET_DEBIT_CARDS_SUCCESS -> {
                findNavController().navigate(LiteDashboardFragmentDirections.actionLiteDashboardFragmentToSetCardPinWelcomeActivity())
            }
            viewModel.EVENT_PRESS_COMPLETE_VERIFICATION -> {
                findNavController().navigate(
                    LiteDashboardFragmentDirections.actionLiteDashboardFragmentToDocumentsDashboardActivity(
                        MyUserManager.user?.customer?.firstName.toString()
                    )
                )
                activity?.finish()
            }
            viewModel.EVENT_PRESS_SET_CARD_PIN -> {
                viewModel.getDebitCards()
            }
            viewModel.EVENT_GET_ACCOUNT_INFO_SUCCESS -> {
                checkUserStatus()
            }
        }
    }


    private fun checkUserStatus() {
        // MyUserManager.user?.notificationStatuses = Constants.USER_STATUS_ON_BOARDED
        when (MyUserManager.user?.notificationStatuses) {
            Constants.USER_STATUS_ON_BOARDED -> {
                btnCompleteVerification.visibility = View.VISIBLE
                btnSetCardPin.visibility = View.GONE
            }
            Constants.USER_STATUS_MEETING_SUCCESS -> {
                btnSetCardPin.visibility = View.VISIBLE
                btnCompleteVerification.visibility = View.GONE
            }
            Constants.USER_STATUS_MEETING_SCHEDULED -> {
                btnSetCardPin.visibility = View.GONE
                btnCompleteVerification.visibility = View.GONE
            }
            Constants.USER_STATUS_CARD_ACTIVATED -> {
                btnSetCardPin.visibility = View.GONE
                btnCompleteVerification.visibility = View.GONE
            }
        }
    }

    private fun doLogout() {
        AuthUtils.navigateToHardLogin(requireContext())
        activity?.finish()
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext())
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

    override fun onResume() {
        super.onResume()

        if (Constants.USER_STATUS_CARD_ACTIVATED == MyUserManager.user?.notificationStatuses) {
            btnSetCardPin.visibility = View.GONE
            btnCompleteVerification.visibility = View.GONE
        }
    }
}