package co.yap.modules.dashboard.more.profile.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.ConfirmNewCardPinFragment
import co.yap.modules.dashboard.more.main.activities.MoreActivity
import co.yap.modules.dashboard.more.profile.viewmodels.UpdateConfirmPasscodeViewModel
import co.yap.modules.setcardpin.interfaces.ISetCardPin
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.Constants.KEY_IS_USER_LOGGED_IN
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.toast
import kotlinx.android.synthetic.main.activity_create_passcode.*

class UpdateConfirmPasscodeFragment : ConfirmNewCardPinFragment() {
    val args: UpdateConfirmPasscodeFragmentArgs by navArgs()
    private lateinit var sharedPreferenceManager: SharedPreferenceManager

    override val viewModel: ISetCardPin.ViewModel
        get() = ViewModelProviders.of(this).get(UpdateConfirmPasscodeViewModel::class.java)


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
                    proceedNext(it)
                } ?: toast("Invalid username")
            }
        })
    }

    private fun proceedNext(username: String) {
        val action =
            UpdateConfirmPasscodeFragmentDirections.actionUpdateConfirmPasscodeFragmentToForgotPasscodeNavigation(
                username,
                !Utils.isUsernameNumeric(username),
                viewModel.mobileNumber, Constants.FORGOT_PASSCODE_FROM_CHANGE_PASSCODE
            )
        findNavController().navigate(action)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadData()
        if (activity is MoreActivity) {
            (activity as MoreActivity).viewModel.preventTakeDeviceScreenShot.value = true
        }
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnAction -> {
                    sharedPreferenceManager.savePassCodeWithEncryption(viewModel.state.pincode)
                    val action =
                        UpdateConfirmPasscodeFragmentDirections.actionUpdateConfirmPasscodeFragmentToSuccessFragment(
                            "Your passcode has been changed \n succesfully",
                            "",
                            Constants.CHANGE_PASSCODE
                        )
                    findNavController().navigate(action)
                }
            }
        })
        viewModel.errorEvent.observe(this, Observer {
            dialer.startAnimation()
        })
    }

    override fun loadData() {
        dialer.updateDialerLength(6)
        viewModel.state.newPin = args.newPinCode

    }
}