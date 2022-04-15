package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.banklist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentEasyBankTransferBankListBinding
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseFragment
import co.yap.widgets.SpaceGridItemDecoration
import co.yap.widgets.search.IYapSearchView
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.helpers.extentions.toast

class BankListFragment : AddMoneyBaseFragment<IBankList.ViewModel>(),
    IBankList.View,IYapSearchView {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_easy_bank_transfer_bank_list

    override val viewModel: BankListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        setRecyclerView()
        viewModel.getBankList()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, observer)
        viewModel.bankList.observe(viewLifecycleOwner) { list ->
            viewModel.bankListAdapter.setData(list)
        }

        getBinding().layoutSearchView.yapSearchViewListener = this
    }

    private fun setRecyclerView() {
        getBinding().rvBankList.addItemDecoration(
            SpaceGridItemDecoration(
                dimen(R.dimen.margin_normal_large),
                1,
                true
            )
        )
        // set it in adapter viewModel.bankListAdapter.onItemClickListener = itemClickListener
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
    }

    private val observer = Observer<Int> {
        when (it) {
            R.id.btnLinkAccount -> {
                toast("link an account")
            }
        }
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivLeftIcon -> {
                activity?.finish()
            }
        }
    }

    override fun getBinding() = getDataBindingView<FragmentEasyBankTransferBankListBinding>()

    override fun onSearchActive(isActive: Boolean) {
        if(isActive) {
            getBinding().tvSelectBank.visibility = View.GONE
            getBinding().tvChooseBank.visibility = View.GONE
        }else{
            getBinding().tvSelectBank.visibility = View.VISIBLE
            getBinding().tvChooseBank.visibility = View.VISIBLE
        }
    }

    override fun onTypingSearch(search: String?) {
        //filter adapter
    }
}