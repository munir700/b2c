package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.topup.topupamount


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentTopupAmountBinding
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseFragment

class TopupAmountFragment: AddMoneyBaseFragment<ITopupAmount.ViewModel>(),
    ITopupAmount.View {
    override val viewModel: TopupAmountViewModel by viewModels()
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_topup_amount

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.lifecycleOwner = this
        viewModel.setAvailableBalance(resources)
    }

    override fun setObservers() {

    }

    override fun removeObservers() {

    }

}