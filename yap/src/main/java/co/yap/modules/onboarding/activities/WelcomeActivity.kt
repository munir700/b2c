package co.yap.modules.onboarding.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import co.yap.R
import co.yap.modules.onboarding.adapters.WelcomePagerAdapter
import co.yap.modules.onboarding.enums.AccountType
import co.yap.modules.onboarding.interfaces.IWelcome
import co.yap.modules.onboarding.viewmodels.WelcomeViewModel
import co.yap.yapcore.BaseActivity
import co.yap.yapcore.BaseBindingActivity
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class WelcomeActivity : BaseBindingActivity<IWelcome.ViewModel>(), IWelcome.View {
    companion object {

        private val ACCOUNT_TYPE = "account_type"

        fun newIntent(context: Context, accountType: AccountType): Intent {
            val intent = Intent(context, WelcomeActivity::class.java)
            intent.putExtra(ACCOUNT_TYPE, accountType)
            return intent
        }
    }

    override fun getBindingVariable(): Int = 0

    override fun getLayoutId(): Int = R.layout.screen_onboarding_welcome

    override val viewModel: IWelcome.ViewModel
        get() = ViewModelProviders.of(this).get(WelcomeViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.accountType = getAccountType()
        val pager = findViewById<ViewPager>(R.id.welcome_pager)
        pager.adapter = WelcomePagerAdapter(
            context = this,
            contents = viewModel.pages,
            layout = R.layout.content_onboarding_welcome
        )

        val wormDotsIndicator = findViewById<WormDotsIndicator>(R.id.worm_dots_indicator)
        wormDotsIndicator.setViewPager(pager)
    }

    private fun getAccountType(): AccountType {
        return intent.getSerializableExtra(ACCOUNT_TYPE) as AccountType
    }

}