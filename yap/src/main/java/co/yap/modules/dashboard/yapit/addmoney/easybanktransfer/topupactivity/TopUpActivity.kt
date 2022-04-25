package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.topupactivity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import co.yap.BR
import co.yap.R
import co.yap.databinding.ActivityTopUpBinding
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator

class TopUpActivity : BaseBindingActivity<ActivityTopUpBinding, ITopUp.ViewModel>(), INavigator,
    IFragmentHolder {
    override fun getBindingVariable() : Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.activity_top_up
    override val viewModel: TopUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.top_up_amount_nav_host_fragment) as NavHostFragment
        intent?.let {
            val bundle = it.getBundleExtra(Constants.EXTRA)
            bundle?.let{bundle ->
                 navHostFragment.navController.setGraph(R.navigation.top_up_amount_navigation,bundle)
            }
        }
    }

    override val navigator:  IBaseNavigator
        get() = DefaultNavigator(
            this@TopUpActivity,
            R.id.top_up_amount_nav_host_fragment
        )
    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.top_up_amount_navigation)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
    }

}