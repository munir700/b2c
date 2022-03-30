package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.banklist

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import co.yap.BR
import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder

class BankListAdapter(
    mValue: MutableList<BankListMainModel>, navigation: NavController?
) :
    BaseRVAdapter<BankListMainModel, BankListItemViewModel, BankListAdapter.ViewHolder>(
        mValue, navigation
    ) {
    override fun getLayoutId(viewType: Int): Int = getViewModel(viewType).layoutRes()

    override fun getViewHolder(
        view: View,
        viewModel: BankListItemViewModel,
        mDataBinding: ViewDataBinding,
        viewType: Int
    ): ViewHolder = ViewHolder(
        view,
        viewModel,
        mDataBinding
    )

    class ViewHolder(
        view: View,
        viewModel: BankListItemViewModel,
        mDataBinding: ViewDataBinding
    ) : BaseViewHolder<BankListMainModel, BankListItemViewModel>(
        view,
        viewModel,
        mDataBinding
    ){
    }

    override fun getViewModel(viewType: Int): BankListItemViewModel =
        BankListItemViewModel()

    override fun getVariableId(): Int = BR.viewModel
}