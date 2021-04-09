package co.yap.billpayments.billers

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.base.PayBillBaseFragment
import co.yap.networking.customers.responsedtos.billpayment.BillerCatalogModel
import co.yap.yapcore.interfaces.OnItemClickListener

class BillersFragment : PayBillBaseFragment<IBillers.ViewModel>(),
    IBillers.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override val viewModel: BillersViewModel
        get() = ViewModelProviders.of(this).get(BillersViewModel::class.java)

    override fun getLayoutId(): Int = R.layout.fragment_billers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getBillerCatalogs(
            viewModel.parentViewModel?.selectedBillProvider?.categoryId ?: ""
        )
        setObservers()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEventObserver)
        viewModel.adapter.setItemListener(onItemClickListener)
    }

    val onItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            viewModel.parentViewModel?.selectedBillerCatalog = (data as BillerCatalogModel)
            navigate(R.id.action_billersFragment_to_billerDetailFragment)
        }
    }

    private val clickEventObserver = Observer<Int> {
        when (it) {
            R.id.includedSearchView -> {
                navigate(R.id.action_billersFragment_to_billerSearchFragment)
            }
        }
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
    }

    override fun onBackPressed(): Boolean {
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }
}
