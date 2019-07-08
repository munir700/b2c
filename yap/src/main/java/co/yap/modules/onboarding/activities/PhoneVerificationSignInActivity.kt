package co.yap.modules.onboarding.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.enums.AccountType
import co.yap.modules.onboarding.interfaces.IPhoneVerificationSignIn
import co.yap.modules.onboarding.viewmodels.PhoneVerificationSignInViewModel
import co.yap.yapcore.BaseBindingActivity

class PhoneVerificationSignInActivity : BaseBindingActivity<IPhoneVerificationSignIn.ViewModel>() {

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, PhoneVerificationSignInActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.nextButtonPressEvent.observe(this, nextButtonObserver)
    }

    override val viewModel: IPhoneVerificationSignIn.ViewModel
        get() = ViewModelProviders.of(this).get(PhoneVerificationSignInViewModel::class.java)

    private val nextButtonObserver = Observer<Boolean> { showToast("Send pressed") }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_phone_verification


    override fun onDestroy() {
        super.onDestroy()
        viewModel.nextButtonPressEvent.removeObserver(nextButtonObserver)
    }

}