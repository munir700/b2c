package co.yap.modules.dashboard.home.helpers.transaction

import android.content.Context
import android.os.Handler
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.text.style.SuperscriptSpan
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import co.yap.R
import co.yap.modules.dashboard.home.adaptor.TransactionsHeaderAdapter
import co.yap.modules.dashboard.home.component.categorybar.CustomCategoryBar
import co.yap.modules.dashboard.home.interfaces.IYapHome
import co.yap.networking.transactions.responsedtos.categorybar.Categories
import co.yap.networking.transactions.responsedtos.categorybar.MonthData
import co.yap.translation.Translator
import co.yap.widgets.tooltipview.TooltipView
import co.yap.yapcore.binders.UIBinder
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.DateUtils.TIME_ZONE_Default
import co.yap.yapcore.helpers.extentions.getAvailableBalanceWithFormat
import kotlinx.android.synthetic.main.content_fragment_yap_home_new.view.*
import kotlinx.android.synthetic.main.fragment_dashboard_home.view.*
import java.text.SimpleDateFormat


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
    var visibleMonth: String? = null
    var currentMode: Int = Constants.EXPAND_MODE
    private var filteredList = listOf<Categories>()


    init {
        //setOnGraphBarClickListeners()
        initCustomTooltip()
        //setTooltipOnZero()
        setRvTransactionScroll()
    }

/*
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
                        transactionsView.toolbarLayout.id.appbar.setExpanded(true)

                        }

                    }
                }
            )
        )
    }
*/

    private fun initCustomTooltip() {
        tooltip = transactionsView.findViewById(R.id.tooltip)
    }

    private fun addToolTipDelay(delay: Long, process: () -> Unit) {
        Handler().postDelayed({
            process()
        }, delay)
    }

    /*fun setTooltipOnZero() {
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
    }*/

    fun setTooltipVisibility(visibility: Int = View.VISIBLE) {
        //transactionsView.tvTransactionDate?.visibility = visibility
        tooltip?.visibility = visibility
        tooltip?.arrowView = transactionsView.findViewById(R.id.arrowView)
        tooltip?.arrowView?.visibility = visibility
    }

/*
    fun addTooltip(view: View?, data: HomeTransactionListData, firstTime: Boolean = false) {
        setTooltipVisibility(View.VISIBLE)
       */
/* transactionsView.tvTransactionDate.text = DateUtils.reformatStringDate(
            data.originalDate ?: "",
            "yyyy-MM-dd",
            DateUtils.FORMAT_MON_YEAR
        )*//*


        view?.let {
            val text = String.format(
                Locale.getDefault(),
                "%s \nAED %s",
                DateUtils.reformatStringDate(
                    data.originalDate ?: "",
                    "yyyy-MM-dd",
                    DateUtils.FORMAT_DATE_MON_YEAR
                ),
                data.closingBalance.toString()
                    .toFormattedCurrency(showCurrency = false,
                        currency = SessionManager.getDefaultCurrency())
            )
            tooltip?.apply {
                visibility = View.VISIBLE
                it.bringToFront()
                this.text.text = data.date?.let {
                    SpannableString(text).apply {
                        setSpan(
                            ForegroundColorSpan(ContextCompat.getColor(context, R.color.greyDark)),
                            0,
                            text.substring(0, text.indexOf("\n")).length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                }
                this.ivCross.setOnClickListener { setTooltipVisibility(View.GONE) }

                val viewPosition = IntArray(2)
                val arrowViewPosition = IntArray(2)
                it.getLocationInWindow(viewPosition)
                tooltip?.arrowView?.getLocationInWindow(arrowViewPosition)
                val screen = DisplayMetrics()
                (context as Activity).windowManager.defaultDisplay.getMetrics(screen)
                var rightPadding = 0
                if (viewPosition[0] + this.width >= screen.widthPixels) {
                    // It is the end of the screen so adjust X
//                    if((arrowViewPosition[0].minus(this.width))>screen.widthPixels/2){
//                        translationX =
//                            screen.widthPixels.toFloat() - this.width-10
//                    }else {
                    translationX =
                        screen.widthPixels.toFloat() - this.width - rightPadding / 2

//                    }

                    // Adjust position of arrow of tooltip
                    arrowX = viewPosition[0] - x + (it.width / 2) - rightPadding / 2
                    tooltip?.arrowView?.translationX =
                        ((viewPosition[0].toFloat() - (it.width / 2))) + context.dimen(R.dimen.tooltip_default_corner_radius) - rightPadding / 2 // translationX-it.width / 2//viewPosition[0] - x + (it.width / 2)
                } else {
                    val viewWidth = it.width + (context.dimen(R.dimen.margin_one_dp) * 2)
                    if ((viewPosition[0] - viewWidth) > 0) {
                        translationX =
                            viewPosition[0].toFloat() - tooltip?.arrowView?.width!!//context.dimen(R.dimen._2sdp)
                        arrowX =
                            viewPosition[0] - x + (it.width / 2)
                        tooltip?.arrowView?.translationX =
                            viewPosition[0].toFloat() - (it.width / 2) + context.dimen(R.dimen.tooltip_default_corner_radius)
                    } else {
                        translationX = 0f
                        arrowX = 0f
                        tooltip?.arrowView?.translationX = viewPosition[0].toFloat()
                    }
                }
                val notificationView =
                    transactionsView.findViewById<DiscreteScrollView>(R.id.rvNotificationList)
                if (firstTime) {
                    addToolTipDelay(10) {
                        y = (it.y - this.height) - (tooltip?.arrowView?.height?.div(2)
                            ?: context.dimen(R.dimen._6sdp))
                        if (notificationView.adapter?.itemCount ?: 0 > 0) {
                            y += notificationView.height
                        }
                    }
                } else {
                    y = (it.y - this.height) - (tooltip?.arrowView?.height?.div(2) ?: context.dimen(
                        R.dimen._6sdp
                    ))
                    if (notificationView.adapter?.itemCount ?: 0 > 0) {
                        y += notificationView.height
                    }
                }
            }
        }
    }
*/

    private fun removeRvTransactionScroll() {
        //   rvTransactionScrollListener?.let { transactionsView.rvTransaction.removeOnScrollListener(it) }

    }

    private fun setRvTransactionScroll() {
        rvTransactionScrollListener =
            object : OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    when (newState) {
                        SCROLL_STATE_IDLE -> {
                            //reached top
                            if (!recyclerView.canScrollVertically(-1)) {
                                checkScroll = false
                                transactionsView.layoutBalance.tvBalanceTitle.text =
                                    Translator.getString(
                                        context,
                                        R.string.screen_fragment_yap_home_todays_balance
                                    )
                                transactionsView.layoutBalance.tvAvailableBalance.text =
                                    viewModel.state.availableBalance
                                        .getAvailableBalanceWithFormat()
                                val filterd: List<MonthData>? =
                                    viewModel.monthData?.value?.filter { monthData -> monthData.date == visibleMonth }
                                filterd?.let {
                                    if (filterd.isNotEmpty()) {
                                        filteredList =
                                            filterd[0].categories
                                        viewModel.transactionsLiveData.value?.let { list ->
                                            if (filteredList.isNotEmpty() && list.isNotEmpty()) {
                                                updateData(
                                                    transactionsView.customCategoryBar,
                                                    filteredList,
                                                    SimpleDateFormat(DateUtils.SERVER_DATE_FORMAT).parse(
                                                        list[0]?.originalDate
                                                    ).toString(),
                                                    Constants.EXPAND_MODE,
                                                    false
                                                )
                                                transactionsView.customCategoryBar.visibility =
                                                    View.VISIBLE
                                            } else {
                                                setCategoryWithZero()
                                            }
                                        }
                                    } else {
                                        setCategoryWithZero()
                                    }
                                } ?: setCategoryWithZero()

                                currentMode = Constants.EXPAND_MODE
                            }
                        }
                        SCROLL_STATE_DRAGGING -> {
                            checkScroll = true
                        }
                        SCROLL_STATE_SETTLING -> {
                            checkScroll = true
                        }
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    var issc = false
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val position = layoutManager.findFirstVisibleItemPosition()
                    if (transactionsView.lyInclude.multiStateView.rvTransaction.adapter is TransactionsHeaderAdapter) {
                        viewModel.transactionsLiveData.value?.takeIf { it.isNotEmpty() }?.apply {

                            if ((transactionsView.lyInclude.multiStateView.rvTransaction.adapter as TransactionsHeaderAdapter).getDataList().size > 0) {
                                if (!checkScroll) {
                                    transactionsView.layoutBalance.tvAvailableBalance.text =
                                        viewModel.state.availableBalance
                                            .getAvailableBalanceWithFormat()
                                    transactionsView.layoutBalance.tvBalanceTitle.text =
                                        Translator.getString(
                                            context,
                                            R.string.screen_fragment_yap_home_todays_balance
                                        )
                                    visibleMonth =
                                        this[position].monthYear.toString()

                                    val filterd: List<MonthData>? =
                                        viewModel.monthData?.value?.filter { monthData -> monthData.date == visibleMonth }
                                    filterd?.let {
                                        if (filterd.isNotEmpty()) {
                                            filteredList =
                                                filterd[0].categories
                                            if (filteredList.isNotEmpty()) {
                                                updateData(
                                                    transactionsView.customCategoryBar,
                                                    filteredList,
                                                    SimpleDateFormat(DateUtils.SERVER_DATE_FORMAT).parse(
                                                        this[position].originalDate
                                                    ).toString(),
                                                    Constants.DEFAULT_MODE,
                                                    false
                                                )
                                                transactionsView.customCategoryBar.visibility =
                                                    View.VISIBLE
                                            } else {
                                                setCategoryWithZero()
                                            }
                                        } else {
                                            setCategoryWithZero()
                                        }
                                    } ?: setCategoryWithZero()
                                    currentMode = Constants.DEFAULT_MODE
                                } else {
                                    //new month
                                    if (this[position].monthYear.isNullOrBlank()) {
                                        this[position].monthYear =
                                            visibleMonth
                                    }
                                    if (this[position].monthYear.toString() != visibleMonth) {

                                        val filterd: List<MonthData>? =
                                            viewModel.monthData?.value?.filter { monthData ->
                                                monthData.date == this[position].monthYear.toString()
                                            }
                                        filterd?.let {
                                            if (filterd.isNotEmpty()) {
                                                filteredList =
                                                    filterd[0].categories

                                                if (filteredList.isNotEmpty()) {
                                                    updateData(
                                                        transactionsView.customCategoryBar,
                                                        filteredList,
                                                        SimpleDateFormat(DateUtils.SERVER_DATE_FORMAT).parse(
                                                            this[position].originalDate
                                                        ).toString(),
                                                        Constants.COLLAPSE_MODE,
                                                        false
                                                    )
                                                    transactionsView.customCategoryBar.visibility =
                                                        View.VISIBLE
                                                    currentMode = Constants.COLLAPSE_MODE

                                                } else {
                                                    setCategoryWithZero()
                                                }
                                            } else {
                                                setCategoryWithZero()
                                            }

                                        } ?: setCategoryWithZero()

                                        visibleMonth =
                                            this[position].monthYear.toString()
                                    } else if (currentMode != Constants.COLLAPSE_MODE && this[position].monthYear.toString() == visibleMonth
                                    ) {
                                        //only collapse
                                        val filterd: List<MonthData>? =
                                            viewModel.monthData?.value?.filter { monthData ->
                                                monthData.date == this[position].monthYear.toString()
                                            }
                                        filterd?.let {
                                            if (filterd.isNotEmpty()) {
                                                filteredList =
                                                    filterd[0].categories

                                                if (filteredList.isNotEmpty()) {
                                                    updateData(
                                                        transactionsView.customCategoryBar,
                                                        filteredList,
                                                        SimpleDateFormat(DateUtils.SERVER_DATE_FORMAT).parse(
                                                            this[position].originalDate
                                                        ).toString(),
                                                        Constants.COLLAPSE_MODE,
                                                        false
                                                    )
                                                    transactionsView.customCategoryBar.visibility =
                                                        View.VISIBLE
                                                } else {
                                                    setCategoryWithZero()
                                                }
                                            } else {
                                                setCategoryWithZero()
                                            }

                                        } ?: setCategoryWithZero()

                                        currentMode = Constants.COLLAPSE_MODE

                                    }
                                    transactionsView.layoutBalance.tvAvailableBalance.text =
                                        if (position == 0) viewModel.state.availableBalance.getAvailableBalanceWithFormat() else
                                            (transactionsView.lyInclude.multiStateView.rvTransaction.adapter as TransactionsHeaderAdapter).getDataForPosition(
                                                position
                                            ).closingBalance.toString()
                                                .getAvailableBalanceWithFormat()
                                    if (DateUtils.isToday(
                                            this[position].originalDate.toString(),
                                            "yyyy-MM-dd",
                                            TIME_ZONE_Default
                                        )
                                    ) {
                                        transactionsView.layoutBalance.tvBalanceTitle.text =
                                            Translator.getString(
                                                context,
                                                R.string.screen_fragment_yap_home_todays_balance
                                            )
                                    } else {
                                        UIBinder.setDateWithSuperScript(
                                            transactionsView.layoutBalance.tvBalanceTitle,
                                            (transactionsView.lyInclude.multiStateView.rvTransaction.adapter as TransactionsHeaderAdapter).getDataForPosition(
                                                position
                                            ).balanceYear ?: "",
                                            (transactionsView.lyInclude.multiStateView.rvTransaction.adapter as TransactionsHeaderAdapter).getDataForPosition(
                                                position
                                            ).dateForBalance ?: "",
                                            (transactionsView.lyInclude.multiStateView.rvTransaction.adapter as TransactionsHeaderAdapter).getDataForPosition(
                                                position
                                            ).suffixForDay ?: ""
                                        )
                                    }
                                }
                            }
                        }
                    }
                    /*transactionsView.layoutBalance.tvAvailableBalance.text =
                        viewModel.transactionsLiveData.value!![position].closingBalance.toString()
                            .getAvailableBalanceWithFormat()
                    transactionsView.layoutBalance.tvBalanceTitle.text = if (position==0) Translator.getString(
                        context,
                        R.string.screen_fragment_yap_home_todays_balance
                    ) else Translator.getString(
                        context,
                        R.string.screen_fragment_yap_home_balance_on_date,
                        DateUtils.reformatStringDate(
                            viewModel.transactionsLiveData.value!![position].originalDate ?: "",
                            "yyyy-MM-dd",
                            DateUtils.FORMAT_MONTH_DAY
                        )
                    )
*/
                    /*if (!checkScroll) {
                        val graphLayoutManager =
                            transactionsView.rvTransactionsBarChart.layoutManager as LinearLayoutManager
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
                    }*/
                }
            }
        rvTransactionScrollListener?.let {
            transactionsView.lyInclude.multiStateView.rvTransaction.addOnScrollListener(
                it
            )
        }
    }

    fun onToolbarCollapsed() {
        toolbarCollapsed = true
    }

    fun onToolbarExpanded() {
        toolbarCollapsed = false
    }

    private fun updateData(
        customCategoryBar: CustomCategoryBar, progressList: List<Categories>, date: String,
        mode: Int,
        isZero: Boolean
    ) {
        customCategoryBar.setCategoryBar(progressList, mode, date, isZero)
    }

    fun setCategoryWithZero() {
        transactionsView.customCategoryBar.goneWithZeoProgress()
    }

}
