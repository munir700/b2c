package co.yap.modules.dashboard.more.profile.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.app.login.EncryptionUtils
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.SetNewCardPinFragment
import co.yap.modules.dashboard.more.main.activities.MoreActivity
import co.yap.modules.dashboard.more.profile.viewmodels.UpdateNewPasscodeViewModel
import co.yap.modules.setcardpin.interfaces.ISetCardPin
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.SharedPreferenceManager

class UpdateNewPasscodeFragment:SetNewCardPinFragment() {
    private lateinit var sharedPreferenceManager: SharedPreferenceManager
    override val viewModel: ISetCardPin.ViewModel
        get() = ViewModelProviders.of(this).get(UpdateNewPasscodeViewModel::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferenceManager = SharedPreferenceManager(requireContext())

        viewModel.forgotPasscodeclickEvent.observe(this, Observer {
            var username = ""
            if (sharedPreferenceManager.getValueBoolien(SharedPreferenceManager.KEY_IS_USER_LOGGED_IN, false)) {
                username= EncryptionUtils.decrypt(
                    context as MoreActivity,
                    sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_USERNAME) as String
                ) as String
            }
            val action=UpdateNewPasscodeFragmentDirections.actionUpdateNewPasscodeFragmentToForgotPasscodeNavigation(username,viewModel.emailOtp,viewModel.mobileNumber,
                Constants.FORGOT_PASSCODE_FROM_CHANGE_PASSCODE)
            findNavController().navigate(action)
        })
    }


    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnAction -> {
                    val action=UpdateNewPasscodeFragmentDirections.actionUpdateNewPasscodeFragmentToUpdateConfirmPasscodeFragment(newPinCode = viewModel.state.pincode)
                    findNavController().navigate(action)
                }
            }
        })
    }

}