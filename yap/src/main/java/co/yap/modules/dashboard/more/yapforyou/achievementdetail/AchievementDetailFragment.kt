package co.yap.modules.dashboard.more.yapforyou.achievementdetail

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.main.activities.MoreActivity
import co.yap.modules.dashboard.more.yapforyou.fragments.YapForYouBaseFragment
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyActivity
import co.yap.yapcore.enums.YFYAchievementTaskType
import co.yap.yapcore.helpers.extentions.inviteFriendIntent
import co.yap.yapcore.helpers.extentions.launchActivity

class AchievementDetailFragment : YapForYouBaseFragment<IAchievementDetail.ViewModel>(),
    IAchievementDetail.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_achievement_details
    override val viewModel: AchievementDetailViewModel
        get() = ViewModelProviders.of(this).get(AchievementDetailViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObservers()
    }

    private val onClickObserver = Observer<Int> {
        when (it) {
            R.id.btnAction -> {
                when (viewModel.parentViewModel?.selectedAchievementTask?.activityOnAction) {
                    YFYAchievementTaskType.INVITE_FRIEND.title -> {
                        context?.inviteFriendIntent()
                    }
                    AddMoneyActivity::class.simpleName -> {
                        launchActivity<AddMoneyActivity> { }
                    }
                    MoreActivity::javaClass.name -> {
                        launchActivity<MoreActivity> { }
                    }
                }
            }
        }
    }

    override fun addObservers() {
        viewModel.clickEvent.observe(this, onClickObserver)
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObserver(onClickObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        removeObservers()
    }
}