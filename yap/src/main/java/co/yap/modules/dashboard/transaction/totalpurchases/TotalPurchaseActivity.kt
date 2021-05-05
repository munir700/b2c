package co.yap.modules.dashboard.transaction.totalpurchases

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingActivity

class TotalPurchaseActivity : BaseBindingActivity<ITotalPurchases.ViewModel>(),
    ITotalPurchases.View {
    override fun getBindingVariable(): Int = BR.totalPurchaseViewModel

    override fun getLayoutId(): Int = R.layout.activity_total_purchase

    override val viewModel: ITotalPurchases.ViewModel
        get() = ViewModelProviders.of(this).get(TotalPurchasesViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.state.totalSpendings.set("92.50")
        viewModel.state.countWithDate.set("Starbucks")
        viewModel.state.toolbarTitle = "5 transactions"

    }

}