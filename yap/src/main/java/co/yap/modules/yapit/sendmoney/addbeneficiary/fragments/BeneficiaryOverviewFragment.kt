package co.yap.modules.yapit.sendmoney.addbeneficiary.fragments


import android.content.Context
import android.content.DialogInterface
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
import co.yap.translation.Translator
import kotlinx.android.synthetic.main.fragment_beneficiary_overview.*

// Need to check whether to add account detail screen on click of bank details box & hide button or

class BeneficiaryOverviewFragment : SendMoneyBaseFragment<IBeneficiaryOverview.ViewModel>(),
    IBeneficiaryOverview.View {

    var isFromAddBeneficiary: Boolean = false

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_beneficiary_overview

    override val viewModel: IBeneficiaryOverview.ViewModel
        get() = ViewModelProviders.of(this).get(BeneficiaryOverviewViewModel::class.java)

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
        if (!isFromAddBeneficiary) {
            editBeneficiaryScreen()
        }


        viewModel.clickEvent.observe(this, Observer {
            when (it) {
//                R.id.llBankDetail ->
//                    findNavController().navigate(R.id.action_beneficiaryOverviewFragment_to_beneficiaryAccountDetailsFragment)


                R.id.confirmButton ->
                    if (!isFromAddBeneficiary) {
                        //may be show a dialogue to confirm edit beneficairy call and go back???
                    } else {
                        ConfirmAddBeneficiary(activity!!)

                    }
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
                    findNavController().navigate(R.id.action_addBeneficiaryFragment_to_addBankDetailsFragment)// start funds transfer screen

                })

            .setNegativeButton(
                Translator.getString(
                    context,
                    R.string.screen_add_beneficiary_detail_display_button_block_alert_no
                ),
                DialogInterface.OnClickListener { dialog, which ->

                    super.onBackPressed() // finish this nav graph or all screens till here and start the required screen

                })

            .show()
    }

}