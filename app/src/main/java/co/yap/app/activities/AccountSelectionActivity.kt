package co.yap.app.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import co.yap.app.BR
import co.yap.app.R
import co.yap.app.di.BaseActivity
import co.yap.app.modules.login.activities.LogInActivity
import co.yap.modules.onboarding.activities.OnboardingActivity
import co.yap.modules.onboarding.activities.WelcomeActivity
import co.yap.modules.onboarding.enums.AccountType
import co.yap.yapcore.IBase
import kotlinx.android.synthetic.main.activity_account_selection.*


class AccountSelectionActivity : BaseActivity<IBase.ViewModel<IBase.State>>() {
    override val viewModel: IBase.ViewModel<IBase.State>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    private lateinit var viewDataBinding: ViewDataBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_account_selection)
        viewDataBinding.setVariable(BR.accountSelection, "")
        viewDataBinding.executePendingBindings()

        btnBusiness.setOnClickListener {
//            startActivity(WelcomeActivity.newIntent(this, AccountType.B2B))
            startActivity(OnboardingActivity.newIntent(this, AccountType.B2B))
        }

        btnPersonal.setOnClickListener {
            startActivity(WelcomeActivity.newIntent(this, AccountType.B2C))
        }

        tvSignIn.setOnClickListener {
            startActivity(LogInActivity.newIntent(this))

        }
    }
}
