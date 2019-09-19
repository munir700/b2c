package co.yap.modules.dashboard.cards.paymentcarddetail.limits.activities

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.CardClickListener
import co.yap.modules.dashboard.cards.paymentcarddetail.limits.interfaces.ICardLimits
import co.yap.modules.dashboard.cards.paymentcarddetail.limits.viewmodel.CardLimitViewModel
import co.yap.modules.dashboard.constants.Constants
import co.yap.yapcore.BaseBindingActivity

class CardLimitsActivity : BaseBindingActivity<ICardLimits.ViewModel>(),
    ICardLimits.View, CardClickListener {

    override val viewModel: ICardLimits.ViewModel
        get() = ViewModelProviders.of(this).get(CardLimitViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_card_limits

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.tbBtnBack -> {
                    showToast("Back Pressed")
                }
            }
        })
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
}