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
import co.yap.countryutils.country.utils.Currency
import co.yap.modules.dashboard.yapit.sendmoney.activities.BeneficiaryCashTransferActivity
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IAddBeneficiary
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels.AddBeneficiaryViewModel
import co.yap.modules.dashboard.yapit.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.modules.others.helper.getCurrencyPopMenu
import co.yap.translation.Translator
import co.yap.widgets.popmenu.OnMenuItemClickListener
import co.yap.widgets.popmenu.PopupMenu
import co.yap.widgets.popmenu.PopupMenuItem
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
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

    override val viewModel: AddBeneficiaryViewModel
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
        val currencies = viewModel.parentViewModel?.selectedCountry?.value?.supportedCurrencies
        currencyPopMenu =
            requireContext().getCurrencyPopMenu(
                this,
                getCurrencyList(currencies),
                popupItemClickListener,
                null
            )
        // setting the default select currecny on selected state
        val index: Int =
            currencies?.indexOf(viewModel.parentViewModel?.selectedCountry?.value?.getCurrency())
                ?: 0
        currencyPopMenu?.selectedPosition = index
    }

    private fun getCurrencyList(currencies: List<Currency>?): ArrayList<PopupMenuItem> {
        val popMenuCurrenciesList = ArrayList<PopupMenuItem>()
        for (currency in currencies!!.iterator()) {
            popMenuCurrenciesList.add(PopupMenuItem(currency.name))
        }

        return popMenuCurrenciesList
    }


    private val observer = Observer<Int> {
        when (it) {
            R.id.confirmButton -> {
                if (viewModel.state.transferType != "Cash Pickup")
                    findNavController().navigate(R.id.action_addBeneficiaryFragment_to_addBankDetailsFragment)
            }
            R.id.emptyCardLayout -> {

            }
            R.id.tvChangeCurrency -> {
                currencyPopMenu?.showAsAnchorRightBottom(tvChangeCurrency, 0, 30)

            }
        }
    }

    private val popupItemClickListener =
        OnMenuItemClickListener<PopupMenuItem?> { position, _ ->
            val currencyItem =
                viewModel.parentViewModel?.selectedCountry?.value?.supportedCurrencies?.get(position)
            if (currencyItem != null) {
                currencyPopMenu?.selectedPosition = position
                viewModel.state.currency = currencyItem.code ?: ""
                viewModel.parentViewModel?.selectedCountry?.value?.setCurrency(currencyItem)
                viewModel.parentViewModel?.selectedCountry?.value?.let { country ->
                    if (country.isoCountryCode2Digit == "AE") {
                        viewModel.parentViewModel?.beneficiary?.value?.beneficiaryType =
                            SendMoneyBeneficiaryType.DOMESTIC.name
                    } else {
                        country.getCurrency()?.cashPickUp?.let { it ->
                            if (!it) {
                                country.getCurrency()?.rmtCountry?.let { isRmt ->
                                    if (isRmt) {
                                        viewModel.parentViewModel?.beneficiary?.value?.beneficiaryType =
                                            SendMoneyBeneficiaryType.RMT.name
                                    } else {
                                        viewModel.parentViewModel?.beneficiary?.value?.beneficiaryType =
                                            SendMoneyBeneficiaryType.SWIFT.name
                                    }
                                }
                            } else {
                                viewModel.parentViewModel?.beneficiary?.value?.beneficiaryType =
                                    SendMoneyBeneficiaryType.CASHPAYOUT.name
                            }
                        }
                    }
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
            startActivity(
                BeneficiaryCashTransferActivity.newIntent(
                    requireContext(),
                    beneficiary
                )
            )
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
        if (currencyPopMenu?.isShowing!!) {
            currencyPopMenu?.dismiss()
            return true
        }
        return false
    }

//    private fun getBindings(): FragmentAddBeneficiaryInternationalBankTransferBinding? {
//        return viewDataBinding as? FragmentAddBeneficiaryInternationalBankTransferBinding
//    }

}