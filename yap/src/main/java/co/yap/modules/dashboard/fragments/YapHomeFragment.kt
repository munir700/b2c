package co.yap.modules.dashboard.fragments

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.adapters.GraphBarsAdapter
import co.yap.modules.dashboard.adapters.TransactionsHeaderAdapter
import co.yap.modules.dashboard.models.TransactionModel
import co.yap.modules.dashboard.viewmodels.YapHomeViewModel
import co.yap.yapcore.BaseBindingFragment
import kotlinx.android.synthetic.main.fragment_yap_home.*
import kotlinx.android.synthetic.main.view_graph.*
import co.yap.modules.dashboard.interfaces.IYapHome
import co.yap.yapcore.helpers.RecyclerTouchListener


class YapHomeFragment : BaseBindingFragment<IYapHome.ViewModel>(), IYapHome.View {
    var transactionsListingData: ArrayList<TransactionModel> = ArrayList<TransactionModel>()

    private var transactionAdapter: TransactionsHeaderAdapter? = null

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_yap_home

    override val viewModel: IYapHome.ViewModel
        get() = ViewModelProviders.of(this).get(YapHomeViewModel::class.java)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        transactionsListingData = viewModel.loadJSONDummyList()

        setUpTransactionsListRecyclerView()
        setUpGraphRecyclerView()

        setOnGraphBarClickListeners()
        setOnTransactionCellClickListeners()
    }


    private fun setOnGraphBarClickListeners() {

        rvTransactionsBarChart.addOnItemTouchListener(
            RecyclerTouchListener(
                this!!.activity!!, rvTransactionsBarChart,
                object : RecyclerTouchListener.ClickListener {
                    override fun onLongClick(view: View?, position: Int) {

                    }

                    override fun onClick(view: View, position: Int) {
                        Toast.makeText(
                            activity,
                            "bar no "+Integer.toString(position),
                            Toast.LENGTH_SHORT
                        ).show()

                        rvTransaction.smoothScrollToPosition(position)
                    }


                })
        )
    }

    private fun setOnTransactionCellClickListeners() {

        rvTransaction.addOnItemTouchListener(
            RecyclerTouchListener(
                this!!.activity!!, rvTransaction,
                object : RecyclerTouchListener.ClickListener {
                    override fun onLongClick(view: View?, position: Int) {

                    }

                    override fun onClick(view: View, position: Int) {
                        Toast.makeText(
                            activity,
                           "listing cell no "+ Integer.toString(position),
                            Toast.LENGTH_SHORT
                        ).show()

                        rvTransactionsBarChart.smoothScrollToPosition(position)
                    }


                })
        )
    }

    private fun setUpTransactionsListRecyclerView() {
        transactionAdapter = TransactionsHeaderAdapter(context!!, transactionsListingData)
        rvTransaction.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        rvTransaction.layoutManager = layoutManager
        rvTransaction.adapter = transactionAdapter
        viewModel.clickEvent.observe(this, Observer {

        })
    }

    fun setUpGraphRecyclerView() {
        rvTransactionsBarChart.adapter =
            GraphBarsAdapter(transactionsListingData, this!!.activity!!)
        rvTransactionsBarChart.setLayoutManager(
            LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
    }
}