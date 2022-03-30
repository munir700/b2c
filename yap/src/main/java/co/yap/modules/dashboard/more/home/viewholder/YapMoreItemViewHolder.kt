package co.yap.modules.dashboard.more.home.viewholder

import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
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

        val unwrappedDrawable = AppCompatResources.getDrawable(
            itemYapMoreBinding.imgIcon.context,
            R.drawable.cornor_edges_light_purple
        )
        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
        itemYapMoreBinding.imgIcon.background = wrappedDrawable
        itemYapMoreBinding.viewModel =
            YapMoreItemViewModel().apply {
                this.onItemClickListener = onItemClickListener
                setItem(moreOption, position)
            }
        itemYapMoreBinding.executePendingBindings()
    }
}
