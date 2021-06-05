package co.yap.modules.dashboard.cards.cardlist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentCardsListBinding
import co.yap.modules.dashboard.cards.addpaymentcard.main.activities.AddPaymentCardActivity
import co.yap.modules.dashboard.cards.paymentcarddetail.activities.PaymentCardDetailActivity
import co.yap.modules.dashboard.main.fragments.YapDashboardChildFragment
import co.yap.modules.others.fragmentpresenter.activities.FragmentPresenterActivity
import co.yap.networking.cards.responsedtos.Card
import co.yap.widgets.advrecyclerview.decoration.StickyHeaderItemDecoration
import co.yap.widgets.advrecyclerview.expandable.RecyclerViewExpandableItemManager
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.enums.CardStatus
import co.yap.yapcore.enums.CardType

class CardsListFragment : YapDashboardChildFragment<ICardsList.ViewModel>(), ICardsList.View {

    private val mRecyclerViewExpandableItemManager: RecyclerViewExpandableItemManager by lazy {
        RecyclerViewExpandableItemManager(null)
    }
    private val mWrappedAdapter: RecyclerView.Adapter<*> by lazy {
        mRecyclerViewExpandableItemManager.createWrappedAdapter(mAdapter)
    }
    private val mAdapter: CardListAdapter by lazy {
        CardListAdapter(mutableMapOf(), mRecyclerViewExpandableItemManager)
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_cards_list

    override val viewModel: CardsListViewModel
        get() = ViewModelProviders.of(this).get(CardsListViewModel::class.java)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initArguments()
        intRecyclersView()
    }

    private fun initArguments() {
        viewModel.state.cardMap.clear()
        arguments?.let { bundle ->
            val list = bundle.getParcelableArrayList<Card>("cardslist")
            list?.removeAt(list.size - 1)
            list?.apply {
                viewModel.state.cardMap = sortedBy { card ->
                    card.cardType
                }.distinct().groupBy { card ->
                    card.cardType
                }.toMutableMap()
            }
        }
    }

    private fun intRecyclersView() {
        mRecyclerViewExpandableItemManager.defaultGroupsExpandedState = true
        getDataBindingView<FragmentCardsListBinding>().recyclerView.apply {
            addItemDecoration(StickyHeaderItemDecoration())
            mRecyclerViewExpandableItemManager.attachRecyclerView(this)
            adapter = mWrappedAdapter
            viewModel.cardAdapter?.set(mAdapter)
            mAdapter.setData(viewModel.state.cardMap)
            setHasFixedSize(true)
        }
        mAdapter.onItemClick =
            { view: View, pos: Int, childPosition: Int, data: Card? ->
                data?.let { card ->
                    when (card.status) {
                        CardStatus.ACTIVE.name -> {
                            if (card.cardType == CardType.DEBIT.type) {
                                if (card.pinCreated) gotoPaymentCardDetailScreen(card)
                                else openStatusScreen(card)
                            } else
                                gotoPaymentCardDetailScreen(card)
                        }
                        CardStatus.BLOCKED.name, CardStatus.EXPIRED.name -> gotoPaymentCardDetailScreen(
                            card
                        )
                        CardStatus.INACTIVE.name -> {
                            card.deliveryStatus?.let {
                                openStatusScreen(card)
                            } ?: gotoPaymentCardDetailScreen(card)
                        }
                    }

                }
            }
    }

    private fun gotoPaymentCardDetailScreen(paymentCard: Card) {
        startActivityForResult(
            PaymentCardDetailActivity.newIntent(
                requireContext(),
                paymentCard
            ), RequestCodes.REQUEST_PAYMENT_CARD_DETAIL
        )
    }

    private fun openStatusScreen(card: Card) {
        context?.let { context ->
            startActivityForResult(
                FragmentPresenterActivity.getIntent(
                    context = context,
                    type = Constants.MODE_STATUS_SCREEN,
                    payLoad = card
                ), Constants.EVENT_CREATE_CARD_PIN
            )
        }
    }

    private fun openAddCard() {
        startActivityForResult(
            AddPaymentCardActivity.newIntent(requireContext()),
            RequestCodes.REQUEST_CARD_ADDED
        )
    }

    override fun onToolBarClick(id: Int) {
        super.onToolBarClick(id)
        when (id) {
            R.id.ivLeftIcon -> {
                activity?.onBackPressed()
            }
            R.id.ivRightIcon -> {
                openAddCard()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RequestCodes.REQUEST_PAYMENT_CARD_DETAIL, Constants.EVENT_CREATE_CARD_PIN, RequestCodes.REQUEST_CARD_ADDED -> {
                    val returnIntent = Intent()
                    requireActivity().setResult(Activity.RESULT_OK, returnIntent)
                    requireActivity().onBackPressed()
                }
            }

        }
    }

}