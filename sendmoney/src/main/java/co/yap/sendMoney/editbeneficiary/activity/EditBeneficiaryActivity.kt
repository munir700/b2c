package co.yap.sendMoney.editbeneficiary.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.sendMoney.editbeneficiary.interfaces.IEditBeneficiary
import co.yap.sendMoney.editbeneficiary.viewmodel.EditBeneficiaryViewModel
import co.yap.modules.otp.GenericOtpFragment
import co.yap.modules.otp.OtpDataModel
import co.yap.modules.otp.OtpToolBarData
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.sendmoney.BR
import co.yap.sendmoney.R
import co.yap.sendmoney.databinding.ActivityEditBeneficiaryBinding
import co.yap.translation.Translator
import co.yap.widgets.popmenu.PopupMenu
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.Constants.EXTRA
import co.yap.yapcore.constants.Constants.IS_IBAN_NEEDED
import co.yap.yapcore.constants.Constants.OVERVIEW_BENEFICIARY
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.enums.OTPActions
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.getCurrencyPopMenu
import co.yap.yapcore.helpers.extentions.startFragmentForResult
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.activity_edit_beneficiary.*


class EditBeneficiaryActivity : BaseBindingActivity<IEditBeneficiary.ViewModel>(),
    IEditBeneficiary.View {


    override fun getBindingVariable() = BR.editBeneficiaryViewModel

    override fun getLayoutId() = R.layout.activity_edit_beneficiary
    private var currencyPopMenu: PopupMenu? = null


    override val viewModel: IEditBeneficiary.ViewModel
        get() = ViewModelProviders.of(this).get(EditBeneficiaryViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.let {
            if (it.hasExtra(EXTRA)) {
                val bundle = it.getBundleExtra(EXTRA)
                bundle?.let { bundleData ->
                    viewModel.state.needOverView =
                        bundleData.getBoolean(OVERVIEW_BENEFICIARY, false)
                    updateAccountTitle(bundleData)
                    viewModel.state.beneficiary =
                        bundleData.getParcelable(Beneficiary::class.java.name)
                }
            }
        }

        setObservers()
        currencyPopMenu = getCurrencyPopMenu(this, mutableListOf(), null, null)
    }

    private fun updateAccountTitle(bundleData: Bundle) {
        when (bundleData.getString(IS_IBAN_NEEDED)) {
            "loadFromServer" -> {
                viewModel.requestCountryInfo()
                viewModel.state.showIban = false //binding needed
            }
            "Yes" -> {
                viewModel.state.needIban = true
                viewModel.state.showIban = true //binding needed
            }
        }
    }

    override fun setObservers() {
        viewModel.clickEvent?.observe(this, Observer {
            when (it) {
                R.id.tbBtnBack -> {
                    val intent = Intent()
                    setResult(Activity.RESULT_CANCELED, intent)
                    finish()
                }
                R.id.confirmButton -> {
                    if (viewModel.state.needOverView == true) {
                        viewModel.state.beneficiary?.let { beneficiary ->
                            viewModel.validateBeneficiaryDetails(beneficiary)
                        }
                    } else {
                        viewModel.requestUpdateBeneficiary()
                    }
                }
                R.id.tvChangeCurrency ->
                    currencyPopMenu?.showAsAnchorRightBottom(tvChangeCurrency)

            }
        })

        viewModel.onUpdateSuccess.observe(this, Observer {
            val intent = Intent()
            if (it) {
                intent.putExtra(Constants.BENEFICIARY_CHANGE, true)
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                intent.putExtra(Constants.BENEFICIARY_CHANGE, false)
                setResult(Activity.RESULT_CANCELED, intent)
            }

        })
        viewModel.isBeneficiaryValid.observe(this, isBeneficiaryValidObserver)
        viewModel.onBeneficiaryCreatedSuccess.observe(this, onBeneficiaryCreatedSuccessObserver)

    }

    private val isBeneficiaryValidObserver = Observer<Boolean> { isValid ->
        if (isValid) {
            var action = ""
            viewModel.state.beneficiary?.beneficiaryType?.let { type ->
                if (type.isNotBlank())
                    action = when (type) {
                        SendMoneyBeneficiaryType.SWIFT.type -> OTPActions.SWIFT_BENEFICIARY.name
                        SendMoneyBeneficiaryType.RMT.type -> OTPActions.RMT_BENEFICIARY.name
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
                ), true
            ) { resultCode, data ->
                if (resultCode == Activity.RESULT_OK) {
                    viewModel.createBeneficiaryRequest()
                }
            }
        }
    }

    private val onBeneficiaryCreatedSuccessObserver = Observer<Boolean> {
        if (it) {
            Utils.confirmationDialog(this,
                Translator.getString(
                    this,
                    R.string.screen_add_beneficiary_detail_display_text_alert_title
                ),
                Translator.getString(
                    this,
                    R.string.screen_add_beneficiary_detail_display_button_block_alert_description
                ), Translator.getString(
                    this,
                    R.string.screen_add_beneficiary_detail_display_button_block_alert_yes
                ), Translator.getString(
                    this,
                    R.string.screen_add_beneficiary_detail_display_button_block_alert_no
                ),
                object : OnItemClickListener {
                    override fun onItemClick(view: View, data: Any, pos: Int) {
                        if (data is Boolean) {
                            if (data) {
                                setIntentResult(true)
                            } else {
                                setIntentResult()
                            }
                        }
                    }
                })
        }
    }

    private fun setIntentResult(isMoneyTransfer: Boolean = false) {
        val intent = Intent()
        intent.putExtra(Constants.BENEFICIARY_CHANGE, true)
        intent.putExtra(Constants.IS_TRANSFER_MONEY, isMoneyTransfer)
        intent.putExtra(Beneficiary::class.java.name, viewModel.state.beneficiary)
        this.setResult(Activity.RESULT_OK, intent)
        this.finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RequestCodes.REQUEST_TRANSFER_MONEY -> {
                    val isTransferred = data?.getBooleanExtra(
                        Constants.MONEY_TRANSFERED,
                        false
                    )
                    if (isTransferred == true) {
                        setIntentResult()
                    } else {
                        setIntentResult()
                    }
                }
            }
        }
    }


    private fun getbinding(): ActivityEditBeneficiaryBinding {
        return viewDataBinding as ActivityEditBeneficiaryBinding
    }
}