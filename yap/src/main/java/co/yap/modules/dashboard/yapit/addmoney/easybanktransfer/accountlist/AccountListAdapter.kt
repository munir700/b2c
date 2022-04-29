package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.accountlist

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import co.yap.yapcore.BR
import co.yap.yapcore.BaseListItemViewModel
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder

class AccountListAdapter(
    val list: MutableList<AccountsListModel>, navigation: NavController?
) :
    BaseRVAdapter<AccountsListModel, BaseListItemViewModel<AccountsListModel>, BaseViewHolder<AccountsListModel, BaseListItemViewModel<AccountsListModel>>>(
        list,
        navigation
    ) {

    private val bank = 1
    private val account = 2

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getLayoutId(viewType: Int): Int = getViewModel(viewType).layoutRes()

    override fun getViewHolder(
        view: View,
        viewModel: BaseListItemViewModel<AccountsListModel>,
        mDataBinding: ViewDataBinding,
        viewType: Int
    ) = BaseViewHolder(view, viewModel, mDataBinding)

    override fun getViewModel(viewType: Int) =
        if (viewType == bank) AccountBankListItemViewModel() else AccountListItemViewModel()

    override fun getVariableId() = BR.viewModel

    override fun getItemViewType(position: Int): Int {
        return when {
            list.size <= 0 -> -1
            list[position].leanCustomerAccounts == null -> bank
            else -> account
        }
    }
}
