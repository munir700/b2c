package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.banklist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentEasyBankTransferBankListBinding
import co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.accountlist.AccountListFragment
import co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.leansdk.LeanSdkManager
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseFragment
import co.yap.widgets.search.IYapSearchView
import co.yap.networking.leanteach.responsedtos.LeanOnBoardModel
import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.helpers.extentions.toast
import co.yap.yapcore.interfaces.OnItemClickListener
import me.leantech.link.android.Lean

class BankListFragment :
    AddMoneyBaseFragment<FragmentEasyBankTransferBankListBinding, IBankList.ViewModel>(),
    IBankList.View,IYapSearchView{
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_easy_bank_transfer_bank_list

    override val viewModel: BankListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataArguments()
        setObservers()
        setRecyclerClick()
        viewModel.getBankList()

    }

    private fun getDataArguments() {
        arguments?.let { bundle ->
            bundle.getParcelable<LeanOnBoardModel>(Constants.ONBOARD_USER_LEAN)?.let {
                viewModel.leanOnBoardModel = it
            }
        }
    }

    override fun setObservers() {
        viewModel.bankList.observe(viewLifecycleOwner) { list ->
            viewModel.bankListAdapter.setData(list)
        }

        getBinding().layoutSearchView.yapSearchViewListener = this
    }

    override fun removeObservers() {
        viewModel.bankList.removeObservers(this)
        getBinding().layoutSearchView.yapSearchViewListener = null
    }

    private fun setRecyclerClick() {
        viewModel.bankListAdapter.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(view: View, data: Any, pos: Int) {
                if (data is BankListMainModel) {
                    data.identifier?.let { startPaymentSourceJourney(it) }
                }
            }
        }
    }

    private fun startPaymentSourceJourney(bankIdentifier: String) {
        with(viewModel.leanOnBoardModel) {
            LeanSdkManager.lean?.createPaymentSource(
                requireActivity(),
                customerId.toString(),
                bankIdentifier,
                destinationId.toString(),
                object : Lean.LeanListener {
                    override fun onResponse(status: Lean.LeanStatus) {
                        if (status.status == co.yap.modules.others.helper.Constants.SUCCESS_STATUS)
                            startFragment(
                                fragmentName = AccountListFragment::class.java.name
                            )
                        else toast(status.status)
                    }
                }
            )
        }
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.ivLeftIcon -> {
                activity?.finish()
            }
        }
    }

    fun getBinding() = getDataBindingView<FragmentEasyBankTransferBankListBinding>()

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