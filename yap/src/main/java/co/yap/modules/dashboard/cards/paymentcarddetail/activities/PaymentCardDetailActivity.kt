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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.yap.BR
import co.yap.R
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
import co.yap.modules.dashboard.cards.reportcard.activities.ReportLostOrStolenCardActivity
import co.yap.modules.dashboard.home.adaptor.TransactionsHeaderAdapter
import co.yap.modules.dashboard.transaction.activities.TransactionDetailsActivity
import co.yap.modules.others.helper.Constants
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.cards.responsedtos.CardBalance
import co.yap.networking.transactions.responsedtos.transaction.Content
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.helpers.CustomSnackbar
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.MyUserManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_payment_card_detail.*
import kotlinx.android.synthetic.main.layout_card_info.*


class PaymentCardDetailActivity : BaseBindingActivity<IPaymentCardDetail.ViewModel>(),
    IPaymentCardDetail.View, CardClickListener {

    private lateinit var snackbar: Snackbar
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

    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
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

                viewModel.EVENT_FREEZE_UNFREEZE_CARD -> {
                    cardFreezeUnfreeze = true
                    if (viewModel.card.value?.blocked!!) {
                        viewModel.card.value?.blocked = false
                        dismissSnackbar()
                        if (Constants.CARD_TYPE_DEBIT == viewModel.state.cardType) {
                            tvPrimaryCardStatus.text = "Freeze card"
                        } else {
                            tvSpareCardStatus.text = "Freeze card"
                        }
                    } else {
                        viewModel.card.value?.blocked = true
                        showSnackbar()
                        if (Constants.CARD_TYPE_DEBIT == viewModel.state.cardType) {
                            tvPrimaryCardStatus.text = "Unfreeze card"
                        } else {
                            tvSpareCardStatus.text = "Unfreeze card"
                        }
                    }
                }

                viewModel.EVENT_CARD_DETAILS -> {
                    showCardDetailsPopup()

                }

                viewModel.EVENT_REMOVE_CARD -> {
                    try {
                        val updatedCardBalance =
                            (MyUserManager.cardBalance.value?.availableBalance?.toDouble()?.plus(
                                viewModel.card.value?.availableBalance!!.toDouble()
                            ))
                        MyUserManager.cardBalance.value =
                            CardBalance(availableBalance = updatedCardBalance.toString())
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    cardRemoved = true
                    showToast("Card successfully removed!")
                    setupActionsIntent()
                    finish()
                }

            }
        })

        viewModel.card.observe(this, Observer {
            viewModel.cardTransactionRequest.serialNumber = viewModel.card.value?.cardSerialNumber!!
            viewModel.cardTransactionRequest.number = 1
            viewModel.requestAccountTransactions()
        })

        viewModel.transactionsLiveData.observe(this, Observer {
            ivNoTransaction.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            if (viewModel.isLoadMore.value!!) {
                getRecycleViewAdaptor().setList(it)
            } else {
                getRecycleViewAdaptor().setList(it)
            }
        })

        getRecycleViewAdaptor().setItemListener(listener)
        rvTransaction.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy);
                val layoutManager =
                    rvTransaction.layoutManager as LinearLayoutManager
                val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
                if (lastVisiblePosition == layoutManager.itemCount - 1) {
                    if (!viewModel.isLoadMore.value!!) {
                        viewModel.isLoadMore.value = true
                        viewModel.cardTransactionRequest.number =
                            viewModel.cardTransactionRequest.number + 1
                        viewModel.loadMore()
                    }
                }
            }
        })
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
        viewModel.state.cardName = viewModel.card.value?.cardName!!

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

        if (viewModel.card.value?.blocked!!) {
            showSnackbar()
            if (Constants.CARD_TYPE_DEBIT == viewModel.state.cardType) {
                tvPrimaryCardStatus.text = "Unfreeze card"
            } else {
                tvSpareCardStatus.text = "Unfreeze card"
            }

        }

        btnCardDetails.setOnClickListener { viewModel.getCardDetails() }
    }

    private fun showSnackbar() {
        snackbar = CustomSnackbar.getCustomSnackbarSticky(this, clSnackbar, " This card is frozen")
        snackbar.show()

        val tvAction = snackbar.view.findViewById(co.yap.yapcore.R.id.tvAction) as TextView
        tvAction.setOnClickListener {
            /* viewModel.card.blocked = false
             dismissSnackbar()
             if (Constants.CARD_TYPE_DEBIT == viewModel.state.cardType) {
                 tvPrimaryCardStatus.text = "Freeze card"
             } else {
                 tvSpareCardStatus.text = "Freeze card"
             }*/
            viewModel.freezeUnfreezeCard()
        }
    }

    fun dismissSnackbar() {
        snackbar.dismiss()
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
                startActivityForResult(
                    ReportLostOrStolenCardActivity.newIntent(
                        this,
                        viewModel.card.value!!
                    ), Constants.REQUEST_REPORT_LOST_OR_STOLEN
                )

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
                if (resultCode == Activity.RESULT_OK) {
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
            if (data is Content) {
                startActivity(
                    TransactionDetailsActivity.newIntent(
                        applicationContext,
                        (data as HomeTransactionListData).content[0].transactionId
                    )
                )
            }
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