package co.yap.modules.dashboard.activities

import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import co.yap.R
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.*
import co.yap.yapcore.interfaces.IBaseNavigator
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_yap_dashboard.*

class YapDashboardActivity : BaseBindingActivity<IDefault.ViewModel>(), INavigator, IFragmentHolder, AppBarConfiguration.OnNavigateUpListener {

    override fun getBindingVariable(): Int = 0

    override fun getLayoutId(): Int = R.layout.activity_yap_dashboard
    override val viewModel: IDefault.ViewModel
        get() = ViewModelProviders.of(this).get(DefaultViewModel::class.java)

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this, R.id.nav_host_fragment)

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_yap_dashboard)

        val host: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return
        val navController = host.navController

        appBarConfiguration = AppBarConfiguration(navController.graph) //configure nav controller
        setupNavigation(navController) //setup navigation
        // setupActionBarWithNavController(navController, appBarConfiguration)
        setupBottomNavigation(navController)

    }

    private fun setupNavigation(navController: NavController) {
        val sideNavView = findViewById<NavigationView>(R.id.nav_view)
        sideNavView?.setupWithNavController(navController)
        val drawerLayout: DrawerLayout? = findViewById(R.id.drawer_layout)

        //fragments load from here but how ?
//        appBarConfiguration = AppBarConfiguration(
//            setOf(R.id.fragment1, R.id.fragment2),
//            drawerLayout
//        )
    }

    private fun setupBottomNavigation(navController: NavController) {
        bottomNav.setupWithNavController(navController)
    }

}