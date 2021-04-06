package co.yap.billpayments.mybills

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.base.PayBillBaseFragment
import co.yap.networking.customers.responsedtos.billpayment.BillModel
import co.yap.translation.Strings
import co.yap.widgets.bottomsheet.CoreBottomSheet
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.BillStatus
import co.yap.yapcore.helpers.DateUtils
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
            viewModel.myBills.value?.toMutableList()?.let { it1 -> viewModel.adapter.setList(it1) }
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
                }
            }
        }
    }

    fun openSortBottomSheet() {
        this.childFragmentManager.let {
            val coreBottomSheet = CoreBottomSheet(
                mListener = onBottomSheetItemClickListener,
                bottomSheetItems = viewModel?.getFiltersList(),
                headingLabel = getString(Strings.screen_my_bills_text_title_bottom_sheet),
                viewType = Constants.VIEW_WITHOUT_FLAG
            )
            coreBottomSheet.show(it, "")
        }
    }

    val onBottomSheetItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            viewModel.state.loading = true
            when (pos) {
                0 -> {
                    viewModel.myBills.value?.sortByDescending { billModel ->
                        billModel.billStatus?.equals(
                            BillStatus.OVERDUE.title
                        )
                    }
                }
                1 -> {
                    viewModel.myBills.value?.sortBy { billModel ->
                        billModel.billDueDate?.let {
                            DateUtils.stringToDate(
                                it, DateUtils.FORMAT_DATE_MON_YEAR
                            )
                        }
                    }
                }
                2 -> {
                    viewModel.myBills.value?.sortBy { billModel -> billModel.name }
                }
                3 -> {
                    viewModel.myBills.value?.sortByDescending { billModel -> billModel.name }
                }
            }
            viewModel.myBills.value?.toMutableList()?.let { viewModel.adapter.setList(it) }
            viewModel.state.loading = false
        }
    }

    val toolbarClickObserver = Observer<Int> {
        when (it) {
            R.id.ivRightFirstIcon -> openSortBottomSheet()
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
