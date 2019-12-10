package co.yap.modules.dashboard.yapit.sendmoney.editbeneficiary.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.yapit.sendmoney.editbeneficiary.interfaces.IEditBeneficiary
import co.yap.modules.dashboard.yapit.sendmoney.editbeneficiary.viewmodel.EditBeneficiaryViewModel
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.yapcore.BaseBindingActivity

class EditBeneficiaryActivity : BaseBindingActivity<IEditBeneficiary.ViewModel>(),
    IEditBeneficiary.View {

    companion object {
        const val Bundle_EXTRA = "bundle_extra"
        const val REQUEST_CODE = 101
        fun newIntent(context: Context): Intent {
            return Intent(context, EditBeneficiaryActivity::class.java)
        }
    }

    override fun getBindingVariable() = BR.editBeneficiaryViewModel

    override fun getLayoutId() = R.layout.activity_edit_beneficiary


    override val viewModel: IEditBeneficiary.ViewModel
        get() = ViewModelProviders.of(this).get(EditBeneficiaryViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.let {
            if (it.hasExtra(Bundle_EXTRA)) {
                val bundle = it.getBundleExtra(Bundle_EXTRA)
                bundle?.let { viewModel.state.beneficiary = bundle.getParcelable(Beneficiary::class.java.name) }
            }
        }


    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
    }

    override fun setObservers() {
    }
}