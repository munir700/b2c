package co.yap.modules.dashboard.fragments

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.adapters.DashboardAdapter
import co.yap.modules.dashboard.adapters.TransactionAdapter
import co.yap.modules.dashboard.adapters.TransactionsHeaderAdapter
import co.yap.modules.dashboard.interfaces.IYapHome
import co.yap.modules.dashboard.models.TransactionModel
import co.yap.modules.dashboard.viewmodels.YapHomeViewModel
import co.yap.yapcore.BaseBindingFragment
import kotlinx.android.synthetic.main.fragment_yap_home.*
import kotlinx.android.synthetic.main.view_graph.*


class YapHomeFragment : BaseBindingFragment<IYapHome.ViewModel>(), IYapHome.View {
    var listing: ArrayList<TransactionModel> = ArrayList<TransactionModel>()

    //    private var transactionAdapter: TransactionAdapter? = null
    private var transactionAdapter: TransactionsHeaderAdapter? = null

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_yap_home

    override val viewModel: IYapHome.ViewModel
        get() = ViewModelProviders.of(this).get(YapHomeViewModel::class.java)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        listing = viewModel.loadJSONDummyList()

        transactionAdapter = TransactionsHeaderAdapter( context!!, listing)
        rvTransaction.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        rvTransaction.layoutManager = layoutManager
        rvTransaction.adapter = transactionAdapter
        viewModel.clickEvent.observe(this, Observer {

        })

        // set up graph

        setUpGraphRecyclerView()

        rvTransactionsBarChart.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                when (e!!.action) {
                    MotionEvent.ACTION_DOWN -> {
                        rvTransaction.smoothScrollToPosition((listing.size - 1))
                    }
                }
            }

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                return true
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

            }

        })
    }

    fun setUpGraphRecyclerView() {
        rvTransactionsBarChart.adapter =
            DashboardAdapter(listing, this!!.activity!!)
        rvTransactionsBarChart.setLayoutManager(
            LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
    }
}