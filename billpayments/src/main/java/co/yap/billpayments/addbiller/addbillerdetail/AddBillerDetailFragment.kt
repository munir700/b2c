package co.yap.billpayments.addbiller.addbillerdetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.addbiller.base.AddBillBaseFragment
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.translation.Strings
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.customAlertDialog
import co.yap.yapcore.helpers.extentions.afterTextChanged
import kotlinx.android.synthetic.main.fragment_biller_detail.*

class AddBillerDetailFragment : AddBillBaseFragment<IAddBillerDetail.ViewModel>(),
    IAddBillerDetail.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_biller_detail

    override val viewModel: AddBillerDetailViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        etNickName.afterTextChanged {
            viewModel.listener.onItemClick(etNickName, it, -1)
        }
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
        viewModel.addBillerError.observe(viewLifecycleOwner, Observer { errorCode ->
            errorCode?.let {
                requireContext().customAlertDialog(
                    topIconResId = R.drawable.ic_error_info_primary,
                    title = if (errorCode == viewModel.state.EVENT_BILLER_NOTAVAILABLE) getString(
                        Strings.screen_bill_payment_add_bill_service_error_dialog_title,
                        viewModel.parentViewModel?.selectedBillerCatalog?.billerName ?: ""
                    )
                    else getString(Strings.screen_bill_payment_add_bill_error_dialog_title),
                    message = if (errorCode == viewModel.state.EVENT_BILLER_NOTAVAILABLE) getString(
                        Strings.screen_bill_payment_add_bill_service_error_dialog_text
                    ) else getString(Strings.screen_bill_payment_add_bill_error_dialog_text),
                    positiveButton = if (errorCode == viewModel.state.EVENT_BILLER_NOTAVAILABLE) null else getString(
                        Strings.screen_bill_payment_add_bill_error_dialog_p_button_text
                    ),
                    negativeButton = if (errorCode == viewModel.state.EVENT_BILLER_NOTAVAILABLE) getString(
                        Strings.screen_bill_payment_add_bill_service_error_dialog_button_text
                    ) else getString(
                        Strings.screen_bill_payment_add_bill_error_dialog_n_button_text
                    ),
                    cancelable = false,
                    negativeCallback = {
                        if (errorCode != viewModel.state.EVENT_BILLER_NOTAVAILABLE) navigateBack()
                    }
                )
            }
        })
    }

    val clickObserver = Observer<Int> {
        when (it) {
            R.id.btnAddBiller -> {
                addBillerClick()
            }
        }
    }

    private fun addBillerClick() {
        val request =
            viewModel.getBillerInformationRequest(viewModel.billerDetailsResponse.value)
        viewModel.addBiller(request) {
            onBillAdded(it)
        }
    }

    private fun onBillAdded(viewBillModel: ViewBillModel?) {
        val title =
            getString(Strings.screen_bill_detail_success_dialog_title).format(viewModel.parentViewModel?.selectedBillerCatalog?.billerName)
        val description =
            getString(Strings.screen_bill_detail_success_dialog_button_description).format(
                viewModel.parentViewModel?.selectedBillerCatalog?.billerName
            )
        requireContext().customAlertDialog(
            topIconResId = R.drawable.ic_item_selected,
            title = title,
            message = description,
            positiveButton = getString(Strings.screen_bill_detail_success_dialog_button_text),
            negativeButton = getString(Strings.screen_bill_detail_success_dialog_button_text_do_it_later),
            positiveCallback = { setIntentResult(false, viewBillModel) },
            negativeCallback = { setIntentResult(true, viewBillModel) }
        )
    }

    private fun setIntentResult(
        isSkip: Boolean,
        viewBillModel: ViewBillModel?
    ) {
        val intent = Intent()
        intent.putExtra(ExtraKeys.IS_SKIP_PAY_BILL.name, isSkip)
        if (!isSkip) intent.putExtra(ExtraKeys.SELECTED_BILL.name, viewBillModel)
        requireActivity().setResult(Activity.RESULT_OK, intent)
        requireActivity().finish()
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
        viewModel.addBillerError.removeObservers(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun onStart() {
        super.onStart()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    }
}
