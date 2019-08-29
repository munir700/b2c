package co.yap.modules.dashboard.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.interfaces.IYapDashboardHome
import co.yap.modules.dashboard.viewmodels.YapDashboardHomeViewModel
import co.yap.yapcore.BaseBindingFragment
import kotlinx.android.synthetic.main.fragment_yap_dashboard_home.*

class YapDashboardHome : BaseBindingFragment<IYapDashboardHome.ViewModel>(),
    IYapDashboardHome.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_yap_dashboard_home

    override val viewModel: IYapDashboardHome.ViewModel
        get() = ViewModelProviders.of(this).get(YapDashboardHomeViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.clickEvent.observe(this, Observer {

        })


        //////////////////////
        //todo For testing only remove it while implementation YapDashboardHome.
        button11.setOnClickListener {
            val action =
                YapDashboardHomeDirections.actionYapDashboardHomeToYapStoreFragment()
            view.findNavController().navigate(action)
        }
        //////////////////////


    }
}