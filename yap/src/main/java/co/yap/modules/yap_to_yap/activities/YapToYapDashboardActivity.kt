package co.yap.modules.yap_to_yap.activities

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.modules.dashboard.interfaces.IYapDashboard
import co.yap.modules.yap_to_yap.interfaces.IYapToYap
import co.yap.modules.yap_to_yap.viewmodels.YapToYapViewModel
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultActivity
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator
import kotlinx.android.synthetic.main.layout_drawer_yap_dashboard.*

class YapToYapDashboardActivity : BaseBindingActivity<IYapToYap.ViewModel>(), INavigator,
    IFragmentHolder {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.activity_yap_to_yap_dashboard
    override val viewModel: IYapToYap.ViewModel
        get() = ViewModelProviders.of(this).get(YapToYapViewModel::class.java)

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this@YapToYapDashboardActivity, R.id.main_nav_host_fragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set Observer
        viewModel.clickEvent.observe(this, clickEventObserver)

    }
    private val clickEventObserver = Observer<Int> {
        when (it) {
            R.id.tbIvClose -> {
//                findNavController().navigateUp()
                showToast("Cross Button Clicked")
            }
            R.id.tbIvGift -> {
                showToast("Gift Button Clicked")
            }
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
    }
}