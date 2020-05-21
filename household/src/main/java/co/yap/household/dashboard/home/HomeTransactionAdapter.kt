package co.yap.household.dashboard.home

import android.view.View
import androidx.databinding.ViewDataBinding
import co.yap.household.BR
import co.yap.networking.models.ApiResponse
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.widgets.advrecyclerview.utils.AbstractExpandableItemViewHolder
import co.yap.widgets.advrecyclerview.utils.BaseExpandableRVAdapter

class HomeTransactionAdapter(internal var transactionData: Map<String?, List<Transaction>>) :
    BaseExpandableRVAdapter<Transaction, HomeTransactionChildItemVM, HomeTransactionAdapter.ChildViewHolder
            , HomeTransactionListData, HomeTransactionGroupItemVM, HomeTransactionAdapter.GroupViewHolder>() {
    init {
        // ExpandableItemAdapter requires stable ID, and also
        // have to implement the getGroupItemId()/getChildItemId() methods appropriately.
        setHasStableIds(true)
    }

    fun setTransactionData(transactionData: Map<String?, List<Transaction>>?) {
        transactionData?.let {
            if (this.transactionData != transactionData) {
                this.transactionData = transactionData
            }
        } ?: emptyMap<String?, List<Transaction>>()
        notifyDataSetChanged()
    }

    override fun getChildViewHolder(
        view: View,
        viewModel: HomeTransactionChildItemVM,
        mDataBinding: ViewDataBinding,
        viewType: Int
    ) = ChildViewHolder(view, viewModel, mDataBinding)

    override fun getGroupViewHolder(
        view: View,
        viewModel: HomeTransactionGroupItemVM,
        mDataBinding: ViewDataBinding,
        viewType: Int
    ) = GroupViewHolder(view, viewModel, mDataBinding)

    override fun onBindGroupViewHolder(holder: GroupViewHolder, groupPosition: Int, viewType: Int) {
        val transaction =
            transactionData[transactionData.keys.toList()[groupPosition]] ?: mutableListOf()
        val groupData = HomeTransactionListData(transaction = transaction)
        holder.setItem(groupData, groupPosition)
    }

    override fun onBindChildViewHolder(
        holder: ChildViewHolder, groupPosition: Int, childPosition: Int, viewType: Int
    ) {
        val transaction =
            transactionData[transactionData.keys.toList()[groupPosition]]?.get(childPosition)
        transaction?.let {
            holder.setItem(it, childPosition)
        }
    }

    override fun getChildLayoutId(viewType: Int) = getChildViewModel(viewType).layoutRes()
    override fun getGroupLayoutId(viewType: Int) = getGroupViewModel(viewType).layoutRes()
    override fun getGroupViewModel(viewType: Int) = HomeTransactionGroupItemVM()
    override fun getGroupVariableId() = BR.viewModel
    override fun getChildViewModel(viewType: Int) = HomeTransactionChildItemVM()
    override fun getChildVariableId() = BR.viewModel
    override fun getGroupCount() = transactionData.size
    override fun getChildCount(groupPosition: Int) =
        transactionData[transactionData.keys.toList()[groupPosition]]?.size ?: 0

    override fun getGroupId(groupPosition: Int) = groupPosition.toLong()
    override fun getChildId(groupPosition: Int, childPosition: Int) =
        childPosition.plus(groupPosition).toLong()

    class ChildViewHolder(
        view: View,
        viewModel: HomeTransactionChildItemVM,
        mDataBinding: ViewDataBinding
    ) : AbstractExpandableItemViewHolder<Transaction, HomeTransactionChildItemVM>(
        view,
        viewModel,
        mDataBinding
    )

    class GroupViewHolder(
        view: View,
        viewModel: HomeTransactionGroupItemVM,
        mDataBinding: ViewDataBinding
    ) : AbstractExpandableItemViewHolder<HomeTransactionListData, HomeTransactionGroupItemVM>(
        view,
        viewModel,
        mDataBinding
    )
}
