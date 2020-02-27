package co.yap.modules.dashboard.home.helpers.transaction

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.DisplayMetrics
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import co.yap.R
import co.yap.modules.dashboard.home.interfaces.IYapHome
import co.yap.networking.transactions.responsedtos.transaction.HomeTransactionListData
import co.yap.widgets.tooltipview.TooltipView
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.DateUtils.FORMAT_DATE_MON_YEAR
import co.yap.yapcore.helpers.DateUtils.FORMAT_MON_YEAR
import co.yap.yapcore.helpers.RecyclerTouchListener
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.dimen
import com.yarolegovich.discretescrollview.DiscreteScrollView
import kotlinx.android.synthetic.main.content_fragment_yap_home.view.*
import kotlinx.android.synthetic.main.fragment_yap_home.view.*
import kotlinx.android.synthetic.main.view_graph.view.*
import java.util.*

class TransactionsViewHelper(
    val context: Context, val transactionsView: View,
    val viewModel: IYapHome.ViewModel

) {
    private var tooltip: TooltipView? = null
    var checkScroll: Boolean = false
    var totalItemCount: Int = 0
    var barSelectedPosition: Int = 0
    private var toolbarCollapsed = false
    private var rvTransactionScrollListener: OnScrollListener? = null


    init {
        setOnGraphBarClickListeners()
        initCustomTooltip()
        //setTooltipOnZero()
        setRvTransactionScroll()

    }


    private fun setOnGraphBarClickListeners() {

        transactionsView.rvTransactionsBarChart.addOnItemTouchListener(
            RecyclerTouchListener(
                context, true, transactionsView.rvTransactionsBarChart,
                object : RecyclerTouchListener.ClickListener {
                    override fun onLeftSwipe(view: View, position: Int) {
                        val layoutManager =
                            transactionsView.rvTransactionsBarChart.layoutManager as LinearLayoutManager
                        if (position >= layoutManager.findLastVisibleItemPosition()) {
                            transactionsView.rvTransactionsBarChart.scrollToPosition(
                                layoutManager.findLastCompletelyVisibleItemPosition() + 1
                            )
                        }
                        checkScroll = true
                        view.performClick()
                        transactionsView.rvTransaction.smoothScrollToPosition(position)

                    }

                    override fun onRightSwipe(view: View, position: Int) {
                        val layoutManager =
                            transactionsView.rvTransactionsBarChart.layoutManager as LinearLayoutManager
                        if (position <= layoutManager.findFirstVisibleItemPosition()) {

                            transactionsView.rvTransactionsBarChart.scrollToPosition(
                                layoutManager.findFirstCompletelyVisibleItemPosition() - 1
                            )
                        }
                        checkScroll = true
                        view.performClick()
                        transactionsView.rvTransaction.smoothScrollToPosition(position)
                    }

                    override fun onClick(view: View, position: Int) {
                        checkScroll = true
                        view.performClick()
                        removeRvTransactionScroll()
                        transactionsView.rvTransaction.smoothScrollToPosition(position)
                        setRvTransactionScroll()
                        if (position == 0) {
                            transactionsView.appbar.setExpanded(true)

                        }

                    }
                }
            )
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

    fun setTooltipOnZero() {
        setTooltipVisibility(View.VISIBLE)
        addToolTipDelay(300) {
            val newView =
                transactionsView.rvTransactionsBarChart.getChildAt(0)
            if (null != newView) {
                addTooltip(
                    newView.findViewById(R.id.transactionBar),
                    viewModel.transactionsLiveData.value!![0], true
                )
            }
        }
    }

    fun setTooltipVisibility(visibility: Int = View.VISIBLE) {
        transactionsView.tvTransactionDate?.visibility = visibility
        tooltip?.visibility = visibility
    }

    fun addTooltip(view: View?, data: HomeTransactionListData, firstTime: Boolean = false) {
        transactionsView.tvTransactionDate.visibility = View.VISIBLE
        transactionsView.tvTransactionDate.text = data.date?.let {
            DateUtils.reformatStringDate(
                it,
                FORMAT_DATE_MON_YEAR,
                FORMAT_MON_YEAR
            )
        }

        view?.let {
            val text = String.format(
                Locale.US,
                "%s AED %s",
                data.date,
                Utils.getFormattedCurrency(data.closingBalance.toString())
            )
            tooltip?.apply {
                visibility = View.VISIBLE
                it.bringToFront()
                this.text = data.date?.let {
                    SpannableString(text).apply {
                        setSpan(
                            ForegroundColorSpan(ContextCompat.getColor(context, R.color.greyDark)),
                            0,
                            it.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
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
                if (firstTime) {
                    addToolTipDelay(10) {
                        y = (it.y - this.height) + context.dimen(R.dimen._8sdp)
                    }
                } else {
                    y = (it.y - this.height) + context.dimen(R.dimen._8sdp)
                }


//                 y = viewPosition[1].toFloat() - this.height - toolbarHeight - view.height - Utils.convertDpToPx(context, 20f)
                val notificationView =
                    transactionsView.findViewById<DiscreteScrollView>(R.id.rvNotificationList)
                if (notificationView.adapter?.itemCount?:0 > 0) {
                    y += notificationView.height
                }
            }

        }

    }

    private fun removeRvTransactionScroll() {
        transactionsView.rvTransaction.removeOnScrollListener(rvTransactionScrollListener!!)
    }

    private fun setRvTransactionScroll() {

        rvTransactionScrollListener =
            object : OnScrollListener() {
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
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val position = layoutManager.findFirstVisibleItemPosition()
                    if (!checkScroll) {
                        val graphLayoutManager =
                            transactionsView.rvTransactionsBarChart.layoutManager as LinearLayoutManager
                        // if dy is greater then 0 mean scroll to bottom
                        // if dy is less then 0 means scroll to top
                        val view =
                            transactionsView.rvTransactionsBarChart.layoutManager?.findViewByPosition(
                                position
                            )
                        view?.performClick()
                        if (dy > 0) {
                            if (position >= graphLayoutManager.findLastVisibleItemPosition()) {
                                transactionsView.rvTransactionsBarChart.scrollToPosition(
                                    graphLayoutManager.findLastCompletelyVisibleItemPosition() + 1
                                )

                            }
                        } else if (dy < 0) {
                            if (position <= graphLayoutManager.findFirstVisibleItemPosition()) {

                                transactionsView.rvTransactionsBarChart.scrollToPosition(
                                    graphLayoutManager.findFirstCompletelyVisibleItemPosition() - 1
                                )

                            }

                        }
                    }


                }
            }

        transactionsView.rvTransaction.addOnScrollListener(rvTransactionScrollListener!!)

    }

    fun onToolbarCollapsed() {
        toolbarCollapsed = true
    }

    fun onToolbarExpanded() {
        toolbarCollapsed = false
    }

}
