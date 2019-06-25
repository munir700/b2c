package co.yap.app.onboarding.activities

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import co.yap.app.R
import co.yap.app.di.BaseActivity
import co.yap.yapcore.IBase

class OnboardingActivity : BaseActivity<IBase.ViewModel<IBase.State>>() {
    override val viewModel: IBase.ViewModel<IBase.State>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

//    private lateinit var viewDataBinding: ViewDataBinding
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_onboarding_navigation)
//
//        btnBusiness.setOnClickListener {
//            showToast("start b2b onboarding Flow")
//        }
//
//        btnPersonal.setOnClickListener {
//            showToast("start b2c onboarding Flow")
//        }
//
//        tvSignIn.setOnClickListener {
//
//            showToast("start sign in screen here")
//        }
//    }

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_navigation)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return

         val navController = host.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupActionBar(navController, appBarConfiguration)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                Integer.toString(destination.id)
            }

            Log.d("NavigationActivity", "Navigated to $dest")
        }
    }


    private fun setupActionBar(
        navController: NavController,
        appBarConfig: AppBarConfiguration
    ) {
       /*
         This allows NavigationUI to decide what label to show in the action bar
         By using appBarConfig, it will also determine whether to
         show the up arrow or drawer menu icon
        */
        setupActionBarWithNavController(navController, appBarConfig)
     }


     override fun onSupportNavigateUp(): Boolean {
        // Allows NavigationUI to support proper up navigation or the drawer layout
        // drawer menu, depending on the situation
        return findNavController(R.id.my_nav_host_fragment).navigateUp(appBarConfiguration)
    }
 }