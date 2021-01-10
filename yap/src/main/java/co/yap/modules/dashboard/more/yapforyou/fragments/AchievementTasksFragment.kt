package co.yap.modules.dashboard.more.yapforyou.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.databinding.FragmentAchievementTasksBinding
import co.yap.modules.dashboard.more.yapforyou.interfaces.IAchievement
import co.yap.modules.dashboard.more.yapforyou.models.YAPForYouGoal
import co.yap.modules.dashboard.more.yapforyou.viewmodels.AchievementViewModel
import co.yap.widgets.MultiStateView
import co.yap.yapcore.BR
import co.yap.yapcore.interfaces.OnItemClickListener

class AchievementTasksFragment : YapForYouBaseFragment<IAchievement.ViewModel>(),
    IAchievement.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_achievement_tasks

    override val viewModel: AchievementViewModel
        get() = ViewModelProviders.of(this).get(AchievementViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.parentViewModel?.selectedAchievement?.get()?.let {
            getBinding().multiStateView.viewState =
                if (it.goals.isNullOrEmpty()) MultiStateView.ViewState.EMPTY else MultiStateView.ViewState.CONTENT
            it.goals?.let { goals ->
                viewModel.adapter.setList(goals)
            }
        }

        viewModel.adapter.setItemListener(detailItemClickListener)
        viewModel.adapter.allowFullItemClickListener = true
    }

    private val detailItemClickListener = object :
        OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is YAPForYouGoal) {
                viewModel.setSelectedAchievementTask(YAPForYouGoal = data)
                navigate(R.id.achievementDetailFragment)
            }
        }
    }

    override fun getBinding(): FragmentAchievementTasksBinding {
        return (viewDataBinding as FragmentAchievementTasksBinding)
    }
}
