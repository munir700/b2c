package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.accountlist

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentEasyBankTransferAccountListBinding
import co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.banklist.BankListFragment
import co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.topup.topupamount.TopupAmountFragment
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseFragment
import co.yap.modules.others.helper.Constants
import co.yap.networking.leanteach.responsedtos.accountlistmodel.LeanCustomerAccounts
import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel
import co.yap.translation.Strings
import co.yap.widgets.MultiStateView
import co.yap.widgets.State
import co.yap.widgets.Status
import co.yap.widgets.loading.CircularProgressBar
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText

class AccountListFragment :
    AddMoneyBaseFragment<FragmentEasyBankTransferAccountListBinding, IAccountList.ViewModel>(),
    IAccountList.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_easy_bank_transfer_account_list

    override val viewModel: AccountListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.lifecycleOwner = this
        viewModel.state.stateLiveData?.value = State.loading("")
        viewModel.getAccountList()
        viewModel.onboardUser()
        setObserver()
        setTextWithFormat()
    }

    override fun setObserver() {
        accountListObserver()
        stateDataObserver()
        onBoardObserver()
        clickObserver()
    }

    private fun accountListObserver() {
        viewModel.accountList.observe(viewLifecycleOwner) {
            (viewDataBinding.multiStateView.getView(
                MultiStateView.ViewState.CONTENT
            ) as ConstraintLayout).findViewById<RecyclerView>(R.id.rvAccountList).adapter =
                AccountListAdapter(it, object : AccountChildItemViewModel.OnItemClickListenerChild {
                    override fun onItemClick(
                        view: View,
                        data: Any,
                        bankListMainModel: BankListMainModel?,
                        pos: Int
                    ) {
                        bankListMainModel?.let { bankModel ->
                            if (bankModel.status == Constants.ACTIVE_STATUS) {
                                //this case will be handled when user will come in this screen after adding account
                                arguments?.let { bundle ->
                                    bundle.getString(co.yap.yapcore.constants.Constants.CUSTOMER_ID_LEAN)
                                        ?.let {
                                            startNewScreen(data as LeanCustomerAccounts, it)
                                        } ?: viewModel.customerId?.let { it1 ->
                                        startNewScreen(
                                            data as LeanCustomerAccounts,
                                            it1
                                        )
                                    }
                                } //this case will be handled when user already have an account
                                    ?: viewModel.customerId?.let { it1 ->
                                        startNewScreen(
                                            data as LeanCustomerAccounts,
                                            it1
                                        )
                                    }
                            }
                        }
                    }
                })
        }
    }

    private fun stateDataObserver() {
        viewModel.state.stateLiveData?.observe(viewLifecycleOwner) {
            handleState(it)
        }
    }

    private fun onBoardObserver() {
        viewModel.leanOnBoardModel.observe(viewLifecycleOwner) {
            if (it?.customerId.isNullOrEmpty().not()) {
                viewModel.customerId = it.customerId
            }
        }
    }

    private fun clickObserver() {
        viewModel.clickEvent.observe(viewLifecycleOwner) {
            when (it) {
                R.id.btnLinkAccount -> {
                    if (viewModel.customerId.isNullOrEmpty().not()) {
                        startFragment(
                            fragmentName = BankListFragment::class.java.name, bundle = bundleOf(
                                co.yap.yapcore.constants.Constants.ONBOARD_USER_LEAN to viewModel.customerId
                            )
                        )
                    }
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

    private fun startNewScreen(leanCustomerAccounts: LeanCustomerAccounts, customerID: String) {
        startFragment(
            fragmentName = TopupAmountFragment::class.java.name,
            bundle = bundleOf(
                co.yap.yapcore.constants.Constants.CUSTOMER_ID_LEAN to customerID,
                co.yap.yapcore.constants.Constants.MODEL_LEAN to leanCustomerAccounts
            )
        )
    }

    private fun handleState(state: State?) {
        viewDataBinding.multiStateView.viewState = when (state?.status) {
            Status.LOADING -> MultiStateView.ViewState.LOADING
            Status.EMPTY -> MultiStateView.ViewState.EMPTY
            Status.SUCCESS -> MultiStateView.ViewState.CONTENT
            Status.ERROR -> MultiStateView.ViewState.ERROR
            else -> throw IllegalStateException("State is not handled " + state?.status)
        }
        (viewDataBinding.multiStateView.getView(
            MultiStateView.ViewState.LOADING
        ) as CircularProgressBar).indeterminateMode = true
    }

    private fun setTextWithFormat() {
        val mainLayout: ConstraintLayout =
            (viewDataBinding.multiStateView.getView(MultiStateView.ViewState.EMPTY) as ConstraintLayout)
        context?.getColor(R.color.colorPrimaryDark)?.let {
            (mainLayout.getViewById(R.id.tvWelcomeText) as AppCompatTextView).setTextColor(it)
        }
        (mainLayout.getViewById(R.id.tvDescText) as AppCompatTextView).text =
            context?.resources?.getText(
                getString(Strings.screen_lean_welcome_screen_connect_one_of_your_existing_bank),
                context?.color(
                    R.color.colorPrimaryDark,
                    "instantly"
                ),
                context?.color(
                    R.color.colorPrimaryDark,
                    "zero fees!"
                )
            )
    }
}