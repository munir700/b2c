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
import co.yap.modules.dashboard.cards.addpaymentcard.main.activities.AddPaymentCardActivity
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
import co.yap.wallet.samsung.SamsungPayWalletManager
import co.yap.widgets.guidedtour.OnTourItemClickListener
import co.yap.widgets.guidedtour.TourSetup
import co.yap.widgets.guidedtour.models.GuidedTourViewDetail
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.constants.RequestCodes.REQUEST_CARD_ADDED
import co.yap.yapcore.enums.*
import co.yap.yapcore.helpers.TourGuideManager
import co.yap.yapcore.helpers.TourGuideType
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.launchTourGuide
import co.yap.yapcore.helpers.extentions.showBlockedFeatureAlert
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.FeatureProvisioning
import co.yap.yapcore.managers.SessionManager
import com.liveperson.infra.configuration.Configuration.getDimension
import kotlinx.android.synthetic.main.fragment_yap_cards.*

class YapCardsFragment : YapDashboardChildFragment<IYapCards.ViewModel>(), IYapCards.View {

    private val EVENT_PAYMENT_CARD_DETAIL: Int get() = 11

    //    private val EVENT_CARD_ADDED: Int get() = 12
    private var selectedCardPosition: Int = 0

    //lateinit var adapter: YapCardsAdaptor
    private var tourStep: TourSetup? = null

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_yap_cards

    override val viewModel: YapCardsViewModel
        get() = ViewModelProviders.of(this).get(YapCardsViewModel::class.java)

    // private fun getCardAdaptor(): YapCardsAdaptor = viewModel.adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setupAdaptor(requireContext())
        viewModel.clickEvent.observe(this, observer)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPager()
        toolbar?.findViewById<AppCompatImageView>(R.id.ivRightIcon)?.imageTintList =
            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
//        viewModel.getCards()
        viewModel.cards.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                viewModel.adapter.setList(it)
                updateCardCount()
            }
        })
        SessionManager.card.observe(this, Observer {
            it?.let {
                viewModel.getCards()
            }
        })
        viewModel.parentViewModel?.isYapCardsFragmentVisible?.observe(
            this,
            Observer { isCardsFragmentVisible ->
                if (isCardsFragmentVisible) {
                    if (PartnerBankStatus.ACTIVATED.status == SessionManager.user?.partnerBankStatus) {
                        tourStep =
                            requireActivity().launchTourGuide(TourGuideType.CARD_HOME_SCREEN) {
                                this.addAll(setViewsArray())
                            }
                    }
                } else {
                    tourStep?.let {
                        if (it.isShowing)
                            it.dismiss()
                    }
                }
            })
    }

    private fun updateCardCount() {
        viewModel.updateCardCount(viewModel.adapter.itemCount - if (viewModel.state.enableAddCard.get()) 1 else 0)
    }

    private fun setupPager() {
        //getCardAdaptor() = YapCardsAdaptor(requireContext(), mutableListOf())
        viewPager2.adapter = viewModel.adapter

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

        viewModel.adapter.setItemListener(object : OnItemClickListener {
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
            val card = viewModel.adapter.getDataForPosition(pos)
            when (it) {
                R.id.imgCard -> {
                    if (card.cardName == Constants.addCard) {
                        openAddCard()
                    } else
                        when (card.status) {
                            CardStatus.ACTIVE.name -> {
                                if (card.cardType == CardType.DEBIT.type) {
                                    if (card.pinCreated) openDetailScreen(pos) else openStatusScreen(
                                        view,
                                        pos
                                    )
                                } else
                                    openDetailScreen(pos)
                            }
                            CardStatus.BLOCKED.name, CardStatus.EXPIRED.name -> openDetailScreen(
                                pos
                            )
                            CardStatus.INACTIVE.name -> {
                                card.deliveryStatus?.let {
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
                    when (card.status) {
                        CardStatus.BLOCKED.name -> {
                            if (FeatureProvisioning.getFeatureProvisioning(FeatureSet.UNFREEZE_CARD)) {
                                showBlockedFeatureAlert(requireActivity(), FeatureSet.UNFREEZE_CARD)
                            } else {
                                viewModel.unFreezeCard(card.cardSerialNumber) {
                                    viewModel.getUpdatedCard(pos) { card ->
                                        card?.let {
                                            viewModel.adapter.setItemAt(pos, card)
                                        }
                                    }
                                }
                            }
                        }
                        CardStatus.HOTLISTED.name -> {
                            startReorderCardFlow(card)
                        }
                        CardStatus.ACTIVE.name -> {
                            if (card.cardType == CardType.DEBIT.type) {
                                if (PartnerBankStatus.ACTIVATED.status == SessionManager.user?.partnerBankStatus && !card.pinCreated
                                ) {
                                    openSetPinScreen(card)
                                }
                            }
                        }
                        CardStatus.INACTIVE.name -> {
                            if (card.cardType == CardType.DEBIT.type) {
                                if (PartnerBankStatus.ACTIVATED.status == SessionManager.user?.partnerBankStatus) {
                                    if (card.deliveryStatus == CardDeliveryStatus.SHIPPED.name)
                                        openSetPinScreen(card)
                                    else
                                        openStatusScreen(view, pos)
                                }
                            }
                        }
                    }
                }
                R.id.btnSamsungPay -> {
                    viewModel.getSPayCardFormYapCard(card) { SCard ->
                        SCard?.let {
                            SamsungPayWalletManager.getInstance(requireContext())
                                .openFavoriteCard(SCard.cardId)
                        } ?: run {
                            viewModel.getCardDetails(card.cardSerialNumber) { details ->
                            }
                        }

                    }
//                    requireContext().alert("Card Already Added")
//                    SamsungPayWalletManager.getInstance(requireContext())
//                        .getWalletInfo { status, bundle ->

//                    SamsungPayWalletManager.getInstance(requireContext())
//                        .getWalletInfo {status, bundle ->
//                            viewModel.getCardTokenForSamsungPay { data ->
//                                val toJson =
//                                    GsonBuilder().disableHtmlEscaping().create()
//                                        .toJson(data)
//                                val finalPayload =
//                                    EncodingUtils.base64Encode(toJson.toByteArray(Charsets.UTF_8))
//                                SamsungPayWalletManager.getInstance(requireContext())
//                                    .addYapCardToSamsungPay(
//                                        finalPayload
//                                    )
//
//                            }
//                        }
//                    }
                }
            }
        }
    }


    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivRightIcon -> {
                openAddCard()
//                val tour = TourSetup(requireActivity(), setViewsArray())
//                tour.startTour()
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
                    val cardReorder = data?.getBooleanExtra("cardReorder", false)

                    when {
                        true == removed -> {
                            viewModel.adapter.removeItemAt(selectedCardPosition)
                            viewModel.adapter.notifyDataSetChanged()
                            updateCardCount()
                        }
                        true == cardBlocked -> {
                            viewModel.adapter.removeAllItems()
                            viewModel.getCards()
                        }
                        true == cardReorder -> {
                            viewModel.adapter.removeAllItems()
                            viewModel.getCards()
                        }
                        else -> {
                            viewModel.adapter.getDataList()
                                .firstOrNull { it.cardSerialNumber == updatedCard?.cardSerialNumber }
                                ?.let { card ->
                                    val pos = viewModel.adapter.getDataList().indexOf(card)
                                    updatedCard?.let { viewModel.adapter.setItemAt(pos, it) }
                                } ?: showToast("Card not found")
                        }
                    }
                }
            }
            REQUEST_CARD_ADDED -> {
                if (resultCode == Activity.RESULT_OK) {
                    val updatedCard: Boolean? = data?.getBooleanExtra("cardAdded", false)
                    val paymentCard: Card? = data?.getParcelableExtra("paymentCard")
//                    val cardName:String  = data?.getParcelableExtra("paymentCard")
                    if (true == updatedCard) {
                        viewModel.adapter.removeAllItems()
                        openDetailScreen(
                            pos = viewModel.cards.value?.size ?: 0,
                            card = paymentCard
                        )
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
                                viewModel.adapter.removeAllItems()
                            }
                        }
                    }
                }
            }
            RequestCodes.REQUEST_ADD_FUNDS_WHEN_ADD -> {
                viewModel.adapter.removeAllItems()
                viewModel.getCards()
            }
            RequestCodes.REQUEST_REORDER_CARD -> {
                if (resultCode == Activity.RESULT_OK) {
                    val cardReorder = data?.getBooleanExtra("cardReorder", false)
                    if (true == cardReorder) {
                        viewModel.adapter.removeAllItems()
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
        } ?: gotoPaymentCardDetailScreen(viewModel.adapter.getDataForPosition(pos))
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
        startActivityForResult(
            AddPaymentCardActivity.newIntent(requireContext()),
            REQUEST_CARD_ADDED
        )
    }

    private fun openStatusScreen(view: View, pos: Int) {
        context?.let { context ->
            startActivityForResult(
                FragmentPresenterActivity.getIntent(
                    context = context,
                    type = Constants.MODE_STATUS_SCREEN,
                    payLoad = viewModel.adapter.getDataForPosition(pos)
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

    private fun getCardFromSerialNumber(serialNumber: String): Card? {
        return viewModel.adapter.getDataList()
            .firstOrNull { it.cardSerialNumber == serialNumber }
    }

    private fun setViewsArray(): ArrayList<GuidedTourViewDetail> {
        val list = ArrayList<GuidedTourViewDetail>()
        val toolBarView: View? = toolbar?.findViewById(R.id.ivRightIcon)
        toolBarView?.let { toolBarRightIcon ->
            list.add(
                GuidedTourViewDetail(
                    toolBarRightIcon,
                    title = getString(Strings.screen_cards_display_text_tour_add_card_heading),
                    description = getString(Strings.screen_cards_display_text_tour_add_card_description),
                    showSkip = false,
                    showPageNo = false,
                    btnText = getString(Strings.screen_cards_display_text_tour_add_card_btn_text),
                    padding = 0f,
                    circleRadius = getDimension(R.dimen._57sdp),
                    callBackListener = tourItemListener
                )
            )
        }
        return list
    }

    private val tourItemListener = object : OnTourItemClickListener {
        override fun onTourCompleted(pos: Int) {
            TourGuideManager.lockTourGuideScreen(
                TourGuideType.CARD_HOME_SCREEN,
                completed = true
            )
        }

        override fun onTourSkipped(pos: Int) {
            TourGuideManager.lockTourGuideScreen(
                TourGuideType.CARD_HOME_SCREEN,
                skipped = true
            )
        }
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.parentViewModel?.isYapCardsFragmentVisible?.removeObservers(this)
    }
}
