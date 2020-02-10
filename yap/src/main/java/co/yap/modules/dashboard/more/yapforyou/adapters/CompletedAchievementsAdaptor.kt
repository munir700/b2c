package co.yap.modules.dashboard.more.yapforyou.adapters

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemCompletedAchievementBinding
import co.yap.modules.dashboard.more.yapforyou.Achievements
import co.yap.modules.dashboard.more.yapforyou.itemviewmodels.CompletedAchievementItemViewModel
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.BaseBindingRecyclerAdapter

class CompletedAchievementsAdaptor(val list: MutableList<Achievements>) :
    BaseBindingRecyclerAdapter<Achievements, CompletedAchievementsAdaptor.CompletedItemViewHolder>(
        list
    ) {

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_completed_achievement

    override fun onCreateViewHolder(binding: ViewDataBinding): CompletedItemViewHolder {
        return CompletedItemViewHolder(binding as ItemCompletedAchievementBinding)
    }

    override fun onBindViewHolder(holder: CompletedItemViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.onBind(list[position])
    }

    class CompletedItemViewHolder(private val itemCompletedAchievementBinding: ItemCompletedAchievementBinding) :
        RecyclerView.ViewHolder(itemCompletedAchievementBinding.root) {

        fun onBind(achievement: Achievements) {
            val item = achievement.copy()
            if (item.percentage?.toIntOrNull() == 100) {
               item.percentage =  Translator.getString(
                    itemCompletedAchievementBinding.tvCompltedPercentage.context,
                    Strings.screen_yap_for_you_display_text_completed_percentage
                ).format(
                    "${item.percentage}%"
                )

                itemCompletedAchievementBinding.viewModel =
                    CompletedAchievementItemViewModel(item)
                itemCompletedAchievementBinding.executePendingBindings()
            }
        }
    }
}