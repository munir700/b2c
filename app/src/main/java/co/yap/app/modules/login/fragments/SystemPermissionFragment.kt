package co.yap.app.modules.login.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.app.BR
import co.yap.app.R
import co.yap.app.constants.Constants
import co.yap.app.modules.login.interfaces.ISystemPermission
import co.yap.app.modules.login.viewmodels.SystemPermissionViewModel
import co.yap.household.onboarding.main.OnBoardingHouseHoldActivity
import co.yap.modules.dashboard.main.activities.YapDashboardActivity
import co.yap.modules.webview.WebViewFragment
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.RequestCodes.REQUEST_NOTIFICATION_SETTINGS
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_ID
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_START_DESTINATION_ID
import co.yap.yapcore.dagger.base.navigation.host.NavHostPresenterActivity
import co.yap.yapcore.enums.YAPThemes
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.helpers.extentions.switchTheme
import co.yap.yapcore.helpers.livedata.SwitchProfileLiveData
import co.yap.yapcore.managers.SessionManager

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SystemPermissionFragment : BaseBindingFragment<ISystemPermission.ViewModel>(),
    ISystemPermission.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_biometric_permission

    override val viewModel: ISystemPermission.ViewModel
        get() = ViewModelProviders.of(this).get(SystemPermissionViewModel::class.java)

    private fun getScreenType(): String {
        return arguments?.let { SystemPermissionFragmentArgs.fromBundle(it).screenType } as String
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.screenType = getScreenType()
        viewModel.registerLifecycleOwner(this)
        setObservers()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObserver(clickObserver)
    }

    private val clickObserver = Observer<Int> { view ->
        when (view) {
            R.id.tvTermsAndConditions -> {
                startFragment(
                    fragmentName = WebViewFragment::class.java.name, bundle = bundleOf(
                        co.yap.yapcore.constants.Constants.PAGE_URL to co.yap.yapcore.constants.Constants.URL_TERMS_CONDITION
                    ), showToolBar = false
                )
            }
            R.id.tvNoThanks -> {
                grantPermissions(false)
            }
            R.id.btnTouchId -> {
                grantPermissions(true)
            }

        }
    }

    private fun grantPermissions(isGranted: Boolean) {
        when (viewModel.screenType) {
            Constants.TOUCH_ID_SCREEN_TYPE -> {
                viewModel.getTouchScreenValues(isGranted)
                val action =
                    SystemPermissionFragmentDirections.actionSystemPermissionFragmentToSystemPermissionFragmentNotification(
                        Constants.NOTIFICATION_SCREEN_TYPE
                    )
                findNavController().navigate(action)
            }
            Constants.NOTIFICATION_SCREEN_TYPE -> {
                if (isGranted) navigateToNotificationSettings() else {
                    navigateToDashboard()
                    viewModel.getNotificationScreenValues(isGranted)
                }

            }
        }
    }

    private fun navigateToNotificationSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                val intent: Intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                    .putExtra(Settings.EXTRA_APP_PACKAGE, requireContext().packageName)
                if (intent.resolveActivity(requireContext().packageManager) != null)
                    startActivityForResult(intent, REQUEST_NOTIFICATION_SETTINGS)
            } catch (e: ActivityNotFoundException) {
            }
        }
    }

    override fun onDestroyView() {
        removeObservers()
        super.onDestroyView()

    }

    private fun navigateToDashboard() {
        if (SessionManager.user?.otpBlocked == true || SessionManager.user?.freezeInitiator != null)
            startFragment(
                fragmentName = OtpBlockedInfoFragment::class.java.name,
                clearAllPrevious = true
            )
        else {
            if (SessionManager.shouldGoToHousehold()) {
                SessionManager.user?.uuid?.let { it1 ->
                    SwitchProfileLiveData.get(it1, this@SystemPermissionFragment)
                        .observe(this@SystemPermissionFragment, switchProfileObserver)
                }
            } else {
                launchActivity<YapDashboardActivity>(clearPrevious = true)
            }
        }
    }

    private val switchProfileObserver = Observer<AccountInfo?> {
        it.run {
            if (SessionManager.isOnBoarded(SessionManager.user)) {
                if (SessionManager.isExistingUser()) {
                    launchActivity<YapDashboardActivity>(clearPrevious = true)
                } else {
                    context.switchTheme(YAPThemes.HOUSEHOLD())
                    launchActivity<NavHostPresenterActivity>(clearPrevious = true) {
                        putExtra(NAVIGATION_Graph_ID, R.navigation.hh_main_nav_graph)
                        putExtra(
                            NAVIGATION_Graph_START_DESTINATION_ID,
                            R.id.householdDashboardFragment
                        )
                    }
                }
            } else {
                context.switchTheme(YAPThemes.HOUSEHOLD())
//                SessionManager.user?.notificationStatuses = AccountStatus.PARNET_MOBILE_VERIFICATION_PENDING.name
                launchActivity<OnBoardingHouseHoldActivity>(clearPrevious = true) {
                    putExtra(NAVIGATION_Graph_ID, R.navigation.hh_new_user_onboarding_navigation)
                    putExtra(
                        NAVIGATION_Graph_START_DESTINATION_ID,
                        R.id.HHOnBoardingWelcomeFragment
                    )
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_NOTIFICATION_SETTINGS) {
            if (NotificationManagerCompat.from(requireContext())
                    .areNotificationsEnabled()
            ) viewModel.getNotificationScreenValues(true) else viewModel.getNotificationScreenValues(
                false
            )
            navigateToDashboard()
        }
    }
}
