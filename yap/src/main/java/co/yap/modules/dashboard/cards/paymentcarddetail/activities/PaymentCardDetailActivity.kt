package co.yap.modules.dashboard.cards.paymentcarddetail.activities

import android.app.Activity
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
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.adapters.TransactionsHeaderAdapter
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.activities.AddFundsActivity
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.CardClickListener
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.PrimaryCardBottomSheet
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.SpareCardBottomSheet
import co.yap.modules.dashboard.cards.paymentcarddetail.interfaces.IPaymentCardDetail
import co.yap.modules.dashboard.cards.paymentcarddetail.limits.activities.CardLimitsActivity
import co.yap.modules.dashboard.cards.paymentcarddetail.removefunds.activities.RemoveFundsActivity
import co.yap.modules.dashboard.cards.paymentcarddetail.statments.activities.CardStatementsActivity
import co.yap.modules.dashboard.cards.paymentcarddetail.viewmodels.PaymentCardDetailViewModel
import co.yap.modules.dashboard.constants.Constants
import co.yap.networking.cards.responsedtos.Card
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.enums.CardStatus
import co.yap.yapcore.helpers.CustomSnackbar
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
                        spareCardBottomSheet = SpareCardBottomSheet(this)
                        spareCardBottomSheet.show(supportFragmentManager, "")
                    }
                }
                R.id.llAddFunds -> {
                    startActivity(AddFundsActivity.newIntent(this, viewModel.card))
                }
                R.id.llFreezeSpareCard -> {
                    viewModel.freezeUnfreezeCard()
                }
                R.id.llFreezePrimaryCard -> {

                    viewModel.freezeUnfreezeCard()
                }
                R.id.llRemoveFunds -> {
                    startActivity(RemoveFundsActivity.newIntent(this, viewModel.card))
                }
                R.id.llCardLimits -> {
                    startActivity(
                        CardLimitsActivity.getIntent(
                            this, viewModel.card
                        )
                    )
                }

                viewModel.EVENT_FREEZE_UNFREEZE_CARD -> {
                    cardFreezeUnfreeze = true
                    if (viewModel.card.blocked) {
                        viewModel.card.blocked = false
                        dismissSnackbar()
                        if (Constants.CARD_TYPE_DEBIT == viewModel.state.cardType) {
                            tvPrimaryCardStatus.text = "Freeze card"
                        } else {
                            tvSpareCardStatus.text = "Freeze card"
                        }
                    } else {
                        viewModel.card.blocked = true
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
                    cardRemoved = true
                    showToast("Card successfully removed!")
                    setupActionsIntent()
                    finish()
                }

            }
        })
    }

    private fun setupView() {
        viewModel.card = intent.getParcelableExtra(CARD)
        viewModel.state.cardType = viewModel.card.cardType
        viewModel.state.cardPanNumber = viewModel.card.maskedCardNo
        viewModel.state.cardName = viewModel.card.cardName

        if (Constants.CARD_TYPE_DEBIT == viewModel.state.cardType) {
            tvTitle.text = "Primary card"
            rlPrimaryCardActions.visibility = View.VISIBLE
            rlCardBalance.visibility = View.GONE
        } else {
            viewModel.getCardBalance()
            tvTitle.text = "Spare card"
            rlSpareCardActions.visibility = View.VISIBLE
        }

        if (viewModel.card.blocked) {
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
                    UpdateCardNameActivity.newIntent(this, viewModel.card),
                    Constants.REQUEST_CARD_NAME_UPDATED
                )
            }
            Constants.EVENT_CHANGE_PIN -> {
                startActivity(Intent(this, ChangeCardPinActivity::class.java))
            }
            Constants.EVENT_VIEW_STATEMENTS -> {
                startActivity(CardStatementsActivity.newIntent(this, viewModel.card))
            }
            Constants.EVENT_REPORT_CARD -> {
                showToast("Report card")
            }
            Constants.EVENT_REMOVE_CARD -> {
                viewModel.removeCard()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            Constants.REQUEST_CARD_NAME_UPDATED -> {
                if (resultCode == Activity.RESULT_OK) {
                    viewModel.state.cardName = data?.getStringExtra("name").toString()
                    viewModel.card.cardName = viewModel.state.cardName
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
        tvCardNumber.text = viewModel.cardDetail.cardNumber
        tvCardValidity.text = viewModel.cardDetail.expiryDate
        tvCvvV.text = viewModel.cardDetail.cvv

        if (Constants.CARD_TYPE_DEBIT == viewModel.state.cardType) {
            tvCardType.text = "Primary card"
        } else {
            tvCardType.text = "Spare card"
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
        rvTransaction.adapter =
            TransactionsHeaderAdapter(
                this,
                viewModel.transactionLogicHelper.transactionList
            )
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

        val updateCard = viewModel.card

        updateCard.physical = viewModel.state.physical
        updateCard.cardType = viewModel.state.cardType
        updateCard.maskedCardNo = viewModel.state.cardPanNumber
        updateCard.cardBalance = viewModel.state.cardBalance
        updateCard.cardName = viewModel.state.cardName
        updateCard.accountType = viewModel.state.accountType

        if (cardFreezeUnfreeze) {
            if (viewModel.card.blocked)
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