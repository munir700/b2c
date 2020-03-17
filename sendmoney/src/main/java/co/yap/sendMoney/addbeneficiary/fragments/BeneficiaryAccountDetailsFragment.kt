package co.yap.sendMoney.addbeneficiary.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.sendMoney.R
import co.yap.sendMoney.activities.BeneficiaryCashTransferActivity
import co.yap.sendMoney.addbeneficiary.interfaces.IBeneficiaryAccountDetails
import co.yap.sendMoney.addbeneficiary.viewmodels.BeneficiaryAccountDetailsViewModel
import co.yap.sendMoney.editbeneficiary.activity.EditBeneficiaryActivity
import co.yap.sendMoney.fragments.SendMoneyBaseFragment
import co.yap.modules.otp.GenericOtpFragment
import co.yap.modules.otp.OtpDataModel
import co.yap.modules.otp.OtpToolBarData
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.translation.Translator
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.enums.OTPActions
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.ExtraType
import co.yap.yapcore.helpers.extentions.getValue
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.startFragmentForResult
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.MyUserManager

class BeneficiaryAccountDetailsFragment :
    SendMoneyBaseFragment<IBeneficiaryAccountDetails.ViewModel>(),
    IBeneficiaryAccountDetails.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_beneficiary_account_detail

    override val viewModel: BeneficiaryAccountDetailsViewModel
        get() = ViewModelProviders.of(this).get(BeneficiaryAccountDetailsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObservers()
    }

    private fun addObservers() {
        viewModel.clickEvent.observe(this, observer)
        viewModel.success.observe(this, Observer {
            if (it) {
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
                                        setIntentResult()
                                    } else {
                                        setIntentResult()
                                    }
                                }
                            }
                        })
                }
            } else {
                // error while creating beneficiary
            }
        })
        viewModel.isBeneficiaryValid.observe(this, Observer { it ->
            if (it) {
                var action = ""
                viewModel.parentViewModel?.beneficiary?.value?.beneficiaryType?.let {
                    if (it.isNotEmpty())
                        action = when (SendMoneyBeneficiaryType.valueOf(it)) {
                            SendMoneyBeneficiaryType.SWIFT -> OTPActions.SWIFT_BENEFICIARY.name
                            SendMoneyBeneficiaryType.RMT -> OTPActions.RMT_BENEFICIARY.name
                            else -> " "
                        }
                }

                startFragmentForResult<GenericOtpFragment>(
                    GenericOtpFragment::class.java.name,
                    bundleOf(
                        OtpDataModel::class.java.name to OtpDataModel(
                            otpAction = action,
                            mobileNumber = MyUserManager.user?.currentCustomer?.getCompletePhone(),
                            username = MyUserManager.user?.currentCustomer?.getFullName(),
                            emailOtp = false,
                            toolBarData = OtpToolBarData()
                        )
                    ),true
                ) { resultCode, data ->
                    if (resultCode == Activity.RESULT_OK) {
                        viewModel.createBeneficiaryRequest()
                    }
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.otpCreateObserver.observe(this, otpCreateObserver)
        viewModel.parentViewModel?.otpSuccess?.observe(
            this,
            otpSuccessObserver
        )
    }

    private fun openEditBeneficiary(beneficiary: Beneficiary?) {
        beneficiary?.let {
            val bundle = Bundle()
            bundle.putBoolean(Constants.OVERVIEW_BENEFICIARY, true)
            bundle.putString(Constants.IS_IBAN_NEEDED, "Yes")
            bundle.putParcelable(Beneficiary::class.java.name, beneficiary)
            launchActivity<EditBeneficiaryActivity>(RequestCodes.REQUEST_NOTIFY_BENEFICIARY_LIST) {
                putExtra(Constants.EXTRA, bundle)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val beneficiary =
                data?.getValue(
                    Beneficiary::class.java.name,
                    ExtraType.PARCEABLE.name
                ) as? Beneficiary
            beneficiary?.let {
                viewModel.parentViewModel?.beneficiary?.value =
                    it // to update the existing beneficary
                viewModel.validateBeneficiaryDetails(it)
            }
        }
    }

    private fun startMoneyTransfer() {
        viewModel.beneficiary?.let { beneficiary ->
            requireActivity().launchActivity<BeneficiaryCashTransferActivity>(requestCode = RequestCodes.REQUEST_TRANSFER_MONEY) {
                putExtra(Constants.BENEFICIARY, beneficiary)
                putExtra(Constants.POSITION, 0)
                putExtra(Constants.IS_NEW_BENEFICIARY, true)
            }
        }
    }

    private fun setIntentResult() {
        activity?.let { it ->
            val intent = Intent()
            intent.putExtra(Constants.BENEFICIARY_CHANGE, true)
            it.setResult(Activity.RESULT_OK, intent)
            it.finish()
        }
    }

    private val observer = Observer<Int> {
        when (it) {
            R.id.confirmButton -> openEditBeneficiary(viewModel.parentViewModel?.beneficiary?.value)
        }
    }

    private val otpSuccessObserver = Observer<Boolean> {
        if (it) {
            viewModel.parentViewModel?.beneficiary?.value?.accountNo =
                viewModel.state.accountIban.replace(" ", "")
            viewModel.createBeneficiaryRequest()
            viewModel.parentViewModel?.otpSuccess?.value = false
        }
    }

    private val otpCreateObserver = Observer<Boolean> {
        if (it) {
            viewModel.parentViewModel?.beneficiary?.value?.beneficiaryType?.let { it ->
                if (it.isNotEmpty())
                    when (SendMoneyBeneficiaryType.valueOf(it)) {
                        SendMoneyBeneficiaryType.SWIFT -> {
                            moveToOptScreen(Constants.SWIFT_BENEFICIARY)
                        }
                        SendMoneyBeneficiaryType.RMT -> {
                            moveToOptScreen(Constants.RMT_BENEFICIARY)
                        }
                        else -> {
                        }
                    }
            }
        }
    }

    private fun moveToOptScreen(otpAction: String) {
        val action =
            BeneficiaryAccountDetailsFragmentDirections.actionBeneficiaryAccountDetailsFragmentToGenericOtpFragment4(
                "",
                false,
                MyUserManager.user?.currentCustomer?.getFormattedPhoneNumber(requireContext())
                    ?: "",
                otpAction
            )
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clickEvent.removeObservers(this)
        viewModel.otpCreateObserver.removeObservers(this)
        viewModel.parentViewModel?.otpSuccess?.removeObserver(otpSuccessObserver)
    }
}