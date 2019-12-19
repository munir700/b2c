package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.countryutils.country.InternationalPhoneTextWatcher
import co.yap.databinding.FragmentAddBeneficiaryInternationalBankTransferBinding
import co.yap.modules.dashboard.yapit.sendmoney.activities.BeneficiaryCashTransferActivity
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IAddBeneficiary
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels.AddBeneficiaryViewModel
import co.yap.modules.dashboard.yapit.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.modules.others.helper.getCurrencyPopMenu
import co.yap.translation.Translator
import co.yap.widgets.popmenu.PopupMenu
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.OnItemClickListener
import kotlinx.android.synthetic.main.activity_edit_beneficiary.tvChangeCurrency
import kotlinx.android.synthetic.main.fragment_add_beneficiary_international_bank_transfer.*


//this wil be the common screen in all three case only change in CASH FLOW CHANGE CURRENCY OPTION WILL BE HIDDEN

class AddBeneficiaryInternationlTransferFragment :
    SendMoneyBaseFragment<IAddBeneficiary.ViewModel>(),
    IAddBeneficiary.View {
    private var currencyPopMenu: PopupMenu? = null
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
        viewModel.addBeneficiarySuccess.observe(this, Observer {
            if (it) {
                addBeneficiaryDialog()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        etMobileNumber.addTextChangedListener(
            InternationalPhoneTextWatcher(
                requireContext(),
                viewModel.state.country2DigitIsoCode,
                viewModel.state.countryCode.toInt(),
                true
            )
        )
    }

    private fun initComponents() {
        currencyPopMenu = requireContext().getCurrencyPopMenu(this,null,null)
//        (activity as? SendMoneyHomeActivity)?.viewModel?.selectedCountry?.value?.let {
//            getBindings()?.ccpSelector?.setCountryForNameCode(it.isoCountryCode2Digit ?: "")
//        }
    }


    val observer = Observer<Int> {
        when (it) {
            R.id.confirmButton -> {
                if (viewModel.state.transferType != "Cash Pickup")
                    findNavController().navigate(R.id.action_addBeneficiaryFragment_to_addBankDetailsFragment)
            }
            R.id.emptyCardLayout -> {

            }
            R.id.tvChangeCurrency -> {
                currencyPopMenu?.showAsAnchorRightBottom(tvChangeCurrency,0,30)

            }
        }
    }

    private fun addBeneficiaryDialog() {
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
                                activity?.let {
                                    setIntentResult()
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
            activity?.let {
                setIntentResult()
            }
        }
    }

    private fun setIntentResult() {
        val intent = Intent()
        intent.putExtra("beneficiary_change", true)
        activity?.setResult(Activity.RESULT_OK, intent)
        activity?.finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clickEvent.removeObservers(this)
    }

    override fun onBackPressed(): Boolean {
        if(currencyPopMenu?.isShowing!!)
        {
            currencyPopMenu?.dismiss()
            return true
        }
        return false
    }

    private fun getBindings(): FragmentAddBeneficiaryInternationalBankTransferBinding? {
        return viewDataBinding as? FragmentAddBeneficiaryInternationalBankTransferBinding
    }

}