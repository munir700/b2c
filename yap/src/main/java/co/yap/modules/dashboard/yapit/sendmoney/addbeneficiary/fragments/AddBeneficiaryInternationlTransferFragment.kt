package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentAddBeneficiaryInternationalBankTransferBinding
import co.yap.modules.dashboard.yapit.sendmoney.activities.BeneficiaryCashTransferActivity
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IAddBeneficiary
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels.AddBeneficiaryViewModel
import co.yap.modules.dashboard.yapit.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.translation.Translator
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.OnItemClickListener


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.clickEvent.observe(this, observer)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
    }

    private fun initComponents() {
//        (activity as? SendMoneyHomeActivity)?.viewModel?.selectedCountry?.value?.let {
//            getBindings()?.ccpSelector?.setCountryForNameCode(it.isoCountryCode2Digit ?: "")
//        }
    }


    val observer = Observer<Int> {
        when (it) {
            R.id.confirmButton -> {
                if(viewModel.state.transferType != "Cash Pickup")
                    findNavController().navigate(R.id.action_addBeneficiaryFragment_to_addBankDetailsFragment)
            }
            R.id.emptyCardLayout -> {

            }
            Constants.ADD_CASH_PICK_UP_SUCCESS -> {
                addBeneficiaryDialog()
            }
        }
    }

    private fun addBeneficiaryDialog(){
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
                                startActivity(Intent(it, BeneficiaryCashTransferActivity::class.java))
                                activity?.let { activity ->
                                    activity.finish()
                                }
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
    override fun onDestroy() {
        super.onDestroy()
        viewModel.clickEvent.removeObservers(this)
    }

    override fun onBackPressed(): Boolean {
        return super.onBackPressed()
    }

    private fun getBindings(): FragmentAddBeneficiaryInternationalBankTransferBinding? {
        return viewDataBinding as? FragmentAddBeneficiaryInternationalBankTransferBinding
    }

}