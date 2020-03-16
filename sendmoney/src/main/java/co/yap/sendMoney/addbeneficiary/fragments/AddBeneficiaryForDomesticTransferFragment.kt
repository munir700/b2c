package co.yap.sendMoney.addbeneficiary.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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
import co.yap.translation.Translator
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.MyUserManager

class AddBeneficiaryForDomesticTransferFragment :
    SendMoneyBaseFragment<IAddBeneficiary.ViewModel>(),
    IAddBeneficiary.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_add_beneficiary_domestic_transfer
    override val viewModel: AddBeneficiaryViewModel
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
            onConfirmClick(viewModel.state.otpType)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    private fun onConfirmClick(optType: String?) {
        optType?.let {
            val action =
                AddBeneficiaryForDomesticTransferFragmentDirections.actionAddBeneficiaryForDomesticTransferFragmentToGenericOtpFragment4(
                    "",
                    false,
                    MyUserManager.user?.currentCustomer?.getFormattedPhoneNumber(requireContext())
                        ?: "",
                    optType
                )
            findNavController().navigate(action)
            //findNavController().navigate(R.id.action_addBeneficiaryForDomesticTransferFragment_to_genericOtpFragment4)
        } ?: showToast("Invalid otp action")
    }

    private fun setIntentResult() {
        val intent = Intent()
        intent.putExtra(Constants.BENEFICIARY_CHANGE, true)
        activity?.setResult(Activity.RESULT_OK, intent)
        activity?.finish()
    }

    private fun checkOtpSuccessFlow() {
        viewModel.addDomesticBeneficiary(viewModel.parentViewModel?.beneficiary?.value)
    }

}
