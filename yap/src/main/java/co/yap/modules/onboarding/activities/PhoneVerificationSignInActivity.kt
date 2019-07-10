package co.yap.modules.onboarding.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.enums.AccountType
import co.yap.modules.onboarding.interfaces.IPhoneVerificationSignIn
import co.yap.modules.onboarding.viewmodels.PhoneVerificationSignInViewModel
import co.yap.yapcore.BaseBindingActivity
import kotlinx.android.synthetic.main.activity_phone_verification.*

class PhoneVerificationSignInActivity : BaseBindingActivity<IPhoneVerificationSignIn.ViewModel>() {

    companion object {

        private val PASSCODE = "passcode"
        private val USERNAME = "username"

        fun newIntent(context: Context, passcode: String, username: String): Intent {
            val intent = Intent(context, PhoneVerificationSignInActivity::class.java)
            intent.putExtra(PASSCODE, passcode)
            intent.putExtra(USERNAME, username)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.nextButtonPressEvent.observe(this, nextButtonObserver)
        viewModel.verifyOtpResult.observe(this, verifyOtpResultObserver)
        viewModel.postDemographicDataResult.observe(this, postDemographicDataObserver)

        setUsername()
        setPasscode()
    }

    override val viewModel: IPhoneVerificationSignIn.ViewModel
        get() = ViewModelProviders.of(this).get(PhoneVerificationSignInViewModel::class.java)

    private val nextButtonObserver = Observer<Boolean> {
        viewModel.verifyOtp()
    }

    private val verifyOtpResultObserver = Observer<Boolean> {
        viewModel.postDemographicData()
    }

    private val postDemographicDataObserver = Observer<Boolean> {
        startActivity(LiteDashboardActivity.newIntent(this@PhoneVerificationSignInActivity, AccountType.B2C_ACCOUNT))
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_phone_verification


    override fun onDestroy() {
        super.onDestroy()
        viewModel.nextButtonPressEvent.removeObserver(nextButtonObserver)
    }

    private fun setUsername() {
        viewModel.state.username = intent.getSerializableExtra(USERNAME) as String
    }

    private fun setPasscode() {
        viewModel.state.passcode = intent.getSerializableExtra(PASSCODE) as String
    }

    /*fun changeResendColor(disable:Boolean) {
        if (disable){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvResendOtp.setTextColor(resources.getColor(R.color.greyDark, null))
            } else {
                tvResendOtp.setTextColor(resources.getColor(R.color.greyDark))
            }
        }else if (!disable){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvResendOtp.setTextColor(resources.getColor(R.color.colorPrimary, null))
            } else {
                tvResendOtp.setTextColor(resources.getColor(R.color.colorPrimary))
            }
        }

    }*/
}