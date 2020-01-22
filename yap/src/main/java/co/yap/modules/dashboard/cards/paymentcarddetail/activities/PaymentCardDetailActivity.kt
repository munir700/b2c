package co.yap.modules.dashboard.cards.paymentcarddetail.activities

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.yap.BR
import co.yap.R
import co.yap.databinding.ActivityPaymentCardDetailBinding
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.activities.AddFundsActivity
import co.yap.modules.dashboard.cards.paymentcarddetail.forgotcardpin.activities.ForgotCardPinActivity
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.CardClickListener
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.PrimaryCardBottomSheet
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.SpareCardBottomSheet
import co.yap.modules.dashboard.cards.paymentcarddetail.interfaces.IPaymentCardDetail
import co.yap.modules.dashboard.cards.paymentcarddetail.limits.activities.CardLimitsActivity
import co.yap.modules.dashboard.cards.paymentcarddetail.removefunds.activities.RemoveFundsActivity
import co.yap.modules.dashboard.cards.paymentcarddetail.statments.activities.CardStatementsActivity
import co.yap.modules.dashboard.cards.paymentcarddetail.viewmodels.PaymentCardDetailViewModel
import co.yap.modules.dashboard.cards.reordercard.activities.ReorderCardActivity
import co.yap.modules.dashboard.cards.reportcard.activities.ReportLostOrStolenCardActivity
import co.yap.modules.dashboard.home.adaptor.TransactionsHeaderAdapter
import co.yap.modules.dashboard.home.filters.activities.TransactionFiltersActivity
import co.yap.modules.dashboard.home.filters.models.TransactionFilters
import co.yap.modules.others.helper.Constants
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.translation.Strings
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.enums.CardStatus
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.getCustomSnackbarSticky
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.MyUserManager
import com.ezaka.customer.app.utils.toCamelCase
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_payment_card_detail.*
import kotlinx.android.synthetic.main.layout_card_info.*


class PaymentCardDetailActivity : BaseBindingActivity<IPaymentCardDetail.ViewModel>(),
    IPaymentCardDetail.View, CardClickListener {

    private var snackbar: Snackbar? = null
    private lateinit var primaryCardBottomSheet: PrimaryCardBottomSheet
    private lateinit var spareCardBottomSheet: SpareCardBottomSheet

    private var cardFreezeUnfreeze: Boolean = false
    private var cardRemoved: Boolean = false
    private var limitsUpdated: Boolean = false
    private var nameUpdated: Boolean = false

    companion object {
        private const val CARD = "card"
        fun newIntent(context: Context, card: Card): Intent {
            val intent = Intent(context, PaymentCardDetailActivity::class.java)
            intent.putExtra(CARD, card)
            return intent
        }
    }

    override val viewModel: IPaymentCardDetail.ViewModel
        get() = ViewModelProviders.of(this).get(PaymentCardDetailViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_payment_card_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpTransactionsListRecyclerView()
        setObservers()
        setupView()
    }

    private fun getBindings(): ActivityPaymentCardDetailBinding {
        return viewDataBinding as ActivityPaymentCardDetailBinding
    }


    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
        viewModel.card.observe(this, Observer {
            viewModel.cardTransactionRequest.serialNumber = it.cardSerialNumber
            viewModel.requestAccountTransactions()
        })

        viewModel.transactionsLiveData.observe(this, Observer {
            if (viewModel.isLoadMore.value!!) {
                if (getRecycleViewAdaptor().itemCount == 0) getBindings().appbar.setExpanded(true)

                if (getRecycleViewAdaptor().itemCount > 0)
                    getRecycleViewAdaptor().removeItemAt(getRecycleViewAdaptor().itemCount - 1)

                val listToAppend: MutableList<HomeTransactionListData> = mutableListOf()
                val oldData = getRecycleViewAdaptor().getDataList()
                for (parentItem in it) {

                    var shouldAppend = false
                    for (i in 0 until oldData.size) {
                        if (parentItem.date == oldData[i].date) {
                            if (parentItem.content.size != oldData[i].content.size) {
                                shouldAppend = true
                                break
                            }
                            shouldAppend = true
                            break
                        }
                    }
                    if (!shouldAppend)
                        listToAppend.add(parentItem)
                }
                getRecycleViewAdaptor().addList(listToAppend)
            } else {
                if (it.isEmpty()) {
                    viewModel.state.isTxnsEmpty.set(true)
                } else {
                    viewModel.state.isTxnsEmpty.set(false)
                    getRecycleViewAdaptor().setList(it)
                }
            }
        })

        getBindings().rvTransaction.addOnScrollListener(
            object :
                RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager =
                        getBindings().rvTransaction.layoutManager as LinearLayoutManager
                    val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
                    if (lastVisiblePosition == layoutManager.itemCount - 1) {
                        if (!viewModel.isLoadMore.value!! && !viewModel.isLast.value!!) {
                            viewModel.isLoadMore.value = true
                        }
                    }
                }
            })

        viewModel.isLoadMore.observe(this, Observer {
            if (it) {
                viewModel.cardTransactionRequest.number =
                    viewModel.cardTransactionRequest.number + 1
                val item =
                    getRecycleViewAdaptor().getDataForPosition(getRecycleViewAdaptor().itemCount - 1)
                        .copy()
                item.totalAmount = "loader"
                getRecycleViewAdaptor().run { addListItem(item) }
                viewModel.loadMore()
            }
        })

        ///

//        viewModel.transactionsLiveData.observe(this, Observer {
//            tvNoTransaction.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
//            if (viewModel.isLoadMore.value!!) {
//                getRecycleViewAdaptor().setList(it)
//            } else {
//                getRecycleViewAdaptor().setList(it)
//            }
//        })
//
//        getRecycleViewAdaptor().setItemListener(listener)
//        rvTransaction.addOnScrollListener(object :
//            RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy);
//                val layoutManager =
//                    rvTransaction.layoutManager as LinearLayoutManager
//                val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
//                if (lastVisiblePosition == layoutManager.itemCount - 1) {
//                    if (!viewModel.isLoadMore.value!!) {
//                        viewModel.isLoadMore.value = true
//                        viewModel.cardTransactionRequest.number =
//                            viewModel.cardTransactionRequest.number + 1
//                        viewModel.loadMore()
//                    }
//                }
//            }
//        })
    }

    private val clickObserver = Observer<Int> {
        when (it) {
            R.id.ivBack -> {
                setupActionsIntent()
                finish()
            }
            R.id.ivMenu -> {
                if (Constants.CARD_TYPE_DEBIT == viewModel.state.cardType) {
                    primaryCardBottomSheet = PrimaryCardBottomSheet(this)
                    primaryCardBottomSheet.show(supportFragmentManager, "")
                } else {
                    spareCardBottomSheet =
                        SpareCardBottomSheet(viewModel.card.value?.physical!!, this)
                    spareCardBottomSheet.show(supportFragmentManager, "")
                }
            }
            R.id.llAddFunds -> {
                startActivityForResult(
                    AddFundsActivity.newIntent(this, viewModel.card.value!!),
                    Constants.REQUEST_ADD_REMOVE_FUNDS
                )
            }
            R.id.llFreezeSpareCard -> {
                viewModel.freezeUnfreezeCard()
            }
            R.id.llFreezePrimaryCard -> {

                viewModel.freezeUnfreezeCard()
            }
            R.id.llRemoveFunds -> {
                if (!viewModel.card.value?.blocked!!) {
                    startActivityForResult(
                        RemoveFundsActivity.newIntent(this, viewModel.card.value!!),
                        Constants.REQUEST_ADD_REMOVE_FUNDS
                    )
                } else {
                    showToast("Please unfreeze card to use this feature")
                }
            }
            R.id.llCardLimits -> {
                startActivityForResult(
                    CardLimitsActivity.getIntent(this, viewModel.card.value!!),
                    Constants.REQUEST_SET_LIMITS
                )
            }
            R.id.rlFilter -> {
                if (viewModel.state.isTxnsEmpty.get() == false || viewModel.state.filterCount.get() ?: 0 > 0)
                    startActivityForResult(
                        TransactionFiltersActivity.newIntent(
                            this,
                            viewModel.transactionFilters
                        ),
                        RequestCodes.REQUEST_TXN_FILTER
                    )
            }
            viewModel.EVENT_FREEZE_UNFREEZE_CARD -> {
                cardFreezeUnfreeze = true
                viewModel.card.value?.blocked = viewModel.card.value?.blocked != true
                checkFreezeUnfreezStatus()
            }

            viewModel.EVENT_CARD_DETAILS -> {
                showCardDetailsPopup()
            }

            viewModel.EVENT_REMOVE_CARD -> {
                MyUserManager.updateCardBalance()
                cardRemoved = true
                showToast("Card successfully removed!")
                setupActionsIntent()
                finish()
            }
        }
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {

        }
    }

    private fun getRecycleViewAdaptor(): TransactionsHeaderAdapter {
        return (rvTransaction.adapter as TransactionsHeaderAdapter)
    }

    private fun setupView() {
        viewModel.card.value = intent.getParcelableExtra(CARD)
        viewModel.state.cardType = viewModel.card.value?.cardType!!
        viewModel.state.cardPanNumber = viewModel.card.value?.maskedCardNo!!
        viewModel.card.value?.cardName?.let { cardName ->
            viewModel.card.value?.nameUpdated?.let {
                if (it) {
                    viewModel.state.cardName = cardName
                } else {
                    viewModel.state.cardName = cardName.toCamelCase()
                }
            }
        }


        viewModel.card.value?.status?.let {
            when (it) {
                CardStatus.ACTIVE.name -> {
                }
                CardStatus.BLOCKED.name -> {
                }
                CardStatus.HOTLISTED.name -> {
                    showLostStolenSnackbar()
                }
                CardStatus.INACTIVE.name -> {
                }
            }
        }

        if (Constants.CARD_TYPE_DEBIT == viewModel.state.cardType) {
            viewModel.state.cardTypeText = Constants.TEXT_PRIMARY_CARD
            rlPrimaryCardActions.visibility = View.VISIBLE
            rlCardBalance.visibility = View.GONE
        } else {
            if (viewModel.card.value?.physical!!) {
                viewModel.state.cardTypeText = Constants.TEXT_SPARE_CARD_PHYSICAL
            } else {
                viewModel.state.cardTypeText = Constants.TEXT_SPARE_CARD_VIRTUAL
            }
            viewModel.getCardBalance()
            rlSpareCardActions.visibility = View.VISIBLE
        }
        checkFreezeUnfreezStatus()

        btnCardDetails.setOnClickListener { viewModel.getCardDetails() }
    }

    private fun checkFreezeUnfreezStatus() {
        viewModel.card.value?.blocked?.let {
            if (it) {
                showSnackbar()
                if (Constants.CARD_TYPE_DEBIT == viewModel.state.cardType) {
                    tvPrimaryCardStatus.text = "Unfreeze card"
                } else {
                    tvSpareCardStatus.text = "Unfreeze card"
                }
            } else {
                dismissSnackbar()
                if (Constants.CARD_TYPE_DEBIT == viewModel.state.cardType) {
                    tvPrimaryCardStatus.text = "Freeze card"
                } else {
                    tvSpareCardStatus.text = "Freeze card"
                }
            }
        }
    }

    private fun showSnackbar() {
        if (snackbar?.isShown != true) {
            snackbar = window.decorView.getCustomSnackbarSticky(
                clSnackbar,
                getString(Strings.screen_cards_display_text_freeze_card),
                getString(Strings.screen_cards_display_text_freeze_card_action)
            )

            snackbar?.show()

            val tvAction = snackbar?.view?.findViewById(co.yap.yapcore.R.id.tvAction) as TextView
            tvAction.setOnClickListener {
                viewModel.freezeUnfreezeCard()
            }
        }
    }

    private fun showLostStolenSnackbar() {
        snackbar = window.decorView.getCustomSnackbarSticky(
            clSnackbar,
            getString(Strings.screen_cards_display_text_lost_stolen_card),
            getString(Strings.screen_cards_display_text_lost_stolen_card_action)
        )
        snackbar?.show()
        val tvAction = snackbar?.view?.findViewById(co.yap.yapcore.R.id.tvAction) as TextView
        tvAction.setOnClickListener {
            startReorderCardFlow()
        }
    }

    private fun dismissSnackbar() {
        snackbar?.dismiss()
    }

    override fun onClick(eventType: Int) {

        if (Constants.CARD_TYPE_DEBIT == viewModel.state.cardType) {
            primaryCardBottomSheet.dismiss()
        } else {
            spareCardBottomSheet.dismiss()
        }

        when (eventType) {

            Constants.EVENT_ADD_CARD_NAME -> {
                startActivityForResult(
                    UpdateCardNameActivity.newIntent(this, viewModel.card.value!!),
                    Constants.REQUEST_CARD_NAME_UPDATED
                )
            }
            Constants.EVENT_CHANGE_PIN -> {
                if (!viewModel.card.value?.blocked!!) {
                    startActivity(
                        ChangeCardPinActivity.newIntent(
                            this,
                            viewModel.card.value?.cardSerialNumber!!
                        )
                    )
                } else {
                    showToast("Please unfreeze card to use this feature")
                }
            }

            Constants.EVENT_FORGOT_CARD_PIN -> {
                startActivity(
                    ForgotCardPinActivity.newIntent(
                        this,
                        viewModel.card.value!!.cardSerialNumber
                    )
                )
            }

            Constants.EVENT_VIEW_STATEMENTS -> {
                startActivity(CardStatementsActivity.newIntent(this, viewModel.card.value!!))
            }
            Constants.EVENT_REPORT_CARD -> {
                viewModel.card.value?.let {
                    startActivityForResult(
                        ReportLostOrStolenCardActivity.newIntent(
                            this,
                            viewModel.card.value!!
                        ), Constants.REQUEST_REPORT_LOST_OR_STOLEN
                    )
                }
            }

            Constants.EVENT_REMOVE_CARD -> {
                showRemoveCardPopup()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            Constants.REQUEST_CARD_NAME_UPDATED -> {
                if (resultCode == Activity.RESULT_OK) {
                    nameUpdated = true
                    viewModel.state.cardName = data?.getStringExtra("name").toString()
                    viewModel.card.value?.cardName = viewModel.state.cardName
                    viewModel.card.value?.nameUpdated = true
                }
            }

            Constants.REQUEST_ADD_REMOVE_FUNDS -> {
                checkFreezeUnfreezStatus()
                if (resultCode == Activity.RESULT_OK) {
                    // Send Broadcast for updating transactions list in `Home Fragment`
                    val intent =
                        Intent(co.yap.yapcore.constants.Constants.BROADCAST_UPDATE_TRANSACTION)
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent)

                    viewModel.card.value?.availableBalance =
                        data?.getStringExtra("newBalance").toString()
                    viewModel.state.cardBalance =
                        "AED " + Utils.getFormattedCurrency(data?.getStringExtra("newBalance").toString())
                }
            }

            Constants.REQUEST_REPORT_LOST_OR_STOLEN -> {
                if (resultCode == Activity.RESULT_OK) {
                    setupCardBlockActionsIntent()
                    finish()
                }
            }
            Constants.REQUEST_SET_LIMITS -> {
                if (resultCode == Activity.RESULT_OK) {
                    limitsUpdated = true
                    viewModel.card.value = data?.getParcelableExtra<Card>("card")!!
                }
            }
            RequestCodes.REQUEST_REORDER_CARD -> {
                if (resultCode == Activity.RESULT_OK) {
                    setupCardReorderActionsIntent()
                    finish()
                }
            }
            RequestCodes.REQUEST_TXN_FILTER -> {
                if (resultCode == Activity.RESULT_OK) {
                    val filters: TransactionFilters? =
                        data?.getParcelableExtra<TransactionFilters?>("txnRequest")
                    if (viewModel.transactionFilters != filters) {
                        setTransactionRequest(filters)
                        viewModel.requestAccountTransactions()
                    }
                }
            }

        }
    }

    private fun setTransactionRequest(filters: TransactionFilters?) {
        filters?.let {
            viewModel.transactionFilters = it
            viewModel.cardTransactionRequest.number = 0
            viewModel.cardTransactionRequest.size = 20
            viewModel.cardTransactionRequest.txnType = getTxnType()
            viewModel.cardTransactionRequest.amountStartRange = it.amountStartRange
            viewModel.cardTransactionRequest.amountEndRange = it.amountEndRange
            viewModel.cardTransactionRequest.title = null
            viewModel.cardTransactionRequest.totalAppliedFilter = getTotalAppliedFilter()
            viewModel.state.filterCount.set(viewModel.cardTransactionRequest.totalAppliedFilter)
        }
    }

    private fun getTxnType(): String? {
        return if (viewModel.transactionFilters.incomingTxn == false && viewModel.transactionFilters.outgoingTxn == false || viewModel.transactionFilters.incomingTxn == true && viewModel.transactionFilters.outgoingTxn == true) {
            null
        } else if (viewModel.transactionFilters.incomingTxn == true)
            co.yap.yapcore.constants.Constants.MANUAL_CREDIT
        else
            co.yap.yapcore.constants.Constants.MANUAL_DEBIT
    }

    private fun getTotalAppliedFilter(): Int {
        var count = viewModel.transactionFilters.totalAppliedFilter
        if (viewModel.transactionFilters.incomingTxn == true) count++
        if (viewModel.transactionFilters.outgoingTxn == true) count++

        return count
    }

    private fun startReorderCardFlow() {
        viewModel.card.value?.let {
            startActivityForResult(
                ReorderCardActivity.newIntent(
                    this@PaymentCardDetailActivity,
                    it
                ), RequestCodes.REQUEST_REORDER_CARD
            )
        }
    }

    private fun showCardDetailsPopup() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_card_details)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btnClose = dialog.findViewById(R.id.ivCross) as ImageView
        val tvCardNumber = dialog.findViewById(R.id.tvCardNumberValue) as TextView
        val tvCardValidity = dialog.findViewById(R.id.tvCardValidityValue) as TextView
        val tvCvvV = dialog.findViewById(R.id.tvCvvValue) as TextView
        val tvCardType = dialog.findViewById(R.id.tvCardType) as TextView
        tvCardValidity.text = viewModel.cardDetail.expiryDate
        tvCvvV.text = viewModel.cardDetail.cvv


        if (null != viewModel.cardDetail.cardNumber) {
            if (viewModel.cardDetail.cardNumber?.trim()?.contains(" ")!!) {
                tvCardNumber.text = viewModel.cardDetail.cardNumber
            } else {
                if (viewModel.cardDetail.cardNumber?.length == 16) {
                    val formattedCardNumber: StringBuilder =
                        StringBuilder(viewModel.cardDetail.cardNumber)
                    formattedCardNumber.insert(4, " ")
                    formattedCardNumber.insert(9, " ")
                    formattedCardNumber.insert(14, " ")
                    tvCardNumber.text = formattedCardNumber
                }
            }
        }

        if (Constants.CARD_TYPE_DEBIT == viewModel.state.cardType) {
            tvCardType.text = "Primary card"
        } else {
            if (viewModel.card.value?.nameUpdated!!) {
                tvCardType.text = viewModel.card.value?.cardName!!
            } else {
                if (viewModel.card.value?.physical!!) {
                    tvCardType.text = Constants.TEXT_SPARE_CARD_PHYSICAL
                } else {
                    tvCardType.text = Constants.TEXT_SPARE_CARD_VIRTUAL
                }
            }
        }
        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun setUpTransactionsListRecyclerView() {
        rvTransaction.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        rvTransaction.layoutManager = layoutManager
        rvTransaction.adapter = TransactionsHeaderAdapter(mutableListOf(), adaptorlistener)
    }

    private val adaptorlistener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
//            if (data is Content) {
//                startActivity(
//                    TransactionDetailsActivity.newIntent(
//                        applicationContext,
//                        (data as HomeTransactionListData).content[0].transactionId
//                    )
//                )
//            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clickEvent.removeObservers(this)
    }

    override fun onBackPressed() {
        setupActionsIntent()
        super.onBackPressed()
    }

    private fun setupActionsIntent() {

        if (cardFreezeUnfreeze || cardRemoved || limitsUpdated || nameUpdated) {
            val updateCard = viewModel.card.value!!
            updateCard.cardBalance = viewModel.state.cardBalance
            updateCard.cardName = viewModel.state.cardName
            updateCard.nameUpdated = nameUpdated

            if (cardFreezeUnfreeze) {
                if (viewModel.card.value?.blocked!!)
                    updateCard.status = "BLOCKED"
                else
                    updateCard.status = "ACTIVE"
            }

            val returnIntent = Intent()
            returnIntent.putExtra("card", updateCard)
            returnIntent.putExtra("cardRemoved", cardRemoved)
            setResult(Activity.RESULT_OK, returnIntent)
        }
    }

    private fun setupCardBlockActionsIntent() {
        val returnIntent = Intent()
        returnIntent.putExtra("cardBlocked", true)
        setResult(Activity.RESULT_OK, returnIntent)
    }

    private fun setupCardReorderActionsIntent() {
        val returnIntent = Intent()
        returnIntent.putExtra("cardReorder", true)
        setResult(Activity.RESULT_OK, returnIntent)
    }

    private fun showRemoveCardPopup() {
        val builder = AlertDialog.Builder(this@PaymentCardDetailActivity)
        builder.setTitle("Remove card from YAP account")
        builder.setMessage("Once removed, the balance from this card will be transferred to your main card.")
        builder.setPositiveButton("CONFIRM") { _, _ ->
            viewModel.removeCard()
        }

        builder.setNeutralButton("CANCEL") { _, _ ->

        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}