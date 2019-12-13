package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IBankDetails
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels.BankDetailsViewModel
import co.yap.modules.dashboard.yapit.sendmoney.fragments.SendMoneyBaseFragment

// UI fields must be made dynamically based upon the response of API
class AddBankDetailsFragment : SendMoneyBaseFragment<IBankDetails.ViewModel>(),
    IBankDetails.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_add_bank_detail

    override val viewModel: IBankDetails.ViewModel
        get() = ViewModelProviders.of(this).get(BankDetailsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.clickEvent.observe(this, observer)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    val observer = Observer<Int> {
        when (it) {
            R.id.confirmButton ->
                findNavController().navigate(R.id.action_addBankDetailsFragment_to_beneficiaryAccountDetailsFragment)
        }
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()

    }

    override fun onBackPressed(): Boolean {
        return super.onBackPressed()
    }
}