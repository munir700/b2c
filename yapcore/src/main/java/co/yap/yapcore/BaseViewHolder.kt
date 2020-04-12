package co.yap.yapcore

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.networking.models.ApiResponse

abstract class BaseViewHolder<ITEM : ApiResponse, VM : BaseListItemViewModel<ITEM>>
    (view: View, viewModel: VM, private val mDataBinding: ViewDataBinding) :
    RecyclerView.ViewHolder(view) {
    private val mViewModel: VM = viewModel
    fun setItem(item: ITEM, position: Int) {
        mViewModel.setItem(item, position)
        mDataBinding.executePendingBindings()
    }

}