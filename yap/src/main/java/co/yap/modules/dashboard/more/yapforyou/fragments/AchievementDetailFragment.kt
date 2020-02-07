package co.yap.modules.dashboard.more.yapforyou.fragments

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.databinding.FragmentAchievementDetailBinding
import co.yap.modules.dashboard.more.main.fragments.MoreBaseFragment
import co.yap.modules.dashboard.more.yapforyou.interfaces.IAchievementDetail
import co.yap.modules.dashboard.more.yapforyou.viewmodels.AchievementDetailViewModel
import co.yap.yapcore.BR

class AchievementDetailFragment : MoreBaseFragment<IAchievementDetail.ViewModel>(),
    IAchievementDetail.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.layout_item_beneficiary

    override val viewModel: IAchievementDetail.ViewModel
        get() = ViewModelProviders.of(this).get(AchievementDetailViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObservers()
    }

    override fun addObservers() {

    }

    override fun getBinding(): FragmentAchievementDetailBinding {
        return (viewDataBinding as FragmentAchievementDetailBinding)
    }


}