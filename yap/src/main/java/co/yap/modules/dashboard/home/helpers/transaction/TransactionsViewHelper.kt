package co.yap.modules.dashboard.home.helpers.transaction

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
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.yap.R
import co.yap.modules.dashboard.home.adaptor.GraphBarsAdapter
import co.yap.modules.dashboard.home.adaptor.GraphBarsAdapter.Companion.isCellHighlighted
import co.yap.modules.dashboard.home.adaptor.GraphBarsAdapter.Companion.isCellHighlightedFromTransaction
import co.yap.modules.dashboard.home.adaptor.GraphBarsAdapter.Companion.previouslySelected
import co.yap.modules.dashboard.home.adaptor.TransactionsHeaderAdapter
import co.yap.modules.dashboard.home.interfaces.IYapHome
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.widgets.tooltipview.TooltipView
import co.yap.yapcore.helpers.RecyclerTouchListener
import co.yap.yapcore.helpers.Utils
import kotlinx.android.synthetic.main.content_fragment_yap_home.view.*
import kotlinx.android.synthetic.main.view_graph.view.*


class TransactionsViewHelper(
    val owner: LifecycleOwner,
    val context: Context, val transactionsView: View,
    val viewModel: IYapHome.ViewModel

) {
    private var tooltip: TooltipView? = null
    var checkScroll: Boolean = false
    var horizontalScrollPosition: Int = 0
    private var toolbarCollapsed = false


    init {
//
//        initState()
//        initComponents()
        previouslySelected = 0
        setUpTransactionsListRecyclerView()
//        viewModel.transactionsLiveDataA.observe(owner, Observer {
//            (transactionsView.rvTransaction.adapter as TransactionsHeaderAdapter).submitList(it)
//            getRecycleViewAdaptor()?.setState(PagingState.DONE)
//        })


//        setUpGraphRecyclerView()
        setOnGraphBarClickListeners()
        setOnTransactionCellClickListeners()
        autoScrollGraphBarsOnTransactionsListScroll()
        initCustomTooltip()
        // setTooltipOnZero()

    }

//
//    fun calculatePercentagePerDayFromClosingBalance(closingBalance : Double) : Double {
////  will count it in the end beacause we already kniw the current closing balance if rhe kast transactiuon in thr day
////  val maxClosingBalance = closingBalanceArray.max()
//  transactions closing balance of all the days from past
//
//        return (closingBalance/viewModel.MAX_CLOSING_BALANCE) * 100
//
//        return closingBalanceArray.map { (0 / viewModel.MAX_CLOSING_BALANCE) * 100
//
//    }

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
            previouslySelected = 0
            addTooltip(
                newView.findViewById(R.id.transactionBar),
                viewModel.transactionLogicHelper.transactionList!![0]
            )
        }
    }

    private fun addTooltip(view: View?, data: HomeTransactionListData) {
        view?.let {
            val text =
                data.date + " AED " + Utils.getFormattedCurrency(data.closingBalance.toString())
            tooltip?.apply {
                visibility = View.VISIBLE
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

                y =
                    viewPosition[1].toFloat() - this.height - toolbarHeight - view.height - Utils.convertDpToPx(
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
//        transactionsView.rvTransaction.adapter =
//            TransactionsHeaderAdapter(
//                context,
//                viewModel.transactionLogicHelper.transactionList
//            )
    }

    private fun setUpGraphRecyclerView() {
//        if (!viewModel.transactionLogicHelper.transactionList.value.isNullOrEmpty()) {
//            transactionsView.rvTransactionsBarChart.adapter =
//                GraphBarsAdapter(
//                    viewModel.transactionLogicHelper.transactionList,
//                   /* context,*/
//                    viewModel.MAX_CLOSING_BALANCE
//                )
            transactionsView.rvTransactionsBarChart.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                true
            )
//        }
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
                            viewModel.transactionLogicHelper.transactionList!![position]
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
//                        checkScroll = false
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
//                        val newView =
//                            transactionsView.rvTransactionsBarChart.getChildAt(position)
////                                .apply {
////                                performClick()
////                            }
//                        transactionsView.rvTransaction.smoothScrollToPosition(position)
////                        previouslySelected = position
////
//                        addTooltip(
//                            newView.findViewById(R.id.transactionBar),
//                            viewModel.transactionLogicHelper.transactionList[position]
//                        )
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

    //
//    private fun initComponents() {
//        transactionsView.rvTransaction.adapter = TransactionsHeaderAdapter { viewModel.retry() }
//        (transactionsView.rvTransaction.adapter as TransactionsHeaderAdapter).setItemListener(
//            listener
//        )
//    }
//
//    private fun initState() {
//        //retryBtn.setOnClickListener { viewModel.retry() }
//        viewModel.getState().observe(owner, Observer { state ->
//            if (viewModel.listIsEmpty()) {
//                transactionsView.rvTransaction.visibility = View.GONE
////                txt_error.visibility =
////                    if (state == PagingState.DONE || state == PagingState.ERROR) View.VISIBLE else View.GONE
////                progress_bar.visibility =
////                    if (state == PagingState.LOADING) View.VISIBLE else View.GONE
//            } else {
////                txt_error.visibility = View.GONE
////                progress_bar.visibility = View.GONE
////                transactionsView.rvTransaction.visibility = View.VISIBLE
//                getRecycleViewAdaptor()?.setState(state)
//            }
//        })
//    }
//
//    val listener = object : OnItemClickListener {
//        override fun onItemClick(view: View, data: Any, pos: Int) {
////            val action =
////                YapStoreFragmentDirections.actionYapStoreFragmentToYapStoreDetailFragment((data as Store).id.toString())
////            view.findNavController().navigate(action)
//        }
//    }
//
    private fun getRecycleViewAdaptor(): TransactionsHeaderAdapter? {
        return if (transactionsView.rvTransaction.adapter is TransactionsHeaderAdapter) {
            (transactionsView.rvTransaction.adapter as TransactionsHeaderAdapter)
        } else {
            null
        }
    }

}