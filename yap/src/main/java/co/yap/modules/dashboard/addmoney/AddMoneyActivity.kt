package co.yap.modules.dashboard.addmoney

import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.BR
import co.yap.yapcore.BaseBindingActivity

class AddMoneyActivity : BaseBindingActivity<IAddMoney.ViewModel>(), IAddMoney.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_add_money

    override val viewModel: IAddMoney.ViewModel
        get() = ViewModelProviders.of(this).get(AddMoneyViewModel::class.java)
}