package co.yap.app.modules.accountselection.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import co.yap.app.BR
import co.yap.app.R
import co.yap.app.di.BaseActivity
import co.yap.app.modules.login.activities.BiometricPermissionActivity
import co.yap.app.modules.login.activities.LogInActivity
import co.yap.modules.onboarding.activities.WelcomeActivity
import co.yap.modules.onboarding.enums.AccountType
import co.yap.yapcore.IBase
import co.yap.yapcore.defaults.DefaultActivity
import co.yap.yapcore.defaults.IDefault
import kotlinx.android.synthetic.main.activity_account_selection.*


class AccountSelectionActivity : DefaultActivity() {

    private lateinit var viewDataBinding: ViewDataBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_account_selection)
        viewDataBinding.setVariable(BR.accountSelection, "")
        viewDataBinding.executePendingBindings()

        btnBusiness.setOnClickListener {
            startActivity(WelcomeActivity.newIntent(this, AccountType.B2B))
        }

        btnPersonal.setOnClickListener {
            startActivity(WelcomeActivity.newIntent(this, AccountType.B2C))
        }

        tvSignIn.setOnClickListener {
            startActivity(LogInActivity.newIntent(this))
            // startActivity(BiometricPermissionActivity.newIntent(this,"Touchid"))

        }
    }
}
