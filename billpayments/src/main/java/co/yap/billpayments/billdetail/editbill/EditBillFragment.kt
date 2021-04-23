package co.yap.billpayments.billdetail.editbill

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.billdetail.base.BillDetailBaseFragment
import co.yap.translation.Strings
import co.yap.yapcore.helpers.showAlertDialogAndExitApp

class EditBillFragment : BillDetailBaseFragment<IEditBill.ViewModel>(),
    IEditBill.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_edit_bill

    override val viewModel: IEditBill.ViewModel
        get() = ViewModelProviders.of(this).get(EditBillViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
    }

    val clickObserver = Observer<Int> {
        when (it) {
            R.id.btnEditBill -> showPopUp()
        }
    }

    override fun showPopUp() {
        requireActivity().showAlertDialogAndExitApp(
            dialogTitle = getString(Strings.screen_edit_bill_dialog_title),
            message = getString(Strings.screen_edit_bill_dialog_description),
            leftButtonText = getString(Strings.common_button_confirm),
            titleVisibility = true,
            isTwoButton = true,
            closeActivity = false,
            callback = { showToast("Delete this bill") }
        )
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }
}
