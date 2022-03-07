package co.yap.app.modules.login.fragments

import android.app.Activity
import android.content.Intent
import android.hardware.fingerprint.FingerprintManager
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.app.R
import co.yap.app.constants.Constants
import co.yap.app.main.MainActivity
import co.yap.app.main.MainChildFragment
import co.yap.app.modules.login.interfaces.IVerifyPasscode
import co.yap.app.modules.login.viewmodels.VerifyPasscodeViewModel
import co.yap.household.onboard.onboarding.main.OnBoardingHouseHoldActivity
import co.yap.modules.onboarding.enums.AccountType
import co.yap.modules.onboarding.fragments.WaitingListFragment
import co.yap.modules.others.helper.Constants.REQUEST_CODE
import co.yap.modules.otp.GenericOtpFragment
import co.yap.modules.otp.OtpDataModel
import co.yap.modules.reachonthetop.ReachedTopQueueFragment
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.networking.customers.responsedtos.AmendmentStatus
import co.yap.translation.Strings
import co.yap.widgets.NumberKeyboardListener
import co.yap.yapcore.constants.Constants.KEY_APP_UUID
import co.yap.yapcore.constants.Constants.KEY_IS_FINGERPRINT_PERMISSION_SHOWN
import co.yap.yapcore.constants.Constants.KEY_IS_USER_LOGGED_IN
import co.yap.yapcore.constants.Constants.KEY_TOUCH_ID_ENABLED
import co.yap.yapcore.constants.Constants.VERIFY_PASSCODE_FROM
import co.yap.yapcore.constants.Constants.VERIFY_PASS_CODE_BTN_TEXT
import co.yap.yapcore.enums.OTPActions
import co.yap.yapcore.enums.VerifyPassCodeEnum
import co.yap.yapcore.firebase.FirebaseEvent
import co.yap.yapcore.firebase.trackEventWithScreenName
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.TourGuideManager
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.biometric.BiometricCallback
import co.yap.yapcore.helpers.biometric.BiometricManagerX
import co.yap.yapcore.helpers.biometric.BiometricUtil
import co.yap.yapcore.helpers.extentions.*
import co.yap.yapcore.leanplum.SignInEvents
import co.yap.yapcore.leanplum.trackEvent
import co.yap.yapcore.managers.SessionManager
import kotlinx.android.synthetic.main.fragment_verify_passcode.*
import co.yap.modules.kyc.amendments.missinginfo.MissingInfoFragment

class VerifyPasscodeFragment : MainChildFragment<IVerifyPasscode.ViewModel>(), BiometricCallback,
    IVerifyPasscode.View, NumberKeyboardListener {

    private lateinit var mBiometricManagerX: BiometricManagerX
    private var shardPrefs: SharedPreferenceManager? = null
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_verify_passcode

    override val viewModel: VerifyPasscodeViewModel
        get() = ViewModelProvider(this).get(VerifyPasscodeViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shardPrefs = SharedPreferenceManager.getInstance(requireContext())
        dialer.hideFingerprintView()
        receiveData()
        updateUUID()
        bioMetricLogic()
        onbackPressLogic()
        dialer.setNumberKeyboardListener(this)
        dialer.upDatedDialerPad(viewModel.state.passcode)
        dialer.removeError()
    }

    private fun addObservers() {
        viewModel.onClickEvent.observe(this, onClickView)
        viewModel.loginSuccess.observe(this, loginSuccessObserver)
        viewModel.validateDeviceResult.observe(this, validateDeviceResultObserver)
        viewModel.accountInfo.observe(this, onFetchAccountInfo)
        viewModel.createOtpResult.observe(this, createOtpObserver)
    }

    private fun receiveData() {
        arguments?.let { it ->
            viewModel.state.username = VerifyPasscodeFragmentArgs.fromBundle(it).username
            if (VerifyPasscodeFragmentArgs.fromBundle(it).isAccountBlocked) {
                viewModel.showAccountBlockedError(getString(Strings.screen_verify_passcode_text_account_locked))
            }

            it.getString(VERIFY_PASS_CODE_BTN_TEXT)?.let {
                viewModel.state.btnVerifyPassCodeText = it
            }
            viewModel.state.verifyPassCodeEnum =
                it.getString(REQUEST_CODE, VerifyPassCodeEnum.ACCESS_ACCOUNT.name)
        }
    }

    private fun updateUUID() {
        shardPrefs?.getValueString(KEY_APP_UUID)?.let {
            viewModel.state.deviceId = it
        } ?: toast("Invalid UUID found")
    }

    private fun bioMetricLogic() {
        mBiometricManagerX = BiometricManagerX(
            requireContext(), mapOf(
                Pair(
                    FingerprintManager.FINGERPRINT_ERROR_HW_UNAVAILABLE,
                    getString(R.string.error_override_hw_unavailable)
                )
            )
        )

        val fingerprintsEnabled = mBiometricManagerX.hasFingerprintEnrolled()
        if (BiometricUtil.hasBioMetricFeature(requireContext()) && fingerprintsEnabled) {
            if (shardPrefs?.getValueBoolien(
                    KEY_TOUCH_ID_ENABLED,
                    false
                ) == true && shardPrefs?.getDecryptedPassCode() != null
            ) {
                dialer.showFingerprintView()
                showFingerprintDialog()
            } else {
                dialer.hideFingerprintView()
            }
        }
        dialer.setNumberKeyboardListener(object : NumberKeyboardListener {
            override fun onLeftButtonClicked() {
                showFingerprintDialog()
            }
        })
    }

    private fun onbackPressLogic() {
        ivBackBtn.setOnClickListener {
            if ((VerifyPassCodeEnum.valueOf(viewModel.state.verifyPassCodeEnum) == VerifyPassCodeEnum.VERIFY)) {
                activity?.onBackPressed()
            } else {
                viewModel.logout {
                    doLogout()
                }
            }
        }
    }

    private fun showFingerprintDialog() {
        mBiometricManagerX.showDialog(
            requireActivity(),
            BiometricManagerX.DialogStrings(title = getString(R.string.biometric_title))
        )
    }

    override fun onStart() {
        super.onStart()
        dialer.reset()
    }

    override fun onResume() {
        super.onResume()
        mBiometricManagerX.subscribe(this@VerifyPasscodeFragment)
    }

    override fun onPause() {
        super.onPause()
        mBiometricManagerX.unSubscribe()
    }

    private fun goToNext(name: String) {
        viewModel.createForgotPassCodeOtp {
            startOtpFragment(name)
        }
    }

    private fun startOtpFragment(name: String) {
        startFragmentForResult<GenericOtpFragment>(
            GenericOtpFragment::class.java.name,
            bundleOf(
                OtpDataModel::class.java.name to OtpDataModel(
                    otpAction = OTPActions.FORGOT_PASS_CODE.name,
                    mobileNumber = viewModel.mobileNumber,
                    username = name,
                    emailOtp = !Utils.isUsernameNumeric(name),
                    otpMessage = viewModel.otpMessage(OTPActions.FORGOT_PASS_CODE.name)
                )
            ),
            showToolBar = true
        ) { resultCode, data ->
            if (resultCode == Activity.RESULT_OK) {
                val token =
                    data?.getValue(
                        "token",
                        ExtraType.STRING.name
                    ) as? String
                viewModel.mobileNumber = (data?.getValue(
                    "mobile",
                    ExtraType.STRING.name
                ) as? String) ?: ""

                token?.let {

                    val action =
                        VerifyPasscodeFragmentDirections.actionVerifyPasscodeFragmentToForgotPasscodeNavigation(
                            viewModel.mobileNumber,
                            it,
                            "VERIFY_PASSCODE_FRAGMENT"
                        )
                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun doLogout() {
        activity?.let { SessionManager.doLogout(it, true) }
        if (activity is MainActivity) {
            (activity as MainActivity).onBackPressedDummy()
        } else {
            activity?.onBackPressed()
        }
    }

    override fun onDestroy() {
        viewModel.onClickEvent.removeObserver(onClickView)
        viewModel.loginSuccess.removeObservers(this)
        viewModel.validateDeviceResult.removeObservers(this)
        viewModel.createOtpResult.removeObservers(this)
        super.onDestroy()
    }

    private fun isUserLoginIn(): Boolean {
        return shardPrefs?.getValueBoolien(
            KEY_IS_USER_LOGGED_IN,
            false
        ) ?: false
    }

    private val onClickView = Observer<Int> {
        when (it) {
            R.id.btnVerifyPasscode -> {
                viewModel.isFingerprintLogin = false
                viewModel.state.passcode = dialer.getText()
                if (!isUserLoginIn()) {
                    setUsername()
                } else {
                    shardPrefs?.getDecryptedUserName()?.let {
                        viewModel.state.username = it
                    } ?: updateName()
                }
                if ((VerifyPassCodeEnum.valueOf(viewModel.state.verifyPassCodeEnum) == VerifyPassCodeEnum.VERIFY))
                    viewModel.verifyPasscode()
                else
                    viewModel.login()
            }
            R.id.tvForgotPassword -> {
                if (SessionManager.user?.otpBlocked == true) {
                    showToast(Utils.getOtpBlockedMessage(requireContext()))
                } else {
                    trackEventWithScreenName(if (viewModel.state.isAccountLocked.get() == true) FirebaseEvent.FORGOT_PWD_BLOCKED else FirebaseEvent.CLICK_FORGOT_PWD)
                    if (!isUserLoginIn()) {
                        goToNext(viewModel.state.username)
                    } else {
                        shardPrefs?.getDecryptedUserName()
                            ?.let { username ->
                                viewModel.state.username = username
                                goToNext(viewModel.state.username)
                            } ?: toast("Invalid user name")
                    }
                }
            }
        }
    }

    private fun updateName() {
        if (isUserLoginIn()) {
            viewModel.state.username = SessionManager.user?.currentCustomer?.email ?: ""
            return
        }

        viewModel.state.username = ""
    }

    private val loginSuccessObserver = Observer<Boolean> {
        if (it) {
            if (viewModel.isFingerprintLogin) {
                shardPrefs?.save(KEY_IS_USER_LOGGED_IN, true)
                navigateToDashboard()
            } else {
                if ((VerifyPassCodeEnum.valueOf(viewModel.state.verifyPassCodeEnum) == VerifyPassCodeEnum.VERIFY)) {
                    navigateToDashboard()
                }
            }
        } else {
            dialer.startAnimation()
        }
    }

    private val validateDeviceResultObserver = Observer<Boolean> {
        if (it) {
            navigateToDashboard()
        } else {
            viewModel.createOtp()
        }
    }

    private val onFetchAccountInfo = Observer<AccountInfo> {
        it?.run {
            trackEventWithScreenName(if (viewModel.isFingerprintLogin) FirebaseEvent.SIGN_IN_TOUCH else FirebaseEvent.SIGN_IN_PIN)
            if (!this.isWaiting) {
                if (this.iban.isNullOrBlank()) {
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
    }

    private fun getCardAndTourInfo(accountInfo: AccountInfo?) {
        TourGuideManager.getTourGuides()
        SessionManager.getDebitCard { card ->
            SessionManager.setupDataSetForBlockedFeatures(card)
            SessionManager.updateCardBalance { }
            shardPrefs?.save(KEY_IS_USER_LOGGED_IN, true)
            if (shardPrefs?.getValueBoolien(
                    KEY_IS_FINGERPRINT_PERMISSION_SHOWN,
                    false
                ) != true
            ) {
                if (BiometricUtil.hasBioMetricFeature(requireContext())) {
                    val action =
                        VerifyPasscodeFragmentDirections.actionVerifyPasscodeFragmentToSystemPermissionFragment(
                            Constants.TOUCH_ID_SCREEN_TYPE
                        )
                    navigate(action)
                    shardPrefs?.save(
                        KEY_IS_FINGERPRINT_PERMISSION_SHOWN,
                        true
                    )
                } else {
                    shardPrefs?.save(
                        KEY_IS_FINGERPRINT_PERMISSION_SHOWN,
                        true
                    )
                    val action =
                        VerifyPasscodeFragmentDirections.actionVerifyPasscodeFragmentToSystemPermissionFragment(
                            Constants.NOTIFICATION_SCREEN_TYPE
                        )
                    navigate(action)
                }
            } else {
                if (accountInfo?.accountType == AccountType.B2C_HOUSEHOLD.name) {
                    SharedPreferenceManager.getInstance(requireContext())
                        .setThemeValue(co.yap.yapcore.constants.Constants.THEME_HOUSEHOLD)
                    val bundle = Bundle()
                    bundle.putBoolean(OnBoardingHouseHoldActivity.EXISTING_USER, false)
                    bundle.putParcelable(OnBoardingHouseHoldActivity.USER_INFO, accountInfo)
                    startActivity(
                        OnBoardingHouseHoldActivity.getIntent(
                            requireContext(),
                            bundle
                        )
                    )
                    activity?.finish()
                } else {
                    if (accountInfo?.otpBlocked == true || SessionManager.user?.freezeInitiator != null)
                        startFragment(fragmentName = OtpBlockedInfoFragment::class.java.name)
                    else {
                        activity?.let {
                            SharedPreferenceManager.getInstance(it.applicationContext)
                                .getValueString(KEY_APP_UUID)?.apply {
                                    SessionManager.sendFcmTokenToServer(this)
                                }
                            // launching missing info screen
                            if (AmendmentStatus.SUBMIT_TO_CUSTOMER.name == accountInfo?.amendmentStatus) {
                                startFragment(
                                    fragmentName = MissingInfoFragment::class.java.name
                                )
                            } else {
                                navigate(R.id.action_goto_yapDashboardActivity)
                            }
                        }
                    }
                    activity?.finish()

                }
            }
        }
    }

    private val createOtpObserver = Observer<Boolean> {
        val action =
            VerifyPasscodeFragmentDirections.actionVerifyPasscodeFragmentToPhoneVerificationSignInFragment(
                viewModel.state.username,
                viewModel.state.passcode
            )
        navigate(action)
    }

    private fun setUsername() {
        viewModel.state.username =
            arguments?.let { VerifyPasscodeFragmentArgs.fromBundle(it).username } as String
    }

    private fun navigateToDashboard() {
        trackEvent(SignInEvents.SIGN_IN.type)
        if ((VerifyPassCodeEnum.valueOf(viewModel.state.verifyPassCodeEnum) == VerifyPassCodeEnum.VERIFY)) {
            val intent = Intent()
            intent.putExtra("CheckResult", true)
            activity?.setResult(Activity.RESULT_OK, intent)
            activity?.finish()
        } else {
            viewModel.getAccountInfo()
        }
    }

    override fun onBackPressed(): Boolean {
        return true
    }

    override fun onBiometricAuthenticationInternalError(error: String) {
        showToast(error)
    }

    override fun onAuthenticationSuccessful() {
        viewModel.isFingerprintLogin = true

        shardPrefs?.getDecryptedPassCode()?.let { passedCode ->
            viewModel.state.passcode = passedCode
            dialer.upDatedDialerPad(viewModel.state.passcode)
        }

        shardPrefs?.getDecryptedUserName()
            ?.let { encryptedUserName ->
                viewModel.state.username = encryptedUserName
            }

        if (!viewModel.state.username.isNullOrEmpty() && !viewModel.state.passcode.isNullOrEmpty()) {
            if ((VerifyPassCodeEnum.valueOf(viewModel.state.verifyPassCodeEnum) == VerifyPassCodeEnum.VERIFY))
                viewModel.verifyPasscode()
            else
                viewModel.login()
        }
    }

    override fun onNumberClicked(number: Int, text: String) {
        viewModel.state.passcode = dialer.getText()
    }

    override fun onLeftButtonClicked() {
        super.onLeftButtonClicked()
        showFingerprintDialog()
    }
}


