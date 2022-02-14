package co.yap.modules.dashboard.cards.home.fragments

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.addpaymentcard.main.activities.AddPaymentCardActivity
import co.yap.modules.dashboard.cards.cardlist.CardsListFragment
import co.yap.modules.dashboard.cards.home.interfaces.IYapCards
import co.yap.modules.dashboard.cards.home.viewmodels.YapCardsViewModel
import co.yap.modules.dashboard.cards.paymentcarddetail.activities.PaymentCardDetailActivity
import co.yap.modules.dashboard.cards.reordercard.activities.ReorderCardActivity
import co.yap.modules.dashboard.main.fragments.YapDashboardChildFragment
import co.yap.modules.dashboard.yapit.topup.cardslisting.TopUpBeneficiariesActivity
import co.yap.modules.dummy.ActivityNavigator
import co.yap.modules.dummy.NavigatorProvider
import co.yap.modules.others.fragmentpresenter.activities.FragmentPresenterActivity
import co.yap.modules.setcardpin.activities.SetCardPinWelcomeActivity
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.coreitems.CoreBottomSheetData
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.wallet.samsung.SamsungPayWalletManager
import co.yap.wallet.samsung.isSamsungPayFeatureEnabled
import co.yap.widgets.bottomsheet.BottomSheetConfiguration
import co.yap.widgets.guidedtour.OnTourItemClickListener
import co.yap.widgets.guidedtour.TourSetup
import co.yap.widgets.guidedtour.models.GuidedTourViewDetail
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.constants.RequestCodes.REQUEST_CARD_ADDED
import co.yap.yapcore.enums.*
import co.yap.yapcore.firebase.FirebaseEvent
import co.yap.yapcore.firebase.trackEventWithScreenName
import co.yap.yapcore.helpers.*
import co.yap.yapcore.helpers.extentions.*
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.FeatureProvisioning
import co.yap.yapcore.managers.SessionManager
import com.liveperson.infra.configuration.Configuration.getDimension
import kotlinx.android.synthetic.main.fragment_yap_cards.*

class YapCardsFragment : YapDashboardChildFragment<IYapCards.ViewModel>(), IYapCards.View {

    private var tourStep: TourSetup? = null
    private lateinit var mNavigator: ActivityNavigator
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_yap_cards

    override val viewModel: YapCardsViewModel
        get() = ViewModelProviders.of(this).get(YapCardsViewModel::class.java)

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
        viewModel.cards.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                viewModel.adapter.setList(it)
                updateCardCount()
            }
        })
        SessionManager.card.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.getCards()
            }
        })
        viewModel.parentViewModel?.isYapCardsFragmentVisible?.observe(
            viewLifecycleOwner,
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
        mNavigator = (activity?.applicationContext as NavigatorProvider).provideNavigator()
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
        viewPager2.registerOnPageChangeCallback(pageChangedCallBack)
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
                    if (requireContext().isSamsungPayFeatureEnabled()) {
//                    Internal testing only
//                        viewModel.getSPayCardFormYapCard(card) { SCard ->
//                            SCard?.let {
////                                viewModel.openFavoriteCard(SCard.cardId) {
////
////                                }
//                                requireActivity().alert("Card already added in Samsung Pay")
//                            } ?: run {
//                                confirm(
//                                    message = "This Card is not currently enrolled in Samsung Pay. Would you like to add your card?",
//                                    title = "Add card to Samsung Pay",
//                                    positiveButton = "YES"
//                                ) {
//                                    viewModel.getCardDetails(card.cardSerialNumber) { details ->
//                                    }
//                                }
//                            }
//                        }

//                    Connected TO BE fetch card paylod from BE
                        SamsungPayWalletManager.getInstance(requireContext())
                            .getWalletInfo { i, bundle, state ->
                                addCardToSamSungPay(card)
                            }
                    }
                }
                R.id.imgStatus -> {
                    if (CardStatus.valueOf(card.status).name.isNotEmpty())
                        when (CardStatus.valueOf(card.status)) {
                            CardStatus.ACTIVE -> {
                                if (card.cardType == CardType.DEBIT.type) {
                                    if (PartnerBankStatus.ACTIVATED.status == SessionManager.user?.partnerBankStatus && !card.pinCreated) {
                                        openStatusScreen(
                                            view,
                                            pos
                                        )
                                    } else
                                       openCardDetailBottomSheet(card)
                                } else
                                    openCardDetailBottomSheet(card)
                            }
                            CardStatus.BLOCKED -> {
                                openDetailScreen(
                                    pos
                                )
                            }
                            CardStatus.HOTLISTED -> {
                                openDetailScreen(
                                    pos
                                )
                            }
                            CardStatus.INACTIVE -> {
                                openStatusScreen(
                                    view,
                                    pos
                                )
                            }
                            CardStatus.EXPIRED -> {
                                openDetailScreen(
                                    pos
                                )
                            }
                        }
                }
            }
        }
    }

    private fun addCardToSamSungPay(card: Card) {
        viewModel.getSPayCardFormYapCard(card) { SCard ->
            SCard?.let {
                requireActivity().alert("Card already added in Samsung Pay")
//                SamsungPayWalletManager.getInstance(requireActivity())
//                    .openFavoriteCard(SCard.cardId) { state ->
//                        when (state.status) {
//                            Status.ERROR -> requireActivity().alert(
//                                state.message ?: ""
//                            )
//                            else -> {
//                            }
//                        }
//                    }
            } ?: run {
                confirm(
                    message = "This Card is not currently enrolled in Samsung Pay. Would you like to add your card?",
                    title = "Add card to Samsung Pay",
                    positiveButton = "YES"
                ) {
                    viewModel.getSamsungPayloadAndAddCard(card.cardSerialNumber) { sPayCard, state ->
                    }
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
            R.id.ivLeftIcon -> {
                openCardsList()
            }
        }
    }

    private fun openCardsList() {
        startFragmentForResult<CardsListFragment>(
            CardsListFragment::class.java.name, bundle = bundleOf(
                "cardslist" to viewModel.cards.value
            )
        ) { resultCode, data ->
            if (resultCode == Activity.RESULT_OK) {
                viewModel.adapter.removeAllItems()
                viewModel.getCards()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RequestCodes.REQUEST_PAYMENT_CARD_DETAIL -> {
                if (resultCode == Activity.RESULT_OK) {
                    val updatedCard = data?.getParcelableExtra<Card>("card")
                    val removed = data?.getBooleanExtra("cardRemoved", false)
                    val cardBlocked = data?.getBooleanExtra("cardBlocked", false)
                    val cardReorder = data?.getBooleanExtra("cardReorder", false)
                    val addRemoveFunds = data?.getBooleanExtra("addRemoveFunds", false)
                    val nameUpdate = data?.getBooleanExtra("nameUpdate", false)
                    when {
                        true == removed -> {
                            viewModel.removeCard(updatedCard)
                           viewModel.state.cardIndicator.set(
                                "${viewPager2.currentItem.plus(1)} of ${viewModel.state.totalCardsCount.get()}")
                       }
                        true == cardBlocked -> {
                            viewModel.adapter.removeAllItems()
                            viewModel.getCards()
                        }
                        true == cardReorder -> {
                            viewModel.adapter.removeAllItems()
                            viewModel.getCards()
                        }
                        true == addRemoveFunds -> {
                            viewModel.adapter.removeAllItems()
                            viewModel.getCards()
                        }
                        true == nameUpdate -> {
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
                        SessionManager.updateCardBalance {}
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
        viewModel.selectedCardPosition = pos
        card?.let {
            gotoPaymentCardDetailScreen(it)
        } ?: gotoPaymentCardDetailScreen(viewModel.adapter.getDataForPosition(pos))
    }

    private fun gotoPaymentCardDetailScreen(paymentCard: Card) {
        startActivityForResult(
            PaymentCardDetailActivity.newIntent(
                requireContext(),
                paymentCard
            ), RequestCodes.REQUEST_PAYMENT_CARD_DETAIL
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

    private fun openCardDetailBottomSheet(card: Card) {
        mNavigator.startVerifyPassCodePresenterActivity(
            requireActivity(),
            bundleOf(
                Constants.VERIFY_PASS_CODE_BTN_TEXT to getString(
                    Strings.screen_verify_passcode_button_verify
                )
            )
        ) { resultCode, data ->
            if (resultCode == Activity.RESULT_OK) {
                trackEventWithScreenName(if (co.yap.modules.others.helper.Constants.CARD_TYPE_DEBIT == card.cardType) FirebaseEvent.CLICK_CARD_DETAILS_CARD_MAIN_SCREEN else FirebaseEvent.CLICK_CARD_DETAILS_VIRTUAL_CARD_DASHBOARD)
                viewModel.getCardDetail(card.cardSerialNumber) {
                    launchBottomSheetSegment(
                        cardBottomSheetItemClickListener,
                        configuration = BottomSheetConfiguration(
                            heading = Translator.getString(
                                requireContext(),
                                Strings.screen_cards_display_text_bottom_sheet_heading,
                                card.cardName.toString()
                            )
                        ),
                        viewType = Constants.VIEW_CARD_DETAIL_ITEM,
                        listData = viewModel.list
                    )
                }
            }
        }
    }

    private val cardBottomSheetItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is CoreBottomSheetData) {
                when (view.id) {
                    R.id.tvCopyCard -> {
                        Utils.copyToClipboard(view.context, data.subTitle ?: "")
                        view.context.toast(
                            Translator.getString(
                                requireContext(),
                                Strings.screen_more_detail_display_text_copied_to_clipboard
                            ),
                            Toast.LENGTH_SHORT
                        )
                    }
                }
            }
        }
    }

    private val pageChangedCallBack = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            if (position.plus(1) < viewModel.cards.value?.size ?: 0) viewModel.state.cardIndicator.set(
                "${position.plus(1)} of ${viewModel.state.totalCardsCount.get()}"
            )
        }
    }

    private fun updateCardCount() {
        viewModel.updateCardCount(viewModel.adapter.itemCount - if (viewModel.state.enableAddCard.get()) 1 else 0)
    }
}
