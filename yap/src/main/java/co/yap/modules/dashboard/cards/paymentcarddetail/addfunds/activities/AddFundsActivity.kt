package co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.activities

import android.os.Bundle
import android.text.InputFilter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.interfaces.IFundActions
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.viewmodels.AddFundsViewModel
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.helpers.CustomSnackbar
import co.yap.yapcore.helpers.DecimalDigitsInputFilter
import kotlinx.android.synthetic.main.activity_fund_actions.*


class AddFundsActivity : BaseBindingActivity<IFundActions.ViewModel>(),
    IFundActions.View {


    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_fund_actions

    override val viewModel: IFundActions.ViewModel
        get() = ViewModelProviders.of(this).get(AddFundsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        etAmount.filters = arrayOf<InputFilter>(
            DecimalDigitsInputFilter(2)
        )
        viewModel.clickEvent.observe(this, Observer {
            viewModel.state.toast = "i am here"
        })
        viewModel.errorEvent.observe(this, Observer {
            CustomSnackbar.showErrorCustomSnackbar(context = this, layout = clSnackbar, message = viewModel.state.errorDescription)
        })
    }


}