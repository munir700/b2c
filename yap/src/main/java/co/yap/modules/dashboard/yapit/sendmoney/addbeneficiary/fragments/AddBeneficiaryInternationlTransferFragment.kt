package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.fragments

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IAddBeneficiary
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels.AddBeneficiaryViewModel
import co.yap.modules.dashboard.yapit.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.translation.Translator


//this wil be the common screen in all three case only change in CASH FLOW CHANGE CURRENCY OPTION WILL BE HIDDEN

class AddBeneficiaryInternationlTransferFragment :
    SendMoneyBaseFragment<IAddBeneficiary.ViewModel>(),
    IAddBeneficiary.View {

    override fun getBindingVariable(): Int = BR.viewModel
    /*
     Need to take the decision that if is from cash flow then use the fragment_add_beneficiary_domestic_transfer or other wise use fragment_add_beneficiary
       */
//    override fun getLayoutId(): Int = R.layout.fragment_add_beneficiary_domestic_transfer
    override fun getLayoutId(): Int = R.layout.fragment_add_beneficiary_international_bank_transfer

    override val viewModel: IAddBeneficiary.ViewModel
        get() = ViewModelProviders.of(this).get(AddBeneficiaryViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.confirmButton ->
                    ConfirmAddBeneficiary()

//                R.id.confirmDomesticButton ->
//                    ConfirmAddBeneficiary(this.activity!!)
            }
        })
    }

    override fun onPause() {
        viewModel.clickEvent.removeObservers(this)
        super.onPause()

    }

    override fun onBackPressed(): Boolean {
        return super.onBackPressed()
    }

    fun ConfirmAddBeneficiary() {
        context?.let {
            androidx.appcompat.app.AlertDialog.Builder(it)
                .setTitle(
                    Translator.getString(
                        it,
                        R.string.screen_add_beneficiary_detail_display_text_alert_title
                    )
                )
                .setMessage(
                    Translator.getString(
                        it,
                        R.string.screen_add_beneficiary_detail_display_button_block_alert_description
                    )
                )
                .setPositiveButton(
                    Translator.getString(
                        it,
                        R.string.screen_add_beneficiary_detail_display_button_block_alert_yes
                    ),
                    DialogInterface.OnClickListener { dialog, which ->
                        //                    findNavController().navigate(R.id.action_addBeneficiaryFragment_to_addBankDetailsFragment)
                        onConfirmClick()


                    })

                .setNegativeButton(
                    Translator.getString(
                        it,
                        R.string.screen_add_beneficiary_detail_display_button_block_alert_no
                    ),
                    null
                )
                .show()
        }

    }

    fun onConfirmClick() {

// for sure it's fields needs to be update, as they are taken form cashpayout

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

//    fun ConfirmAddBeneficiary() {
//        AlertDialog.Builder(this!!.activity!!)
//            .setTitle(getString(R.string.screen_add_beneficiary_detail_display_text_alert_title))
//            .setMessage(getString(R.string.screen_add_beneficiary_detail_display_button_block_alert_description))
//            .setPositiveButton(getString(R.string.screen_add_beneficiary_detail_display_button_block_alert_yes),
//                DialogInterface.OnClickListener { dialog, which ->
//                    //                    doLogout()
//                })
//
//            .setNegativeButton(
//                getString(R.string.screen_add_beneficiary_detail_display_button_block_alert_no),
//                null
//            )
//            .show()
//    }

}