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
import co.yap.app.databinding.ActivityPhoneVerificationBinding
import co.yap.app.main.MainChildFragment
import co.yap.app.modules.login.interfaces.IPhoneVerificationSignIn
import co.yap.app.modules.login.viewmodels.PhoneVerificationSignInViewModel
import co.yap.household.onboard.onboarding.main.OnBoardingHouseHoldActivity
import co.yap.modules.autoreadsms.MySMSBroadcastReceiver
import co.yap.modules.dashboard.main.activities.YapDashboardActivity
import co.yap.modules.kyc.amendments.missinginfo.MissingInfoFragment
import co.yap.modules.onboarding.enums.AccountType
import co.yap.modules.onboarding.fragments.WaitingListFragment
import co.yap.modules.reachonthetop.ReachedTopQueueFragment
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.networking.customers.responsedtos.AmendmentStatus
import co.yap.yapcore.constants.Constants.SMS_CONSENT_REQUEST
import co.yap.yapcore.constants.Constants.TOUCH_ID_SCREEN_TYPE
import co.yap.yapcore.firebase.FirebaseEvent
import co.yap.yapcore.firebase.trackEventWithScreenName
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.TourGuideManager
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.biometric.BiometricUtil
import co.yap.yapcore.helpers.extentions.getOtpFromMessage
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.helpers.extentions.startSmsConsent
import co.yap.yapcore.leanplum.SignInEvents
import co.yap.yapcore.leanplum.trackEvent
import co.yap.yapcore.managers.SessionManager
import com.google.android.gms.auth.api.phone.SmsRetriever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PhoneVerificationSignInFragment :
    MainChildFragment<ActivityPhoneVerificationBinding , IPhoneVerificationSignIn.ViewModel>(), IPhoneVerificationSignIn.View {
    private var intentFilter: IntentFilter? = null
    private var appSMSBroadcastReceiver: MySMSBroadcastReceiver? = null
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_phone_verification

    override val viewModel: PhoneVerificationSignInViewModel
        get() = ViewModelProviders.of(this).get(PhoneVerificationSignInViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        viewModel.setFeatureFlagCall()
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
        requireContext().registerReceiver(
            appSMSBroadcastReceiver,
            intentFilter,
            SmsRetriever.SEND_PERMISSION,
            null
        )
        viewModel.clickEvent.observe(this, Observer {
            viewModel.verifyOtp()
        })
        viewModel.postDemographicDataResult.observe(this, postDemographicDataObserver)
        viewModel.accountInfo.observe(this, onFetchAccountInfo)
    }

    private fun initBroadcast() {
        intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        appSMSBroadcastReceiver =
            MySMSBroadcastReceiver(object : MySMSBroadcastReceiver.OnSmsReceiveListener {
                override fun onReceive(code: Intent?) {
                    code?.let {
                        it.resolveActivity(requireContext().packageManager)?.run {
                            if (packageName == "com.google.android.gms" && className == "com.google.android.gms.auth.api.phone.ui.UserConsentPromptActivity"
                            )
                                startActivityForResult(it, SMS_CONSENT_REQUEST)
                        }
                    }
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
                SessionManager.setupDataSetForBlockedFeatures(card)
                SessionManager.updateCardBalance { }
                if (accountType == AccountType.B2C_HOUSEHOLD.name) {
                    val bundle = Bundle()
                    SharedPreferenceManager.getInstance(requireContext())
                        .setThemeValue(co.yap.yapcore.constants.Constants.THEME_HOUSEHOLD)
                    bundle.putBoolean(OnBoardingHouseHoldActivity.EXISTING_USER, false)
                    bundle.putParcelable(OnBoardingHouseHoldActivity.USER_INFO, accountInfo)
                    startActivity(OnBoardingHouseHoldActivity.getIntent(requireContext(), bundle))
                    activity?.finish()
                } else {
                    if (BiometricUtil.hasBioMetricFeature(requireActivity())
                    ) {
                        SharedPreferenceManager.getInstance(requireContext()).save(
                            co.yap.yapcore.constants.Constants.KEY_IS_FINGERPRINT_PERMISSION_SHOWN,
                            true
                        )
                        if (SharedPreferenceManager.getInstance(requireContext()).getValueBoolien(
                                co.yap.yapcore.constants.Constants.KEY_TOUCH_ID_ENABLED,
                                false
                            )
                        ) {
                            if (accountInfo.otpBlocked == true || SessionManager.user?.freezeInitiator != null)
                                startFragment(fragmentName = OtpBlockedInfoFragment::class.java.name)
                            else {
                                activity?.let {
                                    SharedPreferenceManager.getInstance(it.applicationContext)
                                        .getValueString(co.yap.yapcore.constants.Constants.KEY_APP_UUID)?.apply {
                                            SessionManager.sendFcmTokenToServer(this)
                                        }
                                }
                                if (!this.isWaiting) {
                                    if (this.iban.isNullOrBlank()) {
                                        startFragment(
                                            fragmentName = ReachedTopQueueFragment::class.java.name,
                                            clearAllPrevious = true
                                        )

                                    } else {
                                        moveNext(this.amendmentStatus)
                                    }
                                } else {
                                    startFragment(
                                        fragmentName = WaitingListFragment::class.java.name,
                                        clearAllPrevious = true
                                    )
                                }

                            }
                            activity?.finish()
                        } else {
                            val action =
                                PhoneVerificationSignInFragmentDirections.actionPhoneVerificationSignInFragmentToSystemPermissionFragment(
                                    TOUCH_ID_SCREEN_TYPE
                                )
                            findNavController().navigate(action)
                        }

                    } else {
                        if (accountInfo.otpBlocked == true || SessionManager.user?.freezeInitiator != null) {
                            startFragment(fragmentName = OtpBlockedInfoFragment::class.java.name)
                        } else {
                            activity?.let {
                                SharedPreferenceManager.getInstance(it.applicationContext)
                                    .getValueString(co.yap.yapcore.constants.Constants.KEY_APP_UUID)?.apply {
                                        SessionManager.sendFcmTokenToServer(this)
                                    }
                            }
                            if (!this.isWaiting) {
                                if (this.iban.isNullOrBlank()) {
                                    startFragment(
                                        fragmentName = ReachedTopQueueFragment::class.java.name,
                                        clearAllPrevious = true
                                    )

                                } else {
                                    moveNext(this.amendmentStatus)
                                }
                            } else {
                                startFragment(
                                    fragmentName = WaitingListFragment::class.java.name,
                                    clearAllPrevious = true
                                )
                            }
                        }
                        activity?.finish()
                    }
                }
            }
        }
    }

    private fun moveNext(amendmentStatus: String?) {
        // launching missing info screen
        if (AmendmentStatus.SUBMIT_TO_CUSTOMER.name == amendmentStatus) {
            startFragment(
                fragmentName = MissingInfoFragment::class.java.name
            )
        } else {
            trackEvent(SignInEvents.SIGN_IN.type)
            launchActivity<YapDashboardActivity>()
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
                    data?.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE).also {
                        viewModel.state.otp.set(it?.getOtpFromMessage() ?: "")
                    }
                }
        }
    }

    override fun removeObservers() {
        viewModel.state.otp.removeOnPropertyChangedCallback(stateObserver)
        viewModel.postDemographicDataResult.removeObservers(this)
        viewModel.accountInfo.removeObservers(this)
        viewModel.clickEvent.removeObservers(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivLeftIcon -> {
                Utils.hideKeyboard(requireView())
                GlobalScope.launch(Dispatchers.Main) {
                    delay(100)
                    navigateBack()
                }
            }
        }
    }
}