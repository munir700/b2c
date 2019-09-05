package co.yap.modules.dashboard.helpers.transaction

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Handler
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.DisplayMetrics
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.modules.dashboard.adapters.GraphBarsAdapter
import co.yap.modules.dashboard.adapters.GraphBarsAdapter.Companion.isCellHighlighted
import co.yap.modules.dashboard.adapters.GraphBarsAdapter.Companion.isCellHighlightedFromTransaction
import co.yap.modules.dashboard.adapters.GraphBarsAdapter.Companion.previouslySelected
import co.yap.modules.dashboard.adapters.TransactionsHeaderAdapter
import co.yap.modules.dashboard.interfaces.IYapHome
import co.yap.modules.dashboard.models.TransactionModel
import co.yap.widgets.tooltipview.TooltipView
import co.yap.yapcore.helpers.RecyclerTouchListener
import co.yap.yapcore.helpers.Utils
import kotlinx.android.synthetic.main.content_fragment_yap_home.view.*
import kotlinx.android.synthetic.main.view_graph.view.*


class TransactionsViewHelper(
    val context: Context, val transactionsView: View,
    val viewModel: IYapHome.ViewModel
) {
    private var tooltip: TooltipView? = null
    var checkScroll: Boolean = false
    var horizontalScrollPosition: Int = 0

    init {
        previouslySelected = 0
        setUpTransactionsListRecyclerView()
        setUpGraphRecyclerView()
        setOnGraphBarClickListeners()
        setOnTransactionCellClickListeners()
        autoScrollGraphBarsOnTransactionsListScroll()
        initCustomTooltip()
        setTooltipOnZero()
    }

    private fun initCustomTooltip() {
        tooltip = transactionsView.findViewById(R.id.tooltip)
    }

    fun addToolTipDelay(delay: Long, process: () -> Unit) {
        Handler().postDelayed({
            process()
        }, delay)
    }

    private fun setTooltipOnZero() {
        addToolTipDelay(300) {
            val newView =
                transactionsView.rvTransactionsBarChart.getChildAt(0)
            previouslySelected=0
            addTooltip(
                newView.findViewById(R.id.transactionBar),
                viewModel.transactionLogicHelper.transactionList[0]
            )
        }
    }

    private fun addTooltip(view: View?, data: TransactionModel) {
        view?.let {
            val text = data.date + " AED " + Utils.getFormattedCurrency(data.closingBalance)
            tooltip?.apply {
                this.text = SpannableString(text).apply {
                    setSpan(
                        ForegroundColorSpan(ContextCompat.getColor(context, R.color.greyDark)),
                        0,
                        data.date.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }

                val viewPosition = IntArray(2)
                view.getLocationInWindow(viewPosition)
                val screen = DisplayMetrics()
                (context as Activity).windowManager.defaultDisplay.getMetrics(screen)

                // Calculate X for tooltip
                if (viewPosition[0] + this.width >= screen.widthPixels) {
                    // It is the end of the screen so adjust X
                    x = (screen.widthPixels - this.width).toFloat()
                    // Adjust position of arrow of tooltip
                    arrowX = viewPosition[0] - x
                } else {
                    x = viewPosition[0].toFloat()
                }


                // Calculate Y. Subtract the height of the collapsing toolbar
                val toolbarHeight =
                    context.resources.getDimension(R.dimen.collapsing_toolbar_height)
                y =
                    (viewPosition[1].toFloat() - toolbarHeight) - (view.height / 2) - Utils.convertDpToPx(
                        context,
                        20f
                    )
            }

        }

    }

    private fun setUpTransactionsListRecyclerView() {
        transactionsView.rvTransaction.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        transactionsView.rvTransaction.layoutManager = layoutManager
        transactionsView.rvTransaction.adapter =
            TransactionsHeaderAdapter(
                context,
                viewModel.transactionLogicHelper.transactionList
            )
    }

    private fun setUpGraphRecyclerView() {
        transactionsView.rvTransactionsBarChart.adapter =
            GraphBarsAdapter(
                viewModel.transactionLogicHelper.transactionList,
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
                context, true, transactionsView.rvTransactionsBarChart,
                object : RecyclerTouchListener.ClickListener {


                    override fun onItemTouchEvent(view: View?, position: Int) {

                        checkScroll = true

                        isCellHighlighted = false
                        isCellHighlightedFromTransaction = false

                        transactionsView.rvTransactionsBarChart.getChildAt(previouslySelected)
                            .performClick()
                        horizontalScrollPosition = position

                        isCellHighlighted = true
                        isCellHighlightedFromTransaction = true

                        val newView =
                            transactionsView.rvTransactionsBarChart.getChildAt(position).apply {
                                performClick()
                            }
                        transactionsView.rvTransaction.smoothScrollToPosition(position)
                        previouslySelected = position

                        addTooltip(
                            newView.findViewById(R.id.transactionBar),
                            viewModel.transactionLogicHelper.transactionList[position]
                        )

                    }

                    override fun scrollOnItemsTouchEvent(view: View?, position: Int) {

                        checkScroll = true
                        isCellHighlighted = false
                        isCellHighlightedFromTransaction = false

                        transactionsView.rvTransactionsBarChart.getChildAt(previouslySelected)
                            .performClick()
                        horizontalScrollPosition = position

                        isCellHighlighted = true
                        isCellHighlightedFromTransaction = true

                        transactionsView.rvTransactionsBarChart.getChildAt(position).apply {
                            performClick()
                        }
                        transactionsView.rvTransaction.smoothScrollToPosition(position)
                        previouslySelected = position

                    }

                    override fun onLongClick(view: View?, position: Int) {

                    }

                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onClick(view: View, position: Int) {

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
                }
            )
        )
    }

    private fun setOnTransactionCellClickListeners() {

        transactionsView.rvTransaction.addOnItemTouchListener(
            RecyclerTouchListener(
                context, false, transactionsView.rvTransaction,
                object : RecyclerTouchListener.ClickListener {
                    override fun scrollOnItemsTouchEvent(view: View?, position: Int) {
                        checkScroll = false

//                        checkScroll = false
////                        checkScroll = true
//                        isCellHighlighted = false
//                        isCellHighlightedFromTransaction = false
////
//                        transactionsView.rvTransactionsBarChart.getChildAt(previouslySelected)
//                            .performClick()
//                        horizontalScrollPosition = position

//                        isCellHighlighted = true
//                        isCellHighlightedFromTransaction = true

                        val newView =
                            transactionsView.rvTransactionsBarChart.getChildAt(position)
//                                .apply {
//                                performClick()
//                            }
                        transactionsView.rvTransaction.smoothScrollToPosition(position)
//                        previouslySelected = position
//
                        addTooltip(
                            newView.findViewById(R.id.transactionBar),
                            viewModel.transactionLogicHelper.transactionList[position]
                        )
                    }

                    override fun onItemTouchEvent(view: View?, position: Int) {
                        checkScroll = false
                    }

                    override fun onLongClick(view: View?, position: Int) {

                    }

                    override fun onClick(view: View, position: Int) {
                        //first remove previously selected
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
//                        transactionsView.rvTransactionsBarChart.getChildAt(position).performClick()
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

//                if (totalItemsInView1 != layoutManager.findFirstCompletelyVisibleItemPosition()) {
//                    visibleitems1 =
//                        layoutManager.findFirstVisibleItemPosition()
//                }
                if (layoutManager.findFirstCompletelyVisibleItemPosition() >= 28) {

                    visibleitems1 =
                        layoutManager.findFirstCompletelyVisibleItemPosition()

                } else {
                    visibleitems1 =
                        layoutManager.findFirstCompletelyVisibleItemPosition() - 1

                }
            }
        }

        verticalOffSet1 += dy

        if (previouslySelected != horizontalScrollPosition) {

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
//                    transactionsView.rvTransactionsBarChart.smoothScrollToPosition(
//                        horizontalScrollPosition
//                    )
            transactionsView.rvTransactionsBarChart.getChildAt(horizontalScrollPosition)
                .performClick()
            previouslySelected = horizontalScrollPosition
        }

//            }
//        }
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
//                if (totalItemsInView1 != layoutManager.findFirstCompletelyVisibleItemPosition()) {
//                    visibleitems1 =
//                        layoutManager.findFirstVisibleItemPosition()
//                }
                if (layoutManager.findFirstCompletelyVisibleItemPosition() >= 28) {

                    visibleitems1 =
                        layoutManager.findFirstCompletelyVisibleItemPosition()

                } else {
                    visibleitems1 =
                        layoutManager.findFirstCompletelyVisibleItemPosition() - 1

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

            if (currentPosition >= 0) {
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
                        currentPosition
                    )
                    val newView =
                        transactionsView.rvTransactionsBarChart.getChildAt(currentPosition)
                    newView.performClick()
                    addTooltip(
                        newView.findViewById(R.id.transactionBar),
                        viewModel.transactionLogicHelper.transactionList[currentPosition]
                    )
                    previouslySelected = currentPosition
                }

            }
        }
    }


}