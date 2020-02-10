package co.yap.modules.dashboard.more.yapforyou.adapters

import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemYapForYouBinding
import co.yap.modules.dashboard.more.yapforyou.Achievements
import co.yap.modules.dashboard.more.yapforyou.itemviewmodels.YAPForYouItemViewModel
import co.yap.yapcore.interfaces.OnItemClickListener

class YAPForYouItemViewHolder(private val itemYapForYouBinding: ItemYapForYouBinding) :
    RecyclerView.ViewHolder(itemYapForYouBinding.root) {

    fun onBind(
        position: Int,
        achievements: Achievements,
        dimensions: IntArray,
        onItemClickListener: OnItemClickListener?
    ) {

//        val unwrappedDrawable = AppCompatResources.getDrawable(
//            itemYapForYouBinding.imgIcon.context,
//            R.drawable.bg_circle_container
//        )
//        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
//        DrawableCompat.setTint(wrappedDrawable, achievements.image)

//        itemYapForYouBinding.imgIcon.background = wrappedDrawable

        itemYapForYouBinding.viewModel =
            YAPForYouItemViewModel(
                achievements,
                position,
                onItemClickListener
            )
        itemYapForYouBinding.executePendingBindings()

    }
}