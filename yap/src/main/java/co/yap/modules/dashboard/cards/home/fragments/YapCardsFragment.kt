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
import co.yap.yapcore.SingleClickEvent
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
    val EVENT_CREATE_CARD_PIN: Int get() = 13
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
        if (MyUserManager.cards.value == null || MyUserManager.cards.value!!.isEmpty()) {
            viewModel.getCards()
        } else {
            setupList()
        }

        viewModel.state.listUpdated.observe(this, Observer {
            if (it)
                setupList()
        })
    }

    private fun setupList() {
        if (viewModel.state.enableAddCard.get())
            MyUserManager.cards.value?.add(getAddCard())
        adapter.setList(MyUserManager.cards.value!!)
        updateCardCount()
    }

    private fun updateCardCount() {
        viewModel.updateCardCount(adapter.itemCount - if (viewModel.state.enableAddCard.get()) 1 else 0)
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
                viewModel.clickEvent.setPayload(
                    SingleClickEvent.AdaptorPayLoadHolder(
                        view,
                        data,
                        pos
                    )
                )
                viewModel.clickEvent.setValue(view.id)
            }
        })
    }

    val observer = Observer<Int> {
        val pos = viewModel.clickEvent.getPayload()?.position
        val view = viewModel.clickEvent.getPayload()?.view
        viewModel.clickEvent.setPayload(null)
        if (pos != null && view != null) {
            when (it) {
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
                                            openStatusScreen(view, pos)
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
                R.id.tbBtnAddCard -> {
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
                            if (getCard(pos).cardType == "DEBIT") {
                                if (MyUserManager.user?.notificationStatuses == "MEETING_SUCCESS") {
                                    openSetPinScreen(getCard(pos).cardSerialNumber)
                                }
                            } else {
                                if (getCard(pos).deliveryStatus == null) {
                                } else {
                                    when (getCard(pos).deliveryStatus?.let {
                                        CardDeliveryStatus.valueOf(it)
                                    }) {
                                        CardDeliveryStatus.SHIPPED -> {
                                            openSetPinScreen(getCard(pos).cardSerialNumber)
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
        } else {
            if (it == R.id.tbBtnAddCard) {
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
                    val cardBlocked = data?.getBooleanExtra("cardBlocked", false)

                    if (removed!!) {
                        adapter.removeItemAt(selectedCardPosition)
                        adapter.notifyDataSetChanged()
                        updateCardCount()
                    } else if (cardBlocked!!) {
                        adapter.removeAllItems()
                        viewModel.getCards()
                    } else {
                        adapter.setItemAt(selectedCardPosition, updatedCard!!)
                    }
                }
            }

            EVENT_CARD_ADDED -> {
                if (resultCode == Activity.RESULT_OK) {
                    val updatedCard: Boolean? = data?.getBooleanExtra("cardAdded", false)
                    if (updatedCard!!) {
                        adapter.removeAllItems()
                        viewModel.getCards()
                    }
                }
            }

            EVENT_CREATE_CARD_PIN -> {
                if (resultCode == Activity.RESULT_OK) {
                    val isPinCreated: Boolean? =
                        data?.getBooleanExtra(Constants.isPinCreated, false)
                    if (isPinCreated!!) {
                        viewModel.state.enableAddCard.set(
                            MyUserManager.user?.notificationStatuses.equals(co.yap.modules.onboarding.constants.Constants.USER_STATUS_CARD_ACTIVATED)
                        )
                        adapter.removeAllItems()
                        viewModel.getCards()
                    }
                }
            }
        }
    }

    private fun openDetailScreen(pos: Int) {
        selectedCardPosition = pos
        startActivityForResult(
            PaymentCardDetailActivity.newIntent(
                requireContext(),
                getCard(pos)
            ), EVENT_PAYMENT_CARD_DETAIL
        )
    }

    private fun openAddCard() {
        startActivityForResult(
            AddPaymentCardActivity.newIntent(requireContext()),
            EVENT_CARD_ADDED
        )
    }

    private fun openStatusScreen(view: View, pos: Int) {
        view.findNavController().navigate(
            YapCardsFragmentDirections.actionYapCardsToYapCardStatusFragment(
                getCard(pos)
            )
        )
    }

    private fun openSetPinScreen(cardSerialNumber: String) {
        startActivityForResult(
            SetCardPinWelcomeActivity.newIntent(
                requireContext(),
                cardSerialNumber
            ), EVENT_CREATE_CARD_PIN
        )
    }

    override fun onResume() {
        if (co.yap.modules.dashboard.constants.Constants.isPinCreated) {
            co.yap.modules.dashboard.constants.Constants.isPinCreated = false
            adapter.removeAllItems()
            viewModel.getCards()
        }
        super.onResume()
    }

    private fun getCard(pos: Int): Card {
        return adapter.getDataForPosition(pos)
    }

    override fun onDestroyView() {
        if (viewModel.state.enableAddCard.get()) {
            val list = adapter.getDataList() as ArrayList<Card>
            list.removeAt(list.size - 1)
            MyUserManager.cards.value = list
        } else {
            MyUserManager.cards.value = adapter.getDataList() as ArrayList<Card>
        }

        super.onDestroyView()
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }

    private fun getAddCard(): Card {

        return Card(
            newPin = "",
            cardType = "DEBIT",
            uuid = "542 d2ef0 -9903 - 4 a19 -a691 - 12331357f f15",
            physical = false,
            active = false,
            cardName = Constants.addCard,
            nameUpdated = false,
            status = "ACTIVE",
            shipmentStatus = "SHIPPED",
            deliveryStatus = "BOOKED",
            blocked = false,
            delivered = false,
            cardSerialNumber = "1000000000612",
            maskedCardNo = "5381 23 * * * * * * 5744",
            atmAllowed = true,
            onlineBankingAllowed = true,
            retailPaymentAllowed = true,
            paymentAbroadAllowed = true,
            accountType = "B2C_ACCOUNT",
            expiryDate = "09/24",
            cardBalance = "0.00",
            cardScheme = "Master Card",
            currentBalance = "0.00",
            availableBalance = "0.00",
            customerId = "1100000000071",
            accountNumber = "1199999000000071",
            productCode = "CD",
            pinCreated = true
        )
    }

}