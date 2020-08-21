package co.yap.app.modules.login.fragments

import android.os.Bundle
import android.view.View
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
import co.yap.yapcore.constants.Constants.KEY_TOUCH_ID_ENABLED
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_ID
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_START_DESTINATION_ID
import co.yap.yapcore.dagger.base.navigation.host.NavHostPresenterActivity
import co.yap.yapcore.enums.AccountStatus
import co.yap.yapcore.enums.YAPThemes
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.helpers.extentions.switchTheme
import co.yap.yapcore.helpers.livedata.SwitchProfileLiveData
import co.yap.yapcore.leanplum.KYCEvents
import co.yap.yapcore.leanplum.trackEvent
import co.yap.yapcore.managers.MyUserManager

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SystemPermissionFragment : BaseBindingFragment<ISystemPermission.ViewModel>(),
    ISystemPermission.View {

    private lateinit var sharedPreferenceManager: SharedPreferenceManager

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_biometric_permission

    override val viewModel: ISystemPermission.ViewModel
        get() = ViewModelProviders.of(this).get(SystemPermissionViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferenceManager = SharedPreferenceManager.getInstance(requireContext())

        viewModel.screenType = getScreenType()
        viewModel.registerLifecycleOwner(this)

        viewModel.permissionGrantedPressEvent.observe(this, permissionGrantedObserver)
        viewModel.permissionNotGrantedPressEvent.observe(this, permissionNotGrantedObserver)
        viewModel.handlePressOnTermsAndConditionsPressEvent.observe(
            this,
            handlePressOnTermsAndConditionsObserver
        )
    }

    override fun onDestroyView() {
        viewModel.permissionGrantedPressEvent.removeObservers(this)
        viewModel.permissionNotGrantedPressEvent.removeObservers(this)
        super.onDestroyView()

    }

    private val permissionGrantedObserver = Observer<Boolean> {
        if (viewModel.screenType == Constants.TOUCH_ID_SCREEN_TYPE) {
            sharedPreferenceManager.save(KEY_TOUCH_ID_ENABLED, true)
            trackEvent(KYCEvents.SIGN_UP_ENABLED_PERMISSION.type,"TouchID")
            val action =
                SystemPermissionFragmentDirections.actionSystemPermissionFragmentToSystemPermissionFragmentNotification(
                    Constants.NOTIFICATION_SCREEN_TYPE
                )
            findNavController().navigate(action)
        } else {
            navigateToDashboard()
        }
    }

    private val permissionNotGrantedObserver = Observer<Boolean> {
        if (viewModel.screenType == Constants.TOUCH_ID_SCREEN_TYPE) {
            sharedPreferenceManager.save(KEY_TOUCH_ID_ENABLED, false)
            val action =
                SystemPermissionFragmentDirections.actionSystemPermissionFragmentToSystemPermissionFragmentNotification(
                    Constants.NOTIFICATION_SCREEN_TYPE
                )
            findNavController().navigate(action)
        } else {
            navigateToDashboard()
        }
    }

    private val handlePressOnTermsAndConditionsObserver = Observer<Int> {
        when (it) {
            R.id.tvTermsAndConditions -> {
                startFragment(
                    fragmentName = WebViewFragment::class.java.name, bundle = bundleOf(
                        co.yap.yapcore.constants.Constants.PAGE_URL to co.yap.yapcore.constants.Constants.URL_TERMS_CONDITION
                    ), showToolBar = true
                )
            }
        }
    }

    private fun navigateToDashboard() {
        if (MyUserManager.user?.otpBlocked == true)
            startFragment(fragmentName = OtpBlockedInfoFragment::class.java.name , clearAllPrevious = true)
        else {
            if (MyUserManager.shouldGoToHousehold()) {
                MyUserManager.user?.uuid?.let { it1 ->
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
            if (MyUserManager.isOnBoarded()) {
                if (MyUserManager.isExistingUser()) {
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
//                MyUserManager.user?.notificationStatuses = AccountStatus.PARNET_MOBILE_VERIFICATION_PENDING.name
                launchActivity<OnBoardingHouseHoldActivity>(clearPrevious = true) {
                    putExtra(NAVIGATION_Graph_ID, R.navigation.hh_new_user_onboarding_navigation)
                    putExtra(NAVIGATION_Graph_START_DESTINATION_ID, R.id.HHOnBoardingWelcomeFragment)
                }
            }
        }
    }


    private fun getScreenType(): String {
        return arguments?.let { SystemPermissionFragmentArgs.fromBundle(it).screenType } as String
    }

}
