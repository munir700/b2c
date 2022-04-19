package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.accountlist

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemAccountListChildBinding
import co.yap.databinding.ItemAccountListGroupBinding
import co.yap.networking.leanteach.responsedtos.accountlistmodel.LeanCustomerAccounts
import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel
import co.yap.yapcore.BaseBindingRecyclerAdapter

class AccountListAdapter(
    private val list: MutableList<Any>,
    private val adaptorClick: AccountChildItemViewModel.OnItemClickListenerChild?
) :
    BaseBindingRecyclerAdapter<Any, RecyclerView.ViewHolder>(list) {

    private val bank = 1
    private val account = 2
    private var bankList: BankListMainModel? = null

    override fun getLayoutIdForViewType(viewType: Int): Int =
        if (viewType == bank) R.layout.item_account_list_group else R.layout.item_account_list_child

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return if (binding is ItemAccountListGroupBinding) GroupViewHolder(
            binding
        ) else ChildViewHolder(
            binding as ItemAccountListChildBinding
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is GroupViewHolder) {
            holder.onBind(list[position], adaptorClick, position)
            bankList = list[position] as BankListMainModel
        } else if (holder is ChildViewHolder) {
            bankList?.let { holder.onBind(list[position], it, adaptorClick, position) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position] is BankListMainModel) bank else account
    }

    class ChildViewHolder(private val itemEmptyBinding: ItemAccountListChildBinding) :
        RecyclerView.ViewHolder(itemEmptyBinding.root) {

        fun onBind(
            data: Any,
            bankList: BankListMainModel,
            adaptorClick: AccountChildItemViewModel.OnItemClickListenerChild?,
            groupPosition: Int
        ) {
            itemEmptyBinding.accountChildViewModel =
                AccountChildItemViewModel(
                    data as LeanCustomerAccounts,
                    bankList,
                    groupPosition,
                    adaptorClick
                )

            itemEmptyBinding.executePendingBindings()
        }
    }

    class GroupViewHolder(private val itemTransactionListHeaderBinding: ItemAccountListGroupBinding) :
        RecyclerView.ViewHolder(itemTransactionListHeaderBinding.root) {

        fun onBind(
            data: Any,
            adaptorClick: AccountChildItemViewModel.OnItemClickListenerChild?,
            groupPosition: Int
        ) {
            itemTransactionListHeaderBinding.accountGroupItemViewModel =
                AccountGroupItemViewModel(
                    data as BankListMainModel,
                    groupPosition,
                    adaptorClick
                )

            itemTransactionListHeaderBinding.executePendingBindings()
        }
    }
}


