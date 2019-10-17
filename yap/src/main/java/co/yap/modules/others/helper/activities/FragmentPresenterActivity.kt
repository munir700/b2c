package co.yap.modules.others.helper.activities

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.ui.AppBarConfiguration
import co.yap.BR
import co.yap.R
import co.yap.databinding.ActivityYapDashboardBinding
import co.yap.modules.dashboard.main.adapters.YapDashboardAdaptor
import co.yap.modules.others.helper.interfaces.IFragmentPresenter
import co.yap.modules.others.helper.viewmodels.FragmentPresenterViewModel
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder

class FragmentPresenterActivity : BaseBindingActivity<IFragmentPresenter.ViewModel>(),
    IFragmentPresenter.View,
    IFragmentHolder, AppBarConfiguration.OnNavigateUpListener {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_yap_dashboard

    override val viewModel: IFragmentPresenter.ViewModel
        get() = ViewModelProviders.of(this).get(FragmentPresenterViewModel::class.java)

    private lateinit var appBarConfiguration: AppBarConfiguration

    lateinit var adapter: YapDashboardAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun addObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
            }
        })
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }

    fun getViewBinding(): ActivityYapDashboardBinding {
        return (viewDataBinding as ActivityYapDashboardBinding)
    }
}