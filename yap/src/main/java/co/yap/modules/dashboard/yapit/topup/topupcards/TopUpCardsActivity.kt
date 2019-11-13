package co.yap.modules.dashboard.yapit.topup.topupcards

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.ActivityTopupCardsBinding
import co.yap.yapcore.BaseBindingActivity
import com.yarolegovich.discretescrollview.transform.ScaleTransformer

class TopUpCardsActivity : BaseBindingActivity<ITopUpCards.ViewModel>() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, TopUpCardsActivity::class.java)
        }
    }

    private lateinit var mAdapter: TopUpCardsAdapter
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.activity_topup_cards
    override val viewModel: ITopUpCards.ViewModel
        get() = ViewModelProviders.of(this).get(
            TopUpCardsViewModel::class.java
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObservers()
        setupCards()
    }

    private fun setupCards() {
        mAdapter = TopUpCardsAdapter(this, mutableListOf())
        getBinding().rvTopUpCards.setSlideOnFling(false)
        getBinding().rvTopUpCards.setOverScrollEnabled(true)
        getBinding().rvTopUpCards.adapter = mAdapter
        //getBinding().rvTopUpCards.addOnItemChangedListener(this)
        //getBinding().rvTopUpCards.addScrollStateChangeListener(this)
        getBinding().rvTopUpCards.smoothScrollToPosition(0)
        getBinding().rvTopUpCards.setItemTransitionTimeMillis(100)
        getBinding().rvTopUpCards.setItemTransformer(
            ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build()
        )
    }

    private fun addObservers() {
        viewModel.clickEvent.observe(this, clickEventObserver)
        viewModel.topUpCards.observe(this, Observer {

        })
    }

    private val clickEventObserver = Observer<Int> {
        when (it) {
            R.id.llBankTransferType -> {

            }
            R.id.llCardsTransferType -> {

            }
        }
    }

    fun getBinding(): ActivityTopupCardsBinding {
        return viewDataBinding as ActivityTopupCardsBinding
    }

}