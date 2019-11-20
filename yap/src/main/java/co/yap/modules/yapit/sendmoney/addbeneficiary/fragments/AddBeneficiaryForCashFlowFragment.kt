package co.yap.modules.yapit.sendmoney.addbeneficiary.fragments

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces.IAddBeneficiary
import co.yap.modules.yapit.sendmoney.addbeneficiary.viewmodels.AddBeneficiaryViewModel
import co.yap.modules.yapit.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.translation.Translator

class AddBeneficiaryForCashFlowFragment : SendMoneyBaseFragment<IAddBeneficiary.ViewModel>(),
    IAddBeneficiary.View {

    override fun getBindingVariable(): Int = BR.viewModel
    /*
     Need to take the decision that if is from cash flow then use the fragment_add_beneficiary_domestic_transfer or other wise use fragment_add_beneficiary
       */

    //    override fun getLayoutId(): Int = R.layout.fragment_add_beneficiary_domestic_transfer
    override fun getLayoutId(): Int = R.layout.fragment_add_beneficiary_cash_flow

    override val viewModel: IAddBeneficiary.ViewModel
        get() = ViewModelProviders.of(this).get(AddBeneficiaryViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onPause() {
        viewModel.clickEvent.removeObservers(this)
        super.onPause()

    }

    override fun onResume() {
        super.onResume()

        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.confirmButton ->
                    ConfirmAddBeneficiary(this.activity!!)

            }
        })
    }

    override fun onBackPressed(): Boolean {

        return super.onBackPressed()
    }

    fun ConfirmAddBeneficiary(context: Context) {
        androidx.appcompat.app.AlertDialog.Builder(context)
            .setTitle(
                Translator.getString(
                    context,
                    R.string.screen_add_beneficiary_detail_display_text_alert_title
                )
            )
            .setMessage(
                Translator.getString(
                    context,
                    R.string.screen_add_beneficiary_detail_display_button_block_alert_description
                )
            )
            .setPositiveButton(
                Translator.getString(
                    context,
                    R.string.screen_add_beneficiary_detail_display_button_block_alert_yes
                ),
                DialogInterface.OnClickListener { dialog, which ->
                    onConfirmClick()
                    // launch following only in success event
//                    findNavController().navigate(R.id.action_addBeneficiaryFragment_to_addBankDetailsFragment)
                })

            .setNegativeButton(
                Translator.getString(
                    context,
                    R.string.screen_add_beneficiary_detail_display_button_block_alert_no
                ),
                null
            )
            .show()
    }

    fun onConfirmClick() {

        val beneficiary: Beneficiary = Beneficiary()

        beneficiary.beneficiaryType = "CASHPAYOUT"
        beneficiary.title = viewModel.state.nickName
        beneficiary.firstName = viewModel.state.firstName
        beneficiary.lastName = viewModel.state.lastName
        beneficiary.currency = viewModel.state.currency
        beneficiary.country = "UAE"
//      beneficiary.country = viewModel.state.country
        beneficiary.mobileNo = viewModel.state.phoneNumber

        viewModel.generateCashPayoutBeneficiaryRequestDTO(beneficiary)
    }
}