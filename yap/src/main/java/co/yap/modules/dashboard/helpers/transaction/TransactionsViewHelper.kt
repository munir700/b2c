package co.yap.modules.dashboard.helpers.transaction

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
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

    var checkScroll: Boolean=false

    init {

        setUpTransactionsListRecyclerView()
        setUpGraphRecyclerView()

        setOnGraphBarClickListeners()
        setOnTransactionCellClickListeners()
        autoScrollGraphBarsOnTransactionsListScroll()
//        autoScrollTransactionsOnGraphScroll()
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
                    override fun onItemTouchEvent(view: View?, position: Int) {
                        checkScroll=true

//                        Log.i("positionTouch", "onInterceptTouchEvent " + position)
//                        isCellHighlighted = true
//                        isCellHighlightedFromTransaction = false
//                        transactionsView.rvTransaction.getChildAt(previouslySelected)
//                            .performClick()
//
//                        //now list click
//                        isCellHighlighted = true
//                        isCellHighlightedFromTransaction = true
//                        transactionsView.rvTransaction.smoothScrollToPosition(
//                            position
//                        )
//                        transactionsView.rvTransaction.getChildAt(position )
//                            .performClick()
//                        previouslySelected = position

//                        isCellHighlighted = false
//                        isCellHighlightedFromTransaction = false
//
//                        transactionsView.rvTransactionsBarChart.getChildAt(previouslySelected)
//                            .performClick()
//
//                        transactionsView.rvTransaction.smoothScrollToPosition(position)
//                        previouslySelected = position


//
                        Log.i("positionTouch", "onInterceptTouchEvent " + position)

                        Toast.makeText(
                            transactionContext,
                            "bar no " + Integer.toString(position),
                            Toast.LENGTH_SHORT
                        ).show()

                        if (previouslySelected != position) {

//first remove previously clicked item
                            isCellHighlighted = true
                            isCellHighlightedFromTransaction = false
                            transactionsView.rvTransactionsBarChart.getChildAt(previouslySelected)
                                .performClick()

                            //now list click
                            isCellHighlighted = true
                            isCellHighlightedFromTransaction = true
//                            transactionsView.rvTransactionsBarChart.smoothScrollBy(dx, dy)
//                            if (currentPosition >0) {
//                            transactionsView.rvTransactionsBarChart.smoothScrollToPosition(
//                                position
//                            )
//                            transactionsView.rvTransaction.smoothScrollToPosition(
//                                position
//                            )
                            transactionsView.rvTransactionsBarChart.getChildAt(position)
                                .performClick()
                            //disable scrolling at the moment

                                transactionsView.rvTransaction.smoothScrollToPosition(
                                    position
                                )
//                                transactionsView.rvTransactionsBarChart.getChildAt(position )
//                                    .performClick()
                            previouslySelected = position
                        }

//                        }
//                        return

                    }

                    override fun onLongClick(view: View?, position: Int) {

                    }

                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onClick(view: View, position: Int) {
//                        Toast.makeText(
//                            transactionContext,
//                            "bar no " + Integer.toString(position),
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        isCellHighlighted = false
//                        isCellHighlightedFromTransaction = false
//
//                        transactionsView.rvTransactionsBarChart.getChildAt(previouslySelected)
//                            .performClick()
//
//                        transactionsView.rvTransaction.smoothScrollToPosition(position)
//                        previouslySelected = position
//                        Log.i("positionTouch", position.toString())
                    }
                })
        )
    }

    private fun setOnTransactionCellClickListeners() {

        transactionsView.rvTransaction.addOnItemTouchListener(
            RecyclerTouchListener(
                transactionContext, transactionsView.rvTransaction,
                object : RecyclerTouchListener.ClickListener {
                    override fun onItemTouchEvent(view: View?, position: Int) {
                        Log.i("positionTouch", "onInterceptTouchEvent")
                        checkScroll=false

                    }

                    override fun onLongClick(view: View?, position: Int) {

                    }

                    override fun onClick(view: View, position: Int) {
//                        Toast.makeText(
//                            transactionContext,
//                            "listing cell no " + Integer.toString(position),
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        //first remove previously selected
//                        isCellHighlighted = true
//
//                        isCellHighlightedFromTransaction = false
//                        transactionsView.rvTransactionsBarChart.getChildAt(previouslySelected)
//                            .performClick()
//
//                        //now list click
//                        isCellHighlighted = true
//                        isCellHighlightedFromTransaction = true
//                        transactionsView.rvTransactionsBarChart.smoothScrollToPosition(position)
//
//                        transactionsView.rvTransactionsBarChart.getChildAt(position)
//                            .performClick()
//                        previouslySelected = position

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
if (!checkScroll){


    if (recyclerView.getLayoutManager() is LinearLayoutManager) {
        val layoutManager = recyclerView.getLayoutManager() as LinearLayoutManager
        if (layoutManager.orientation == LinearLayoutManager.VERTICAL) {
            totalItemsInView = layoutManager.itemCount
//                        if (totalItemsInView != layoutManager.findLastCompletelyVisibleItemPosition()) {
//                            visibleitems = layoutManager.findLastCompletelyVisibleItemPosition()
            visibleitems = layoutManager.findFirstCompletelyVisibleItemPosition()
//                            visibleitems = layoutManager.findFirstCompletelyVisibleItemPosition()

//                        }
//                        if (layoutManager.findLastCompletelyVisibleItemPosition() >= 28) {
//
//                            visibleitems = layoutManager.findLastCompletelyVisibleItemPosition()
//
//
//                        } else {
//                            visibleitems = layoutManager.findLastCompletelyVisibleItemPosition()
//
//                        }
            Log.i("visibleitems", visibleitems.toString())
            Log.i("visibleitems", "totalItemsInView " + totalItemsInView.toString())
        }
    }

    verticalOffSet += dy

    val currFirstPositionView: View? =
        recyclerView.findChildViewUnder(dx.toFloat(), dy.toFloat())
    if (currFirstPositionView != null) {
//                    val currentPosition: Int = recyclerView.getChildAdapterPosition(
//                        currFirstPositionView!!
//                    )
        val currentPosition: Int = visibleitems

//                    if (currentPosition > 0) {
//                        if (previouslySelected != currentPosition) {

//first remove previously clicked item
        isCellHighlighted = true
        isCellHighlightedFromTransaction = false
        transactionsView.rvTransactionsBarChart.getChildAt(previouslySelected)
            .performClick()

        //now list click
        isCellHighlighted = true
        isCellHighlightedFromTransaction = true
//                            transactionsView.rvTransactionsBarChart.smoothScrollBy(dx, dy)
//                            if (currentPosition >0) {
        transactionsView.rvTransactionsBarChart.smoothScrollToPosition(
            currentPosition
        )
        transactionsView.rvTransactionsBarChart.getChildAt(currentPosition )
            .performClick()
        previouslySelected = currentPosition
//                        }

//                    }
    }
}
//                if (recyclerView.getLayoutManager() is LinearLayoutManager) {
//                    val layoutManager = recyclerView.getLayoutManager() as LinearLayoutManager
//                    if (layoutManager.orientation == LinearLayoutManager.VERTICAL) {
//                        totalItemsInView = layoutManager.itemCount
////                        if (totalItemsInView != layoutManager.findLastCompletelyVisibleItemPosition()) {
////                            visibleitems = layoutManager.findLastCompletelyVisibleItemPosition()
//                            visibleitems = layoutManager.findFirstCompletelyVisibleItemPosition()
////                            visibleitems = layoutManager.findFirstCompletelyVisibleItemPosition()
//
////                        }
////                        if (layoutManager.findLastCompletelyVisibleItemPosition() >= 28) {
////
////                            visibleitems = layoutManager.findLastCompletelyVisibleItemPosition()
////
////
////                        } else {
////                            visibleitems = layoutManager.findLastCompletelyVisibleItemPosition()
////
////                        }
//                        Log.i("visibleitems", visibleitems.toString())
//                        Log.i("visibleitems", "totalItemsInView " + totalItemsInView.toString())
//                    }
//                }
//
//                verticalOffSet += dy
//
//                val currFirstPositionView: View? =
//                    recyclerView.findChildViewUnder(dx.toFloat(), dy.toFloat())
//                if (currFirstPositionView != null) {
////                    val currentPosition: Int = recyclerView.getChildAdapterPosition(
////                        currFirstPositionView!!
////                    )
//                    val currentPosition: Int = visibleitems
//
////                    if (currentPosition > 0) {
////                        if (previouslySelected != currentPosition) {
//
////first remove previously clicked item
//                            isCellHighlighted = true
//                            isCellHighlightedFromTransaction = false
//                            transactionsView.rvTransactionsBarChart.getChildAt(previouslySelected)
//                                .performClick()
//
//                            //now list click
//                            isCellHighlighted = true
//                            isCellHighlightedFromTransaction = true
////                            transactionsView.rvTransactionsBarChart.smoothScrollBy(dx, dy)
////                            if (currentPosition >0) {
//                            transactionsView.rvTransactionsBarChart.smoothScrollToPosition(
//                                currentPosition
//                            )
//                            transactionsView.rvTransactionsBarChart.getChildAt(currentPosition )
//                                .performClick()
//                            previouslySelected = currentPosition
////                        }
//
////                    }
//                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

            }
        })
    }

//    private fun autoScrollTransactionsOnGraphScroll() {
//        var verticalOffSet: Int = 0
//        var totalItemsInView: Int = 0
//        var visibleitems: Int = 0
//
//        transactionsView.rvTransactionsBarChart.addOnScrollListener(object :
//            RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
//                if (recyclerView.getLayoutManager() is LinearLayoutManager) {
//                    val layoutManager = recyclerView.getLayoutManager() as LinearLayoutManager
//                    if (layoutManager.orientation == LinearLayoutManager.VERTICAL) {
//                        totalItemsInView = layoutManager.itemCount
//                        if (totalItemsInView != layoutManager.findLastCompletelyVisibleItemPosition()) {
//                            visibleitems = layoutManager.findLastCompletelyVisibleItemPosition()
//
//                        }
//                        if (layoutManager.findLastCompletelyVisibleItemPosition() >= 28) {
//
//                            visibleitems = layoutManager.findLastCompletelyVisibleItemPosition()
//
//                        }
//                        Log.i("visibleitems", visibleitems.toString())
//                        Log.i("visibleitems", "totalItemsInView " + totalItemsInView.toString())
//                    }
//                }
//
//                verticalOffSet += dy
//
//                val currFirstPositionView: View? =
//                    recyclerView.findChildViewUnder(dx.toFloat(), dy.toFloat())
//                if (currFirstPositionView != null) {
////                    val currentPosition: Int = recyclerView.getChildAdapterPosition(
////                        currFirstPositionView!!
////                    )
//                    val currentPosition: Int = visibleitems
//
//                    if (currentPosition >= 0) {
//                        if (previouslySelected != currentPosition) {
//
////first remove previously clicked item
//                            isCellHighlighted = true
//                            isCellHighlightedFromTransaction = false
//                            transactionsView.rvTransaction.getChildAt(previouslySelected)
//                                .performClick()
//
//                            //now list click
//                            isCellHighlighted = true
//                            isCellHighlightedFromTransaction = true
//                            transactionsView.rvTransaction.smoothScrollToPosition(
//                                currentPosition
//                            )
//                            transactionsView.rvTransaction.getChildAt(currentPosition )
//                                .performClick()
//                            previouslySelected = currentPosition
//                        }
//
//                    }
//                }
//            }
//
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//
//            }
//        })
//    }

}