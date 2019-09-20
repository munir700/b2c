package co.yap.modules.dashboard.cards.paymentcarddetail.activities

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
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.CardClickListener
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.PrimaryCardBottomSheet
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.SpareCardBottomSheet
import co.yap.modules.dashboard.cards.paymentcarddetail.interfaces.IPaymentCardDetail
import co.yap.modules.dashboard.cards.paymentcarddetail.limits.activities.CardLimitsActivity
import co.yap.modules.dashboard.cards.paymentcarddetail.viewmodels.PaymentCardDetailViewModel
import co.yap.modules.dashboard.constants.Constants
import co.yap.networking.cards.responsedtos.Card
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.helpers.CustomSnackbar
import co.yap.yapcore.helpers.Utils
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_payment_card_detail.*


class PaymentCardDetailActivity : BaseBindingActivity<IPaymentCardDetail.ViewModel>(),
    IPaymentCardDetail.View, CardClickListener {

    lateinit var snackbar: Snackbar

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
                    finish()
                }
                R.id.ivMenu -> {
                    if (Constants.CARD_TYPE_DEBIT == viewModel.state.cardType) PrimaryCardBottomSheet(
                        this
                    ).show(supportFragmentManager, "")
                    else SpareCardBottomSheet(this).show(supportFragmentManager, "")
                }
                R.id.llAddFunds -> {
                    showToast("Add Funds")
                    //showCardDetailsPopup()
                }
                R.id.llFreezeSpareCard -> {
                    if (viewModel.card.blocked) {
                        viewModel.card.blocked = false
                        dismissSnackbar()
                        tvSpareCardStatus.text = "Freeze card"
                    } else {
                        viewModel.card.blocked = true
                        showSnackbar()
                        tvSpareCardStatus.text = "Unfreeze card"
                    }

                }
                R.id.llFreezePrimaryCard -> {
                    if (viewModel.card.blocked) {
                        viewModel.card.blocked = false
                        dismissSnackbar()
                        tvPrimaryCardStatus.text = "Freeze card"
                    } else {
                        viewModel.card.blocked = true
                        showSnackbar()
                        tvPrimaryCardStatus.text = "Unfreeze card"
                    }
                }
                R.id.llRemoveFunds -> {
                    showToast("Remove Funds")
                }
                R.id.llCardLimits -> {
                    startActivity(
                        CardLimitsActivity.getIntent(
                            this, viewModel.card
                        )
                    )
                }

            }
        })
    }

    private fun setupView() {
        viewModel.card = intent.getParcelableExtra(CARD)
        viewModel.state.cardType = viewModel.card.cardType
        viewModel.state.cardBalance =
            "AED " + Utils.getFormattedCurrency(viewModel.card.availableBalance)
        viewModel.state.cardPanNumber = viewModel.card.maskedCardNo
        viewModel.state.cardName = viewModel.card.cardName

        if (Constants.CARD_TYPE_DEBIT == viewModel.state.cardType) {
            rlPrimaryCardActions.visibility = View.VISIBLE
            rlCardBalance.visibility = View.GONE
        } else {
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
    }

    private fun showSnackbar() {
        snackbar = CustomSnackbar.getCustomSnackbarSticky(this, clSnackbar, " This card is frozen")
        snackbar.show()

        val tvAction = snackbar.view.findViewById(co.yap.yapcore.R.id.tvAction) as TextView
        tvAction.setOnClickListener {
            viewModel.card.blocked = false
            dismissSnackbar()
            if (Constants.CARD_TYPE_DEBIT == viewModel.state.cardType) {
                tvPrimaryCardStatus.text = "Freeze card"
            } else {
                tvSpareCardStatus.text = "Freeze card"
            }
        }
    }

    fun dismissSnackbar() {
        snackbar.dismiss()
    }


    override fun onClick(eventType: Int) {
        when (eventType) {
            Constants.EVENT_ADD_CARD_NAME -> {
                showToast("Add card name")
            }
            Constants.EVENT_CHANGE_PIN -> {
                showToast("Change PIN")
            }
            Constants.EVENT_VIEW_STATEMENTS -> {
                showToast("View statements")
            }
            Constants.EVENT_REPORT_CARD -> {
                showToast("Report card")
            }
            Constants.EVENT_REMOVE_CARD -> {
                showToast("Remove card")
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
}