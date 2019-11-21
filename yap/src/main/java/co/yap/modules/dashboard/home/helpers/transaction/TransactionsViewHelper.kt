package co.yap.modules.dashboard.home.helpers.transaction

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import co.yap.R
import co.yap.modules.dashboard.home.adaptor.GraphBarsAdapter.Companion.isCellHighlighted
import co.yap.modules.dashboard.home.adaptor.GraphBarsAdapter.Companion.isCellHighlightedFromTransaction
import co.yap.modules.dashboard.home.adaptor.GraphBarsAdapter.Companion.previouslySelected
import co.yap.modules.dashboard.home.interfaces.IYapHome
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
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
    private var toolbarCollapsed = false
    var rvTransactionScrollListener: RecyclerView.OnScrollListener? = null

    init {
        previouslySelected = 0
        setOnGraphBarClickListeners()
        //setOnTransactionCellClickListeners()
        //autoScrollGraphBarsOnTransactionsListScroll()
        initCustomTooltip()
        setTooltipOnZero()
        setRvTransactionScroll()
//        transactionsView.rvTransaction.run {
//            addOnScrollListener(rvTransactionScrollListener)
//        }

    }


    private fun setOnGraphBarClickListeners() {

        transactionsView.rvTransactionsBarChart.addOnItemTouchListener(
            RecyclerTouchListener(
                context, true, transactionsView.rvTransactionsBarChart,
                object : RecyclerTouchListener.ClickListener {

                    override fun onClick(view: View, position: Int) {
                        checkScroll = true

//                        val onScrollListener: RecyclerView.OnScrollListener =
//                            object : RecyclerView.OnScrollListener() {
//                                override fun onScrollStateChanged(
//                                    recyclerView: RecyclerView,
//                                    newState: Int
//                                ) {
//                                    when (newState) {
//                                        SCROLL_STATE_IDLE -> {
////                                            isCellHighlighted = true
////                                            isCellHighlightedFromTransaction = true
//
////                                            recyclerView.removeOnScrollListener(this)
////                                            transactionsView.rvTransactionsBarChart.getChildAt(
////                                                position
////                                            )?.performClick()
////                                            addTooltip(
////                                                transactionsView.rvTransactionsBarChart.getChildAt(
////                                                    position
////                                                )?.findViewById(R.id.parent),
////                                                viewModel.transactionsLiveData.value?.get(position)!!
////                                            )
////                                            previouslySelected = position
//                                        }
//
//                                    }
//                                }
//                            }
//
//                        transactionsView.rvTransaction.removeOnScrollListener(onScrollListener)
//                        transactionsView.rvTransaction.addOnScrollListener(onScrollListener)
                        removeRvTransactionScroll()
                        transactionsView.rvTransaction.smoothScrollToPosition(position)
                        setRvTransactionScroll()

//                        isCellHighlighted = false
//                        isCellHighlightedFromTransaction = false

//                        transactionsView.rvTransactionsBarChart.getChildAt(
//                            previouslySelected
//                        )?.performClick()

                        horizontalScrollPosition = position
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
                    override fun onClick(view: View, position: Int) {
                        checkScroll = false
                        checkScroll = false
//                        checkScroll = true
                        isCellHighlighted = false
                        isCellHighlightedFromTransaction = false
//
//                        transactionsView.rvTransactionsBarChart.getChildAt(previouslySelected)
//                            .performClick()
                        horizontalScrollPosition = position
                        isCellHighlighted = true
                        isCellHighlightedFromTransaction = true
                        val newView =
                            transactionsView.rvTransactionsBarChart.getChildAt(position)
//                                .apply {
//                                performClick()
//                            }
                        transactionsView.rvTransaction.smoothScrollToPosition(position)
//                        previouslySelected = position
//
//                        addTooltip(
//                            newView.findViewById(R.id.transactionBar),
//                            viewModel.transactionLogicHelper.transactionList[position]
//                        )

                    }
                })
        )
    }

    private fun initCustomTooltip() {
        tooltip = transactionsView.findViewById(R.id.tooltip)
    }

    private fun addToolTipDelay(delay: Long, process: () -> Unit) {
        Handler().postDelayed({
            process()
        }, delay)
    }

    private fun setTooltipOnZero() {
        addToolTipDelay(300) {
            val newView =
                transactionsView.rvTransactionsBarChart.getChildAt(0)
            if (null != newView) {
                previouslySelected = 0
                addTooltip(
                    newView.findViewById(R.id.parent),
                    viewModel.transactionsLiveData.value!![0]
                )
            }
        }
    }

    fun addTooltip(view: View?, data: HomeTransactionListData) {
        view?.let {
            val text =
                data.date + " AED " + Utils.getFormattedCurrency(data.closingBalance.toString())
            tooltip?.apply {
                visibility = View.VISIBLE
                it.bringToFront()
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
                    translationX = (screen.widthPixels - this.width).toFloat()
                    // Adjust position of arrow of tooltip
                    arrowX = viewPosition[0] - x
                } else {
                    translationX = viewPosition[0].toFloat()
                    arrowX = 0f
                }


                // Calculate Y. Subtract the height of the collapsing toolbar
                var toolbarHeight =
                    context.resources.getDimension(R.dimen.collapsing_toolbar_height)

                if (toolbarCollapsed) toolbarHeight =
                    Utils.getNavigationBarHeight(context as Activity).toFloat()
//                y = viewPosition[1].toFloat() - this.height
                // (view as ChartViewV2).width/2
                //this.height -(view as ChartViewV2).barHeight
                Log.d("------------", "--------------------------")
                Log.d("viewPosition Y >>", viewPosition[1].toString())
                Log.d("bar View height>>", view.height.toString())
                Log.d("tooltip height Y >>", this.height.toString())
                Log.d("convertDpToPx >>", Utils.convertDpToPx(context, 20f).toString())
                Log.d("------------", "--------------------------")

                y = view.bottom.toFloat() - view.height - 140
                // y = viewPosition[1].toFloat() - this.height - toolbarHeight - view.height - Utils.convertDpToPx(context, 20f)
            }

        }

    }


    private fun autoScrollGraphBarsOnTransactionsListScroll() {

        var verticalOffSet: Int = 0
        var totalItemsInView: Int = 0
        var visibleitems: Int = 0

        transactionsView.rvTransaction.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                try {
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
                } catch (e: Exception) {
                    e.printStackTrace()
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
        if (recyclerView.layoutManager is LinearLayoutManager) {
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

    }

    private fun removeRvTransactionScroll() {

        transactionsView.rvTransaction.removeOnScrollListener(rvTransactionScrollListener!!)
    }

    private fun setRvTransactionScroll() {

        rvTransactionScrollListener =
            object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    when (newState) {
                        SCROLL_STATE_IDLE -> {
                            checkScroll = false
                        }
                        SCROLL_STATE_DRAGGING -> {

                        }
                        SCROLL_STATE_SETTLING -> {

                        }
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    var layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val position = layoutManager.findFirstVisibleItemPosition()
                    if (!checkScroll) {
                        transactionsView.rvTransactionsBarChart.layoutManager?.findViewByPosition(
                            position
                        )
                            ?.performClick()
                    }
                }
            }

        transactionsView.rvTransaction.addOnScrollListener(rvTransactionScrollListener!!)
//        transactionsView.rvTransaction.addOnScrollListener(object :
//            RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//            }
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                var layoutManager = recyclerView.layoutManager as LinearLayoutManager
//                val position = layoutManager.findFirstVisibleItemPosition()
//                transactionsView.rvTransactionsBarChart.layoutManager?.findViewByPosition(position)
//                    ?.performClick()
//
//
//            }
//        })

    }


    private fun scrollBarsFromListTouch(
        recyclerView: RecyclerView,
        totalItemsInView: Int,
        visibleitems: Int,
        verticalOffSet: Int,
        dy: Int,
        dx: Int
    ) {
//        var totalItemsInView1 = totalItemsInView
//        var visibleitems1 = visibleitems
//        var verticalOffSet1 = verticalOffSet
//        if (recyclerView.getLayoutManager() is LinearLayoutManager) {
//            val layoutManager = recyclerView.getLayoutManager() as LinearLayoutManager
//            if (layoutManager.orientation == LinearLayoutManager.VERTICAL) {
//                totalItemsInView1 = layoutManager.itemCount
////                if (totalItemsInView1 != layoutManager.findFirstCompletelyVisibleItemPosition()) {
////                    visibleitems1 =
////                        layoutManager.findFirstVisibleItemPosition()
////                }
//                if (layoutManager.findFirstCompletelyVisibleItemPosition() >= 28) {
//
//                    visibleitems1 =
//                        layoutManager.findFirstCompletelyVisibleItemPosition()
//
//                } else {
//                    visibleitems1 =
//                        layoutManager.findFirstCompletelyVisibleItemPosition() - 1
//
//                }
//            }
//        }
//
//        verticalOffSet1 += dy
//
//        val currFirstPositionView: View? =
//            recyclerView.findChildViewUnder(dx.toFloat(), dy.toFloat())
//        if (currFirstPositionView != null) {
//            //                    val currentPosition: Int = recyclerView.getChildAdapterPosition(
//            //                        currFirstPositionView!!
//            //                    )
//            val currentPosition: Int = visibleitems1
//
//            if (currentPosition >= 0) {
//                if (previouslySelected != currentPosition) {
//
//                    //first remove previously clicked item
//                    isCellHighlighted = true
//                    isCellHighlightedFromTransaction = false
//                    transactionsView.rvTransactionsBarChart.getChildAt(
//                        previouslySelected
//                    )
//                        .performClick()
//
//                    //now list click
//                    isCellHighlighted = true
//                    isCellHighlightedFromTransaction = true
//                    //                            if (currentPosition >0) {
//                    transactionsView.rvTransactionsBarChart.smoothScrollToPosition(
//                        currentPosition
//                    )
//                    val newView =
//                        transactionsView.rvTransactionsBarChart.getChildAt(currentPosition)
//                    newView.performClick()
//                    addTooltip(
//                        newView.findViewById(R.id.transactionBar),
//                        viewModel.transactionLogicHelper.transactionList[currentPosition]
//                    )
//                    previouslySelected = currentPosition
//                }
//
//            }
//        }
    }

    fun onToolbarCollapsed() {
        toolbarCollapsed = true
    }

    fun onToolbarExpanded() {
        toolbarCollapsed = false
    }

}
