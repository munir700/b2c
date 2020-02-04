package co.yap.modules.dashboard.yapit.sendmoney.editbeneficiary.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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
                }
            }
        }

        setObservers()
        currencyPopMenu = getCurrencyPopMenu(this, mutableListOf(), null, null)
        maskIban()
    }

    private fun maskIban() {
        etAccountNumber?.let {
            val maskTextWatcher =
                MaskTextWatcher(it, "#### #### #### #### #### #### ####")
            it.addTextChangedListener(maskTextWatcher)
            etAccountNumberRMT?.let { watcher -> watcher.addTextChangedListener(maskTextWatcher) }
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