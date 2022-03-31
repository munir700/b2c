package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.welcomeScreen

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentEasyBankTransferWelcomeBinding
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseFragment
import co.yap.yapcore.helpers.extentions.toast

class EasyBankTransferWelcomeFragment : AddMoneyBaseFragment<IEasyBankTransferWelcome.ViewModel>(),
    IEasyBankTransferWelcome.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_easy_bank_transfer_welcome

    override val viewModel: IEasyBankTransferWelcome.ViewModel
        get() = ViewModelProvider(this).get(EasyBankTransferWelcomeViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        viewModel.setDataFormat()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, observer)
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

    override fun getBinding() = getDataBindingView<FragmentEasyBankTransferWelcomeBinding>()
}