package co.yap.modules.dashboard.helpers.transaction

import android.content.Context
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.modules.dashboard.adapters.GraphBarsAdapter
import co.yap.modules.dashboard.adapters.GraphBarsAdapter.Companion.isCellHighlighted
import co.yap.modules.dashboard.adapters.GraphBarsAdapter.Companion.isCellHighlightedFromTransaction
import co.yap.modules.dashboard.adapters.GraphBarsAdapter.Companion.previouslySelected
import co.yap.modules.dashboard.adapters.TransactionsHeaderAdapter
import co.yap.modules.dashboard.interfaces.IYapHome
import co.yap.yapcore.helpers.RecyclerTouchListener
import it.sephiroth.android.library.xtooltip.ClosePolicy
import it.sephiroth.android.library.xtooltip.Tooltip
import kotlinx.android.synthetic.main.content_fragment_yap_home.view.*
import kotlinx.android.synthetic.main.view_graph.view.*


class TransactionsViewHelper(
    val context: Context, val transactionsView: View,
    val viewModel: IYapHome.ViewModel
) {
    private var tooltip: Tooltip? = null
    var checkScroll: Boolean = false
    var horizontalScrollPosition: Int = 0

    init {
        setUpTransactionsListRecyclerView()
        setUpGraphRecyclerView()
        setOnGraphBarClickListeners()
        setOnTransactionCellClickListeners()
        autoScrollGraphBarsOnTransactionsListScroll()
    }

    private fun addTooltip(view: View?, text: CharSequence? = "") {
        view?.let {
            tooltip?.dismiss()
            tooltip = Tooltip.Builder(context)
                .anchor(view, 0, -50, false)
                .text(text!!)
                .maxWidth(400)
                .styleId(R.style.ToolTipAltStyle)
                .arrow(true)
                .floatingAnimation(Tooltip.Animation.DEFAULT)
                .closePolicy(ClosePolicy.TOUCH_NONE)
                .overlay(false)
                .create()
            tooltip?.show(view, Tooltip.Gravity.TOP, true)
        }

    }

    private fun setUpTransactionsListRecyclerView() {
        transactionsView.rvTransaction.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        transactionsView.rvTransaction.layoutManager = layoutManager
        transactionsView.rvTransaction.adapter =
            TransactionsHeaderAdapter(
                context,
                viewModel.transactionLogicHelper.loadJSONDummyList()
            )
    }

    private fun setUpGraphRecyclerView() {
        transactionsView.rvTransactionsBarChart.adapter =
            GraphBarsAdapter(
                viewModel.transactionLogicHelper.loadJSONDummyList(),
                context
            )
        transactionsView.rvTransactionsBarChart.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            true
        )
    }


    private fun setOnGraphBarClickListeners() {

        transactionsView.rvTransactionsBarChart.addOnItemTouchListener(
            RecyclerTouchListener(
                context, transactionsView.rvTransactionsBarChart,
                object : RecyclerTouchListener.ClickListener {
                    override fun onItemTouchEvent(view: View?, position: Int) {

                        checkScroll = true

                        isCellHighlighted = false
                        isCellHighlightedFromTransaction = false

                        transactionsView.rvTransactionsBarChart.getChildAt(previouslySelected)
                            .performClick()
                        horizontalScrollPosition = position

                        transactionsView.rvTransaction.smoothScrollToPosition(position)
                        previouslySelected = position

                    }

                    override fun onLongClick(view: View?, position: Int) {

                    }

                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onClick(view: View, position: Int) {
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
                context, transactionsView.rvTransaction,
                object : RecyclerTouchListener.ClickListener {
                    override fun onItemTouchEvent(view: View?, position: Int) {
                        checkScroll = false
                    }

                    override fun onLongClick(view: View?, position: Int) {

                    }

                    override fun onClick(view: View, position: Int) {
                        //first remove previously selected
                        isCellHighlighted = true

                        isCellHighlightedFromTransaction = false
                        transactionsView.rvTransactionsBarChart.getChildAt(previouslySelected)
                            .performClick()

                        //now list click
                        isCellHighlighted = true
                        isCellHighlightedFromTransaction = true
                        transactionsView.rvTransactionsBarChart.smoothScrollToPosition(position)

                        transactionsView.rvTransactionsBarChart.getChildAt(position).performClick()
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
                if (!checkScroll) {
                    scrollBarsFromListTouch(
                        recyclerView,
                        totalItemsInView,
                        visibleitems,
                        verticalOffSet,
                        dy,
                        dx
                    )

                } else {

                    scrollBarsFromBarsOnTouch(
                        totalItemsInView,
                        visibleitems,
                        verticalOffSet,
                        recyclerView,
                        dy,
                        dx
                    )
                }

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    private fun scrollBarsFromBarsOnTouch(
        totalItemsInView: Int,
        visibleitems: Int,
        verticalOffSet: Int,
        recyclerView: RecyclerView,
        dy: Int,
        dx: Int
    ) {
        var totalItemsInView1 = totalItemsInView
        var visibleitems1 = visibleitems
        var verticalOffSet1 = verticalOffSet
        if (recyclerView.getLayoutManager() is LinearLayoutManager) {
            val layoutManager = recyclerView.getLayoutManager() as LinearLayoutManager
            if (layoutManager.orientation == LinearLayoutManager.VERTICAL) {
                totalItemsInView1 = layoutManager.itemCount
                if (totalItemsInView1 != layoutManager.findLastCompletelyVisibleItemPosition()) {
                    visibleitems1 =
                        layoutManager.findLastCompletelyVisibleItemPosition() - 1

                }
                if (layoutManager.findLastCompletelyVisibleItemPosition() >= 28) {

                    visibleitems1 =
                        layoutManager.findLastCompletelyVisibleItemPosition() + 1

                } else {
                    visibleitems1 =
                        layoutManager.findLastCompletelyVisibleItemPosition() - 1

                }
            }
        }

        verticalOffSet1 += dy

        val currFirstPositionView: View? =
            recyclerView.findChildViewUnder(dx.toFloat(), dy.toFloat())
        if (currFirstPositionView != null) {

            val currentPosition: Int = visibleitems1

            if (currentPosition > 0) {
                if (previouslySelected != currentPosition) {

                    //first remove previously clicked item
                    isCellHighlighted = true
                    isCellHighlightedFromTransaction = false
                    transactionsView.rvTransactionsBarChart.getChildAt(
                        previouslySelected
                    )
                        .performClick()

                    //now list click
                    isCellHighlighted = true
                    isCellHighlightedFromTransaction = true
                    //                            if (currentPosition >0) {
                    transactionsView.rvTransactionsBarChart.smoothScrollToPosition(
                        horizontalScrollPosition
                    )
                    val newView =
                        transactionsView.rvTransactionsBarChart.getChildAt(horizontalScrollPosition)
                    newView.performClick()
                    addTooltip(newView.findViewById(R.id.transactionBar))
                    previouslySelected = horizontalScrollPosition

                }

            }
        }
    }

    private fun scrollBarsFromListTouch(
        recyclerView: RecyclerView,
        totalItemsInView: Int,
        visibleitems: Int,
        verticalOffSet: Int,
        dy: Int,
        dx: Int
    ) {
        var totalItemsInView1 = totalItemsInView
        var visibleitems1 = visibleitems
        var verticalOffSet1 = verticalOffSet
        if (recyclerView.getLayoutManager() is LinearLayoutManager) {
            val layoutManager = recyclerView.getLayoutManager() as LinearLayoutManager
            if (layoutManager.orientation == LinearLayoutManager.VERTICAL) {
                totalItemsInView1 = layoutManager.itemCount
                if (totalItemsInView1 != layoutManager.findLastCompletelyVisibleItemPosition()) {
                    visibleitems1 =
                        layoutManager.findLastCompletelyVisibleItemPosition() - 1
                }
                if (layoutManager.findLastCompletelyVisibleItemPosition() >= 28) {

                    visibleitems1 =
                        layoutManager.findLastCompletelyVisibleItemPosition() + 1

                } else {
                    visibleitems1 =
                        layoutManager.findLastCompletelyVisibleItemPosition() - 1

                }
            }
        }

        verticalOffSet1 += dy

        val currFirstPositionView: View? =
            recyclerView.findChildViewUnder(dx.toFloat(), dy.toFloat())
        if (currFirstPositionView != null) {
            //                    val currentPosition: Int = recyclerView.getChildAdapterPosition(
            //                        currFirstPositionView!!
            //                    )
            val currentPosition: Int = visibleitems1

            if (currentPosition > 0) {
                if (previouslySelected != currentPosition) {

                    //first remove previously clicked item
                    isCellHighlighted = true
                    isCellHighlightedFromTransaction = false
                    transactionsView.rvTransactionsBarChart.getChildAt(
                        previouslySelected
                    )
                        .performClick()

                    //now list click
                    isCellHighlighted = true
                    isCellHighlightedFromTransaction = true
                    //                            if (currentPosition >0) {
                    transactionsView.rvTransactionsBarChart.smoothScrollToPosition(
                        currentPosition - 1
                    )
                    val newView =
                        transactionsView.rvTransactionsBarChart.getChildAt(currentPosition - 1)
                    newView.performClick()
                    addTooltip(newView.findViewById(R.id.transactionBar))
                    previouslySelected = currentPosition - 1
                }

            }
        }
    }

    private fun autoScrollTransactionsOnGraphScroll() {
        var verticalOffSet: Int = 0
        var totalItemsInView: Int = 0
        var visibleitems: Int = 0

        transactionsView.rvTransactionsBarChart.addOnScrollListener(object :
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

                    if (currentPosition >= 0) {
                        if (previouslySelected != currentPosition) {

//first remove previously clicked item
                            isCellHighlighted = true
                            isCellHighlightedFromTransaction = false
                            transactionsView.rvTransaction.getChildAt(previouslySelected)
                                .performClick()

                            //now list click
                            isCellHighlighted = true
                            isCellHighlightedFromTransaction = true
                            transactionsView.rvTransaction.smoothScrollToPosition(
                                currentPosition - 1
                            )
                            transactionsView.rvTransaction.getChildAt(currentPosition - 1)
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

}