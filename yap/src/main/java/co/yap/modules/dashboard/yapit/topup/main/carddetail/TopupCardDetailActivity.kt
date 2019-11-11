package co.yap.modules.dashboard.yapit.topup.main.carddetail

import android.os.Bundle
import android.os.PersistableBundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingActivity

class TopupCardDetailActivity : BaseBindingActivity<ITopUpCardDetail.ViewModel>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.activity_topup_card_detail
    override val viewModel: ITopUpCardDetail.ViewModel
        get() = ViewModelProviders.of(this).get(TopUpCardDetailViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setObservers()
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, clickEventObserver)
    }

    private val clickEventObserver = Observer<Int> {
        when (it) {

        }
    }
}