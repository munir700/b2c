package co.yap.modules.dashboard.cards.home.fragments

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.addpaymentcard.activities.AddPaymentCardActivity
import co.yap.modules.dashboard.cards.home.adaptor.YapCardsAdaptor
import co.yap.modules.dashboard.cards.home.interfaces.IYapCards
import co.yap.modules.dashboard.cards.home.viewmodels.YapCardsViewModel
import co.yap.modules.dashboard.cards.paymentcarddetail.activities.PaymentCardDetailActivity
import co.yap.modules.dashboard.cards.reordercard.activities.ReorderCardActivity
import co.yap.modules.dashboard.main.fragments.YapDashboardChildFragment
import co.yap.modules.dashboard.yapit.topup.cardslisting.TopUpBeneficiariesActivity
import co.yap.modules.others.fragmentpresenter.activities.FragmentPresenterActivity
import co.yap.modules.setcardpin.activities.SetCardPinWelcomeActivity
import co.yap.networking.cards.responsedtos.Card
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.enums.*
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.showBlockedFeatureAlert
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.FeatureProvisioning
import co.yap.yapcore.managers.SessionManager
import com.samsung.android.sdk.samsungpay.v2.SamsungPay
import com.samsung.android.sdk.samsungpay.v2.SpaySdk
import com.samsung.android.sdk.samsungpay.v2.StatusListener
import com.samsung.android.sdk.samsungpay.v2.card.AddCardInfo
import com.samsung.android.sdk.samsungpay.v2.card.CardManager
import com.samsung.android.sdk.samsungpay.v2.card.GetCardListener
import com.samsung.android.sdk.samsungpay.v2.payment.CardInfo
import com.samsung.android.sdk.samsungpay.v2.payment.PaymentManager
import kotlinx.android.synthetic.main.fragment_yap_cards.*

class YapCardsFragment : YapDashboardChildFragment<IYapCards.ViewModel>(), IYapCards.View {

    private val EVENT_PAYMENT_CARD_DETAIL: Int get() = 11
    private val EVENT_CARD_ADDED: Int get() = 12
    private var selectedCardPosition: Int = 0
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
        toolbar?.findViewById<AppCompatImageView>(R.id.ivRightIcon)?.imageTintList =
            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        viewModel.getCards()
        viewModel.cards.observe(this, Observer {
            if (!it.isNullOrEmpty())
                setupList(it)
        })
        SessionManager.card.observe(this, Observer {
            it?.let {
                viewModel.getCards()
            }
        })
    }

    private fun setupList(cards: ArrayList<Card>) {
        adapter.setList(cards)
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

        val pageMarginPx = Utils.getDimensionInPercent(requireContext(), true, 14)
        val offsetPx = Utils.getDimensionInPercent(requireContext(), true, 14)
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
                if (data is Card)
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
                        when (getCard(pos).status) {
                            CardStatus.ACTIVE.name -> {
                                if (getCard(pos).cardType == CardType.DEBIT.type) {
                                    if (getCard(pos).pinCreated) openDetailScreen(pos) else openStatusScreen(
                                        view,
                                        pos
                                    )
                                } else
                                    openDetailScreen(pos)
                            }
                            CardStatus.BLOCKED.name, CardStatus.EXPIRED.name, CardStatus.PIN_BLOCKED.name -> openDetailScreen(
                                pos
                            )
                            CardStatus.INACTIVE.name -> {
                                getCard(pos).deliveryStatus?.let {
                                    openStatusScreen(view, pos)
                                } ?: openDetailScreen(pos)
                            }
                        }
                }
                R.id.lySeeDetail -> {
                    openDetailScreen(pos)
                }
                R.id.lycard, R.id.imgAddCard, R.id.tvAddCard, R.id.tbBtnAddCard -> {
                    openAddCard()
                }
                R.id.tvCardStatusAction -> {
                    when (getCard(pos).status) {
                        CardStatus.BLOCKED.name -> {
                            if (FeatureProvisioning.getFeatureProvisioning(FeatureSet.UNFREEZE_CARD)) {
                                showBlockedFeatureAlert(requireActivity(), FeatureSet.UNFREEZE_CARD)
                            } else {
                                viewModel.unFreezeCard(getCard(pos).cardSerialNumber) {
                                    viewModel.getUpdatedCard(pos) { card ->
                                        card?.let {
                                            adapter.setItemAt(pos, card)
                                        }
                                    }
                                }
                            }
                        }
                        CardStatus.HOTLISTED.name -> {
                            startReorderCardFlow(getCard(pos))
                        }
                        CardStatus.ACTIVE.name -> {
                            if (getCard(pos).cardType == CardType.DEBIT.type) {
                                if (PartnerBankStatus.ACTIVATED.status == SessionManager.user?.partnerBankStatus && !getCard(
                                        pos
                                    ).pinCreated
                                ) {
                                    openSetPinScreen(getCard(pos))
                                }
                            }
                        }
                        CardStatus.INACTIVE.name -> {
                            if (getCard(pos).cardType == CardType.DEBIT.type) {
                                if (PartnerBankStatus.ACTIVATED.status == SessionManager.user?.partnerBankStatus) {
                                    if (getCard(pos).deliveryStatus == CardDeliveryStatus.SHIPPED.name)
                                        openSetPinScreen(getCard(pos))
                                    else
                                        openStatusScreen(view, pos)
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivRightIcon -> openAddCard()
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
                    val cardReorder = data?.getBooleanExtra("cardReorder", false)

                    when {
                        true == removed -> {
                            adapter.removeItemAt(selectedCardPosition)
                            adapter.notifyDataSetChanged()
                            updateCardCount()
                        }
                        true == cardBlocked -> {
                            adapter.removeAllItems()
                            viewModel.getCards()
                        }
                        true == cardReorder -> {
                            adapter.removeAllItems()
                            viewModel.getCards()
                        }
                        else -> {
                            adapter.getDataList()
                                .firstOrNull { it.cardSerialNumber == updatedCard?.cardSerialNumber }
                                ?.let { card ->
                                    val pos = adapter.getDataList().indexOf(card)
                                    updatedCard?.let { adapter.setItemAt(pos, it) }
                                } ?: showToast("Card not found")
                        }
                    }
                }
            }
            EVENT_CARD_ADDED -> {
                if (resultCode == Activity.RESULT_OK) {
                    val updatedCard: Boolean? = data?.getBooleanExtra("cardAdded", false)
                    val paymentCard: Card? = data?.getParcelableExtra("paymentCard")
                    if (true == updatedCard) {
                        adapter.removeAllItems()
                        openDetailScreen(pos = viewModel.cards.value?.size ?: 0, card = paymentCard)
                        viewModel.getCards()
                    }
                }
            }
            Constants.EVENT_CREATE_CARD_PIN -> {
                if (resultCode == Activity.RESULT_OK) {
                    SessionManager.getDebitCard()
                    val isPinCreated: Boolean? =
                        data?.getBooleanExtra(Constants.isPinCreated, false)
                    val cardSerialNumber: String? =
                        data?.getStringExtra(Constants.CARD_SERIAL_NUMBER)

                    if (!cardSerialNumber.isNullOrBlank()) {
                        getCardFromSerialNumber(serialNumber = cardSerialNumber)?.let {
                            if (SessionManager.user?.otpBlocked == true) {
                                showToast(Utils.getOtpBlockedMessage(requireContext()))
                            } else {
                                startActivityForResult(
                                    TopUpBeneficiariesActivity.newIntent(
                                        requireContext(),
                                        getString(Strings.screen_topup_success_display_text_dashboard_action_button_title)
                                    ),
                                    RequestCodes.REQUEST_ADD_FUNDS_WHEN_ADD
                                )
                            }
                        } ?: showToast("Invalid card found.")
                    } else {
                        isPinCreated?.let {
                            if (it) {
                                adapter.removeAllItems()
                            }
                        }
                    }
                }
            }
            RequestCodes.REQUEST_ADD_FUNDS_WHEN_ADD -> {
                adapter.removeAllItems()
                viewModel.getCards()
            }
            RequestCodes.REQUEST_REORDER_CARD -> {
                if (resultCode == Activity.RESULT_OK) {
                    val cardReorder = data?.getBooleanExtra("cardReorder", false)
                    if (true == cardReorder) {
                        adapter.removeAllItems()
                        viewModel.getCards()
                    }
                }
            }
        }
    }

    private fun openDetailScreen(pos: Int = 0, card: Card? = null) {
        selectedCardPosition = pos
        card?.let {
            gotoPaymentCardDetailScreen(it)
        } ?: gotoPaymentCardDetailScreen(getCard(pos))
    }

    private fun gotoPaymentCardDetailScreen(paymentCard: Card) {
        startActivityForResult(
            PaymentCardDetailActivity.newIntent(
                requireContext(),
                paymentCard
            ), EVENT_PAYMENT_CARD_DETAIL
        )
    }

    private fun openAddCard() {
//        startActivityForResult(
//            AddPaymentCardActivity.newIntent(requireContext()),
//            EVENT_CARD_ADDED
//        )

        samsungPay()

    }

    private fun openStatusScreen(view: View, pos: Int) {
        context?.let { context ->
            startActivityForResult(
                FragmentPresenterActivity.getIntent(
                    context = context,
                    type = Constants.MODE_STATUS_SCREEN,
                    payLoad = getCard(pos)
                ), Constants.EVENT_CREATE_CARD_PIN
            )
        }
    }

    private fun openSetPinScreen(card: Card) {
        context?.let { context ->
            startActivityForResult(
                SetCardPinWelcomeActivity.newIntent(
                    context = context,
                    card = card,
                    skipWelcomeScreen = true
                ), Constants.EVENT_CREATE_CARD_PIN
            )
        }
    }

    private fun startReorderCardFlow(card: Card?) {
        card?.let {
            launchActivity<ReorderCardActivity>(
                type = FeatureSet.REORDER_DEBIT_CARD,
                requestCode = RequestCodes.REQUEST_REORDER_CARD
            ) {
                putExtra(ReorderCardActivity.CARD, it)
            }
        }
    }

    private fun getCard(pos: Int): Card {
        return adapter.getDataForPosition(pos)
    }

    private fun getCardFromSerialNumber(serialNumber: String): Card? {
        return adapter.getDataList().firstOrNull { it.cardSerialNumber == serialNumber }
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }


    private fun samsungPay() {
        val bundle = Bundle()
        bundle.putString(
            SamsungPay.PARTNER_SERVICE_TYPE, SpaySdk.ServiceType.APP2APP.toString()
        )

//        val partnerInfo = PartnerInfo("a3efe152fa4344778aa69754b5f81a22", bundle)
//        val partnerInfo = PartnerInfo("f8ac9f3b119645539bb138db4f64b80e", bundle)
        val samsungPay =
            SamsungPay(context, PartnerInfoHolder.getInstance(requireContext())?.partnerInfo)

        samsungPay.getSamsungPayStatus(object : StatusListener {
            override fun onSuccess(status: Int, bundle: Bundle?) {

                when (status) {
                    SamsungPay.SPAY_NOT_SUPPORTED -> {
                        showToast("not supported samsung pay")
                    }
                    SamsungPay.SPAY_NOT_READY -> {// Activate Samsung Pay or update Samsung Pay, if needed
                        showToast("samsung activate")
                        samsungPay.activateSamsungPay()
                    }
                    SamsungPay.SPAY_READY -> {
                        showToast("samsung ready")
                        val keys: ArrayList<String> = ArrayList()
                        keys.add(CardManager.WALLET_DM_ID)


                        samsungPay.getWalletInfo(keys, object : StatusListener {
                            override fun onSuccess(status: Int, walletData: Bundle?) {
                                val walletsSize= walletData?.size().toString()
                                showToast("your samsung pay wallets size is: $walletsSize")

                                // following is the code to refine regarding add card

//                                CardManager(
//                                    requireContext(),
//                                    PartnerInfoHolder.getInstance(requireContext())?.partnerInfo
//                                ).addCard(getCardInfo(),
//                                    object : AddCardListener {
//                                        override fun onSuccess(
//                                            p0: Int,
//                                            p1: com.samsung.android.sdk.samsungpay.v2.card.Card?
//                                        ) {
//                                            showToast("onSuccess callback addCard=" + p1.toString())
//                                        }
//
//                                        override fun onFail(errorCode: Int, error: Bundle?) {
//                                            showToast("onFail callback is called, code: $errorCode")
//                                        }
//
//                                        override fun onProgress(p0: Int, p1: Int, p2: Bundle?) {
//                                        }
//
//                                    })
                            }

                            override fun onFail(p0: Int, p1: Bundle?) {
                            }

                        })


//                        requestGetAllCards(1)


                    }
                    else ->                        // Not expected result
                        showToast("samsung unexpected")
                }
            }

            override fun onFail(errorCode: Int, bundle: Bundle?) {
                showToast(errorCode.toString())
            }
        })
    }

    val getCardListener: GetCardListener = object : GetCardListener {
        override fun onSuccess(cards: List<com.samsung.android.sdk.samsungpay.v2.card.Card>) {
            showToast("onSuccess callback is called, list.size=" + cards.size)
        }

        override fun onFail(errorCode: Int, errorData: Bundle) {
            showToast("onFail callback is called, errorCode:$errorCode")
        }
    }

    private fun getCardInfo(): AddCardInfo? {
        val cardType = com.samsung.android.sdk.samsungpay.v2.card.Card.CARD_TYPE_CREDIT_DEBIT
        val tokenizationProvider = AddCardInfo.PROVIDER_MASTERCARD
        val cardDetail = Bundle()
        val testPayload = "ThisIsTestPayloadCardInfo1234567890"
        cardDetail.putString(AddCardInfo.EXTRA_PROVISION_PAYLOAD, testPayload)
        return AddCardInfo(cardType, tokenizationProvider, cardDetail)
    }

    fun updateSpay() {
//        val bundle = Bundle()
//        bundle.putString(
//            SpaySdk.PARTNER_SERVICE_TYPE,
//            SpaySdk.ServiceType.INAPP_PAYMENT.toString()
//        )
//
//        val partnerInfo = PartnerInfo("f8ac9f3b119645539bb138db4f64b80e", bundle)
//        val samsungPay = SamsungPay(context, partnerInfo)
//        samsungPay.goToUpdatePage()

        checkcard()
    }

    fun checkcard() {
        val bundle = Bundle()
        bundle.putString(
            SamsungPay.PARTNER_SERVICE_TYPE,
            SpaySdk.ServiceType.INAPP_PAYMENT.toString()
        )
        var paymentManager: PaymentManager =
            PaymentManager(context, PartnerInfoHolder.getInstance(requireContext())?.partnerInfo)
        paymentManager.requestCardInfo(Bundle(), cardInfoListener) // get Card Brand List

//CardInfoListener is for listening requestCardInfo() callback events
    }

    val cardInfoListener: PaymentManager.CardInfoListener = object : PaymentManager.CardInfoListener {
        /*
     * This callback is received when the card information is received successfully.
     */
        override fun onResult(cardResponse: List<CardInfo>?) {
            var visaCount = 0
            var mcCount = 0
            var amexCount = 0
            var dsCount = 0
            var brandStrings = "- Card Info : "
            if (cardResponse != null) {
                var brand: SpaySdk.Brand
                for (i in cardResponse.indices) {
                    brand = cardResponse[i].getBrand()
                    when (brand) {
                        SpaySdk.Brand.AMERICANEXPRESS -> amexCount++
                        SpaySdk.Brand.MASTERCARD -> mcCount++
                        SpaySdk.Brand.VISA -> visaCount++
                        SpaySdk.Brand.DISCOVER -> dsCount++
                        else -> {
                        }
                    }
                }
            }
            brandStrings += "  VI=$visaCount, MC=$mcCount, AX=$amexCount, DS=$dsCount"
           showToast("cardInfoListener onResult$brandStrings"            )
        }

        /*
     * This callback is received when the card information cannot be retrieved.
     * For example, when SDK service in the Samsung Pay app dies abnormally.
     */
        override fun onFailure(errorCode: Int, errorData: Bundle) {
            //Called when an error occurs during In-App cryptogram generation
           showToast(
                "cardInfoListener onFailure : $errorCode"
            )
        }
    }

    private fun requestGetAllCards(requestCode: Int) {

        val getCardListener: GetCardListener = object : GetCardListener {
            override fun onSuccess(cards: List<com.samsung.android.sdk.samsungpay.v2.card.Card>) {
                showToast(cards.size.toString())

            }

            override fun onFail(errorCode: Int, errorData: Bundle) {
                showToast(errorData.toString())

            }
        }

        var cardManager = CardManager(
            requireContext(),
            PartnerInfoHolder.getInstance(requireContext())?.partnerInfo
        )
        cardManager.getAllCards(null, getCardListener)

    }
}
