package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.accountlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentEasyBankTransferAccountListBinding
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseFragment
import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel

class AccountListFragment :
    AddMoneyBaseFragment<FragmentEasyBankTransferAccountListBinding, IAccountList.ViewModel>(),
    IAccountList.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_easy_bank_transfer_account_list

    override val viewModel: AccountListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAccountList()
        setObserver()
    }

    override fun setObserver() {
        viewModel.accountList.observe(viewLifecycleOwner) {
            viewDataBinding.rvAccountList.adapter = AccountListAdapter(it, clickListener)
        }
    }

    private val clickListener = object : AccountChildItemViewModel.OnItemClickListenerChild {
        override fun onItemClick(
            view: View,
            data: Any,
            bankListMainModel: BankListMainModel?,
            pos: Int
        ) {
            bankListMainModel?.let { bankModel ->
                if (bankModel.status == "ACTIVE") {

                }
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
}