package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.topup.topupamount

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentTopupAmountBinding
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseFragment
import co.yap.yapcore.helpers.extentions.generateChipViews

//adjust resize need to be added when required activity is created
class TopupAmountFragment : AddMoneyBaseFragment<ITopupAmount.ViewModel>(),
    ITopupAmount.View {
    override val viewModel: TopupAmountViewModel by viewModels()
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_topup_amount

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.lifecycleOwner = this
        viewModel.setAvailableBalance()
        generateChipViews(viewModel.state.denominationChipList.value!!)
    }

    override fun setObservers() {}
    override fun removeObservers() {}

    private fun generateChipViews(selectedList: List<String>) {
        getBinding().cgDenominations.generateChipViews(
            R.layout.item_denominations_chip,
            selectedList
        )
    }

    private fun getBinding() = viewDataBinding as FragmentTopupAmountBinding
}