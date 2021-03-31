package co.yap.billpayments.paybills

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.base.PayBillBaseFragment
import co.yap.billpayments.databinding.FragmentPayBillsBinding
import co.yap.yapcore.interfaces.OnItemClickListener
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer

class PayBillsFragment : PayBillBaseFragment<IPayBills.ViewModel>(),
    IPayBills.View {

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
                       if(viewModel.notificationAdapter.getDataList().isNullOrEmpty()) View.GONE else View.VISIBLE
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
            R.id.lAddBill -> {
                navigate(R.id.action_payBillsFragment_to_addBillFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }
}