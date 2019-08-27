package co.yap.modules.dashboard.activities

import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import co.yap.R
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultActivity
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.IBaseNavigator
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_yap_dashboard.*

class YapDashboardActivity : DefaultActivity(), INavigator, IFragmentHolder, AppBarConfiguration.OnNavigateUpListener {

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(this, R.id.nav_host_fragment)

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yap_dashboard)

        val host: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return
        val navController = host.navController

        appBarConfiguration = AppBarConfiguration(navController.graph) //configure nav controller
        setupNavigation(navController) //setup navigation
        // setupActionBarWithNavController(navController, appBarConfiguration)

        val navControllerBottom = Navigation.findNavController(this, R.id.nav_host_fragment)
        bottomNav.setupWithNavController(navControllerBottom)
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

}