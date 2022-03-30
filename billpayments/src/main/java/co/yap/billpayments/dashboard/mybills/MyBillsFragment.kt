package co.yap.billpayments.dashboard.mybills

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.base.BillDashboardBaseFragment
import co.yap.billpayments.billdetail.BillDetailActivity
import co.yap.translation.Strings
import co.yap.widgets.bottomsheet.BottomSheetConfiguration
import co.yap.widgets.bottomsheet.CoreBottomSheet
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.enums.FeatureSet
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.extentions.ExtraType
import co.yap.yapcore.helpers.extentions.getValue
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.interfaces.OnItemClickListener

class MyBillsFragment : BillDashboardBaseFragment<IMyBills.ViewModel>(),
    IMyBills.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_my_bills
    override val viewModel: MyBillsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel.parentViewModel?.billsResponse?.value.isNullOrEmpty()) {
            viewModel.parentViewModel?.getViewBills()
        } else {
            viewModel.setBillList()
        }
        setObservers()
    }

    override fun setObservers() {
        viewModel.parentViewModel?.billsResponse?.observe(this, Observer {
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
                viewModel.adapter.getDataList()[pos]
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RequestCodes.REQUEST_BILL_DETAIL -> {
                    handleBillDetailsResult(data)
                }
            }
        }
    }

    private fun handleBillDetailsResult(data: Intent?) {
        val isBillDeleted =
            data?.getValue(ExtraKeys.IS_DELETED.name, ExtraType.BOOLEAN.name) as? Boolean
        val isBillUpdated =
            data?.getValue(ExtraKeys.IS_UPDATED.name, ExtraType.BOOLEAN.name) as? Boolean
        if (isBillDeleted == true || isBillUpdated == true)
            viewModel.parentViewModel?.getViewBills()
        else {
            navigateBack()
            viewModel.parentViewModel?.getViewBills()
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
            R.id.ivRightIcon -> navigate(destinationId = R.id.action_myBillsFragment_to_billCategoryFragment,screenType = FeatureSet.ADD_BILL_PAYMENT)
        }
    }

    override fun removeObservers() {
        viewModel.parentViewModel?.billsResponse?.removeObservers(this)
        viewModel.parentViewModel?.toolBarClickEvent?.removeObservers(this)
        viewModel?.toggleSortIconVisibility(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }
}
