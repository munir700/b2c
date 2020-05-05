package co.yap.modules.dashboard.more.profile.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.SetNewCardPinFragment
import co.yap.modules.dashboard.more.main.activities.MoreActivity
import co.yap.modules.dashboard.more.profile.viewmodels.UpdateNewPasscodeViewModel
import co.yap.modules.setcardpin.fragments.SetCardPinFragment
import co.yap.modules.setcardpin.interfaces.ISetCardPin
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.Constants.KEY_IS_USER_LOGGED_IN
import co.yap.yapcore.databinding.FragmentSetCardPinBinding
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.toast

class UpdateNewPasscodeFragment : SetCardPinFragment() {
    private lateinit var sharedPreferenceManager: SharedPreferenceManager
    override val viewModel: ISetCardPin.ViewModel
        get() = ViewModelProviders.of(this).get(UpdateNewPasscodeViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBindingsUpdate().dialer.updateDialerLength(6)
        if (activity is MoreActivity) {
            (activity as MoreActivity).viewModel.preventTakeDeviceScreenShot.value = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferenceManager = SharedPreferenceManager(requireContext())

        viewModel.forgotPasscodeclickEvent.observe(this, Observer {
            if (sharedPreferenceManager.getValueBoolien(
                    KEY_IS_USER_LOGGED_IN,
                    false
                )
            ) {
                sharedPreferenceManager.getDecryptedUserName()?.let {
                    proceed(it)
                } ?: toast("Invalid username")
            }
        })
    }

    private fun proceed(username: String) {
        val action =
            UpdateNewPasscodeFragmentDirections.actionUpdateNewPasscodeFragmentToForgotPasscodeNavigation(
                username, Utils.isUsernameNumeric(username), viewModel.mobileNumber,
                Constants.FORGOT_PASSCODE_FROM_CHANGE_PASSCODE
            )
        findNavController().navigate(action)
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnAction -> {
                    val action =
                        UpdateNewPasscodeFragmentDirections.actionUpdateNewPasscodeFragmentToUpdateConfirmPasscodeFragment(
                            newPinCode = viewModel.state.pincode
                        )
                    findNavController().navigate(action)
                }
            }
        })
    }

    fun getBindingsUpdate(): FragmentSetCardPinBinding {
        return viewDataBinding as FragmentSetCardPinBinding
    }
}