package co.yap.modules.dashboard.more.yapforyou.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.yapforyou.Achievements
import co.yap.modules.dashboard.more.yapforyou.adapters.YAPForYouAdapter
import co.yap.modules.dashboard.more.yapforyou.interfaces.IYAPForYou
import co.yap.modules.dashboard.more.yapforyou.viewmodels.YAPForYouViewModel
import co.yap.translation.Strings
import co.yap.yapcore.interfaces.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_yap_for_you.*

class YAPForYouFragment : YapForYouBaseFragment<IYAPForYou.ViewModel>() {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_yap_for_you
    lateinit var adapter: YAPForYouAdapter


    override val viewModel: YAPForYouViewModel
        get() = ViewModelProviders.of(this).get(YAPForYouViewModel::class.java)

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
                    val action =
                        YAPForYouFragmentDirections.actionYAPForYouFragmentToAchievementDetailFragment()
                    findNavController().navigate(action)
                }
            }
        })
    }


    private fun setupRecycleView() {
        adapter = YAPForYouAdapter(requireContext(), viewModel.getAchievements())
        rvYapForYou.adapter = adapter
        adapter.allowFullItemClickListener = true
        adapter.setItemListener(listener)
        viewModel.state.selectedAchievementPercentage =
            viewModel.getAchievements()[0].percentage.toString()
        viewModel.state.selectedAchievementTitle = viewModel.getAchievements().get(0).title
    }

    private fun setObservers() {
//        viewModel.clickEvent.observe(this, observer)
    }

    private val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is Achievements) {
                setSelectedAchievement(data)
            }
        }
    }

    private fun setSelectedAchievement(achievement: Achievements) {
        viewModel.parentViewModel?.achievement = achievement
        viewModel.parentViewModel?.achievement?.percentage =
            getString(Strings.screen_yap_for_you_display_text_completed_percentage).format(
                "${achievement.percentage.toString()}%"
            )
        viewModel.state.selectedAchievementTitle = achievement.title
        viewModel.state.selectedAchievementPercentage = viewModel.parentViewModel?.achievement?.percentage
    }
}