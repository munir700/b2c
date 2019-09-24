package co.yap.modules.dashboard.cards.home.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.addpaymentcard.activities.AddPaymentCardActivity
import co.yap.modules.dashboard.cards.home.adaptor.YapCardsAdaptor
import co.yap.modules.dashboard.cards.home.interfaces.IYapCards
import co.yap.modules.dashboard.cards.home.viewmodels.YapCardsViewModel
import co.yap.modules.dashboard.cards.paymentcarddetail.activities.PaymentCardDetailActivity
import co.yap.modules.dashboard.fragments.YapDashboardChildFragment
import co.yap.modules.setcardpin.activities.SetCardPinWelcomeActivity
import co.yap.networking.cards.responsedtos.Card
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.CardDeliveryStatus
import co.yap.yapcore.enums.CardStatus
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.fragment_yap_cards.*

class YapCardsFragment : YapDashboardChildFragment<IYapCards.ViewModel>(), IYapCards.View {

    val EVENT_PAYMENT_CARD_DETAIL: Int get() = 11
    val EVENT_CARD_ADDED: Int get() = 12
    var selectedCardPosition: Int = 0
    lateinit var adapter: YapCardsAdaptor

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_yap_cards

    override val viewModel: IYapCards.ViewModel
        get() = ViewModelProviders.of(this).get(YapCardsViewModel::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.clickEvent.observe(this, observer)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPager()
        viewModel.getCards()
    }

    private fun setupPager() {
        adapter = YapCardsAdaptor(requireContext(), mutableListOf())
        viewPager2.adapter = adapter

        with(viewPager2) {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
        }

        val pageMarginPx = Utils.getDimensionInPercent(context!!, true, 14)
        val offsetPx = Utils.getDimensionInPercent(context!!, true, 14)
        viewPager2.setPageTransformer { page, position ->
            val viewPager = page.parent.parent as ViewPager2
            val offset = position * -(2 * offsetPx + pageMarginPx)
            if (viewPager.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
                if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                    page.translationX = -offset
                } else {
                    page.translationX = offset
                }
            } else {
                page.translationY = offset
            }
        }

        adapter.setItemListener(object : OnItemClickListener {
            override fun onItemClick(view: View, data: Any, pos: Int) {
                when (view.id) {
                    R.id.imgCard -> {
                        if (getCard(pos).cardName == Constants.addCard) {
                            openAddCard()
                        } else
                            when (CardStatus.valueOf(getCard(pos).status)) {
                                CardStatus.ACTIVE -> {
                                    openDetailScreen(pos)
                                }
                                CardStatus.BLOCKED -> {
                                    openDetailScreen(pos)
                                }
                                CardStatus.INACTIVE -> {
                                    if (getCard(pos).deliveryStatus == null) {
                                        openDetailScreen(pos)
                                    } else {
                                        when (getCard(pos).deliveryStatus?.let {
                                            CardDeliveryStatus.valueOf(it)
                                        }) {
                                            CardDeliveryStatus.SHIPPED -> {
                                                openSetPinScreen()
                                            }
                                            else -> {
                                                openStatusScreen(view, pos)
                                            }
                                        }
                                    }
                                }
                            }
                    }
                    R.id.lySeeDetail -> {
                        openDetailScreen(pos)
                    }
                    R.id.lycard -> {
                        openAddCard()
                    }
                    R.id.imgAddCard -> {
                        openAddCard()
                    }
                    R.id.tvAddCard -> {
                        openAddCard()
                    }
                    R.id.tvCardStatusAction -> {
                        when (CardStatus.valueOf(getCard(pos).status)) {
                            CardStatus.ACTIVE -> {
                            }
                            CardStatus.BLOCKED -> {
                                openDetailScreen(pos)
                            }
                            CardStatus.INACTIVE -> {
                                if(getCard(pos).cardType=="DEBIT"){
                                    if(MyUserManager.user?.notificationStatuses=="MEETING_SUCCESS"){
                                        openSetPinScreen()
                                    }
                                }else {
                                if (getCard(pos).deliveryStatus == null) {
                                } else {
                                    when (getCard(pos).deliveryStatus?.let {
                                        CardDeliveryStatus.valueOf(it)
                                    }) {
                                        CardDeliveryStatus.SHIPPED -> {
                                            openSetPinScreen()
                                        }
                                        else -> {
                                            openStatusScreen(view, pos)
                                        }
                                    }
                                }
                            }
                        }
                        }
                    }
                }
            }
        })
    }

    val observer = Observer<Int> {
        when (it) {
            R.id.tbBtnAddCard -> {
                openAddCard()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            EVENT_PAYMENT_CARD_DETAIL -> {
                if (resultCode == Activity.RESULT_OK) {
                    val updatedCard = data?.getParcelableExtra<Card>("card")
                    val removed = data?.getBooleanExtra("cardRemoved", false)

                    if (removed!!) {
                        adapter.removeItemAt(selectedCardPosition)
                        adapter.notifyDataSetChanged()
                        updateCardCount()
                    } else {
                        adapter.setItemAt(selectedCardPosition, updatedCard!!)
                    }
                }
            }

            EVENT_CARD_ADDED -> {
                if (resultCode == Activity.RESULT_OK) {
                    val updatedCard: Boolean? = data?.getBooleanExtra("cardAdded", false)
                    if (updatedCard!!) {
                        viewModel.state.cardList.get()?.clear()
                        viewModel.getCards()
                    }
                }
            }
        }
    }

    private fun updateCardCount() {
        viewModel.updateCardCount(adapter.itemCount - if (viewModel.state.enableAddCard.get()) 1 else 0)
    }

    fun openDetailScreen(pos: Int) {
        selectedCardPosition = pos
        startActivityForResult(
            PaymentCardDetailActivity.newIntent(
                requireContext(),
                getCard(pos)
            ), EVENT_PAYMENT_CARD_DETAIL
        )
    }

    fun openAddCard() {
        startActivityForResult(
            AddPaymentCardActivity.newIntent(requireContext()),
            EVENT_CARD_ADDED
        )
    }

    fun openStatusScreen(view: View, pos: Int) {
        view.findNavController().navigate(
            YapCardsFragmentDirections.actionYapCardsToYapCardStatusFragment(
                getCard(pos)
            )
        )
    }

    fun openSetPinScreen() {
        startActivity(
            Intent(
                requireContext(),
                SetCardPinWelcomeActivity::class.java
            )
        )
    }

    fun getCard(pos: Int): Card {
        //return viewModel.state.cardList.get()?.get(pos)!!
        return adapter.getDataForPosition(pos)
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()

    }
}