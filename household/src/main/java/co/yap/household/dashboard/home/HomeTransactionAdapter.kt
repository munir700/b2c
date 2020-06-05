package co.yap.household.dashboard.home

import android.view.View
import androidx.databinding.ViewDataBinding
import co.yap.household.BR
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.widgets.advrecyclerview.decoration.IStickyHeaderViewHolder
import co.yap.widgets.advrecyclerview.utils.AbstractExpandableItemViewHolder
import co.yap.widgets.advrecyclerview.utils.BaseExpandableRVAdapter

class HomeTransactionAdapter(internal var transactionData: Map<String?, List<Transaction>>) :
    BaseExpandableRVAdapter<Transaction, HomeTransactionChildItemVM, AbstractExpandableItemViewHolder<Transaction, HomeTransactionChildItemVM>
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
//    fun addTransactionData(transactionData: Map<String?, List<Transaction>>?){
//        transactionData?.let {
//            this.transactionData.
//        }
//        this
//    }

    override fun getChildViewHolder(
        view: View,
        viewModel: HomeTransactionChildItemVM,
        mDataBinding: ViewDataBinding,
        viewType: Int
    ) = AbstractExpandableItemViewHolder(view, viewModel, mDataBinding)

    override fun getGroupViewHolder(
        view: View,
        viewModel: HomeTransactionGroupItemVM,
        mDataBinding: ViewDataBinding,
        viewType: Int
    ) = GroupViewHolder(view, viewModel, mDataBinding)

    override fun onBindGroupViewHolder(
        holder: GroupViewHolder,
        groupPosition: Int,
        viewType: Int
    ) {
        val transaction =
            transactionData[transactionData.keys.toList()[groupPosition]] ?: mutableListOf()
        val groupData = HomeTransactionListData(
            transaction = transaction,
            date = transaction[0].creationDate
        )
        holder.setItem(groupData, groupPosition)
    }

    override fun onBindChildViewHolder(
        holder: AbstractExpandableItemViewHolder<Transaction, HomeTransactionChildItemVM>,
        groupPosition: Int,
        childPosition: Int,
        viewType: Int
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

    override fun getGroupId(groupPosition: Int) = groupPosition.plus(1L)
    override fun getChildId(groupPosition: Int, childPosition: Int) =
        transactionData[transactionData.keys.toList()[groupPosition]]?.get(0)?.id?.toLong()
            ?: childPosition.plus(groupPosition).toLong()

    override fun getGroupItemViewType(groupPosition: Int) = groupPosition
    override fun getChildItemViewType(groupPosition: Int, childPosition: Int) =
        transactionData[transactionData.keys.toList()[groupPosition]]?.get(0)?.id
            ?: childPosition.plus(groupPosition)

    class GroupViewHolder(
        view: View,
        viewModel: HomeTransactionGroupItemVM,
        mDataBinding: ViewDataBinding
    ) : AbstractExpandableItemViewHolder<HomeTransactionListData, HomeTransactionGroupItemVM>(
        view,
        viewModel,
        mDataBinding
    ), IStickyHeaderViewHolder
}
