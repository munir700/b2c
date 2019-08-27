package co.yap.modules.dashboard.fragments

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.adapters.TransactionAdapter
import co.yap.modules.dashboard.interfaces.IYapDashboardHome
import co.yap.modules.dashboard.viewmodels.YapDashboardHomeViewModel
import co.yap.yapcore.BaseBindingFragment
import kotlinx.android.synthetic.main.fragment_yap_dashboard_home.*

class YapDashboardHome : BaseBindingFragment<IYapDashboardHome.ViewModel>(), IYapDashboardHome.View {
    private var transactionAdapter: TransactionAdapter? = null
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_yap_dashboard_home

    override val viewModel: IYapDashboardHome.ViewModel
        get() = ViewModelProviders.of(this).get(YapDashboardHomeViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        transactionAdapter=TransactionAdapter(viewModel.loadJSONDummyList(),context!!)
        rvTransaction.setHasFixedSize(true)
        val layoutManager=LinearLayoutManager(context)
        rvTransaction.layoutManager = layoutManager
        rvTransaction.adapter=transactionAdapter
        viewModel.clickEvent.observe(this, Observer {


        })
    }
}