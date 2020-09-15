package co.yap.modules.dashboard.store.young.card

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder
import co.yap.BR
import co.yap.R
import co.yap.databinding.ItemCardEditBinding
import co.yap.networking.customers.responsedtos.HouseHoldCardsDesign
import co.yap.yapcore.helpers.extentions.dimen


class YoungCardEditAdapter(mValue: MutableList<HouseHoldCardsDesign>, navigation: NavController?) :
    BaseRVAdapter<HouseHoldCardsDesign, YoungCardItemVM, YoungCardEditAdapter.ViewHolder>(
        mValue,
        navigation
    ) {
    override fun getLayoutId(viewType: Int) = getViewModel(viewType).layoutRes()
    override fun getViewHolder(
        view: View,
        viewModel: YoungCardItemVM,
        mDataBinding: ViewDataBinding,
        viewType: Int
    ) = ViewHolder(view, viewModel, mDataBinding)
    override fun getViewModel(viewType: Int) = YoungCardItemVM()
    override fun getVariableId() = BR.viewModel
    class ViewHolder(view: View, viewModel: YoungCardItemVM, mDataBinding: ViewDataBinding) :
        BaseViewHolder<HouseHoldCardsDesign, YoungCardItemVM>(view, viewModel, mDataBinding) {
        init {
            val binding = mDataBinding as ItemCardEditBinding
            val params = binding.ivCard.layoutParams
            params.width = binding.ivCard.context.dimen(R.dimen._204sdp)
            params.height = binding.ivCard.context.dimen(R.dimen._216sdp)
            binding.ivCard.layoutParams = params
        }
    }
}