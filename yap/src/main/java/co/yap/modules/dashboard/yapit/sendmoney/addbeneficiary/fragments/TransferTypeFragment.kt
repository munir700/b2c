package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.fragments

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.ITransferType
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels.TransferTypeViewModel
import co.yap.modules.dashboard.yapit.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.translation.Translator

class TransferTypeFragment : SendMoneyBaseFragment<ITransferType.ViewModel>(),
    ITransferType.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_transfer_type

    override val viewModel: ITransferType.ViewModel
        get() = ViewModelProviders.of(this).get(TransferTypeViewModel::class.java)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.llBankTransferType -> {
                    findNavController().navigate(R.id.action_transferTypeFragment_to_addBeneficiaryFragment)
//                    findNavController().navigate(R.id.action_transferTypeFragment_to_addBeneficiaryForDomesticTransferFragment)
                }

                R.id.llCashPickUpTransferType -> {
//                    findNavController().navigate(R.id.action_transferTypeFragment_to_addBeneficiaryForCashFlowFragment)
                    ConfirmAddBeneficiary(activity!!)
//                    findNavController().navigate(R.id.action_transferTypeFragment_to_selectCountryFragment)
                }
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
                    R.string.screen_add_beneficiary_display_text_transfer_type_cash_pickup
                ),
                DialogInterface.OnClickListener { dialog, which ->
                    findNavController().navigate(R.id.action_transferTypeFragment_to_addBeneficiaryForCashFlowFragment)

                    // launch followiun only in success event
//                    findNavController().navigate(R.id.action_addBeneficiaryFragment_to_addBankDetailsFragment)
                })

            .setNegativeButton(
                "Domestic",
                DialogInterface.OnClickListener { dialog, which ->
                    findNavController().navigate(R.id.action_transferTypeFragment_to_addBeneficiaryForDomesticTransferFragment)

                    // launch followiun only in success event
//                    findNavController().navigate(R.id.action_addBeneficiaryFragment_to_addBankDetailsFragment)
                }
            )
            .show()
    }


}