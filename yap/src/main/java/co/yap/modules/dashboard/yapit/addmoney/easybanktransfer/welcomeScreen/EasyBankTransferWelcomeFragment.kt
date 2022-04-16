package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.welcomeScreen

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentEasyBankTransferWelcomeBinding
import co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.banklist.BankListFragment
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseFragment
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.helpers.extentions.toast

class EasyBankTransferWelcomeFragment :
    AddMoneyBaseFragment<FragmentEasyBankTransferWelcomeBinding, IEasyBankTransferWelcome.ViewModel>(),
    IEasyBankTransferWelcome.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_easy_bank_transfer_welcome

    override val viewModel: EasyBankTransferWelcomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        viewModel.setDataFormat()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, observer)
        viewModel.leanOnBoardModel.observe(viewLifecycleOwner) {
            if (it.customerId.isNullOrEmpty().not()) {
                toast("Customer Created successfully")
                startFragment(
                    fragmentName = BankListFragment::class.java.name, bundle = bundleOf(
                        Constants.ONBOARD_USER_LEAN to viewModel.leanOnBoardModel.value
                    )
                )
            }
        }
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
    }

    private val observer = Observer<Int> {
        when (it) {
            R.id.btnLinkAccount -> {
                viewModel.onboardUser()
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

    override fun onDestroyView() {
        super.onDestroyView()
        removeObservers()
    }
}