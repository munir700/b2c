package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.welcomeScreen

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentEasyBankTransferWelcomeBinding
import co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.banklist.BankListFragment
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseFragment
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.helpers.extentions.toast

class EasyBankTransferWelcomeFragment : AddMoneyBaseFragment<IEasyBankTransferWelcome.ViewModel>(),
    IEasyBankTransferWelcome.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_easy_bank_transfer_welcome

    override val viewModel: IEasyBankTransferWelcome.ViewModel
        get() = ViewModelProvider(this).get(EasyBankTransferWelcomeViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        viewModel.setDataFormat()
        //        val lean: Lean = Lean.Builder()
//            // .setAppToken("eyJraWQiOiI3OTE3YzAyNC0xN2M1LTRjOWYtYWI1OC05ZmFjZWI2Njg2NzYiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJqYXZhQHlhcC5jb218ZGFjZTk5N2ItYmUxNy00YmY4LWEyMmYtNmVkOTk0ZDY3YmNmIiwiYXVkIjoiZTNjN2RiYWQtZjE3Zi00OGY2LTkxOTMtNTc2OGNkYjQ2NmU4IiwiaXNzIjoiaHR0cHM6Ly9zZWxmLWlzc3VlZC5tZSIsImV4cCI6MTY0ODgwNzk2MiwiaWF0IjoxNjQ4ODA2NzYyLCJqdGkiOiI4NjNlMTZiNy04YjRkLTQ4MmUtYWUxMy1hZmI1MWZjNjVmMzgiLCJ1dCI6Ik1PQklMRV9BUFAifQ.MTiKc8ojY9ljtfXMESD6UXCpn4HDDlkJvhyo32zzqC0NmMrVILonEhtjIGw5RCX1V2wp22RZ3-ZKN3pyABrvZjQ_v8I9B9sgHBdIcu_6qzl3X_nPDoX_9baMUMtFaUTxYVyF9Rdz8VTkrSIyr425bPyjeoxkZ6heyUMhNHeqe9kSCtU4cEjISFJ9NgXG88tGgk2KXbS7fQlQ75d90VN4q0xW9HK8ePGK6ivxi6g515kOpuSWamXvI3h-PdJyGW3VTt5XslIrsHqnWdQxLmgLG_vmHlylicMWKzFSed9Yc7C3xVYe_wi5JIhsPaUWFDjjyB_n6MMBqRF04o6ZMyruGA")
//            // .setAppToken(CookiesManager.jwtToken?:"")
//            .setVersion(YAPApplication.configManager?.versionCode ?: "")
//            .showLogs()
//            .sandboxMode(true)
//            .build()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, observer)
        viewModel.customerId.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty().not())
                toast("Customer Created successfully")
            startFragment(fragmentName = BankListFragment::class.java.name)
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

    override fun getBinding() = getDataBindingView<FragmentEasyBankTransferWelcomeBinding>()
}