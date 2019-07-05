package co.yap.modules.onboarding.activities

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.enums.AccountType
import co.yap.modules.onboarding.interfaces.ILiteDashboard
import co.yap.modules.onboarding.viewmodels.LiteDashboardViewModel
import co.yap.yapcore.BaseBindingActivity

class LiteDashboardActivity : BaseBindingActivity<ILiteDashboard.ViewModel>() {
    companion object {

        private val ACCOUNT_TYPE = "account_type"

        fun newIntent(context: Context, accountType: AccountType): Intent {
            val intent = Intent(context, LiteDashboardActivity::class.java)
            intent.putExtra(ACCOUNT_TYPE, accountType)
            return intent
        }
    }

    override val viewModel: ILiteDashboard.ViewModel
        get() = ViewModelProviders.of(this).get(LiteDashboardViewModel::class.java)


    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_lite_dashboard

}