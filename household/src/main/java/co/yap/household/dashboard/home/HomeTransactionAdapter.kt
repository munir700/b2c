package co.yap.household.dashboard.home

import android.view.View
import androidx.databinding.ViewDataBinding
import co.yap.household.BR
import co.yap.networking.models.ApiResponse
import co.yap.widgets.advrecyclerview.utils.AbstractExpandableItemViewHolder
import co.yap.widgets.advrecyclerview.utils.BaseExpandableRVAdapter

class HomeTransactionAdapter :
    BaseExpandableRVAdapter<ApiResponse, HomeTransactionChildItemVM, HomeTransactionAdapter.ChildViewHolder
            , ApiResponse, HomeTransactionGroupItemVM, HomeTransactionAdapter.GroupViewHolder>() {
    init {
        // ExpandableItemAdapter requires stable ID, and also
        // have to implement the getGroupItemId()/getChildItemId() methods appropriately.
        setHasStableIds(true)
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

    override fun getChildLayoutId(viewType: Int) = getChildViewModel(viewType).layoutRes()

    override fun getGroupLayoutId(viewType: Int) = getGroupViewModel(viewType).layoutRes()

    override fun getGroupViewModel(viewType: Int) = HomeTransactionGroupItemVM()

    override fun getGroupVariableId() = BR.viewModel

    override fun getChildViewModel(viewType: Int) = HomeTransactionChildItemVM()

    override fun getChildVariableId() = BR.viewModel

    override fun getGroupCount() = 4

    override fun getChildCount(groupPosition: Int) = 16

    override fun getGroupId(groupPosition: Int) = groupPosition.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int) =
        childPosition.plus(groupPosition).toLong()

    class ChildViewHolder(
        view: View,
        viewModel: HomeTransactionChildItemVM,
        mDataBinding: ViewDataBinding
    ) :
        AbstractExpandableItemViewHolder<ApiResponse, HomeTransactionChildItemVM>(
            view,
            viewModel,
            mDataBinding
        )


    class GroupViewHolder(
        view: View,
        viewModel: HomeTransactionGroupItemVM,
        mDataBinding: ViewDataBinding
    ) :
        AbstractExpandableItemViewHolder<ApiResponse, HomeTransactionGroupItemVM>(
            view,
            viewModel,
            mDataBinding
        )

}
