package co.yap.billpayments.dashboard.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.addbiller.main.AddBillActivity
import co.yap.billpayments.base.BillDashboardBaseFragment
import co.yap.billpayments.billdetail.BillDetailActivity
import co.yap.billpayments.databinding.FragmentBillDashboardBinding
import co.yap.billpayments.payall.main.PayAllMainActivity
import co.yap.billpayments.paybill.main.PayBillMainActivity
import co.yap.networking.customers.responsedtos.billpayment.BillProviderModel
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.widgets.MultiStateView
import co.yap.widgets.State
import co.yap.widgets.Status
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.enums.FeatureSet
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.extentions.ExtraType
import co.yap.yapcore.helpers.extentions.getValue
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.showBlockedFeatureAlert
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.FeatureProvisioning
import co.yap.yapcore.managers.SessionManager
import com.google.gson.Gson
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer

class BillDashboardFragment :
    BillDashboardBaseFragment<FragmentBillDashboardBinding, IBillDashboard.ViewModel>(),
    IBillDashboard.View {
    private var onTouchListener: RecyclerTouchListener? = null
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_bill_dashboard
    override var isFromSwipePayBill: Boolean = false
    override val viewModel: BillDashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.parentViewModel?.getViewBills()
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNotificationRecyclerView()
        initSwipeListener()
    }

    private fun initNotificationRecyclerView() {
        viewModel.notificationAdapter.setItemListener(notificationClickEvent)
        viewDataBinding.lbillPaymentDue.rvNotificationList.apply {
            setSlideOnFling(false)
            setOverScrollEnabled(true)
            smoothScrollToPosition(0)
            setItemTransitionTimeMillis(150)
            setItemTransformer(
                ScaleTransformer.Builder()
                    .setMaxScale(1.05f)
                    .setMinScale(1f)
                    .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                    .build()
            )
        }
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
        viewModel.parentViewModel?.billsResponse?.observe(this, Observer { billsList ->
            viewModel.handleBillsResponse(billsList)
        })

        viewModel.state.stateLiveData?.observe(this, Observer {
            handleState(it)
        })
        viewModel.adapter.setItemListener(categoryItemListener)
    }

    private val notificationClickEvent = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            when (view.id) {
                R.id.ivCross -> {
                    viewModel.notificationAdapter.removeItemAt(pos)
                    viewDataBinding.lbillPaymentDue.llNotification.visibility =
                        if (viewModel.notificationAdapter.getDataList()
                                .isNullOrEmpty()
                        ) View.GONE else View.VISIBLE
                }
                R.id.cvNotification -> {
                    openBillDetailActivity(pos, data as ViewBillModel)
                }
            }
        }
    }

    private val categoryItemListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            onCategorySelection(data as BillProviderModel)
        }
    }

    val clickEvent = Observer<Int> {
        when (it) {
            R.id.lMyBills -> navigate(R.id.action_billsDashboardFragment_to_myBillsFragment)
            R.id.lAnalytics -> navigate(R.id.action_billsDashboardFragment_to_billPaymentAnalyticsFragment)
            R.id.lAddBill -> navigate(
                destinationId = R.id.action_billsDashboardFragment_to_addBillFragment,
                screenType = FeatureSet.ADD_BILL_PAYMENT
            )
            R.id.btnPayNow -> {
                if (FeatureProvisioning.getFeatureProvisioning(FeatureSet.PAY_BILL_PAYMENT)) {
                    showBlockedFeatureAlert(requireActivity(), FeatureSet.PAY_BILL_PAYMENT)
                } else {
                    viewModel.clickEvent.getPayload()?.let { payload ->
                        isFromSwipePayBill = true
                        startPayBillFlow(payload.itemData as ViewBillModel)
                    }
                    viewModel.clickEvent.setPayload(null)
                }
            }
            R.id.foregroundContainer -> {
                viewModel.clickEvent.getPayload()?.let { payload ->
                    openBillDetailActivity(payload.position, payload.itemData as ViewBillModel)
                }
                viewModel.clickEvent.setPayload(null)
            }
            R.id.btnPayAll -> {
                startPayAllActivity()
            }
        }
    }

    private fun startPayBillFlow(viewBillModel: ViewBillModel, pos: Int = 0) {
        launchActivity<PayBillMainActivity>(requestCode = RequestCodes.REQUEST_PAY_BILL) {
            putExtra(ExtraKeys.SELECTED_BILL.name, viewBillModel)
            putExtra(ExtraKeys.SELECTED_POSITION.name, pos)
        }
    }

    private fun onCategorySelection(billCategory: BillProviderModel?) {
        if (!billCategory?.categoryType.equals("CREDIT_CARD")) {
            launchActivity<AddBillActivity>(
                requestCode = RequestCodes.REQUEST_ADD_BILL,
                type = FeatureSet.ADD_BILL_PAYMENT
            ) {
                putExtra(
                    ExtraKeys.BILL_PROVIDER.name,
                    billCategory
                )
            }
        }
    }

    private fun openBillDetailActivity(pos: Int, viewBillModel: ViewBillModel) {
        launchActivity<BillDetailActivity>(requestCode = RequestCodes.REQUEST_BILL_DETAIL) {
            putExtra(
                ExtraKeys.SELECTED_BILL.name,
                viewBillModel
            )
            putExtra(
                ExtraKeys.SELECTED_POSITION.name,
                pos
            )
        }
        requireActivity().overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        )
    }


    private fun initSwipeListener() {
        activity?.let { activity ->
            onTouchListener =
                RecyclerTouchListener(
                    requireActivity(),
                    viewDataBinding.lbillPaymentDue.rvAllDueBills
                )
                    .setClickable(
                        object : RecyclerTouchListener.OnRowClickListener {
                            override fun onRowClicked(position: Int) {
                                viewModel.clickEvent.setPayload(
                                    SingleClickEvent.AdaptorPayLoadHolder(
                                        viewDataBinding.lbillPaymentDue.rvAllDueBills.findViewById(R.id.foregroundContainer),
                                        viewModel.dueBillsAdapter.getDataForPosition(position),
                                        position))
                                viewModel.clickEvent.setValue(R.id.foregroundContainer)
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
            viewDataBinding.lbillPaymentDue.rvAllDueBills.addOnItemTouchListener(onTouchListener!!)
        }
    }

    private fun handleState(state: State?) {
        viewDataBinding.multiStateView.viewState = when (state?.status) {
            Status.LOADING -> MultiStateView.ViewState.LOADING
            Status.EMPTY -> MultiStateView.ViewState.EMPTY
            Status.SUCCESS -> MultiStateView.ViewState.CONTENT
            Status.ERROR -> MultiStateView.ViewState.ERROR
            else -> throw IllegalStateException("State is not handled " + state?.status)
        }
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
    }

    override fun onResume() {
        super.onResume()
        SessionManager.updateCardBalance {}
        onTouchListener?.let {
            viewDataBinding.lbillPaymentDue.rvAllDueBills.addOnItemTouchListener(
                it
            )
        }
    }

    override fun onPause() {
        onTouchListener?.let {
            viewDataBinding.lbillPaymentDue.rvAllDueBills.removeOnItemTouchListener(
                it
            )
        }
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RequestCodes.REQUEST_ADD_BILL -> {
                    handleAddBillResult(data)
                }
                RequestCodes.REQUEST_PAY_BILL, RequestCodes.REQUEST_BILL_DETAIL -> {
                    viewModel.parentViewModel?.getViewBills()
                }
            }
        } else {
            if (requestCode == RequestCodes.REQUEST_PAY_BILL && !isFromSwipePayBill)
                viewModel.parentViewModel?.getViewBills()
        }
    }


    private fun handleAddBillResult(data: Intent?) {
        val isSkipPayFlow = data?.getValue(
            ExtraKeys.IS_SKIP_PAY_BILL.name,
            ExtraType.BOOLEAN.name
        ) as Boolean

        val viewBillModel = data.getValue(
            ExtraKeys.SELECTED_BILL.name,
            ExtraType.PARCEABLE.name
        ) as? ViewBillModel

        if (isSkipPayFlow) {
            viewModel.parentViewModel?.getViewBills()
        } else {
            viewBillModel?.let {
                startPayBillFlow(viewBillModel)
            } ?: viewModel.parentViewModel?.getViewBills()
        }
    }

    private fun startPayAllActivity() {
        launchActivity<PayAllMainActivity>(
            requestCode = RequestCodes.REQUEST_PAY_BILL_ALL,
            type = FeatureSet.PAY_BILL_PAYMENT
        ) {
            putExtra(
                ExtraKeys.ALL_BILLS.name,
                Gson().toJson(
                    viewModel.dueBillsAdapter.getDataList()
                        .filter { it.isBillerNotUnavailable().not() })
            )
        }
        requireActivity().overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        )
    }
}