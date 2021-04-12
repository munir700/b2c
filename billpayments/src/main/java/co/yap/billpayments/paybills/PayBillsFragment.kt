package co.yap.billpayments.paybills

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.base.PayBillBaseFragment
import co.yap.billpayments.databinding.FragmentPayBillsBinding
import co.yap.billpayments.paybills.adapter.DueBill
import co.yap.networking.customers.responsedtos.billpayment.BillProviderModel
import co.yap.widgets.MultiStateView
import co.yap.widgets.State
import co.yap.widgets.Status
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.interfaces.OnItemClickListener
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.fragment_pay_bills.*
import kotlinx.android.synthetic.main.layout_item_bill_due.*

class PayBillsFragment : PayBillBaseFragment<IPayBills.ViewModel>(),
    IPayBills.View {
    private var onTouchListener: RecyclerTouchListener? = null
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_pay_bills

    override val viewModel: PayBillsViewModel
        get() = ViewModelProviders.of(this).get(PayBillsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNotificationRecyclerView()
        initSwipeListener()
    }

    private fun initNotificationRecyclerView() {
        viewModel.notificationAdapter.setItemListener(notificationClickEvent)
        getBindings().lbillPaymentDue.rvNotificationList.setSlideOnFling(false)
        getBindings().lbillPaymentDue.rvNotificationList.setOverScrollEnabled(true)
        getBindings().lbillPaymentDue.rvNotificationList.smoothScrollToPosition(0)
        getBindings().lbillPaymentDue.rvNotificationList.setItemTransitionTimeMillis(150)
        getBindings().lbillPaymentDue.rvNotificationList.setItemTransformer(
            ScaleTransformer.Builder()
                .setMaxScale(1.05f)
                .setMinScale(1f)
                .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                .build()
        )
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
        viewModel.state.stateLiveData?.observe(this, Observer {
            handleState(it)
        })
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
    }

    override fun getBindings(): FragmentPayBillsBinding {
        return viewDataBinding as FragmentPayBillsBinding
    }

    private val notificationClickEvent = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            when (view.id) {
                R.id.ivCross -> {
                    viewModel.notificationAdapter.removeItemAt(pos)
                    getBindings().lbillPaymentDue.llNotification.visibility =
                        if (viewModel.notificationAdapter.getDataList()
                                .isNullOrEmpty()
                        ) View.GONE else View.VISIBLE
                }
            }
        }
    }

    val clickEvent = Observer<Int> {
        when (it) {
            R.id.lMyBills -> {
                navigate(R.id.action_payBillsFragment_to_myBillsFragment)
            }
            R.id.lAnalytics -> {
            }
            R.id.lAddBill -> navigate(R.id.action_payBillsFragment_to_addBillFragment)
            R.id.includeCreditCard -> onCategorySelection(viewModel.billcategories.get()?.get(0))
            R.id.includeTelecom -> onCategorySelection(viewModel.billcategories.get()?.get(1))
            R.id.includeUtilities -> onCategorySelection(viewModel.billcategories.get()?.get(2))
            R.id.includeRTA -> onCategorySelection(viewModel.billcategories.get()?.get(3))
            R.id.includeDubaiPolice -> onCategorySelection(viewModel.billcategories.get()?.get(4))
            R.id.btnPayNow -> {
                viewModel.clickEvent.getPayload()?.let { payload ->
                    startPayBillFlow(payload.itemData as DueBill)
                }
                viewModel.clickEvent.setPayload(null)
            }
        }
    }

    private fun startPayBillFlow(dueBill: DueBill) {

    }

    private fun onCategorySelection(billCategory: BillProviderModel?) {
        viewModel.parentViewModel?.selectedBillProvider = billCategory
        navigate(R.id.action_payBillsFragment_to_billersFragment)
    }

    private fun initSwipeListener() {
        activity?.let { activity ->
            onTouchListener =
                RecyclerTouchListener(
                    requireActivity(),
                    getBindings().lbillPaymentDue.rvAllDueBills
                )
                    .setClickable(
                        object : RecyclerTouchListener.OnRowClickListener {
                            override fun onRowClicked(position: Int) {
                                viewModel.clickEvent.setPayload(
                                    SingleClickEvent.AdaptorPayLoadHolder(
                                        foregroundContainer,
                                        viewModel.dueBillsAdapter.getDataForPosition(position),
                                        position
                                    )
                                )
                                viewModel.clickEvent.setValue(foregroundContainer.id)
                            }

                            override fun onIndependentViewClicked(
                                independentViewID: Int,
                                position: Int
                            ) {
                            }
                        }).setSwipeOptionViews(R.id.btnPayNow)
                    .setSwipeable(
                        R.id.foregroundContainer, R.id.swipe
                    )
                    { viewID, position ->
                        viewModel.clickEvent.setPayload(
                            SingleClickEvent.AdaptorPayLoadHolder(
                                activity.findViewById(viewID),
                                viewModel.dueBillsAdapter.getDataForPosition(position),
                                position
                            )
                        )
                        viewModel.clickEvent.setValue(viewID)
                    }
            getBindings().lbillPaymentDue.rvAllDueBills.addOnItemTouchListener(onTouchListener!!)
        }
    }

    private fun handleState(state: State?) {
        when (state?.status) {
            Status.LOADING -> {
                multiStateView.viewState = MultiStateView.ViewState.LOADING
            }
            Status.EMPTY -> {
                multiStateView.viewState = MultiStateView.ViewState.EMPTY
            }
            Status.SUCCESS -> {
                multiStateView.viewState = MultiStateView.ViewState.CONTENT
            }
            Status.ERROR -> {
                multiStateView.viewState = MultiStateView.ViewState.ERROR
            }
            else -> {
                throw IllegalStateException("State is not handled " + state?.status)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        onTouchListener?.let { getBindings().lbillPaymentDue.rvAllDueBills.addOnItemTouchListener(it) }
    }

    override fun onPause() {
        onTouchListener?.let {
            getBindings().lbillPaymentDue.rvAllDueBills.removeOnItemTouchListener(
                it
            )
        }
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }
}