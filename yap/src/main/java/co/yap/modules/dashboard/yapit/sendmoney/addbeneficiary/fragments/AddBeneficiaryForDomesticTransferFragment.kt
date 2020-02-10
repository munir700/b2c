package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.yapit.sendmoney.activities.BeneficiaryCashTransferActivity
import co.yap.modules.dashboard.yapit.sendmoney.activities.SendMoneyHomeActivity
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IAddBeneficiary
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels.AddBeneficiaryViewModel
import co.yap.modules.dashboard.yapit.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.translation.Translator
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.fragment_add_beneficiary_domestic_transfer.*

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
        viewModel.otpCreateObserver.observe(this, otpCreateObserver)
        if (activity is SendMoneyHomeActivity) {
            (activity as SendMoneyHomeActivity).viewModel.otpSuccess.observe(
                this,
                otpSuccessObserver
            )
        }
        viewModel.addBeneficiarySuccess.observe(this, Observer {
            if (it) {
                addBeneficiarySuccessDialog()
            }
        })
    }

    private val otpSuccessObserver = Observer<Boolean> {
        if (it) {
            checkOtpSuccessFlow()
            (activity as SendMoneyHomeActivity).viewModel.otpSuccess.value = false
        }
    }

    private val otpCreateObserver = Observer<Boolean> {
        if (it) {
            onConfirmClick()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etIban.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                var i = 4
                while (i < s.length) {
                    if (s.toString()[i] != ' ') {
                        s.insert(i, " ")
                    }
                    i += 5
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {

            }
        })

        etConfirmIban.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                var i = 4
                while (i < s.length) {
                    if (s.toString()[i] != ' ') {
                        s.insert(i, " ")
                    }
                    i += 5
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {

            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clickEvent.removeObservers(this)
        viewModel.otpCreateObserver.removeObservers(this)
        if (activity is SendMoneyHomeActivity) {
            (activity as SendMoneyHomeActivity).viewModel.otpSuccess.removeObserver(
                otpSuccessObserver
            )
        }
    }

    val observer = Observer<Int> {
        when (it) {
            R.id.confirmButton -> {
                viewModel.createOtp(Constants.DOMESTIC_BENEFICIARY)
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
                                activity?.let {
                                    setIntentResult()
                                }
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
        viewModel.beneficiary?.let {
            launchActivity<BeneficiaryCashTransferActivity>(requestCode = RequestCodes.REQUEST_TRANSFER_MONEY) {
                putExtra(Constants.BENEFICIARY, it)
                putExtra(Constants.POSITION, 0)
                putExtra(Constants.IS_NEW_BENEFICIARY, true)
            }
        }
    }

    private fun onConfirmClick() {
        val action =
            AddBeneficiaryForDomesticTransferFragmentDirections.actionAddBeneficiaryForDomesticTransferFragmentToGenericOtpFragment4(
                "",
                false,
                MyUserManager.user?.currentCustomer?.getFormattedPhoneNumber(requireContext())
                    ?: "",
                Constants.DOMESTIC_BENEFICIARY
            )
        findNavController().navigate(action)
        //findNavController().navigate(R.id.action_addBeneficiaryForDomesticTransferFragment_to_genericOtpFragment4)
    }

    private fun setIntentResult() {
        val intent = Intent()
        intent.putExtra(Constants.BENEFICIARY_CHANGE, true)
        activity?.setResult(Activity.RESULT_OK, intent)
        activity?.finish()
    }

    private fun checkOtpSuccessFlow() {
        val beneficiary = Beneficiary()
        beneficiary.beneficiaryType = SendMoneyBeneficiaryType.DOMESTIC.name
        beneficiary.title = viewModel.state.nickName
        beneficiary.firstName = viewModel.state.firstName
        beneficiary.lastName = viewModel.state.lastName
        beneficiary.country = "AE"
        beneficiary.accountNo = viewModel.state.iban.replace(" ", "")
        viewModel.addDomesticBeneficiary(beneficiary)
    }

}