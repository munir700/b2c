package co.yap.modules.dashboard.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.adapters.NotificationAdapter
import co.yap.modules.dashboard.helpers.transaction.TransactionsViewHelper
import co.yap.modules.dashboard.interfaces.IYapHome
import co.yap.modules.dashboard.models.Notification
import co.yap.modules.dashboard.viewmodels.YapHomeViewModel
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.content_fragment_yap_home.*


class YapHomeFragment : YapDashboardChildFragment<IYapHome.ViewModel>(), IYapHome.View,
    DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>,
    DiscreteScrollView.ScrollStateChangeListener<RecyclerView.ViewHolder> {


    private lateinit var mAdapter: NotificationAdapter
    private var notificationsList: ArrayList<Notification> = ArrayList()
    override lateinit var transactionViewHelper: TransactionsViewHelper

    override val viewModel: IYapHome.ViewModel
        get() = ViewModelProviders.of(this).get(YapHomeViewModel::class.java)

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_yap_home


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        transactionViewHelper = TransactionsViewHelper(
            requireContext(),
            view,
            viewModel
        )
        setUpDummyNotificationList()
        setObservers()
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.ivSearch -> {
                }
                R.id.ivMenu -> parentView?.toggleDrawer()

            }
        })
    }

    private fun setUpDummyNotificationList() {
        notificationsList.add(
            Notification(
                "YAP service notice",
                "On May 24th, International money transfers won’t be available from 7 p.m. to 11 p.m.",
                "",
                "",
                "",
                ""
            )
        )
        notificationsList.add(
            Notification(
                "YAP service notice",
                "On May 24th, International money transfers won’t be available from 7 p.m. to 11 p.m.",
                "",
                "",
                "",
                ""
            )
        )
        notificationsList.add(
            Notification(
                "YAP service notice",
                "On May 24th, International money transfers won’t be available from 7 p.m. to 11 p.m.",
                "",
                "",
                "",
                ""
            )
        )

        mAdapter = NotificationAdapter(notificationsList, requireContext())
        rvNotificationList.setSlideOnFling(false)
        rvNotificationList.setOverScrollEnabled(true)
        rvNotificationList.adapter = mAdapter
        rvNotificationList.addOnItemChangedListener(this)
        rvNotificationList.addScrollStateChangeListener(this)
        rvNotificationList.smoothScrollToPosition(0)
        rvNotificationList.setItemTransitionTimeMillis(100)
        rvNotificationList.setItemTransformer(
            ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build()
        )

    }

    override fun onCurrentItemChanged(p0: RecyclerView.ViewHolder?, p1: Int) {
    }

    override fun onScrollEnd(p0: RecyclerView.ViewHolder, p1: Int) {
    }

    override fun onScrollStart(p0: RecyclerView.ViewHolder, p1: Int) {
    }

    override fun onScroll(
        p0: Float,
        p1: Int,
        p2: Int,
        p3: RecyclerView.ViewHolder?,
        p4: RecyclerView.ViewHolder?
    ) {
    }

}