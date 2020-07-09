package co.yap.modules.setcardpin.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.modules.setcardpin.activities.SetCardPinWelcomeActivity
import co.yap.modules.setcardpin.interfaces.ISetCardPinSuccess
import co.yap.modules.setcardpin.viewmodels.SetCardPinSuccessViewModel
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants

class SetCardPinSuccessFragment : BaseBindingFragment<ISetCardPinSuccess.ViewModel>(),
    ISetCardPinSuccess.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_set_card_pin_success

    override val viewModel: ISetCardPinSuccess.ViewModel
        get() = ViewModelProviders.of(this).get(SetCardPinSuccessViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity is SetCardPinWelcomeActivity) {
            (activity as SetCardPinWelcomeActivity).preventTakeDeviceScreenShot.value = false
        }
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                /*    R.id.btnTopUp -> activity?.finish()*/
                R.id.btnTopUp -> {
                    setupActionsIntentForTopUp()
                    activity?.finish()
                }

                R.id.tvTopUpLater -> {
                    setupActionsIntent()
                    activity?.finish()
                }
            }
        })
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }

    override fun onBackPressed(): Boolean {
        return true
    }

    private fun setupActionsIntent() {
        val returnIntent = Intent()
        returnIntent.putExtra(Constants.isPinCreated, true)
        returnIntent.putExtra(Constants.IS_TOPUP_SKIP, true)
        activity?.setResult(Activity.RESULT_OK, returnIntent)
    }

    private fun setupActionsIntentForTopUp() {
        if (activity is SetCardPinWelcomeActivity) {
            (activity as SetCardPinWelcomeActivity).card?.cardSerialNumber?.let { serialNumber ->
                val returnIntent = Intent()
                returnIntent.putExtra(Constants.CARD_SERIAL_NUMBER, serialNumber)
                returnIntent.putExtra(Constants.isPinCreated, true)
                returnIntent.putExtra(Constants.IS_TOPUP_SKIP, false)
                activity?.setResult(Activity.RESULT_OK, returnIntent)
            }
        } else {
            //invalid flow so finish activity
            activity?.finish()
        }
    }
}