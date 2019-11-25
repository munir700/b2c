package co.yap.modules.dashboard.yapit.topup.landing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.dashboard.more.bankdetails.activities.BankDetailActivity
import co.yap.modules.dashboard.yapit.topup.cardslisting.TopUpBeneficiariesActivity
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingActivity


class TopUpLandingActivity : BaseBindingActivity<ITopUpLanding.ViewModel>() {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, TopUpLandingActivity::class.java)
        }
    }


    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.activity_topup_landing
    override val viewModel: ITopUpLanding.ViewModel
        get() = ViewModelProviders.of(this).get(
            TopUpLandingViewModel::class.java
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.clickEvent.observe(this, clickEventObserver)
    }

    private val clickEventObserver = Observer<Int> {
        when (it) {
            R.id.llBankTransferType -> {
                startActivity(BankDetailActivity.newIntent(this))
            }
            R.id.llCardsTransferType -> {
                startActivity(TopUpBeneficiariesActivity.newIntent(this))
            }
            R.id.tbBtnBack -> {
                onBackPressed()
            }
        }
    }

}