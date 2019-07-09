package co.yap.app.modules.accountselection.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import co.yap.app.BR
import co.yap.app.R
import co.yap.app.modules.login.activities.LogInActivity
import co.yap.app.modules.login.activities.VerifyPasscodeActivity
import co.yap.modules.onboarding.activities.CreatePasscodeActivity
import co.yap.modules.onboarding.activities.OnboardingActivity
import co.yap.modules.onboarding.activities.WelcomeActivity
import co.yap.modules.onboarding.enums.AccountType
import co.yap.yapcore.defaults.DefaultActivity
import kotlinx.android.synthetic.main.activity_account_selection.*


class AccountSelectionActivity : DefaultActivity() {

    private lateinit var viewDataBinding: ViewDataBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_account_selection)
        viewDataBinding.setVariable(BR.accountSelection, "")
        viewDataBinding.executePendingBindings()

        btnBusiness.setOnClickListener {
            //            startActivity(WelcomeActivity.newIntent(this, AccountType.B2B))
            startActivity(OnboardingActivity.newIntent(this, AccountType.B2B_ACCOUNT))
        }

        btnPersonal.setOnClickListener {
            startActivity(WelcomeActivity.newIntent(this, AccountType.B2C_ACCOUNT))
        }

        tvSignIn.setOnClickListener {
//            startActivity(LogInActivity.newIntent(this))
            startActivity(CreatePasscodeActivity.newIntent(this))
//            startActivity(BiometricPermissionActivity.newIntent(this,"Touchid"))
        }
    }
}
