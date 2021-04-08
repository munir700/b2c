package co.yap.billpayments.mybills

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.base.PayBillBaseFragment
import co.yap.translation.Strings
import co.yap.widgets.bottomsheet.roundtickselectionbottomsheet.RoundTickSelectionBottomSheet
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

        }
    }

    override fun openSortBottomSheet() {
        this.childFragmentManager.let {
            val roundTickSelectionBottomSheet = RoundTickSelectionBottomSheet(
                mListener = viewModel.onBottomSheetItemClickListener,
                bottomSheetItems = viewModel.getFiltersList(),
                headingLabel = getString(Strings.screen_my_bills_text_title_bottom_sheet),
                viewType = Constants.VIEW_WITHOUT_FLAG
            )
            roundTickSelectionBottomSheet.show(it, "")
        }
    }

    val toolbarClickObserver = Observer<Int> {
        when (it) {
            R.id.ivSortIcon -> openSortBottomSheet()
            R.id.ivRightIcon -> navigate(R.id.action_myBillsFragment_to_billCategoryFragment)
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
