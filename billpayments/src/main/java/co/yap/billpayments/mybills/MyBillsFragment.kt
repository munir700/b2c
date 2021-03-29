package co.yap.billpayments.mybills

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.base.PayBillBaseFragment
import co.yap.billpayments.mybills.adapter.BillModel
import co.yap.translation.Strings
import co.yap.translation.Translator

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
    }

    val toolbarClickObserver = Observer<Int> {
        when (it) {
            R.id.ivRightIcon -> navigate(R.id.action_myBillsFragment_to_billCategoryFragment)
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
