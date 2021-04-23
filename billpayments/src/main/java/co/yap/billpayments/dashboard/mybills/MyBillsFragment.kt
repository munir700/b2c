package co.yap.billpayments.dashboard.mybills

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.base.BillDashboardBaseFragment
import co.yap.billpayments.billdetail.BillDetailActivity
import co.yap.translation.Strings
import co.yap.widgets.bottomsheet.BottomSheetConfiguration
import co.yap.widgets.bottomsheet.CoreBottomSheet
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.interfaces.OnItemClickListener

class MyBillsFragment : BillDashboardBaseFragment<IMyBills.ViewModel>(),
    IMyBills.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_my_bills
    override val viewModel: MyBillsViewModel
        get() = ViewModelProviders.of(this).get(MyBillsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel.parentViewModel?.bills?.value.isNullOrEmpty()) {
            viewModel.parentViewModel?.getViewBills()
        } else {
            viewModel.setBillList()
        }
        setObservers()
    }

    override fun setObservers() {
        viewModel.parentViewModel?.billsAdapterList?.observe(this, Observer {
            viewModel.setBillList()
        })
        viewModel.parentViewModel?.onToolbarClickEvent?.observe(this, toolbarClickObserver)
        viewModel.adapter.setItemListener(onItemClickListener)
    }

    val onItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            openBillDetailActivity(pos)
        }
    }

    fun openBillDetailActivity(pos: Int) {
        launchActivity<BillDetailActivity>(requestCode = RequestCodes.REQUEST_BILL_DETAIL) {
            putExtra(
                ExtraKeys.SELECTED_BILL.name,
                viewModel.parentViewModel?.bills?.value?.get(pos)
            )
            putExtra(
                ExtraKeys.SELECTED_BILL_POSITION.name,
                pos
            )
        }
        requireActivity().overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        )
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
        viewModel.parentViewModel?.billsAdapterList?.removeObservers(this)
        viewModel.parentViewModel?.toolBarClickEvent?.removeObservers(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }
}
