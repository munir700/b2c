package co.yap.modules.dashboard.cards.analytics.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentCardAnalyticsBinding
import co.yap.modules.dashboard.cards.analytics.adaptors.CATEGORY_ANALYTICS
import co.yap.modules.dashboard.cards.analytics.adaptors.CardAnalyticsLandingAdaptor
import co.yap.modules.dashboard.cards.analytics.adaptors.MERCHANT_ANALYTICS
import co.yap.modules.dashboard.cards.analytics.interfaces.ICardAnalytics
import co.yap.modules.dashboard.cards.analytics.main.fragments.CardAnalyticsBaseFragment
import co.yap.modules.dashboard.cards.analytics.viewmodels.CardAnalyticsViewModel
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.widgets.pieview.*
import co.yap.yapcore.helpers.Utils
import com.google.android.material.tabs.TabLayoutMediator

class CardAnalyticsFragment : CardAnalyticsBaseFragment<ICardAnalytics.ViewModel>(),
    ICardAnalytics.View, OnChartValueSelectedListener {

    private var chart: PieChart? = null
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_card_analytics

    override val viewModel: ICardAnalytics.ViewModel
        get() = ViewModelProviders.of(this).get(CardAnalyticsViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        setupAdaptor()
        setupTabs()
        setPieView()
        getBindingView().tvMonthlyAverage.text = Utils.getSppnableStringForAmount(
            requireContext(),
            viewModel.state.monthlyAverageString,
            "AED",
            "5600.00"
        )
        //viewModel.fetchCardAnalytics()

    }

    /*
    * In this function set Pie View.
    * */

    private fun setPieView() {
        chart = getBindingView().chart1
        chart!!.setUsePercentValues(false)
        chart!!.description.isEnabled = false
        chart!!.dragDecelerationFrictionCoef = 0.95f
        chart!!.isDrawHoleEnabled = true
        chart!!.setHoleColor(Color.TRANSPARENT)
        chart!!.setTransparentCircleColor(Color.WHITE)
        chart!!.setTransparentCircleAlpha(200)
        chart!!.holeRadius = 70f
        chart!!.transparentCircleRadius = 70f
        chart!!.setDrawCenterText(true)
        chart!!.rotationAngle = 0f
        chart!!.isRotationEnabled = false
        chart!!.isHighlightPerTapEnabled = true
        chart!!.setOnChartValueSelectedListener(this)
        chart!!.animateY(1400, Easing.EaseInOutQuad)
        chart!!.legend.isEnabled = false // Hide the legend
        chart!!.setEntryLabelColor(Color.WHITE)
        chart!!.setEntryLabelTextSize(0f)

        setData(5, 2f)
    }

    /*
    * In this set Data in Pie View.
    * */

    private fun setData(count: Int, range: Float) {
        val entries: ArrayList<PieEntry> = ArrayList<PieEntry>()
        for (i in 0 until count) {
            entries.add(
                PieEntry(
                    (Math.random() * range + range / 5).toFloat()
                )
            )
        }
        /*TODO: Pie Chart Set Slice Space*/
        val dataSet = PieDataSet(entries, "")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 0f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 20f
        dataSet.setDrawValues(false)
        // add a lot of colors
        val colors = ArrayList<Int>()
        for (c in ColorTemplate.VORDIPLOM_COLORS) colors.add(c)
        for (c in ColorTemplate.JOYFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.COLORFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.LIBERTY_COLORS) colors.add(c)
        for (c in ColorTemplate.PASTEL_COLORS) colors.add(c)
        colors.add(ColorTemplate.getHoloBlue())
        dataSet.colors = colors
        val data = PieData(dataSet)
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        chart!!.data = data

        chart!!.highlightValue(0f, 0, true)
        chart!!.invalidate()
    }


    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEventObserver)
        viewModel.parentViewModel.selectedItemPosition.observe(this, Observer {
            when (getBindingView().tabLayout.selectedTabPosition) {
                CATEGORY_ANALYTICS -> {
                    showPieView(it)
                    viewModel.fetchCardCategoryAnalytics()


                }
                MERCHANT_ANALYTICS -> {
                    showPieView(it)
                    viewModel.fetchCardMerchantAnalytics()

                }
            }
        })
    }

    /*
    * In this function show PieView.
    * */

    private fun showPieView(indexValue: Int) {
        when (indexValue) {
            0 -> {
                chart!!.highlightValue(0f, 0, true)
            }
            1 -> {
                chart!!.highlightValue(1f, 0, true)
            }
            2 -> {
                chart!!.highlightValue(2f, 0, true)
            }
            3 -> {
                chart!!.highlightValue(3f, 0, true)
            }
            4 -> {
                chart!!.highlightValue(4f, 0, true)
            }
        }
    }

    private val clickEventObserver = Observer<Int> {
        when (it) {
            R.id.ivPrevious -> {
                showToast("Previous")
            }
            R.id.ivNext -> {
                showToast("Next")
            }
        }
    }

    private fun setupAdaptor() {
        val adaptor = CardAnalyticsLandingAdaptor(this)
        getBindingView().viewPager.adapter = adaptor
    }

    private fun setupTabs() {
        TabLayoutMediator(getBindingView().tabLayout, getBindingView().viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = getTabTitle(position)
            }).attach()

        getBindingView().viewPager.isUserInputEnabled = false
        getBindingView().viewPager.offscreenPageLimit = 1
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            CATEGORY_ANALYTICS -> Translator.getString(
                requireContext(),
                Strings.screen_card_analytics_display_tab_title_category
            )
            MERCHANT_ANALYTICS -> Translator.getString(
                requireContext(),
                Strings.screen_card_analytics_display_tab_title_merchant
            )
            else -> null
        }
    }

    private fun getBindingView(): FragmentCardAnalyticsBinding {
        return (viewDataBinding as FragmentCardAnalyticsBinding)
    }

    override fun onNothingSelected() {
        //  Toast.makeText(context!!.applicationContext, "onNothingSelected", Toast.LENGTH_LONG).show()

    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        /*TODO:Pie Chart View Click Listener*/
        val select_index_value = h!!.x.toShort()

    }
}