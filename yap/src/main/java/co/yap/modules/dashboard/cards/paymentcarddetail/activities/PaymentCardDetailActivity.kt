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
import co.yap.modules.dashboard.cards.paymentcarddetail.viewmodels.PaymentCardDetailViewModel
import co.yap.modules.dashboard.constants.Constants
import co.yap.modules.onboarding.activities.OnboardingActivity
import co.yap.modules.onboarding.enums.AccountType
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.helpers.Utils
import kotlinx.android.synthetic.main.activity_payment_card_detail.*


class PaymentCardDetailActivity : BaseBindingActivity<IPaymentCardDetail.ViewModel>(),
    IPaymentCardDetail.View, CardClickListener {

    companion object {
        private const val CARD_TYPE = "cardType"
        private const val CARD_BALANCE = "cardBalance"
        private const val CARD_NAME = "cardName"
        private const val CARD_NUMBER = "cardNumber"
        fun newIntent(context: Context, cardType: String, cardBalance: String, cardName: String, cardNumber: String): Intent {
            val intent = Intent(context, PaymentCardDetailActivity::class.java)
            intent.putExtra(CARD_TYPE, cardType)
            intent.putExtra(CARD_BALANCE, cardBalance)
            intent.putExtra(CARD_NAME, cardName)
            intent.putExtra(CARD_NUMBER, cardNumber)
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
                    showToast("Freeze Spare Card")
                }
                R.id.llFreezePrimaryCard -> {
                    showToast("Freeze Primary Card")
                }
                R.id.llRemoveFunds -> {
                    showToast("Remove Funds")
                }
                R.id.llCardLimits -> {
                    showToast("Set Limits")
                }

            }
        })
    }

    private fun setupView() {
        viewModel.state.cardType =  intent.getStringExtra(CARD_TYPE)
        viewModel.state.cardBalance =  "AED "+Utils.getFormattedCurrency(intent.getStringExtra(CARD_BALANCE))
        viewModel.state.cardPanNumber =  intent.getStringExtra(CARD_NUMBER)
        viewModel.state.cardName =  intent.getStringExtra(CARD_NAME)

        if (Constants.CARD_TYPE_DEBIT == viewModel.state.cardType)  {
            rlPrimaryCardActions.visibility = View.VISIBLE
            rlCardBalance.visibility = View.GONE
        }
        else {
            rlSpareCardActions.visibility = View.VISIBLE
        }
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
                showToast("ReportLostOrStolenCardChildViewModels card")
            }
            Constants.EVENT_REMOVE_CARD -> {
                showToast("Remove card")
            }
        }
    }

    private fun showCardDetailsPopup() {
        val dialog = Dialog(this)
        dialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog .setCancelable(false)
        dialog .setContentView(R.layout.dialog_card_details)
        dialog .window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btnClose = dialog .findViewById(R.id.ivCross) as ImageView
        btnClose.setOnClickListener {
            dialog .dismiss()
        }
        dialog .show()
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