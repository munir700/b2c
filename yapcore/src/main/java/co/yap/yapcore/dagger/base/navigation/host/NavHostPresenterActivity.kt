package co.yap.yapcore.dagger.base.navigation.host

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import co.yap.yapcore.BR
import co.yap.yapcore.R
import co.yap.yapcore.databinding.ActivityNavhostPresenterBinding
import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelActivityV2
import dagger.hilt.android.AndroidEntryPoint

const val NAVIGATION_Graph_ID = "navigationGraphId"
const val NAVIGATION_Graph_START_DESTINATION_ID = "navigationGraphStartDestination"

@AndroidEntryPoint
class NavHostPresenterActivity :
    BaseNavViewModelActivityV2<ActivityNavhostPresenterBinding, INavHostPresenter.State, NavHostPresenterVM>() {

    override val viewModel: NavHostPresenterVM by viewModels()
    override val navigationGraphId: Int
        get() = intent?.getIntExtra(NAVIGATION_Graph_ID, 0) ?: 0
    override val navigationGraphStartDestination: Int
        get() = intent?.getIntExtra(NAVIGATION_Graph_START_DESTINATION_ID, 0) ?: 0

    override fun getBindingVariable() = BR.navHostPresenterVM

    override fun getLayoutId() = R.layout.activity_navhost_presenter
    override fun onDestinationChanged(
        controller: NavController?,
        destination: NavDestination?,
        arguments: Bundle?
    ) {

    }

    override fun onClick(id: Int) {
    }

}