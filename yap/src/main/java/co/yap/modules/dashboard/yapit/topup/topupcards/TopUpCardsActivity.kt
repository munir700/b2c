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
import co.yap.modules.dashboard.yapit.topup.main.carddetail.TopupCardDetailActivity
import co.yap.modules.dashboard.yapit.topup.main.topupamount.activities.TopUpCardActivity
import co.yap.modules.dashboard.yapit.topup.topupcards.addtopupcard.activities.AddTopUpCardActivity
import co.yap.modules.others.helper.Constants
import co.yap.networking.customers.responsedtos.beneficiary.TopUpCard
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.SingleClickEvent
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
        mAdapter.setItemListener(listener)
        getBinding().rvTopUpCards.smoothScrollToPosition(0)
        getBinding().rvTopUpCards.setItemTransitionTimeMillis(150)
//        getBinding().rvTopUpCards.addScrollStateChangeListener(object :
//            DiscreteScrollView.ScrollStateChangeListener<TopUpCardsAdapter.TopUpCardViewHolder> {
//            override fun onScroll(
//                scrollPosition: Float,
//                currentPosition: Int,
//                newPosition: Int,
//                currentHolder: TopUpCardsAdapter.TopUpCardViewHolder?,
//                newCurrent: TopUpCardsAdapter.TopUpCardViewHolder?
//            ) {
//                if (newCurrent is TopUpCardsAdapter.TopUpCardViewHolder) {
//
//                    Log.i(
//                        "discreta: ",
//                        "scrollPosition $scrollPosition ,currentPosition $currentPosition ,newPosition $newPosition"
//                    )
//
//                    if (currentPosition > 0) {
//                        val previous = getBinding().rvTopUpCards.getViewHolder(currentPosition - 1)
//                        if (previous is TopUpCardsAdapter.TopUpCardViewHolder) {
//                            if (abs(scrollPosition) < 0.5) {
//                                newCurrent.binding.parent?.alpha = abs(0.5f)
//                                previous.binding.parent?.alpha = abs(0.5f)
//                            } else {
//                                newCurrent.binding.parent?.alpha = abs(scrollPosition)
//                                previous.binding.parent?.alpha = abs(scrollPosition)
//                            }
//                        }
//                    }
//                }
//            }
//
//            override fun onScrollEnd(
//                currentItemHolder: TopUpCardsAdapter.TopUpCardViewHolder,
//                position: Int
//            ) {
//
//            }
//
//            override fun onScrollStart(
//                currentItemHolder: TopUpCardsAdapter.TopUpCardViewHolder,
//                position: Int
//            ) {
//
//            }
//        })
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
            viewModel.clickEvent.setPayload(
                SingleClickEvent.AdaptorPayLoadHolder(
                    view,
                    data,
                    pos
                )
            )
            viewModel.clickEvent.setValue(view.id)
        }
    }

    private fun updateSelection(viewHolder: RecyclerView.ViewHolder?, adapterPosition: Int) {
        val item = mAdapter.getDataForPosition(adapterPosition)
        if (item.alias == "addCard") {
            viewModel.state.valid.set(false)
            viewModel.state.alias.set("")
        } else {
            viewModel.state.valid.set(true)
            viewModel.state.alias.set(item.alias)
        }
    }

    private fun addObservers() {
        viewModel.clickEvent.observe(this, clickEventObserver)
        viewModel.topUpCards.observe(this, Observer {
            mAdapter.setList(it.toMutableList())
            viewModel.updateCardCount()
        })
    }

    private val clickEventObserver = Observer<Int> {
        if (viewModel.clickEvent.getPayload() != null) {
            val pos = viewModel.clickEvent.getPayload()?.position
            val view = viewModel.clickEvent.getPayload()?.view
            viewModel.clickEvent.setPayload2(null)

            if (pos != getBinding().rvTopUpCards.currentItem) {
                pos?.let { it -> getBinding().rvTopUpCards.smoothScrollToPosition(it) }
                return@Observer
            }
        }
        when (it) {
            R.id.tbBtnAddCard -> {
                addCardProcess()
            }
            R.id.btnSelect -> {
                val item = mAdapter.getDataForPosition(getBinding().rvTopUpCards.currentItem)
                if (item.alias != "addCard")
                    startActivity(
                        TopUpCardActivity.newIntent(
                            this,
                            item
                        )
                    )
            }
            R.id.paymentCard -> {
                val item = mAdapter.getDataForPosition(getBinding().rvTopUpCards.currentItem)
                startActivity(TopUpCardActivity.newIntent(this, item))
            }

            R.id.imgStatus -> {
                val item = mAdapter.getDataForPosition(getBinding().rvTopUpCards.currentItem)
                openCardDetail(item)
            }
            R.id.tbBtnBack -> {
                onBackPressed()
            }
            R.id.lycard -> {
                addCardProcess()
            }
        }
    }


    private fun openCardDetail(card: TopUpCard) {
        startActivityForResult(
            TopupCardDetailActivity.getIntent(
                this@TopUpCardsActivity,
                TopUpCard(
                    card.id,
                    card.logo,
                    card.expiry,
                    card.number,
                    card.alias,
                    card.color
                )
            ),
            Constants.EVENT_DELETE_TOPUP_CARD
        )
    }

    private fun addCardProcess() {
        startActivityForResult(
            AddTopUpCardActivity.newIntent(
                this,
                co.yap.yapcore.constants.Constants.URL_ADD_TOPUP_CARD,
                co.yap.yapcore.constants.Constants.TYPE_ADD_CARD
            ), Constants.EVENT_ADD_TOPUP_CARD
        )
    }

    fun getBinding(): ActivityTopupCardsBinding {
        return viewDataBinding as ActivityTopupCardsBinding
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.EVENT_ADD_TOPUP_CARD && resultCode == Activity.RESULT_OK) {
            if (true == data?.getBooleanExtra("isCardAdded", false))
                viewModel.getPaymentCards()
        } else if (requestCode == Constants.EVENT_DELETE_TOPUP_CARD && resultCode == Activity.RESULT_OK) {
            if (true == data?.getBooleanExtra("isCardDeleted", false)) {
                showToast("Card Removed Successfully")
                viewModel.getPaymentCards()
            }
        }
    }
}