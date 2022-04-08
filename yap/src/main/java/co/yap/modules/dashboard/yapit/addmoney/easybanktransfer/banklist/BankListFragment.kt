package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.banklist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentEasyBankTransferBankListBinding
import co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.leansdk.LeanSdkManager
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseFragment
import co.yap.networking.leanteach.responsedtos.LeanOnBoardModel
import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel
import co.yap.widgets.SpaceGridItemDecoration
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.helpers.extentions.toast
import co.yap.yapcore.interfaces.OnItemClickListener
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

    private fun initializeSdk(bankIdentifier: String) {
        with(viewModel.leanOnBoardModel) {
            LeanSdkManager.lean?.createPaymentSource(
                requireActivity(),
                customerId.toString(),
                bankIdentifier,
                destinationId.toString(),
                object : Lean.LeanListener {
                    override fun onResponse(status: Lean.LeanStatus) {
                        toast(status.status)
                        //                        lean.pay(requireActivity(),"17854275-89d9-4d60-894d-15d8353456be",true,
                        //                            "fda324b0-3d5c-4da4-8d0e-35f46c24a7f1",object :Lean.LeanListener{
                        //                                override fun onResponse(status: Lean.LeanStatus) {
                        //
                        //                                }
                        //                            })
                    }
                }
            )
        }
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, observer)
        viewModel.bankList.observe(viewLifecycleOwner) { list ->
            viewModel.bankListAdapter.setData(list)
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
        viewModel.bankListAdapter.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(view: View, data: Any, pos: Int) {
                if (data is BankListMainModel) {
                    data.identifier?.let { initializeSdk(it) }
                }
            }
        }
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