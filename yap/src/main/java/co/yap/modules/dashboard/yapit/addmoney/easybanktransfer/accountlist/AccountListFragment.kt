package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.accountlist

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentEasyBankTransferAccountListBinding
import co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.topup.topupamount.TopupAmountFragment
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseFragment
import co.yap.modules.others.helper.Constants
import co.yap.networking.leanteach.responsedtos.accountlistmodel.LeanCustomerAccounts
import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel
import co.yap.yapcore.helpers.extentions.startFragment

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
                if (bankModel.status == Constants.ACTIVE_STATUS) {
                    startNewScreen(data as LeanCustomerAccounts)
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

    private fun startNewScreen(leanCustomerAccounts: LeanCustomerAccounts) {
        arguments?.let { bundle ->
            bundle.getString(co.yap.yapcore.constants.Constants.CUSTOMER_ID_LEAN)?.let {
                startFragment(
                    fragmentName = TopupAmountFragment::class.java.name,
                    bundle = bundleOf(
                        co.yap.yapcore.constants.Constants.CUSTOMER_ID_LEAN to it,
                        co.yap.yapcore.constants.Constants.MODEL_LEAN to leanCustomerAccounts
                    )
                )
            }
        }
    }
}