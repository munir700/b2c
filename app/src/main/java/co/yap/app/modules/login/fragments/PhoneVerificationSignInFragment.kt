package co.yap.app.modules.login.fragments

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.app.R
import co.yap.app.constants.Constants
import co.yap.app.main.MainChildFragment
import co.yap.app.modules.login.interfaces.IPhoneVerificationSignIn
import co.yap.app.modules.login.viewmodels.PhoneVerificationSignInViewModel
import co.yap.household.onboarding.main.OnBoardingHouseHoldActivity
import co.yap.modules.autoreadsms.MySMSBroadcastReceiver
import co.yap.modules.dashboard.main.activities.YapDashboardActivity
import co.yap.modules.onboarding.fragments.WaitingListFragment
import co.yap.modules.reachonthetop.ReachedTopQueueFragment
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.yapcore.constants.Constants.SMS_CONSENT_REQUEST
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_ID
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_START_DESTINATION_ID
import co.yap.yapcore.dagger.base.navigation.host.NavHostPresenterActivity
import co.yap.yapcore.enums.YAPThemes
import co.yap.yapcore.firebase.FirebaseEvent
import co.yap.yapcore.firebase.trackEventWithScreenName
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.TourGuideManager
import co.yap.yapcore.helpers.biometric.BiometricUtil
import co.yap.yapcore.helpers.extentions.*
import co.yap.yapcore.helpers.livedata.SwitchProfileLiveData
import co.yap.yapcore.leanplum.SignInEvents
import co.yap.yapcore.leanplum.trackEvent
import co.yap.yapcore.managers.SessionManager
import com.google.android.gms.auth.api.phone.SmsRetriever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PhoneVerificationSignInFragment :
        MainChildFragment<IPhoneVerificationSignIn.ViewModel>(), IPhoneVerificationSignIn.View {
    private var intentFilter: IntentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
    private var appSMSBroadcastReceiver: MySMSBroadcastReceiver? = null
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_phone_verification

    override val viewModel: PhoneVerificationSignInViewModel
        get() = ViewModelProviders.of(this).get(PhoneVerificationSignInViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.reverseTimer(10, requireContext())
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        requireActivity().window.clearFlags(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        getData()
    }

    private val stateObserver = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            if (viewModel.isValidOtpLength(viewModel.state.otp.get() ?: "")) {
                viewModel.clickEvent.call()
            }
        }
    }

    override fun setObservers() {
        viewModel.state.otp.addOnPropertyChangedCallback(stateObserver)
        context?.startSmsConsent()
        initBroadcast()
        context?.registerReceiver(appSMSBroadcastReceiver, intentFilter)
        viewModel.clickEvent.observe(this, Observer {
            viewModel.verifyOtp()
        })
        viewModel.postDemographicDataResult.observe(this, postDemographicDataObserver)
        viewModel.accountInfo.observe(this, onFetchAccountInfo)
    }

    private fun initBroadcast() {
        appSMSBroadcastReceiver =
                MySMSBroadcastReceiver(object : MySMSBroadcastReceiver.OnSmsReceiveListener {
                    override fun onReceive(code: Intent?) {
                        startActivityForResult(code, SMS_CONSENT_REQUEST)
                    }
                })
    }

    private val postDemographicDataObserver = Observer<Boolean> {
        viewModel.getAccountInfo()
    }

    private val onFetchAccountInfo = Observer<AccountInfo> {
        if (!it.isWaiting) {
            if (it.iban.isNullOrBlank()) {
                startFragment(
                        fragmentName = ReachedTopQueueFragment::class.java.name,
                        clearAllPrevious = true
                )
            } else {
                getCardAndTourInfo(it)
            }
        } else {
            startFragment(
                    fragmentName = WaitingListFragment::class.java.name,
                    clearAllPrevious = true
            )
        }
    }

    private fun getCardAndTourInfo(accountInfo: AccountInfo?) {
        accountInfo?.run {
            trackEventWithScreenName(FirebaseEvent.SIGN_IN_PIN)
            TourGuideManager.getTourGuides()
            SessionManager.getDebitCard { card ->
                SessionManager.updateCardBalance { }
                if (SessionManager.shouldGoToHousehold()) {
                    SessionManager.user?.uuid?.let { it1 ->
                        SwitchProfileLiveData.get(it1, this@PhoneVerificationSignInFragment)
                                .observe(this@PhoneVerificationSignInFragment, switchProfileObserver)
                    }
                } else {
                    if (BiometricUtil.hasBioMetricFeature(requireActivity())
                    ) {
                        viewModel.parentViewModel?.shardPrefs?.save(
                                co.yap.yapcore.constants.Constants.KEY_IS_FINGERPRINT_PERMISSION_SHOWN,
                                true
                        )
                        if (SharedPreferenceManager(requireContext()).getValueBoolien(
                                        co.yap.yapcore.constants.Constants.KEY_TOUCH_ID_ENABLED,
                                        false
                                )
                        ) {
                            if (accountInfo.otpBlocked == true || SessionManager.user?.freezeInitiator != null)
                                startFragment(fragmentName = OtpBlockedInfoFragment::class.java.name, clearAllPrevious = true)
                            else {
                                SessionManager.sendFcmTokenToServer(requireContext()) {}
                                if (!this.isWaiting) {
                                    if (this.iban.isNullOrBlank()) {
                                        startFragment(
                                                fragmentName = ReachedTopQueueFragment::class.java.name,
                                                clearAllPrevious = true
                                        )

                                    } else {
                                        launchActivity<YapDashboardActivity>(clearPrevious = true)
                                    }
                                } else {
                                    startFragment(
                                            fragmentName = WaitingListFragment::class.java.name,
                                            clearAllPrevious = true
                                    )
                                }

                            }
                        } else {
                            val action =
                                    PhoneVerificationSignInFragmentDirections.actionPhoneVerificationSignInFragmentToSystemPermissionFragment(
                                            Constants.TOUCH_ID_SCREEN_TYPE
                                    )
                            findNavController().navigate(action)
                        }

                    } else {
                        if (accountInfo.otpBlocked == true || SessionManager.user?.freezeInitiator != null) {
                            startFragment(fragmentName = OtpBlockedInfoFragment::class.java.name, clearAllPrevious = true)
                        } else {
                            SessionManager.sendFcmTokenToServer(requireContext()) {}
                            if (!this.isWaiting) {
                                if (this.iban.isNullOrBlank()) {
                                    startFragment(
                                            fragmentName = ReachedTopQueueFragment::class.java.name,
                                            clearAllPrevious = true
                                    )

                                } else {
                                    trackEvent(SignInEvents.SIGN_IN.type)
                                    launchActivity<YapDashboardActivity>(clearPrevious = true)
                                }
                            } else {
                                startFragment(
                                        fragmentName = WaitingListFragment::class.java.name,
                                        clearAllPrevious = true
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private val switchProfileObserver = Observer<AccountInfo?> {
        it.run {
            if (SessionManager.isOnBoarded()) {
                gotoYapDashboard()
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
//                launchActivity<OnBoardingHouseHoldActivity>(clearPrevious = true) {
//                    putExtra(OnBoardingHouseHoldActivity.USER_INFO, SessionManager.user)
//                }
            }
        }
    }

    private fun gotoYapDashboard() {
        if (BiometricUtil.isFingerprintSupported
                && BiometricUtil.isHardwareSupported(requireActivity())
                && BiometricUtil.isPermissionGranted(requireActivity())
                && BiometricUtil.isFingerprintAvailable(requireActivity())
        ) {
            val action =
                    PhoneVerificationSignInFragmentDirections.actionPhoneVerificationSignInFragmentToSystemPermissionFragment(
                            Constants.TOUCH_ID_SCREEN_TYPE
                    )
            findNavController().navigate(action)
        } else {
            if (SessionManager.isExistingUser()) {
                launchActivity<YapDashboardActivity>(clearPrevious = true)
            } else {
                context.switchTheme(YAPThemes.HOUSEHOLD())
                launchActivity<NavHostPresenterActivity>(clearPrevious = true) {
                    putExtra(NAVIGATION_Graph_ID, R.navigation.hh_main_nav_graph)
                    putExtra(NAVIGATION_Graph_START_DESTINATION_ID, R.id.householdDashboardFragment)
                }
            }
        }
    }

    private fun getData() {
        viewModel.state.username =
                arguments?.let { PhoneVerificationSignInFragmentArgs.fromBundle(it).username } as String
        viewModel.state.passcode =
                arguments?.let { PhoneVerificationSignInFragmentArgs.fromBundle(it).passcode } as String
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            SMS_CONSENT_REQUEST ->
                if (resultCode == Activity.RESULT_OK) {
                    val message = data?.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                    viewModel.state.otp.set(context?.getOtpFromMessage(message ?: "") ?: "")
                }
        }
    }

    override fun removeObservers() {
        viewModel.state.otp.removeOnPropertyChangedCallback(stateObserver)
        viewModel.postDemographicDataResult.removeObservers(this)
        viewModel.accountInfo.removeObservers(this)
        viewModel.clickEvent.removeObservers(this)
        context?.unregisterReceiver(appSMSBroadcastReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivLeftIcon -> {
                requireView().hideKeyboard()
                GlobalScope.launch(Dispatchers.Main) {
                    delay(100)
                    navigateBack()
                }
            }
        }
    }
}