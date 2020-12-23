package co.yap.modules.dashboard.cards.home.fragments

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Base64
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
import co.yap.wallet.encriptions.utils.EncodingUtils
import co.yap.wallet.samsung.SamsungPayWalletManager
import co.yap.wallet.samsung.getTestPayloadForSamsung
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
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_yap_cards.*
import java.nio.charset.StandardCharsets

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
                R.id.btnSamsungPay -> {
                    SamsungPayWalletManager.getInstance(requireContext())
                        .getWalletInfo { status, bundle ->
                            requireContext().getTestPayloadForSamsung { paylaod ->
                                val data = paylaod.toByteArray(StandardCharsets.UTF_8)
                                val finalPayload = EncodingUtils.base64Encode(data)
                                SamsungPayWalletManager.getInstance(requireContext())
                                    .addYapCardToSamsungPay(finalPayload)
                            }
//                            viewModel.getCardTokenForSamsungPay { data ->
//                                val toJson =
//                                    GsonBuilder().disableHtmlEscaping().create()
//                                        .toJson(data)
////                                SamsungPayWalletManager.getInstance(requireContext())
////                                    .addYapCardToSamsungPay(
////                                        "eyJDYXJkSW5mbyI6eyJlbmNyeXB0ZWREYXRhIjoiNzM3MWU4MWUzMTUzZmQ5MjE0MTdlOThhZjM0NDc5ZjFjNGMxNmNjMTAzNDBkNTE3YzIxNmIzOTIxOWRjMjk3NDBmNTY2Zjc5NjNhNDhjMjA5YWFmMTRiZjY2NTMxNTdhOWM3Y2U1ZDVjNjJiM2EzNWM4ZDQ1OGE0ZmRlNTcyZTI2Nzc4NDRiZDU5N2ZmYWFiZDBiYmQ5OWI4ZDZhYTMxYzgzYWExNzQ3MTE1ZWJmMmEyYjkzN2E2Y2IyYTRiMGM3YzgyMjlhZmFiZTMwOTFiOTgxMjFhZWE3NzNmZDA3NTI5NWJiNDUwYmM2ZTVmZjA2ODg3ODVmMGZkOTdkMDY3ZTcyMjhjOTg2NDJhNDY2NTExNjM2NDdiNTM2NWM5YTBmNWU0MWFiMDg0NWFiMjAzMGI2ZTdkMDAxMzhmODMwMTUiLCJpdiI6IjRkNTA0NzkzMTdlMTU0NmVjODBiYzY4YWMyZDIyY2U4IiwiZW5jcnlwdGVkS2V5IjoiNzM4ZTg4NDJjYTk3ZjFlZGRhYTBkODJhMGY3YzUyZTQ2MDY0NTQ4ZDFlOGI1ZDRhNGRiZTM2ZDgzN2NlOWFmYzAxMWRlMWJmMTNiMGIxZTZlYzJmNDRhYmQwYTVmNGQ0NzgyN2Q0ZmU3YjlkMWJmMTZmNTBiZDUwYjljYzg5ZDNjMGEyYjEwYmYwMTZiMGYwNmZiNTIxN2JmNTdiMzY3NTJhYTVlYTZmZTI5MDE4YzM3YTU2Nzc2ZDQ5NTkwMjNiMTMzYjE5MDA1NzA2NTBmNmQ1ODBkODY1MjIxYThlMmE4NGNkMTM1Y2FlNTNhMzYxNTEwZDRjZDNmNzA5MDU4NGZmYmYyYjRlYTI2NzVhOGU1MGY3MDQzM2NkYzUxYzExZWM1N2RlYmQ4NmEyZTNlNmNhZTIwM2VlYTUxYzhhYWQyMWM5MGU0MTE2NDA0OWQwNjhlMzQ4NTI0ZWQ3ZTkwZWY4MTljYmQwNWRmOGVkNTIwNTJlYjYwMDRmY2VhNTJhZmViNTFkMTliYTVlZmMxMmYxNTI5NTljODI3OTNlMTVmMzJlZmEwMmM0MTI2N2I3NDQ4YTIzOGVhYTE3ZmE3NDFjY2Q3MjA2ZjNkNGM5YjFkNDlhMzAwMmNlYWUyMjM2YzcyZjRkODkwN2NjZTFiZjkyYTNkODhmMzU2ZjgzMTEiLCJwdWJsaWNLZXlGaW5nZXJwcmludCI6IjU0YmIwM2ZkM2RjZTNkODQ1NDJiZWYwNzBmOTgxODJiMzY2OTg4M2UzN2JjNDI1YjRlYTA0NzZjYTk0YmUyMmYiLCJvYWVwSGFzaGluZ0FsZ29yaXRobSI6IlNIQTUxMiJ9LCJ0b2tlbml6YXRpb25BdXRoZW50aWNhdGlvblZhbHVlIjoiZXlKa1lYUmhWbUZzYVdSVmJuUnBiRlJwYldWemRHRnRjQ0k2SWpJd01qQXRNVEl0TVRkVU1UWTZNVEE2TlRsYUlpd2lhVzVqYkhWa1pXUkdhV1ZzWkhOSmJrOXlaR1Z5SWpvaVlXTmpiM1Z1ZEU1MWJXSmxjbnhoWTJOdmRXNTBSWGh3YVhKNWZHUmhkR0ZXWVd4cFpGVnVkR2xzVkdsdFpYTjBZVzF3SWl3aWMybG5ibUYwZFhKbElqb2lZbUZLU21jM2JWWmlOMVZhVTNFM1lsSjRVazQwVjBSSGFWcEhTMlJUUmtKMVJHVjJhRzgzWm1JeE5qVTFSbGhPVFhwMVQybEhVa1ZrZDJoSGIwaHRaRzF4YTB0cVMyTmFVM3B5Y1VOYU1rUXpkWEJKZW1od00wODBZMUoxUjBod1NrVnZhakZXYmxWTFRYZHNPRlJuU0hVdmJtVm5jMlIzTVU5amEycHpjMEU0YTFkM1NrOXRXWEl3TlVaVGVUaHFUMnMyTVVJd1VFdHZTVmxUVTBaa1JsbFBjeTltY2psa01VcHdPSGhyYlhGUWF6bFdWMHBMZUc5U2RFeG1Vak5LWm0wM1RWRlpkUzlMZHl0QldVazRjRVZzTms1RVdVSjVURWQ0ZDA1SFNtazFTV3RzY25wWFpTdFNUM3BEVGxabFMzaDVUR2szVWtwd1oxQjBOVmcxVVRJd2VITlNZMVJEUzNORFdEbFhWa1JLTTNwMGVtZEJVREJWTjBFclV6SjVZV1VyVG1aTldsbDBNRlpHTm1vMVdtNDNLMEpLZW5CdVMwRkVRMWRzYnpaYU5WQjBhVlJEZHpSNWFGRXpTelZPTVcwemNuZEJQVDBpTENKemFXZHVZWFIxY21WQmJHZHZjbWwwYUcwaU9pSlNVMEV0VTBoQk1qVTJJaXdpZG1WeWMybHZiaUk2SWpNaWZRPT0ifQ=="
////                                    )
//                                val data = String(
//                                    Base64.encode(
//                                        toJson.toByteArray(Charsets.UTF_8),
//                                        Base64.DEFAULT
//                                    ),
//                                    Charsets.UTF_8
//                                )
//
//
//                            }
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
        startActivityForResult(
            AddPaymentCardActivity.newIntent(requireContext()),
            EVENT_CARD_ADDED
        )
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
}
