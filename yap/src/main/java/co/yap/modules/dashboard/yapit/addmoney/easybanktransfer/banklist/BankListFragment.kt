package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.banklist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentEasyBankTransferBankListBinding
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseFragment
import co.yap.networking.leanteach.responsedtos.LeanOnBoardModel
import co.yap.widgets.SpaceGridItemDecoration
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.helpers.extentions.toast
import me.leantech.link.android.Lean

class BankListFragment : AddMoneyBaseFragment<IBankList.ViewModel>(),
    IBankList.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_easy_bank_transfer_bank_list

    override val viewModel: BankListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        setRecyclerView()
        viewModel.getBankList()
        getDataArguments()
    }

    private fun initializeSdk() {
        var perArray = arrayListOf<Lean.UserPermissions>()
        perArray.add(Lean.UserPermissions.ACCOUNTS)
        perArray.add(Lean.UserPermissions.IDENTITY)
//        perArray.add(Lean.UserPermissions.PAYMENTS)
//        perArray.add(Lean.UserPermissions.TRANSACTIONS)
//        perArray.add(Lean.UserPermissions.BALANCE)

        val lean: Lean = Lean.Builder()
            .setAppToken("9f9b7a4a-e470-4aba-b175-0987309e93db")
            .setVersion("latest")
            .showLogs()
            .sandboxMode(true)
            .build()

        lean.link(requireActivity(),
            viewModel.leanOnBoardModel.customerId.toString(),
            viewModel.bankList.value?.get(0)?.identifier,
            perArray,
            object : Lean.LeanListener {
                override fun onResponse(status: Lean.LeanStatus) {
                    var status = status.bank.bankId
                    lean.createPaymentSource(requireActivity(),
                        viewModel.leanOnBoardModel.customerId.toString(),
                        viewModel.bankList.value?.get(0)?.identifier,
                        viewModel.leanOnBoardModel.destinationId.toString(),
                        object : Lean.LeanListener {
                            override fun onResponse(status: Lean.LeanStatus) {
                                var status = status.bank.bankId
                            }

                        })
                }
            })
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, observer)
        viewModel.bankList.observe(viewLifecycleOwner) { list ->
            viewModel.bankListAdapter.setData(list)
            initializeSdk()
        }
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

    private fun getDataArguments() {
        arguments?.let { bundle ->
            bundle.getParcelable<LeanOnBoardModel>(Constants.ONBOARD_USER_LEAN)?.let {
                viewModel.leanOnBoardModel = it
            }
        }
    }

    override fun getBinding() = getDataBindingView<FragmentEasyBankTransferBankListBinding>()

}