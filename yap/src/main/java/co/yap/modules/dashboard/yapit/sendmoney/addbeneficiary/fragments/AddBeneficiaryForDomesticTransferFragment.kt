package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.yapit.sendmoney.activities.BeneficiaryCashTransferActivity
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IAddBeneficiary
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels.AddBeneficiaryViewModel
import co.yap.modules.dashboard.yapit.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.translation.Translator
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.OnItemClickListener

class AddBeneficiaryForDomesticTransferFragment :
    SendMoneyBaseFragment<IAddBeneficiary.ViewModel>(),
    IAddBeneficiary.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_add_beneficiary_domestic_transfer
    override val viewModel: IAddBeneficiary.ViewModel
        get() = ViewModelProviders.of(this).get(AddBeneficiaryViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.clickEvent.observe(this, observer)
        viewModel.addBeneficiarySuccess.observe(this, Observer {
            if(it){
                addBeneficiarySuccessDialog()
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clickEvent.removeObservers(this)
    }

    val observer = Observer<Int> {
        when (it) {
            R.id.confirmButton -> {
                onConfirmClick()
            }
        }
    }

    private fun addBeneficiarySuccessDialog() {
        context?.let { it ->
            Utils.confirmationDialog(it,
                Translator.getString(
                    it,
                    R.string.screen_add_beneficiary_detail_display_text_alert_title
                ),
                Translator.getString(
                    it,
                    R.string.screen_add_beneficiary_detail_display_button_block_alert_description
                ), Translator.getString(
                    it,
                    R.string.screen_add_beneficiary_detail_display_button_block_alert_yes
                ), Translator.getString(
                    it,
                    R.string.screen_add_beneficiary_detail_display_button_block_alert_no
                ),
                object : OnItemClickListener {
                    override fun onItemClick(view: View, data: Any, pos: Int) {
                        if (data is Boolean) {
                            if (data) {
                                startMoneyTransfer()
                            } else {
                                activity?.let { activity ->
                                    activity.finish()
                                }
                            }
                        }
                    }
                })
        }
    }

    private fun startMoneyTransfer() {
        viewModel.beneficiary?.let { beneficiary ->
            startActivity(BeneficiaryCashTransferActivity.newIntent(requireContext(), beneficiary))
            activity?.let { activity ->
                activity.finish()
            }
        }
    }

    private fun onConfirmClick() {
        val beneficiary = Beneficiary()
        beneficiary.beneficiaryType = SendMoneyBeneficiaryType.DOMESTIC.name
        beneficiary.firstName = viewModel.state.firstName
        beneficiary.lastName = viewModel.state.lastName
        beneficiary.country = "AE"
        beneficiary.accountNo = viewModel.state.iban
        viewModel.addDomesticBeneficiary(beneficiary)
    }

}