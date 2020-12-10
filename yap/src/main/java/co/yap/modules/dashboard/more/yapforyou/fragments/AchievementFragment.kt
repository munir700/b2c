package co.yap.modules.dashboard.more.yapforyou.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.databinding.FragmentAchievementBinding
import co.yap.modules.dashboard.more.yapforyou.interfaces.IAchievement
import co.yap.modules.dashboard.more.yapforyou.viewmodels.AchievementViewModel
import co.yap.networking.transactions.responsedtos.achievement.AchievementTask
import co.yap.widgets.MultiStateView
import co.yap.yapcore.BR
import co.yap.yapcore.interfaces.OnItemClickListener

class AchievementFragment : YapForYouBaseFragment<IAchievement.ViewModel>(),
    IAchievement.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_achievement

    override val viewModel: AchievementViewModel
        get() = ViewModelProviders.of(this).get(AchievementViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.parentViewModel?.achievement?.let {
            getBinding().multiStateView.viewState =
                if (it.features.isNullOrEmpty()) MultiStateView.ViewState.EMPTY else MultiStateView.ViewState.CONTENT
        }
        viewModel.adapter.setItemListener(detailItemClickListener)
        viewModel.adapter.allowFullItemClickListener = true
    }

    private val detailItemClickListener = object :
        OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is AchievementTask) {
                viewModel.parentViewModel?.getDescriptionContent(data.title)
                navigate(R.id.achievementDetailFragment)
            }
        }
    }


    override fun getBinding(): FragmentAchievementBinding {
        return (viewDataBinding as FragmentAchievementBinding)
    }

}