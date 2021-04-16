package co.yap.billpayments.dashboard.mybills

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.base.PayBillBaseFragment
import co.yap.networking.customers.responsedtos.billpayment.BillModel
import co.yap.translation.Strings
import co.yap.widgets.bottomsheet.BottomSheetConfiguration
import co.yap.widgets.bottomsheet.CoreBottomSheet
import co.yap.yapcore.constants.Constants
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
            viewModel.setScreenTitle()
            viewModel.myBills.value?.let { it1 -> viewModel.adapter.setList(it1) }
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
            viewModel.parentViewModel?.selectedBill = data as BillModel
            navigate(R.id.action_myBillsFragment_to_billAccountDetailFragment)
        }
    }

    override fun openSortBottomSheet() {
        this.childFragmentManager.let {
            val coreBottomSheet = CoreBottomSheet(
                mListener = viewModel.onBottomSheetItemClickListener,
                bottomSheetItems = viewModel.getFiltersList(),
                viewType = Constants.VIEW_ITEM_WITHOUT_SEPARATOR,
                configuration = BottomSheetConfiguration(
                    heading = getString(Strings.screen_my_bills_text_title_bottom_sheet),
                    showSearch = false,
                    showHeaderSeparator = true
                )
            )
            coreBottomSheet.show(it, "")
        }
    }

    private val toolbarClickObserver = Observer<Int> {
        when (it) {
            R.id.ivSortIcon -> openSortBottomSheet()
            R.id.ivRightIcon -> navigate(R.id.action_myBillsFragment_to_billCategoryFragment)
        }
    }

    override fun removeObservers() {
        viewModel.myBills.removeObservers(this)
        viewModel.clickEvent.removeObservers(this)
        viewModel.parentViewModel?.toolBarClickEvent?.removeObservers(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }
}
