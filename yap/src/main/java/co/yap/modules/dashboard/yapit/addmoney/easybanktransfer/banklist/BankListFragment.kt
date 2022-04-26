package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.banklist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentEasyBankTransferBankListBinding
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseFragment
import co.yap.networking.leanteach.responsedtos.LeanOnBoardModel
import co.yap.networking.leanteach.responsedtos.banklistmodels.BankListMainModel
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.interfaces.OnItemClickListener

class BankListFragment :
    AddMoneyBaseFragment<FragmentEasyBankTransferBankListBinding, IBankList.ViewModel>(),
    IBankList.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_easy_bank_transfer_bank_list

    override val viewModel: BankListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.lifecycleOwner = this
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
        viewModel.isPaymentJourneySet.observe(viewLifecycleOwner) { isSet ->
            if (isSet) setResultData()
        }

        getBinding().layoutSearchView.yapSearchViewListener = viewModel.yapSearchViewChangeListener
    }

    override fun removeObservers() {
        viewModel.bankList.removeObservers(this)
        getBinding().layoutSearchView.yapSearchViewListener = null
    }

    private fun setRecyclerClick() {
        viewModel.bankListAdapter.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(view: View, data: Any, pos: Int) {
                if (data is BankListMainModel) {
                    data.identifier?.let { viewModel.startPaymentSourceJourney(it, activity) }
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

    fun setResultData() {
        val intent = Intent()
        activity?.setResult(Activity.RESULT_OK, intent)
        activity?.finish()
    }

    fun getBinding() = getDataBindingView<FragmentEasyBankTransferBankListBinding>()
}