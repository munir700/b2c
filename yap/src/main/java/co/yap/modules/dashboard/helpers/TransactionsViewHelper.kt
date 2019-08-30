package co.yap.modules.dashboard.helpers

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import co.yap.modules.dashboard.adapters.GraphBarsAdapter
import co.yap.modules.dashboard.adapters.TransactionsHeaderAdapter
import co.yap.modules.dashboard.interfaces.IYapHome
import co.yap.yapcore.helpers.RecyclerTouchListener
import kotlinx.android.synthetic.main.fragment_yap_home.view.*
import kotlinx.android.synthetic.main.view_graph.view.*

class TransactionsViewHelper(
    val transactionContext: Context, val transactionsView: View,
    val viewModel: IYapHome.ViewModel
) {
    init {
        setUpTransactionsListRecyclerView()
        setUpGraphRecyclerView()

        setOnGraphBarClickListeners()
        setOnTransactionCellClickListeners()

    }

    private fun setOnGraphBarClickListeners() {

        transactionsView.rvTransactionsBarChart.addOnItemTouchListener(
            RecyclerTouchListener(
                transactionContext, transactionsView.rvTransactionsBarChart,
                object : RecyclerTouchListener.ClickListener {
                    override fun onLongClick(view: View?, position: Int) {

                    }

                    override fun onClick(view: View, position: Int) {
                        Toast.makeText(
                            transactionContext,
                            "bar no " + Integer.toString(position),
                            Toast.LENGTH_SHORT
                        ).show()

                        transactionsView.rvTransaction.smoothScrollToPosition(position)
                    }


                })
        )
    }

    private fun setOnTransactionCellClickListeners() {

        transactionsView.rvTransaction.addOnItemTouchListener(
            RecyclerTouchListener(
                transactionContext, transactionsView.rvTransaction,
                object : RecyclerTouchListener.ClickListener {
                    override fun onLongClick(view: View?, position: Int) {

                    }

                    override fun onClick(view: View, position: Int) {
                        Toast.makeText(
                            transactionContext,
                            "listing cell no " + Integer.toString(position),
                            Toast.LENGTH_SHORT
                        ).show()

                        transactionsView.rvTransactionsBarChart.smoothScrollToPosition(position)
                    }


                })
        )
    }

    private fun setUpTransactionsListRecyclerView() {
//     var transactionAdapter :TransactionsHeaderAdapter= TransactionsHeaderAdapter(transactionContext, viewModel.loadJSONDummyList())
        transactionsView.rvTransaction.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(transactionContext)
        transactionsView.rvTransaction.layoutManager = layoutManager
//        transactionsView.rvTransaction.adapter = transactionAdapter
        transactionsView.rvTransaction.adapter =
            TransactionsHeaderAdapter(transactionContext, viewModel.loadJSONDummyList())
//        viewModel.clickEvent.observe(this, Observer {
//
//        })

    }

    fun setUpGraphRecyclerView() {
        transactionsView.rvTransactionsBarChart.adapter =
            GraphBarsAdapter(viewModel.loadJSONDummyList(), transactionContext)
        transactionsView.rvTransactionsBarChart.setLayoutManager(
            LinearLayoutManager(
                transactionContext,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
    }
}