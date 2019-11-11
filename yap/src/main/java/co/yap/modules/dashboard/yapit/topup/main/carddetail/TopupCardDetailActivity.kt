package co.yap.modules.dashboard.yapit.topup.main.carddetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingActivity

class TopupCardDetailActivity : BaseBindingActivity<ITopUpCardDetail.ViewModel>() {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, TopupCardDetailActivity::class.java)
        }
    }
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.activity_topup_card_detail
    override val viewModel: ITopUpCardDetail.ViewModel
        get() = ViewModelProviders.of(this).get(TopUpCardDetailViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        setupView()
    }

    private fun setupView() {
        //TODO: set these values from Selected Card
        viewModel.state.cardNickname.set("Citi Bank Card")
        viewModel.state.cardNo.set("Citi Bank *8765")
        viewModel.state.cardType.set("Visa")
        viewModel.state.cardExpiry.set("12/2021")
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, clickEventObserver)
    }

    private val clickEventObserver = Observer<Int> {
        when (it) {
            R.id.IvClose -> finish()
            R.id.tvRemoveCard -> viewModel.onRemoveCard()
        }
    }
}