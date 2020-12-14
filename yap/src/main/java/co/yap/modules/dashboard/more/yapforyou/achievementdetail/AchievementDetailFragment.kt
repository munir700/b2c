package co.yap.modules.dashboard.more.yapforyou.achievementdetail

import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.BR
import co.yap.modules.dashboard.more.yapforyou.fragments.YapForYouBaseFragment

class AchievementDetailFragment : YapForYouBaseFragment<IAchievementDetail.ViewModel>(),IAchievementDetail.View {
    override fun getBindingVariable(): Int =BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_achievement_details

    override val viewModel: IAchievementDetail.ViewModel
        get() = ViewModelProviders.of(this).get(AchievementDetailViewModel::class.java)

}