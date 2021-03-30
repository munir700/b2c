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

class MyBillsFragment : PayBillBaseFragment<IMyBills.ViewModel>(),
    IMyBills.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_my_bills
    override val viewModel: IMyBills.ViewModel
        get() = ViewModelProviders.of(this).get(MyBillsViewModel::class.java)

    override val onItemClickListener: OnItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            val bill = data as BillModel
            if (bill.billStatus == BillStatus.BILL_DUE.title) {
                navigate(R.id.payBillFragment)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.adapter.onItemClickListener = onItemClickListener
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
    }

    override fun removeObservers() {
        viewModel.myBills.removeObservers(this)
    }


    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }


}
