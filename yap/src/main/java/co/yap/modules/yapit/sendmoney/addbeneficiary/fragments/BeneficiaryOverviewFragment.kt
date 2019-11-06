package co.yap.modules.yapit.sendmoney.addbeneficiary.fragments


import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces.IBeneficiaryOverview
import co.yap.modules.yapit.sendmoney.addbeneficiary.viewmodels.BeneficiaryOverviewViewModel
import co.yap.modules.yapit.sendmoney.fragments.SendMoneyBaseFragment

// Need to check whether to add account detail screen on click of bank details box & hide button or

class BeneficiaryOverviewFragment : SendMoneyBaseFragment<IBeneficiaryOverview.ViewModel>(),
    IBeneficiaryOverview.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_beneficiary_overview

    override val viewModel: IBeneficiaryOverview.ViewModel
        get() = ViewModelProviders.of(this).get(BeneficiaryOverviewViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.llBankDetail ->
                    findNavController().navigate(R.id.action_beneficiaryOverviewFragment_to_beneficiaryAccountDetailsFragment)
            }
        })
    }

    override fun onPause() {
        viewModel.clickEvent.removeObservers(this)
        super.onPause()

    }

    override fun onResume() {
        super.onResume()


    }

    override fun onBackPressed(): Boolean {

        return super.onBackPressed()
    }

}