package co.yap.yapcore.dagger.base.navigation.host

import co.yap.yapcore.BR
import co.yap.yapcore.R
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelActivity
import co.yap.yapcore.databinding.ActivityNavhostPresenterBinding

const val NAVIGATION_Graph_ID = "navigationGraphId"
const val NAVIGATION_Graph_START_DESTINATION_ID = "navigationGraphStartDestination"

class NavHostPresenterActivity :
    BaseNavViewModelActivity<ActivityNavhostPresenterBinding, INavHostPresenter.State, NavHostPresenterVM>() {

    override val navigationGraphId: Int
        get() = intent?.getIntExtra(NAVIGATION_Graph_ID, 0) ?: 0
    override val navigationGraphStartDestination: Int
        get() = intent?.getIntExtra(NAVIGATION_Graph_START_DESTINATION_ID, 0) ?: 0

    override fun getBindingVariable() = BR.navHostPresenterVM

    override fun getLayoutId() = R.layout.activity_navhost_presenter

//    override fun init(savedInstanceState: Bundle?) {
//        super.init(savedInstanceState)
//        navHostFragment?.let {
//            val navigator =
//                KeepStateNavigator(this, it.childFragmentManager, R.id.nav_host_fragment)
//            navController.navigatorProvider += navigator
//        }
//
//    }
}