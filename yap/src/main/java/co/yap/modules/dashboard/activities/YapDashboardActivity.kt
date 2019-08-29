package co.yap.modules.dashboard.activities

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.interfaces.IYapDashboard
import co.yap.modules.dashboard.viewmodels.YapDashBoardViewModel
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.IBaseNavigator
import kotlinx.android.synthetic.main.activity_yap_dashboard.*
import kotlinx.android.synthetic.main.layout_drawer_yap_dashboard.*
import net.cachapa.expandablelayout.ExpandableLayout

class YapDashboardActivity : BaseBindingActivity<IYapDashboard.ViewModel>(), IYapDashboard.View,
    INavigator,
    IFragmentHolder, AppBarConfiguration.OnNavigateUpListener {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_yap_dashboard

    override val viewModel: IYapDashboard.ViewModel
        get() = ViewModelProviders.of(this).get(YapDashBoardViewModel::class.java)

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this, R.id.nav_host_fragment)

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val host: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
                ?: return
        val navController = host.navController

        appBarConfiguration = AppBarConfiguration(navController.graph) //configure nav controller
        setupDrawerNavigation(navController)
        setupBottomNavigation(navController)

        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnCopy -> {}
                R.id.lUserInfo -> expandableLayout.toggle(true)
            }
        })

        expandableLayout.setOnExpansionUpdateListener { expansionFraction, state ->
            when (state) {
                ExpandableLayout.State.EXPANDED -> ivChevron.setImageResource(R.drawable.ic_chevron_up)
                ExpandableLayout.State.COLLAPSED -> ivChevron.setImageResource(R.drawable.ic_chevron_down)
            }
        }

    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }

    private fun setupDrawerNavigation(navController: NavController) {
        drawerNav?.setupWithNavController(navController)

        //fragments load from here but how ?
//        val drawerLayout: DrawerLayout? = findViewById(R.id.drawer_layout)
//        appBarConfiguration = AppBarConfiguration(
//            setOf(R.id.fragment1, R.id.fragment2),
//            drawerLayout
//        )
    }

    private fun setupBottomNavigation(navController: NavController) {
        bottomNav.setupWithNavController(navController)
    }

}