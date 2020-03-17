package co.yap.sendMoney.editbeneficiary.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.sendMoney.R
import co.yap.databinding.ActivityEditBeneficiaryBinding
import co.yap.sendMoney.editbeneficiary.interfaces.IEditBeneficiary
import co.yap.sendMoney.editbeneficiary.viewmodel.EditBeneficiaryViewModel
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.widgets.MaskTextWatcher
import co.yap.widgets.popmenu.PopupMenu
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.Constants.EXTRA
import co.yap.yapcore.constants.Constants.IS_IBAN_NEEDED
import co.yap.yapcore.constants.Constants.OVERVIEW_BENEFICIARY
import co.yap.yapcore.helpers.extentions.getCurrencyPopMenu
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
                    if (viewModel.state.needOverView!!) {
                        val intent = Intent()
                        intent.putExtra(Constants.BENEFICIARY_CHANGE, true)
                        intent.putExtra(
                            Beneficiary::class.java.name,
                            viewModel.state.beneficiary
                        )
                        setResult(Activity.RESULT_OK, intent)
                        finish()
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
    }

    private fun getbinding(): ActivityEditBeneficiaryBinding {
        return viewDataBinding as ActivityEditBeneficiaryBinding
    }
}