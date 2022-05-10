package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.accountlist

import android.app.Activity
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
import co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.topup.activity.TopUpActivity
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseFragment
import co.yap.modules.others.helper.Constants
import co.yap.networking.leanteach.responsedtos.LeanOnBoardModel
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.widgets.MultiStateView
import co.yap.widgets.State
import co.yap.widgets.Status
import co.yap.widgets.loading.CircularProgressBar
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.startFragmentForResult
import co.yap.yapcore.helpers.spannables.color
import co.yap.yapcore.helpers.spannables.getText
import co.yap.yapcore.interfaces.OnItemClickListener

class AccountListFragment :
    AddMoneyBaseFragment<FragmentEasyBankTransferAccountListBinding, IAccountList.ViewModel>(),
    IAccountList.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_easy_bank_transfer_account_list

    override val viewModel: AccountListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.lifecycleOwner = this
        viewModel.setMultiState()
        setObserver()
        setTextWithFormat()
    }

    override fun setObserver() {
        accountListObserver()
        stateDataObserver()
        onBoardObserver()
        clickObserver()
    }

    private val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is AccountsListModel && data.leanCustomerAccounts != null)
                if (data.bankListMainModel?.status == Constants.ACTIVE_STATUS) {
                    viewModel.leanCustomerAccounts = data
                    viewModel.isListClicked = true
                    viewModel.onboardUser()

                }
        }
    }

    private fun accountListObserver() {
        viewModel.accountList.observe(viewLifecycleOwner) { list ->
            val adapter = AccountListAdapter(list, null)
            (viewDataBinding.multiStateView.getView(
                MultiStateView.ViewState.CONTENT
            ) as ConstraintLayout).findViewById<RecyclerView>(R.id.rvAccountList).adapter = adapter
            adapter.onItemClickListener = listener
        }
    }

    private fun stateDataObserver() {
        viewModel.state.stateLiveData?.observe(viewLifecycleOwner) {
            handleState(it)
        }
    }

    private fun onBoardObserver() {
        viewModel.leanOnBoardModel.observe(viewLifecycleOwner) {
            if (viewModel.isListClicked) {
                startTopUpFlow()
            } else {
                startBankFragment()
            }
        }
    }

    private fun clickObserver() {
        viewModel.clickEvent.observe(viewLifecycleOwner) {
            when (it) {
                R.id.btnLinkAccount -> {
                    viewModel.isListClicked = false
                    viewModel.onboardUser()
                }
            }
        }
    }

    private fun startTopUp(
        accountsListModel: AccountsListModel,
        customerID: String,
        destinationId: String
    ) {
        val bundle = bundleOf(
            co.yap.yapcore.constants.Constants.CUSTOMER_ID_LEAN to customerID,
            co.yap.yapcore.constants.Constants.DESTINATION_ID_LEAN to destinationId,
            co.yap.yapcore.constants.Constants.MODEL_LEAN to accountsListModel.leanCustomerAccounts,
            co.yap.yapcore.constants.Constants.MODEL_BANK_LEAN to accountsListModel.bankListMainModel
        )

        launchActivity<TopUpActivity> {
            putExtra(co.yap.yapcore.constants.Constants.EXTRA, bundle)
        }
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

    fun startBankFragment() {
        startFragmentForResult<BankListFragment>(
            fragmentName = BankListFragment::class.java.name,
            bundle = bundleOf(
                co.yap.yapcore.constants.Constants.ONBOARD_USER_LEAN to viewModel.leanOnBoardModel.value
            ),
            showToolBar = true,
            toolBarTitle = Translator.getString(
                requireContext(),
                Strings.screen_lean_bank_list_add_an_account
            )
        ) { resultCode, _ ->
            if (resultCode == Activity.RESULT_OK)
                viewModel.setMultiState()
        }
    }


    private fun startTopUpFlow() {
        //this case will be handled when user will come in this screen after adding account
        arguments?.let { bundle ->
            bundle.getParcelable<LeanOnBoardModel>(co.yap.yapcore.constants.Constants.ONBOARD_USER_LEAN)
                ?.let {
                    it.customerId?.let { cus_id ->
                        it.destinationId?.let { des_id ->
                            startTopUp(
                                viewModel.leanCustomerAccounts,
                                cus_id,
                                des_id
                            )
                        }
                    }
                }
        } //this case will be handled when user already have an account
            ?: viewModel.leanOnBoardModel.value?.customerId?.let { cus_id ->
                viewModel.leanOnBoardModel.value?.destinationId
                    ?.let { des_id ->
                        startTopUp(
                            viewModel.leanCustomerAccounts,
                            cus_id,
                            des_id
                        )
                    }
            }
    }
}