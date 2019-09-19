package co.yap.modules.dashboard.cards.paymentcarddetail.activities

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.adapters.TransactionsHeaderAdapter
import co.yap.modules.dashboard.cards.paymentcarddetail.interfaces.IPaymentCardDetail
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.CardClickListener
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.SpareCardBottomSheet
import co.yap.modules.dashboard.cards.paymentcarddetail.viewmodels.PaymentCardDetailViewModel
import co.yap.modules.dashboard.constants.Constants
import co.yap.yapcore.BaseBindingActivity
import kotlinx.android.synthetic.main.activity_payment_card_detail.*


class PaymentCardDetailActivity : BaseBindingActivity<IPaymentCardDetail.ViewModel>(),
    IPaymentCardDetail.View, CardClickListener {

    override val viewModel: IPaymentCardDetail.ViewModel
        get() = ViewModelProviders.of(this).get(PaymentCardDetailViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_payment_card_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpTransactionsListRecyclerView()
        setObservers()
    }


    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.ivBack -> {
                    showToast("Back Pressed")
                }
                R.id.ivMenu -> {
                    SpareCardBottomSheet(this).show(supportFragmentManager, "")
                    //PrimaryCardBottomSheet(this).show(supportFragmentManager, "")
                }
                R.id.llAddFunds -> {
                    showToast("Add Funds")
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

    override fun onClick(eventType: Int) {
      when(eventType){
          Constants.EVENT_ADD_CARD_NAME->{showToast("Add card name")}
          Constants.EVENT_CHANGE_PIN->{showToast("Change PIN")}
          Constants.EVENT_VIEW_STATEMENTS->{showToast("View statements")}
          Constants.EVENT_REPORT_CARD->{showToast("Report card")}
          Constants.EVENT_REMOVE_CARD->{showToast("Remove card")}
      }
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