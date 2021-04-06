package co.yap.billpayments.mybills

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.base.PayBillBaseFragment
import co.yap.networking.customers.responsedtos.billpayment.BillModel
import co.yap.yapcore.enums.BillStatus
import co.yap.yapcore.interfaces.OnItemClickListener

class MyBillsFragment : PayBillBaseFragment<IMyBills.ViewModel>(),
    IMyBills.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_my_bills
    override val viewModel: MyBillsViewModel
        get() = ViewModelProviders.of(this).get(MyBillsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getMyBillsAPI()
        setObservers()
    }

    override fun setObservers() {
        viewModel.myBills.observe(this, Observer {
            viewModel.getScreenTitle()
            viewModel.adapter.setList(viewModel.myBills.value?.toMutableList() as MutableList<BillModel>)
        })
        viewModel.parentViewModel?.onToolbarClickEvent?.observe(this, toolbarClickObserver)
        viewModel.adapter.setItemListener(onItemClickListener)
        viewModel.clickEvent.observe(this, onViewClickObserver)
    }

    val onViewClickObserver = Observer<Int> {
        when (it) {
            R.id.btnPay -> {
            }
        }
    }

    val onItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            val bill = data as BillModel
            if (!bill.billStatus.equals(BillStatus.PAID.title)) {
                bill.isSelected = !bill.isSelected
                if (bill.isSelected) {
                    viewModel.onItemSelected(pos, bill)
                } else {
                    viewModel.onItemUnselected(pos, bill)
                    activity?.onBackPressed()
                }
            }
        }
    }

    val toolbarClickObserver = Observer<Int> {
        when (it) {
            R.id.ivRightFirstIcon -> {

            }
            R.id.ivRightSecondIcon -> navigate(R.id.action_myBillsFragment_to_billCategoryFragment)
        }
    }

    override fun removeObservers() {
        viewModel.myBills.removeObservers(this)
        viewModel.parentViewModel?.toolBarClickEvent?.removeObservers(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }
}
