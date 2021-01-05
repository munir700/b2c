package co.yap.modules.dashboard.more.yapforyou.adapters

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemTaskAchievementBinding
import co.yap.modules.dashboard.more.yapforyou.itemviewmodels.AchievementTaskItemViewModel
import co.yap.modules.dashboard.more.yapforyou.models.YAPForYouGoal
import co.yap.yapcore.BaseBindingRecyclerAdapter

class AchievementTaskAdaptor(private val list: MutableList<YAPForYouGoal>) :
    BaseBindingRecyclerAdapter<YAPForYouGoal, AchievementTaskAdaptor.AchievementTaskViewHolder>(
        list
    ) {

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_task_achievement

    override fun onCreateViewHolder(binding: ViewDataBinding): AchievementTaskViewHolder {
        return AchievementTaskViewHolder(binding as ItemTaskAchievementBinding)
    }

    override fun onBindViewHolder(holder: AchievementTaskViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.onBind(list[position])
    }

    class AchievementTaskViewHolder(private val itemAchievementBinding: ItemTaskAchievementBinding) :
        RecyclerView.ViewHolder(itemAchievementBinding.root) {

        fun onBind(task: YAPForYouGoal) {
            itemAchievementBinding.viewModel =
                AchievementTaskItemViewModel(
                    task
                )
            itemAchievementBinding.executePendingBindings()
        }
    }
}
