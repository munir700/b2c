package co.yap.modules.dashboard.more.home.viewholder

import androidx.recyclerview.widget.RecyclerView
import co.yap.databinding.ItemYapMoreBinding
import co.yap.modules.dashboard.more.home.models.MoreOption
import co.yap.modules.dashboard.more.home.viewmodels.YapMoreItemViewModel
import co.yap.yapcore.interfaces.OnItemClickListener


class YapMoreItemViewHolder(private val itemYapMoreBinding: ItemYapMoreBinding) :
    RecyclerView.ViewHolder(itemYapMoreBinding.root) {

    fun onBind(
        position: Int,
        moreOption: MoreOption,
        dimensions: IntArray,
        onItemClickListener: OnItemClickListener?
    ) {
        itemYapMoreBinding.viewModel =
            YapMoreItemViewModel(moreOption, position, onItemClickListener)
        itemYapMoreBinding.executePendingBindings()
    }
}