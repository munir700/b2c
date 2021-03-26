package co.yap.billpayments.billcategory

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.base.PayBillBaseFragment
import co.yap.billpayments.billcategory.adapter.BillCategoryModel
import co.yap.billpayments.databinding.FragmentBillCategoryBinding


class BillCategoryFragment : PayBillBaseFragment<IBillCategory.ViewModel>(),
    IBillCategory.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override val viewModel: IBillCategory.ViewModel
        get() = ViewModelProviders.of(this).get(BillCategoryViewModel::class.java)

    override fun getLayoutId(): Int = R.layout.fragment_bill_category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun setObservers() {
        viewModel.billcategories.observe(this, Observer {
            viewModel.adapter.setList(viewModel.billcategories.value?.toList() as List<BillCategoryModel>)
        })
    }

    override fun removeObservers() {
        viewModel.billcategories.removeObservers(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }

    private fun getBindings(): FragmentBillCategoryBinding =
        viewDataBinding as FragmentBillCategoryBinding
}
