package co.yap.billpayments.mybills

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.base.PayBillBaseFragment
import co.yap.billpayments.mybills.adapter.BillModel
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.enums.BillStatus
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.SessionManager

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
            viewModel.state.screenTitle.set(
                Translator.getString(
                    requireContext(),
                    Strings.screen_my_bills_text_title_you_have_n_bills_registered,
                    viewModel.myBills.value?.size.toString()
                )
            )
            viewModel.adapter.setList(viewModel.myBills.value?.toMutableList() as MutableList<BillModel>)
        })
        viewModel.parentViewModel?.toolBarClickEvent?.observe(this, toolbarClickObserver)
        viewModel.adapter.setItemListener(onItemClickListener)
    }

    val onItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            val bill = data as BillModel

            if (!bill.billStatus.equals(BillStatus.PAID.title)) {
                bill.isSelected = !bill.isSelected
                if (bill.isSelected) {
                    onItemSelected(pos, bill)
                } else {
                    onItemUnselected(pos, bill)
                }
            }
        }
    }

    override fun onItemSelected(pos: Int, bill: BillModel) {
        viewModel.selectedBills.add(bill)
        viewModel.state.totalBillAmount =
            viewModel.state.totalBillAmount.plus(bill.amount.toDouble())
        setButtonText()
        viewModel.state.valid.set(true)
        viewModel.adapter.setItemAt(pos, bill)
    }

    override fun onItemUnselected(pos: Int, bill: BillModel) {
        viewModel.adapter.setItemAt(pos, bill)
        viewModel.selectedBills.remove(bill)
        viewModel.state.totalBillAmount =
            viewModel.state.totalBillAmount.minus(bill.amount.toDouble())
        setButtonText()
        if (viewModel.selectedBills.size == 0) {
            viewModel.state.valid.set(false)
        }
    }

    override fun setButtonText() {
        viewModel.state.buttonText.set(
            Translator.getString(
                requireContext(),
                Strings.screen_my_bills_btn_text_pay,
                SessionManager.getDefaultCurrency(),
                viewModel.state.totalBillAmount.toString()
            )
        )
    }

    val toolbarClickObserver = Observer<Int> {
        when (it) {
            R.id.ivRightIcon -> navigate(R.id.action_myBillsFragment_to_billCategoryFragment)
            R.id.btnPay -> {
            }
        }
    }

    override fun removeObservers() {
        viewModel.myBills.removeObservers(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }


}
