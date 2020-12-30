package co.yap.modules.dashboard.more.yapforyou.adapters

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.databinding.ItemAchievementBinding
import co.yap.modules.dashboard.more.yapforyou.itemviewmodels.AchievementTaskItemViewModel
import co.yap.modules.dashboard.more.yapforyou.models.Y4YAchievementTaskData
import co.yap.yapcore.BaseBindingRecyclerAdapter

class AchievementTaskAdaptor(private val list: MutableList<Y4YAchievementTaskData>) :
    BaseBindingRecyclerAdapter<Y4YAchievementTaskData, AchievementTaskAdaptor.AchievementTaskViewHolder>(
        list
    ) {

    override fun getLayoutIdForViewType(viewType: Int): Int = R.layout.item_achievement

    override fun onCreateViewHolder(binding: ViewDataBinding): AchievementTaskViewHolder {
        return AchievementTaskViewHolder(binding as ItemAchievementBinding)
    }

    override fun onBindViewHolder(holder: AchievementTaskViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.onBind(list[position])
    }


    class AchievementTaskViewHolder(private val itemAchievementBinding: ItemAchievementBinding) :
        RecyclerView.ViewHolder(itemAchievementBinding.root) {

        fun onBind(task: Y4YAchievementTaskData) {
            itemAchievementBinding.viewModel =
                AchievementTaskItemViewModel(
                    task
                )
            itemAchievementBinding.executePendingBindings()
        }
    }
}
