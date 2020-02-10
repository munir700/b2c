package co.yap.modules.dashboard.more.yapforyou.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.databinding.FragmentAchievementDetailBinding
import co.yap.modules.dashboard.more.yapforyou.interfaces.IAchievementDetail
import co.yap.modules.dashboard.more.yapforyou.viewmodels.AchievementDetailViewModel
import co.yap.yapcore.BR

class AchievementDetailFragment : YapForYouBaseFragment<IAchievementDetail.ViewModel>(),
    IAchievementDetail.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_achievement_detail

    override val viewModel: IAchievementDetail.ViewModel
        get() = ViewModelProviders.of(this).get(AchievementDetailViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBinding().rvAchievements.adapter = viewModel.adapter
    }

    override fun addObservers() {

    }

    override fun getBinding(): FragmentAchievementDetailBinding {
        return (viewDataBinding as FragmentAchievementDetailBinding)
    }

}