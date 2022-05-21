package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.banklist

import android.view.View
import androidx.databinding.ViewDataBinding
import co.yap.BR
import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel
import co.yap.yapcore.BaseSearchRecyclerAdapter
import co.yap.yapcore.BaseViewHolder

class BankListAdapter(
    mValue: MutableList<BankListMainModel>
) : BaseSearchRecyclerAdapter<BankListMainModel, BankListItemViewModel, BaseViewHolder<BankListMainModel, BankListItemViewModel>>(
    mValue
) {
    override fun getLayoutId(viewType: Int): Int = getViewModel(viewType).layoutRes()

    override fun getViewHolder(
        view: View,
        viewModel: BankListItemViewModel,
        mDataBinding: ViewDataBinding,
        viewType: Int
    ) = BaseViewHolder(view, viewModel, mDataBinding)

    override fun getViewModel(viewType: Int): BankListItemViewModel = BankListItemViewModel()

    override fun getVariableId(): Int = BR.viewModel

}