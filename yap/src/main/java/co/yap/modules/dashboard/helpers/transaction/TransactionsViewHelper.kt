package co.yap.modules.dashboard.helpers.transaction

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import co.yap.modules.dashboard.adapters.GraphBarsAdapter
import co.yap.modules.dashboard.adapters.GraphBarsAdapter.Companion.isCellHighlighted
import co.yap.modules.dashboard.adapters.GraphBarsAdapter.Companion.isCellHighlightedFromTransaction
import co.yap.modules.dashboard.adapters.GraphBarsAdapter.Companion.previouslySelected
import co.yap.modules.dashboard.adapters.TransactionsHeaderAdapter
import co.yap.modules.dashboard.interfaces.IYapHome
import co.yap.yapcore.helpers.RecyclerTouchListener
import kotlinx.android.synthetic.main.fragment_yap_home.view.*
import kotlinx.android.synthetic.main.view_graph.view.*
import java.util.*
import kotlin.Comparator

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

//    private fun sortListByDate() {
//        Collections.sort(myList, object : Comparator<MyObject>() {
//            fun compare(o1: MyObject, o2: MyObject): Int {
//                return if (o1.getDateTime() == null || o2.getDateTime() == null) 0 else o1.getDateTime().compareTo(
//                    o2.getDateTime()
//                )
//            }
//        })
//    }

    private fun setUpTransactionsListRecyclerView() {
        transactionsView.rvTransaction.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(transactionContext)
        transactionsView.rvTransaction.layoutManager = layoutManager
        transactionsView.rvTransaction.adapter =
            TransactionsHeaderAdapter(
                transactionContext,
                viewModel.transactionLogicHelper.loadJSONDummyList()
            )
    }

    private fun setUpGraphRecyclerView() {
        transactionsView.rvTransactionsBarChart.adapter =
            GraphBarsAdapter(
                viewModel.transactionLogicHelper.loadJSONDummyList(),
                transactionContext
            )
        transactionsView.rvTransactionsBarChart.setLayoutManager(
            LinearLayoutManager(
                transactionContext,
                LinearLayoutManager.HORIZONTAL,
                true
            )
        )
    }

    private fun setOnGraphBarClickListeners() {

        transactionsView.rvTransactionsBarChart.addOnItemTouchListener(
            RecyclerTouchListener(
                transactionContext, transactionsView.rvTransactionsBarChart,
                object : RecyclerTouchListener.ClickListener {
                    override fun onLongClick(view: View?, position: Int) {

                    }

                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onClick(view: View, position: Int) {
                        Toast.makeText(
                            transactionContext,
                            "bar no " + Integer.toString(position),
                            Toast.LENGTH_SHORT
                        ).show()
                        isCellHighlighted = false
                        isCellHighlightedFromTransaction = false

                        transactionsView.rvTransactionsBarChart.getChildAt(previouslySelected)
                            .performClick()

                        transactionsView.rvTransaction.smoothScrollToPosition(position)
                        previouslySelected = position
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
                        //first remove previously selected
                        isCellHighlighted = true

                        isCellHighlightedFromTransaction = false
                        transactionsView.rvTransactionsBarChart.getChildAt(previouslySelected)
                            .performClick()

                        //now list click
                        isCellHighlighted = true
                        isCellHighlightedFromTransaction = true
                        transactionsView.rvTransactionsBarChart.smoothScrollToPosition(position)

                        transactionsView.rvTransactionsBarChart.getChildAt(position)
                            .performClick()
                        previouslySelected = position

                    }


                })
        )
    }


}