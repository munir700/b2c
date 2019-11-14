package co.yap.modules.dashboard.yapit.topup.topupcards

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import co.yap.BR
import co.yap.R
import co.yap.databinding.ActivityTopupCardsBinding
import co.yap.modules.dashboard.yapit.topup.main.topupamount.activities.TopUpCardActivity
import co.yap.modules.dashboard.yapit.topup.topupcards.addtopupcard.activities.AddTopUpCardActivity
import co.yap.modules.others.helper.Constants
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.interfaces.OnItemClickListener
import com.yarolegovich.discretescrollview.transform.Pivot
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
        getBinding().rvTopUpCards.addOnItemChangedListener { viewHolder, adapterPosition ->
            updateSelection(viewHolder, adapterPosition)
        }
        mAdapter.allowFullItemClickListener = true
        mAdapter.setItemListener(listener)
        getBinding().rvTopUpCards.smoothScrollToPosition(0)
        getBinding().rvTopUpCards.setItemTransitionTimeMillis(100)
        getBinding().rvTopUpCards.setItemTransformer(
            ScaleTransformer.Builder()
                .setMaxScale(1.05f)
                .setMinScale(0.8f)
                .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                //.setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
                .build()
        )
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            showToast("List item $pos clicked")
            // startActivity(Intent(this, TopUpCardActivity::class.java))
            // on card item click
        }
    }

    private fun updateSelection(viewHolder: RecyclerView.ViewHolder?, adapterPosition: Int) {
        val item = mAdapter.getDataForPosition(adapterPosition)
        viewModel.state.alias.set(item.alias)
    }

    private fun addObservers() {
        viewModel.clickEvent.observe(this, clickEventObserver)
        viewModel.topUpCards.observe(this, Observer {
            mAdapter.setList(it.toMutableList())
            viewModel.updateCardCount(it.size)
        })
    }

    private val clickEventObserver = Observer<Int> {
        when (it) {
            R.id.tbBtnAddCard -> {
                startActivityForResult(
                    Intent(
                        this,
                        AddTopUpCardActivity::class.java
                    ), Constants.EVENT_ADD_TOPUP_CARD
                )
            }
            R.id.btnSelect -> {
                val item = mAdapter.getDataForPosition(getBinding().rvTopUpCards.currentItem)
                startActivity(TopUpCardActivity.newIntent(this, item))
                // open detail from here.
            }
        }
    }

    fun getBinding(): ActivityTopupCardsBinding {
        return viewDataBinding as ActivityTopupCardsBinding
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.EVENT_ADD_TOPUP_CARD && resultCode == Activity.RESULT_OK) {
            if (true == data?.getBooleanExtra("isCardAdded", false))
                viewModel.getPaymentCards()
        }
    }
}