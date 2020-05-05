package co.yap.modules.dashboard.more.profile.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.ChangeCardPinFragment
import co.yap.modules.dashboard.more.main.activities.MoreActivity
import co.yap.modules.dashboard.more.profile.viewmodels.CurrentPasscodeViewModel
import co.yap.modules.setcardpin.interfaces.ISetCardPin
import co.yap.modules.setcardpin.pinflow.IPin
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.Constants.KEY_IS_USER_LOGGED_IN
import co.yap.yapcore.databinding.FragmentSetCardPinBinding
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.toast
import kotlinx.android.synthetic.main.activity_create_passcode.*

open class CurrentPasscodeFragment : ChangeCardPinFragment() {
    private lateinit var sharedPreferenceManager: SharedPreferenceManager

    override val viewModel: IPin.ViewModel
        get() = ViewModelProviders.of(this).get(CurrentPasscodeViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (context is MoreActivity)
            (context as MoreActivity).goneToolbar()
        sharedPreferenceManager = SharedPreferenceManager(requireContext())
        viewModel.forgotPasscodeclickEvent.observe(this, Observer {

            if (sharedPreferenceManager.getValueBoolien(
                   KEY_IS_USER_LOGGED_IN,
                    false
                )
            ) {
                sharedPreferenceManager.getDecryptedUserName()?.let {
                    val action =
                        CurrentPasscodeFragmentDirections.actionCurrentPasscodeFragmentToForgotPasscodeNavigation(
                            it,
                            !Utils.isUsernameNumeric(it),
                            viewModel.mobileNumber,
                            Constants.FORGOT_PASSCODE_FROM_CHANGE_PASSCODE
                        )
                    findNavController().navigate(action)
                } ?: toast("Invalid username found")
            }

        })

        viewModel.errorEvent.observe(this, Observer {
            dialer.startAnimation()
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBinding().dialer.updateDialerLength(6)
        getBinding().dialer.upDatedDialerPad(viewModel.state.pincode)
        if (activity is MoreActivity) {
            (activity as MoreActivity).viewModel.preventTakeDeviceScreenShot.value = true
        }
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnAction -> {
                    findNavController().navigate(R.id.action_currentPasscodeFragment_to_updateNewPasscodeFragment)
                }
            }
        })

    }

    override fun onDestroy() {
        viewModel.forgotPasscodeclickEvent.removeObservers(this)
        super.onDestroy()
    }

    private fun getBinding(): FragmentSetCardPinBinding {
        return viewDataBinding as FragmentSetCardPinBinding
    }
}