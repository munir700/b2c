package co.yap.app.modules.startup.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import co.yap.app.BR
import co.yap.app.R
import co.yap.app.modules.login.activities.LogInActivity
import co.yap.modules.onboarding.activities.InformationErrorActivity
import co.yap.yapcore.defaults.DefaultActivity
import kotlinx.android.synthetic.main.activity_account_selection.*

// TODO: Remove this activity
@Deprecated("Use AccountSelectionFragment instead")
class AccountSelectionActivity : DefaultActivity() {

    private lateinit var viewDataBinding: ViewDataBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_account_selection)
        viewDataBinding.setVariable(BR.accountSelection, "")
        viewDataBinding.executePendingBindings()

        btnBusiness.setOnClickListener {
        }

        btnPersonal.setOnClickListener {
            //    startActivity(WelcomeActivity.newIntent(this, AccountType.B2C_ACCOUNT))
            startActivity(InformationErrorActivity.newIntent(context = this))
        }

        tvSignIn.setOnClickListener {
            startActivity(LogInActivity.newIntent(this))
            finish()
        }

    }
}
