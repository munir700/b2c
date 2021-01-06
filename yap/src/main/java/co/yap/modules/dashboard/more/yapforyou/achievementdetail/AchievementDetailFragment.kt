package co.yap.modules.dashboard.more.yapforyou.achievementdetail

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentAchievementDetailsBinding
import co.yap.modules.dashboard.more.main.activities.MoreActivity
import co.yap.modules.dashboard.more.yapforyou.fragments.YapForYouBaseFragment
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyActivity
import co.yap.yapcore.enums.YapForYouGoalType
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.parentViewModel?.selectedAchievementTask?.title == YapForYouGoalType.FREEZE_UNFREEZE_CARD.title) {
            val animationDrawable: AnimationDrawable =
                getBindings().tvFreezeAnimationView.background as AnimationDrawable
            animationDrawable.setEnterFadeDuration(1000)
            animationDrawable.setExitFadeDuration(1500)
            animationDrawable.isOneShot = true
            animationDrawable.start()
        }
    }

    private val onClickObserver = Observer<Int> {
        when (it) {
            R.id.btnAction -> {
                when (viewModel.parentViewModel?.selectedAchievementTask?.activityOnAction) {
                    YapForYouGoalType.INVITE_FRIEND.title -> {
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

    private fun getBindings(): FragmentAchievementDetailsBinding =
        viewDataBinding as FragmentAchievementDetailsBinding
}