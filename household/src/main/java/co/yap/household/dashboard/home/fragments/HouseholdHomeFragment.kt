package co.yap.household.dashboard.home.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.household.R
import co.yap.household.dashboard.home.interfaces.IHouseholdHome
import co.yap.household.dashboard.home.viewmodels.HouseholdHomeViewModel
import co.yap.household.dashboard.main.fragments.HouseholdDashboardBaseFragment
import co.yap.household.databinding.FragmentHouseholdHomeBinding
import co.yap.modules.yapnotification.adaptors.YapNotificationAdapter
import co.yap.modules.yapnotification.interfaces.NotificationItemClickListener
import co.yap.modules.yapnotification.models.Notification
import co.yap.networking.transactions.responsedtos.transaction.Content
import co.yap.widgets.MultiStateView
import co.yap.yapcore.BR
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.transactions.interfaces.LoadMoreListener

class HouseholdHomeFragment : HouseholdDashboardBaseFragment<IHouseholdHome.ViewModel>(),
    IHouseholdHome.View, NotificationItemClickListener {

    private var mAdapter: YapNotificationAdapter? = null
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_household_home
    override val viewModel: IHouseholdHome.ViewModel
        get() = ViewModelProviders.of(this).get(HouseholdHomeViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewBinding().transactionRecyclerView.setItemClickListener(adaptorClickListener)
        getViewBinding().transactionRecyclerView.setLoadMoreListener(loadMoreListener)
    }

    override fun setObservers() {
        viewModel.viewState.observe(this, viewStateObserver)
        viewModel.clickEvent.observe(this, clickObserver)
        viewModel.notificationList.observe(this, Observer {

        })
    }

    private val clickObserver = Observer<Int> {
        when (it) {
            R.id.ivMenu -> {
                parentView?.toggleDrawer()
            }
            R.id.ivSearch -> {
            }
        }
    }

    private val viewStateObserver = Observer<Int> {
        when (it) {
            Constants.EVENT_LOADING -> {
                getViewBinding().multiStateView.viewState = MultiStateView.ViewState.LOADING
            }
            Constants.EVENT_EMPTY -> {
                getViewBinding().multiStateView.viewState = MultiStateView.ViewState.EMPTY
            }
            Constants.EVENT_CONTENT -> {
                getViewBinding().multiStateView.viewState = MultiStateView.ViewState.CONTENT
            }
        }
    }

    private val adaptorClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            showToast("data is " + (data as Content).title)
        }
    }

    private val loadMoreListener = object : LoadMoreListener {
        override fun onLoadMore() {
            if (viewModel.isLast.value == false) {
                viewModel.homeTransactionRequest.number =
                    viewModel.homeTransactionRequest.number.inc()
                viewModel.loadMore()
            } //in else case remove observer from recycleview
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        viewModel.viewState.removeObserver(viewStateObserver)
        viewModel.clickEvent.removeObserver(clickObserver)
    }

    private fun getViewBinding(): FragmentHouseholdHomeBinding {
        return (viewDataBinding as FragmentHouseholdHomeBinding)
    }

    override fun onClick(notification: Notification) {

    }

}