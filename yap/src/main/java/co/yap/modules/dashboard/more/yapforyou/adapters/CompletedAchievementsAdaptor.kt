package co.yap.modules.dashboard.more.yapforyou.adapters

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemCompletedAchievementBinding
import co.yap.modules.dashboard.more.yapforyou.itemviewmodels.CompletedAchievementItemViewModel
import co.yap.modules.dashboard.more.yapforyou.models.Y4YAchievementData
import co.yap.yapcore.BaseBindingRecyclerAdapter

class CompletedAchievementsAdaptor(val list: MutableList<Y4YAchievementData>) :
    BaseBindingRecyclerAdapter<Y4YAchievementData, CompletedAchievementsAdaptor.CompletedItemViewHolder>(
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

        fun onBind(achievement: Y4YAchievementData) {
            itemCompletedAchievementBinding.viewModel =
                CompletedAchievementItemViewModel(achievement)
            itemCompletedAchievementBinding.executePendingBindings()
        }
    }
}