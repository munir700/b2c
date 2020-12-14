package co.yap.modules.dashboard.more.yapforyou.achievementsuccess

import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentAchievementSuccessBinding
import co.yap.modules.dashboard.more.yapforyou.fragments.YapForYouBaseFragment

class AchievementSuccessFragment : YapForYouBaseFragment<IAchievementSuccess.ViewModel>(),
    IAchievementSuccess.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_achievement_success

    override val viewModel: AchievementSuccessViewModel
        get() = ViewModelProviders.of(this).get(AchievementSuccessViewModel::class.java)

    override fun getBinding(): FragmentAchievementSuccessBinding {
        return viewDataBinding as FragmentAchievementSuccessBinding
    }
}
