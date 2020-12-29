package co.yap.modules.dashboard.more.yapforyou.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.yapforyou.activities.YAPForYouActivity
import co.yap.modules.dashboard.more.yapforyou.interfaces.IYAPForYou
import co.yap.modules.dashboard.more.yapforyou.viewmodels.YAPForYouViewModel
import co.yap.networking.transactions.responsedtos.achievement.Achievement
import co.yap.yapcore.enums.YFYAchievementType
import co.yap.yapcore.interfaces.OnItemClickListener

class YAPForYouFragment : YapForYouBaseFragment<IYAPForYou.ViewModel>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_yap_for_you

    override val viewModel: YAPForYouViewModel
        get() = ViewModelProviders.of(this).get(YAPForYouViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAchievements()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }

    override fun onPause() {
        viewModel.clickEvent.removeObservers(this)
        super.onPause()

    }

    override fun onResume() {
        super.onResume()

        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnView -> {
                    viewModel.parentViewModel?.selectedPosition = 0
                    if (!viewModel.parentViewModel?.achievements.isNullOrEmpty()) {
                        viewModel.parentViewModel?.achievement =
                            viewModel.parentViewModel?.achievements?.get(0)
                        viewModel.state.toolbarVisibility.set(false)
                        navigate(R.id.achievementFragment)
                    }
                }
            }
        })
    }


    private fun setupRecycleView() {
        viewModel.adaptor.allowFullItemClickListener = true
        viewModel.adaptor.setItemListener(listener)
    }

    private val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is Achievement) {
                viewModel.parentViewModel?.selectedPosition = pos
                viewModel.parentViewModel?.achievement = data.copy()
                    .also { it.icon = viewModel.getAchievementIcon(pos, isWithBadged = true) }
                viewModel.state.toolbarVisibility.set(false)
                viewModel.parentViewModel?.configureYFYManager(YFYAchievementType.GET_STARTED.type)
                navigate(R.id.achievementFragment)
            }
        }
    }

    override fun onToolBarClick(id: Int) {
        super.onToolBarClick(id)
        when (id) {
            R.id.ivLeftIcon -> (requireActivity() as YAPForYouActivity).onBackPressed()
            R.id.ivRightIcon -> {
                viewModel.state.toolbarVisibility.set(false)
                navigate(R.id.completedAchievementsFragment)
            }
        }
    }

}