package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IInternationalFundsTransfer
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels.InternationalFundsTransferViewModel
import co.yap.modules.dashboard.yapit.sendmoney.fragments.SendMoneyBaseFragment
import kotlinx.android.synthetic.main.fragment_beneficiary_overview.*

class InternationalFundsTransferFragment :
    SendMoneyBaseFragment<IInternationalFundsTransfer.ViewModel>(),
    IInternationalFundsTransfer.View {


    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_international_funds_transfer

    override val viewModel: IInternationalFundsTransfer.ViewModel
        get() = ViewModelProviders.of(this).get(InternationalFundsTransferViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun editBeneficiaryScreen() {
        etnickName.isEnabled = true
        etFirstName.isEnabled = true
        etLastName.isEnabled = true
        etAccountIbanNumber.isEnabled = true
        etnickName.isEnabled = true
        etSwiftCode.isEnabled = true
        etBankREquiredFieldCode.isEnabled = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        if (!isFromAddBeneficiary) {
//            editBeneficiaryScreen()
//        }
//
//
//        viewModel.clickEvent.observe(this, Observer {
//            when (it) {
//                R.id.llBankDetail ->
//                    findNavController().navigate(R.id.action_beneficiaryOverviewFragment_to_beneficiaryAccountDetailsFragment)
//
//
//                R.id.confirmButton ->
//                    if (!isFromAddBeneficiary) {
//                        ConfirmAddBeneficiary(activity!!)       //may be show a dialogue to confirm edit beneficairy call and go back???
//
//                    } else {
//                        ConfirmAddBeneficiary(activity!!)
//
//                    }
//            }
//        })
    }

    override fun onPause() {
        viewModel.clickEvent.removeObservers(this)
        super.onPause()

    }


    override fun onBackPressed(): Boolean {

        return super.onBackPressed()
    }

}