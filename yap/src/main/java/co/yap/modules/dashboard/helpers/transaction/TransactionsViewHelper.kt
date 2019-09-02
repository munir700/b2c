package co.yap.modules.dashboard.helpers.transaction

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import co.yap.modules.dashboard.adapters.GraphBarsAdapter
import co.yap.modules.dashboard.adapters.GraphBarsAdapter.Companion.isCellHighlighted
import co.yap.modules.dashboard.adapters.GraphBarsAdapter.Companion.isCellHighlightedFromTransaction
import co.yap.modules.dashboard.adapters.GraphBarsAdapter.Companion.previouslySelected
import co.yap.modules.dashboard.adapters.TransactionsHeaderAdapter
import co.yap.modules.dashboard.interfaces.IYapHome
import co.yap.yapcore.helpers.RecyclerTouchListener
import kotlinx.android.synthetic.main.content_fragment_yap_home.view.*
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
        autoScrollGraphBarsOnTransactionsListScroll()
    }

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

    private fun autoScrollGraphBarsOnTransactionsListScroll() {
        var verticalOffSet: Int = 0
        var totalItemsInView: Int = 0
        var visibleitems: Int = 0

        transactionsView.rvTransaction.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (recyclerView.getLayoutManager() is LinearLayoutManager) {
                    val layoutManager = recyclerView.getLayoutManager() as LinearLayoutManager
                    if (layoutManager.orientation == LinearLayoutManager.VERTICAL) {
                        totalItemsInView = layoutManager.itemCount
                        if (totalItemsInView != layoutManager.findLastCompletelyVisibleItemPosition()) {
                            visibleitems = layoutManager.findLastCompletelyVisibleItemPosition() - 1

                        }
                        if (layoutManager.findLastCompletelyVisibleItemPosition() >= 28) {

                            visibleitems = layoutManager.findLastCompletelyVisibleItemPosition() + 1

                        }
                        Log.i("visibleitems", visibleitems.toString())
                        Log.i("visibleitems", "totalItemsInView " + totalItemsInView.toString())
//                        changeHeightAccordingToScroll(recyclerView)
                    }
                }



                verticalOffSet += dy
//                Log.i("offset111", " dy   " + verticalOffSet
//
//                +" Offset   " + recyclerView.computeVerticalScrollOffset().toString()
//                +" Extent   " +  recyclerView.computeVerticalScrollExtent().toString()
//                +" Range  " +  recyclerView.computeVerticalScrollRange().toString())

                val offset = recyclerView.computeVerticalScrollOffset();
                val extent = recyclerView.computeVerticalScrollExtent();
                val range = recyclerView.computeVerticalScrollRange();

                val percentage = (100.0 * offset / (range - extent).toFloat()).toInt()

//                Log.i("percentage","percentage" + percentage   )
//                Log.i("offset111"," Extent  " +  recyclerView.computeVerticalScrollExtent().toString())
//                Log.i("offset111"," Range   " +  recyclerView.computeVerticalScrollRange().toString())

                val currFirstPositionView: View? =
                    recyclerView.findChildViewUnder(dx.toFloat(), dy.toFloat())
                if (currFirstPositionView != null) {
//                    val currentPosition: Int = recyclerView.getChildAdapterPosition(
//                        currFirstPositionView!!
//                    )
                    val currentPosition: Int = visibleitems
                    Log.i("percentage", currentPosition.toString() + " percentage" + percentage)

                    if (currentPosition >= 0) {
                        if (previouslySelected != currentPosition) {
//                            Log.i("offset111", currentPosition.toString())

//first remove previously clicked item
                            isCellHighlighted = true
                            isCellHighlightedFromTransaction = false
                            transactionsView.rvTransactionsBarChart.getChildAt(previouslySelected)
                                .performClick()

                            //now list click
                            isCellHighlighted = true
                            isCellHighlightedFromTransaction = true
//                            transactionsView.rvTransactionsBarChart.smoothScrollBy(dx, dy)
                            transactionsView.rvTransactionsBarChart.smoothScrollToPosition(
                                currentPosition - 1
                            )
                            transactionsView.rvTransactionsBarChart.getChildAt(currentPosition - 1)
                                .performClick()
                            previouslySelected = currentPosition - 1
                        }

                    }
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

            }
        })
    }

    fun computeScrollOffset(
        state: RecyclerView.State, orientation: OrientationHelper,
        startChild: View?, endChild: View?, lm: RecyclerView.LayoutManager,
        smoothScrollbarEnabled: Boolean, reverseLayout: Boolean
    ): Int {
        if (lm.childCount == 0 || state.itemCount == 0 || startChild == null
            || endChild == null
        ) {
            return 0
        }
        val minPosition = Math.min(
            lm.getPosition(startChild),
            lm.getPosition(endChild)
        )
        val maxPosition = Math.max(
            lm.getPosition(startChild),
            lm.getPosition(endChild)
        )
        val itemsBefore = if (reverseLayout)
            Math.max(0, state.itemCount - maxPosition - 1)
        else
            Math.max(0, minPosition)
        if (!smoothScrollbarEnabled) {
            return itemsBefore
        }
        val laidOutArea = Math.abs(
            orientation.getDecoratedEnd(endChild) - orientation.getDecoratedStart(startChild)
        )
        val itemRange = Math.abs(lm.getPosition(startChild) - lm.getPosition(endChild)) + 1
        val avgSizePerRow = laidOutArea.toFloat() / itemRange

        return Math.round(
            itemsBefore * avgSizePerRow + (orientation.getStartAfterPadding() - orientation.getDecoratedStart(
                startChild
            ))
        )
    }
}