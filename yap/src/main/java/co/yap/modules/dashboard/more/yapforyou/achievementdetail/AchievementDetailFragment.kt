package co.yap.modules.dashboard.more.yapforyou.achievementdetail

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.yapforyou.fragments.YapForYouBaseFragment
import co.yap.yapcore.helpers.extentions.toast

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
                toast("start flow")
            }
        }
    }

    /*private fun initiateYfyFlow() {
        when(viewModel.parentViewModel?.getYfyTag()){
            YFYAchievementType.ADD_CARD.type ->{
                launchActivity<AddMoneyActivity> {  }
            }
            YFYAchievementType.SET_PROFILE_PICTURE.type ->{
                navigate(R.id.action_achievementDetailFragment_to_profileSettingsFragment2)
            }
        }
    }*/

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