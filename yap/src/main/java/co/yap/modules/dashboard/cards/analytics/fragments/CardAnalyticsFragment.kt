package co.yap.modules.dashboard.cards.analytics.fragments

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
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
        viewModel.fetchCardAnalytics()

    }

    private fun setPieView() {
        chart = getBindingView().chart1
        chart!!.setUsePercentValues(false)
        chart!!.description.isEnabled = false
        chart!!.dragDecelerationFrictionCoef = 0.95f
        //chart!!.setCenterTextTypeface(tfLight)
        // chart!!.centerText = generateCenterSpannableText()
        chart!!.isDrawHoleEnabled = true
        chart!!.setHoleColor(Color.TRANSPARENT)
        chart!!.setTransparentCircleColor(Color.WHITE)
        chart!!.setTransparentCircleAlpha(200)
        chart!!.holeRadius = 70f
        chart!!.transparentCircleRadius = 70f
        chart!!.setDrawCenterText(true)
        chart!!.rotationAngle = 0f
        // enable rotation of the chart by touch
        // enable rotation of the chart by touch
        chart!!.isRotationEnabled = false
        chart!!.isHighlightPerTapEnabled = true
        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);
        // add a selection listener
        // chart.setUnit(" €");
// chart.setDrawUnitsInChart(true);
// add a selection listener
        chart!!.setOnChartValueSelectedListener(this)
//        seekBarX.setProgress(4)
//        seekBarY.setProgress(10)
        chart!!.animateY(1400, Easing.EaseInOutQuad)
        // chart.spin(2000, 0, 360);
//        Legend l = chart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(false);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);
//        l.setEnabled(false);
        // chart.spin(2000, 0, 360);
//        Legend l = chart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(false);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);
//        l.setEnabled(false);
        chart!!.legend.isEnabled = false // Hide the legend

        // entry label styling
        // entry label styling
        chart!!.setEntryLabelColor(Color.WHITE)
        //  chart!!.setEntryLabelTypeface(tfRegular)
        chart!!.setEntryLabelTextSize(0f)

        setData(5, 2f)
    }

    private fun setData(count: Int, range: Float) {
        val entries: ArrayList<PieEntry> = ArrayList<PieEntry>()
        for (i in 0 until count) {
            entries.add(
                PieEntry(
                    (Math.random() * range + range / 5).toFloat(),
                    parties[i % parties.size],
                    resources.getDrawable(R.drawable.arrow)
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
        //dataSet.setSelectionShift(0f);
        val data = PieData(dataSet)
        //  data.setValueFormatter(PercentFormatter(chart))
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        // data.setValueTypeface(tfLight)
        chart!!.data = data
        chart!!.highlightValue(0f, 0, true)
        chart!!.invalidate()


    }

    /*TODO: Set Icon*/
    private fun generateCenterSpannableText(): SpannableString? {
        val s =
            SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda")
        val d =
            ContextCompat.getDrawable(context!!.applicationContext, R.drawable.ic_shopping)
        d!!.setBounds(0, 0, d.intrinsicWidth, d.intrinsicHeight)
        val span =
            ImageSpan(d, ImageSpan.ALIGN_BASELINE)
        s.setSpan(span, 0, 3, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        s.setSpan(RelativeSizeSpan(1.7f), 0, 14, 0)
        s.setSpan(
            StyleSpan(Typeface.NORMAL),
            14,
            s.length - 15,
            0
        )
        s.setSpan(
            ForegroundColorSpan(Color.GRAY),
            14,
            s.length - 15,
            0
        )
        s.setSpan(RelativeSizeSpan(.8f), 14, s.length - 15, 0)
        s.setSpan(
            StyleSpan(Typeface.ITALIC),
            s.length - 14,
            s.length,
            0
        )
        s.setSpan(
            ForegroundColorSpan(ColorTemplate.getHoloBlue()),
            s.length - 14,
            s.length,
            0
        )
        return s
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEventObserver)
        viewModel.parentViewModel.selectedItemPosition.observe(this, Observer {
            when (getBindingView().tabLayout.selectedTabPosition) {
                CATEGORY_ANALYTICS -> {
                    showToast(
                        "Position $it and data is ${viewModel.parentViewModel.categoryAnalyticsItemLiveData.value?.get(
                            it
                        )?.title}"
                    )
                }
                MERCHANT_ANALYTICS -> {
                    showToast(
                        "Position $it and data is ${viewModel.parentViewModel.merchantAnalyticsItemLiveData.value?.get(
                            it
                        )?.title}"
                    )
                }
            }
        })
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


    val parties = arrayOf(
        "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
        "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
        "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
        "Party Y", "Party Z"
    )

    override fun onNothingSelected() {

        Toast.makeText(context!!.applicationContext, "onNothingSelected", Toast.LENGTH_LONG).show()
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        /*TODO:Pie Cart View Click Listener*/
        val select_index_value = h!!.x.toShort()
        Log.d("MIRZA", "TEST  $select_index_value")

    }
}