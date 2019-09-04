package co.yap.modules.dashboard.helpers.transaction

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
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
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.OnBalloonClickListener
import com.skydoves.balloon.createBalloon
import it.sephiroth.android.library.xtooltip.ClosePolicy
import it.sephiroth.android.library.xtooltip.Tooltip
import kotlinx.android.synthetic.main.content_fragment_yap_home.view.*
import kotlinx.android.synthetic.main.view_graph.view.*


class TransactionsViewHelper(
    val context: Context, val transactionsView: View,
    val viewModel: IYapHome.ViewModel
) {
    var tooltip: Tooltip? = null
    lateinit var balloon: Balloon

    init {
        setUpTransactionsListRecyclerView()
        setUpGraphRecyclerView()

        setOnGraphBarClickListeners()
        setOnTransactionCellClickListeners()
        autoScrollGraphBarsOnTransactionsListScroll()
//        autoScrollTransactionsOnGraphScroll()

        initBalloon()
    }

    private fun initBalloon() {
        balloon = createBalloon(context) {
            setArrowSize(10)
            setWidthRatio(0.5f)
            setHeight(65)
            // setArrowPosition(0.7f)
            setCornerRadius(4f)
            setAlpha(0.9f)
            setText("You can access your profile from on now.")
            setTextColorResource(R.color.white)
            setBackgroundColorResource(R.color.colorPrimary)
            setDismissWhenClicked(true)
            setBalloonAnimation(BalloonAnimation.FADE)
            setLifecycleOwner(context as LifecycleOwner)
        }
    }

    private fun addTooltip(view: View?) {
        view?.let {
            tooltip?.dismiss()
            tooltip = Tooltip.Builder(context)
                // .anchor(view, 0, 0, true)
                .anchor(500, 500)
                .text("Hello from dynamic")
                // .styleId(Int)
                .maxWidth(400)
                .arrow(true)
                .floatingAnimation(Tooltip.Animation.DEFAULT)
                .closePolicy(ClosePolicy.TOUCH_NONE)
                // .showDuration(5000)
                // .fadeDuration(1000)
                .overlay(true)
                .create()
            tooltip?.show(view, Tooltip.Gravity.TOP, true)
        }


    }

    private fun showTooltip(view: View?) {
        view?.let {
            // tooltip?.show(view, Tooltip.Gravity.TOP, true)
            balloon.dismiss()
            balloon.show(view)
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
                context,
                tooltip
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

                        showTooltip(view)

                        isCellHighlighted = false
                        isCellHighlightedFromTransaction = false

                        transactionsView.rvTransactionsBarChart.getChildAt(previouslySelected)
                            .performClick()

                        transactionsView.rvTransaction.smoothScrollToPosition(position)
                        previouslySelected = position

                    }

                    override fun onLongClick(view: View?, position: Int) {

                    }

                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onClick(view: View, position: Int) {
                        Toast.makeText(
                            context,
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
                context, transactionsView.rvTransaction,
                object : RecyclerTouchListener.ClickListener {
                    override fun onItemTouchEvent(view: View?, position: Int) {

                    }

                    override fun onLongClick(view: View?, position: Int) {

                    }

                    override fun onClick(view: View, position: Int) {
                        Toast.makeText(
                            context,
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

                        } else {
                            visibleitems = layoutManager.findLastCompletelyVisibleItemPosition() - 1

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

                    if (currentPosition > 0) {
                        if (previouslySelected != currentPosition) {

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
                                currentPosition - 1
                            )
                            val v = transactionsView.rvTransactionsBarChart.getChildAt(currentPosition - 1)
                            v.performClick()
                            previouslySelected = currentPosition - 1

                            showTooltip(v)
                        }

                    }
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

            }
        })
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