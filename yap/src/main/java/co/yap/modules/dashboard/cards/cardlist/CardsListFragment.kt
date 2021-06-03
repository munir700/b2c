package co.yap.modules.dashboard.cards.cardlist

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

    private lateinit var mAdapter: CardListAdapter
    private lateinit var mWrappedAdapter: RecyclerView.Adapter<*>
    private lateinit var mRecyclerViewExpandableItemManager: RecyclerViewExpandableItemManager
    private val EVENT_PAYMENT_CARD_DETAIL: Int get() = 11

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_cards_list

    override val viewModel: CardsListViewModel
        get() = ViewModelProviders.of(this).get(CardsListViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initArguments()
    }

    private fun initArguments() {
        arguments?.let { bundle ->
            bundle.getParcelableArrayList<Card>("cardslist")?.apply {
                viewModel.state.cardMap = sortedBy { card ->
                    card.cardType
                }.distinct().groupBy { card ->
                    card.cardType
                }.toMutableMap()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intRecyclersView()
    }

    private fun intRecyclersView() {
        mRecyclerViewExpandableItemManager = RecyclerViewExpandableItemManager(null)
        mAdapter = CardListAdapter(mutableMapOf(), mRecyclerViewExpandableItemManager)
        mWrappedAdapter = mRecyclerViewExpandableItemManager.createWrappedAdapter(mAdapter)
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
                    if (card.cardName == Constants.addCard) {
                        openAddCard()
                    } else
                        when (card.status) {
                            CardStatus.ACTIVE.name -> {
                                if (card.cardType == CardType.DEBIT.type) {
                                    if (card.pinCreated) openDetailScreen(
                                        pos,
                                        card
                                    ) else openStatusScreen(
                                        view,
                                        data
                                    )
                                } else
                                    openDetailScreen(pos, card)
                            }
                            CardStatus.BLOCKED.name, CardStatus.EXPIRED.name -> openDetailScreen(
                                pos, card
                            )
                            CardStatus.INACTIVE.name -> {
                                card.deliveryStatus?.let {
                                    openStatusScreen(view, data)
                                } ?: openDetailScreen(pos, card)
                            }
                        }
                }
            }
    }

    private fun openAddCard() {
        startActivityForResult(
            AddPaymentCardActivity.newIntent(requireContext()),
            RequestCodes.REQUEST_CARD_ADDED
        )
    }

    private fun openDetailScreen(pos: Int = 0, card: Card) {
        card?.let {
            gotoPaymentCardDetailScreen(it)
        }
    }

    private fun gotoPaymentCardDetailScreen(paymentCard: Card) {
        activity?.onBackPressed()
        startActivityForResult(
            PaymentCardDetailActivity.newIntent(
                requireContext(),
                paymentCard
            ), EVENT_PAYMENT_CARD_DETAIL
        )
    }

    private fun openStatusScreen(view: View, card: Card) {
        activity?.onBackPressed()
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

    override fun onToolBarClick(id: Int) {
        super.onToolBarClick(id)
        when (id) {
            R.id.ivLeftIcon -> {
                activity?.onBackPressed()
            }
            R.id.ivRightIcon -> {

            }
        }
    }

}