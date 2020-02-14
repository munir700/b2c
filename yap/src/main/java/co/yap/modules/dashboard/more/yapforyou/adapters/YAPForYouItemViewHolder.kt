package co.yap.modules.dashboard.more.yapforyou.adapters

import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemYapForYouBinding
import co.yap.modules.dashboard.more.yapforyou.itemviewmodels.YAPForYouItemViewModel
import co.yap.networking.transactions.responsedtos.achievement.Achievement
import co.yap.yapcore.interfaces.OnItemClickListener

class YAPForYouItemViewHolder(private val itemYapForYouBinding: ItemYapForYouBinding) :
    RecyclerView.ViewHolder(itemYapForYouBinding.root) {

    fun onBind(
        position: Int,
        achievements: Achievement,
        onItemClickListener: OnItemClickListener?
    ) {

//        val unwrappedDrawable = AppCompatResources.getDrawable(
//            itemYapForYouBinding.imgIcon.context,
//            R.drawable.bg_circle_container
//        )
//        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
//        DrawableCompat.setTint(wrappedDrawable, achievements.image)

//        itemYapForYouBinding.imgIcon.background = wrappedDrawable

        val icon = getAchievementIcon(position)
        itemYapForYouBinding.viewModel =
            YAPForYouItemViewModel(
                achievement = achievements,
                position = position,
                icon = icon,
                onItemClickListener = onItemClickListener
            )
        itemYapForYouBinding.executePendingBindings()

    }

    private fun getAchievementIcon(position: Int): Int {
        return when (position) {
            0 -> R.drawable.ic_round_badge_dark_purple
            1 -> R.drawable.ic_round_badge_dark_blue
            2 -> R.drawable.ic_round_badge_dark_peach
            3 -> R.drawable.ic_round_badge_dark_green
            4 -> R.drawable.ic_round_badge_dark_pink
            5 -> R.drawable.ic_round_badge_dark_green
            else -> R.drawable.ic_round_badge_dark_grey
        }
    }
}