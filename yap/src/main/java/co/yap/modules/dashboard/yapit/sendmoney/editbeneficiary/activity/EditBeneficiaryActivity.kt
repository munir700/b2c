package co.yap.modules.dashboard.yapit.sendmoney.editbeneficiary.activity

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.yapit.sendmoney.editbeneficiary.interfaces.IEditBeneficiary
import co.yap.modules.dashboard.yapit.sendmoney.editbeneficiary.viewmodel.EditBeneficiaryViewModel
import co.yap.yapcore.BaseBindingActivity

class EditBeneficiaryActivity : BaseBindingActivity<IEditBeneficiary.ViewModel>(),
    IEditBeneficiary.View {

    companion object {
        fun newIntent(context: Context): Intent {

            return Intent(context, EditBeneficiaryActivity::class.java)
        }
    }
    override fun getBindingVariable()= BR.editBeneficiaryViewModel

    override fun getLayoutId() = R.layout.activity_edit_beneficiary


    override val viewModel: IEditBeneficiary.ViewModel
        get() = ViewModelProviders.of(this).get(EditBeneficiaryViewModel::class.java)


    override fun setObservers() {
    }
}