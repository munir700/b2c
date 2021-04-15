package co.yap.billpayments.addbiller.addbillerdetail

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.billpayments.BR
import co.yap.billpayments.R
import co.yap.billpayments.addbiller.base.AddBillBaseFragment
import co.yap.translation.Strings
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.helpers.successDialog

class AddBillerDetailFragment : AddBillBaseFragment<IAddBillerDetail.ViewModel>(),
    IAddBillerDetail.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_biller_detail

    val viewModelAdd: AddBillerDetailViewModel
        get() = ViewModelProviders.of(this).get(AddBillerDetailViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun setObservers() {
        viewModelAdd.clickEvent.observe(this, clickObserver)
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
            viewModelAdd.getBillerInformationRequest(viewModelAdd.billerDetailsResponse.value)
        viewModelAdd.addBiller(request) {
            val title =
                getString(Strings.screen_bill_detail_success_dialog_title).format(viewModelAdd.parentViewModel?.selectedBillerCatalog?.billerName)
            val description =
                getString(Strings.screen_bill_detail_success_dialog_button_description).format(
                    viewModelAdd.parentViewModel?.selectedBillerCatalog?.billerName
                )
            requireContext().successDialog(
                topIcon = R.drawable.ic_tick,
                title = title,
                message = description,
                buttonText = getString(Strings.screen_bill_detail_success_dialog_button_text),
                bottomText = getString(Strings.screen_bill_detail_success_dialog_button_text_do_it_later)
            ) {
                setIntentResult()
            }
        }
    }


    private fun setIntentResult() {
        val intent = Intent()
        intent.putExtra("IS_SKIP", true)
        requireActivity().setResult(RequestCodes.REQUEST_CODE_CREATE_PASSCODE, intent)
        requireActivity().finish()
    }

    override fun removeObservers() {
        viewModelAdd.clickEvent.removeObservers(this)
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
