package co.yap.modules.dashboard.yapit.sendmoney.editbeneficiary.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.yapit.sendmoney.editbeneficiary.interfaces.IEditBeneficiary
import co.yap.modules.dashboard.yapit.sendmoney.editbeneficiary.viewmodel.EditBeneficiaryViewModel
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.widgets.MaskTextWatcher
import co.yap.widgets.popmenu.PopupMenu
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.Constants.EXTRA
import co.yap.yapcore.constants.Constants.OVERVIEW_BENEFICIARY
import co.yap.yapcore.helpers.Utils
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
                bundle?.let {
                    viewModel.state.needOverView = it.getBoolean(OVERVIEW_BENEFICIARY, false)
                    viewModel.state.beneficiary = bundle.getParcelable(Beneficiary::class.java.name)
//                    viewModel.state.beneficiary?.accountNo?.length?.let {
//                        if (it >= 22)
//                            viewModel.state.beneficiary?.accountNo =
//                                Utils.formateIbanString(viewModel.state.beneficiary?.accountNo)
//                    }
                }
            }
        }

        setObservers()
        currencyPopMenu = getCurrencyPopMenu(this, mutableListOf(), null, null)
        //formatIbanLogic()

       val maskTextWatcher = MaskTextWatcher(etAccountNumber, "#### #### #### #### #### #### ####")
        etAccountNumber.addTextChangedListener(maskTextWatcher)
        etAccountNumberRMT.addTextChangedListener(maskTextWatcher)
        //etAccountNumber
    }

    private fun formatIbanLogic() {
        if (viewModel.state.beneficiary?.accountNo?.length ?: 0 >= 22) {
            etAccountNumber.addTextChangedListener(object : TextWatcher {

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
    }


    override fun setObservers() {
        viewModel.clickEvent?.observe(this, Observer {
            when (it) {
                R.id.tbBtnBack -> {
                    val intent = Intent()
                    setResult(Activity.RESULT_CANCELED, intent)
                    finish()
                }
                R.id.confirmButton ->
                    viewModel.requestUpdateBeneficiary()
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
}