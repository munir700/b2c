package co.yap.modules.dashboard.more.profile.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.app.login.EncryptionUtils
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.ChangeCardPinFragment
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.SetNewCardPinFragment
import co.yap.modules.dashboard.more.activities.MoreActivity
import co.yap.modules.dashboard.more.profile.viewmodels.CurrentPasscodeViewModel
import co.yap.modules.setcardpin.interfaces.ISetCardPin
import co.yap.yapcore.helpers.SharedPreferenceManager

class CurrentPasscodeFragment :ChangeCardPinFragment(){
    private lateinit var sharedPreferenceManager: SharedPreferenceManager

    override val viewModel: ISetCardPin.ViewModel
        get() = ViewModelProviders.of(this).get(CurrentPasscodeViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (context as MoreActivity).goneToolbar()
        sharedPreferenceManager = SharedPreferenceManager(context as MoreActivity)


        viewModel.forgotPasscodeclickEvent.observe(this, Observer {
            var username = ""
            if (sharedPreferenceManager.getValueBoolien(SharedPreferenceManager.KEY_IS_USER_LOGGED_IN, false)) {
                username= EncryptionUtils.decrypt(
                    context as MoreActivity,
                    sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_USERNAME) as String
                ) as String
            }


            val action=CurrentPasscodeFragmentDirections.actionCurrentPasscodeFragmentToForgotPasscodeNavigation(username,viewModel.emailOtp,viewModel.mobileNumber)
            findNavController().navigate(action)
        })

    }
    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnAction -> {
                    findNavController().navigate(R.id.action_currentPasscodeFragment_to_updateNewPasscodeFragment)
                  /*  val action = ChangeCardPinFragmentDirections.actionChangeCardPinFragmentToSetNewCardPinFragment(viewModel.state.pincode)
                    findNavController().navigate(action)*/
                }
            }
        })

    }

    override fun onDestroy() {
        viewModel.forgotPasscodeclickEvent.removeObservers(this)
        super.onDestroy()
    }

}