package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IBeneficiaryAccountDetails
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels.BeneficiaryAccountDetailsViewModel
import co.yap.modules.dashboard.yapit.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.yapcore.helpers.Utils

class BeneficiaryAccountDetailsFragment :
    SendMoneyBaseFragment<IBeneficiaryAccountDetails.ViewModel>(),
    IBeneficiaryAccountDetails.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_beneficiary_account_detail

    override val viewModel: IBeneficiaryAccountDetails.ViewModel
        get() = ViewModelProviders.of(this).get(BeneficiaryAccountDetailsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.clickEvent.observe(this, observer)
        viewModel.success.observe(this, Observer {
            if (it)
                context?.let { it ->
                    Utils.ConfirmAddBeneficiary(it)
                }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private val observer = Observer<Int> {
        when (it) {
            R.id.confirmButton ->
                viewModel.createBeneficiaryRequest()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clickEvent.removeObservers(this)

    }

    override fun onBackPressed(): Boolean {

        return super.onBackPressed()
    }

}