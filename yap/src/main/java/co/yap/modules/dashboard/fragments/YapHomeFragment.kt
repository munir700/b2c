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
import co.yap.modules.dashboard.interfaces.IYapHome
import co.yap.modules.dashboard.viewmodels.YapHomeViewModel
import co.yap.yapcore.BaseBindingFragment
import kotlinx.android.synthetic.main.fragment_yap_home.*
import kotlinx.android.synthetic.main.view_graph.*


class YapHomeFragment : BaseBindingFragment<IYapHome.ViewModel>(), IYapHome.View,
    RecyclerView.OnItemTouchListener {


    private var transactionAdapter: TransactionAdapter? = null

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_yap_home

    override val viewModel: IYapHome.ViewModel
        get() = ViewModelProviders.of(this).get(YapHomeViewModel::class.java)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        transactionAdapter = TransactionAdapter(viewModel.loadJSONDummyList(), context!!)
        rvTransaction.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        rvTransaction.layoutManager = layoutManager
        rvTransaction.adapter = transactionAdapter
        viewModel.clickEvent.observe(this, Observer {

        })

        // set up graph

        setUpGraphRecyclerView()
    }

    fun setUpGraphRecyclerView() {
        rvTransactionsBarChart.adapter =
            DashboardAdapter(viewModel.getGraphDummyData(), this!!.activity!!)
        rvTransactionsBarChart.setLayoutManager(
            LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )

        rvTransactionsBarChart.setOnTouchListener(object : RecyclerView.OnItemTouchListener,
            View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event!!.action) {
                    MotionEvent.ACTION_DOWN -> {
                        rvTransaction.smoothScrollToPosition((viewModel.loadJSONDummyList().size - 1))
                    }
                }
                return true
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                when (e!!.action) {
                    MotionEvent.ACTION_DOWN -> {
                        rvTransaction.smoothScrollToPosition((viewModel.loadJSONDummyList().size - 1))
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

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        when (e!!.action) {
            MotionEvent.ACTION_DOWN -> {
                rvTransaction.smoothScrollToPosition((viewModel.loadJSONDummyList().size - 1))
            }
        }
        return true
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        when (e!!.action) {
            MotionEvent.ACTION_DOWN -> {
                rvTransaction.smoothScrollToPosition((viewModel.loadJSONDummyList().size - 1))
            }
        }
    }
}