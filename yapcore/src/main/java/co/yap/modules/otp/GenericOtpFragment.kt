package co.yap.modules.otp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R

class GenericOtpFragment : BaseBindingFragment<IGenericOtp.ViewModel>(), IGenericOtp.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_generic_otp

    override val viewModel: IGenericOtp.ViewModel
        get() = ViewModelProviders.of(this).get(GenericOtpViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            bundle.getParcelable<OtpDataModel>(OtpDataModel::class.java.name)?.let {
                viewModel.state.otpDataModel = it
            }
        }
        setObservers()
        viewModel.initializeData(requireContext())
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
        viewModel.errorEvent.observe(this, errorEvent)
    }

    val clickEvent = Observer<Int> {
        when (it) {
            R.id.btnDone -> {
                setResultData()
            }
        }
    }
    val errorEvent = Observer<Int> {
        otpErrorDialog()
    }
//TODO handle this case
    /*   override fun loadData() {
           *//* if (activity is BeneficiaryCashTransferActivity) {
             (activity as BeneficiaryCashTransferActivity).viewModel.state.toolBarTitle =
                 "Confirm transfer"
         }*//*
    }*/

    override fun setResultData() {
        val intent = Intent()
        viewModel.token.let {
            intent.putExtra("token", viewModel.token)
        }
        activity?.setResult(Activity.RESULT_OK, intent)
        activity?.finish()
    }

    private fun otpErrorDialog() {
        this.activity?.let {
            AlertDialog.Builder(it)
                .setMessage(viewModel.state.errorMessage)
                .setPositiveButton(
                    "Retry"
                ) { _, _ ->
                    viewModel.createOtp(context = requireContext())
                }

                .setNegativeButton(
                    getString(R.string.screen_profile_settings_logout_display_text_alert_cancel)
                ) { _, _ ->
                    activity?.finish()
                }.setCancelable(false)
                .show()
        }

    }
}