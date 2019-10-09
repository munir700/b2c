package co.yap.modules.dashboard.more.profile.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.ConfirmNewCardPinFragment
import co.yap.modules.dashboard.more.activities.MoreActivity
import co.yap.modules.dashboard.more.profile.viewmodels.UpdateConfirmPasscodeViewModel
import co.yap.modules.setcardpin.interfaces.ISetCardPin

class UpdateConfirmPasscodeFragment : ConfirmNewCardPinFragment() {

    override val viewModel: ISetCardPin.ViewModel
        get() = ViewModelProviders.of(this).get(UpdateConfirmPasscodeViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadData()
    }
    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnAction -> {
                    val action=UpdateConfirmPasscodeFragmentDirections.actionUpdateConfirmPasscodeFragmentToSuccessFragment2("Your passcode has been changed \n succesfully","","CHANGE_PASSCODE")
                    findNavController().navigate(action)
//                    findNavController().navigate(R.id.action_confirmNewCardPinFragment_to_changePinSuccessFragment)
                }
            }
        })
        viewModel.forgotPasscodeclickEvent.observe(this, Observer {
            findNavController().navigate(R.id.action_updateConfirmPasscodeFragment_to_forgot_passcode_navigation)
        })
       /* viewModel.errorEvent.observe(this, Observer {
            dialer.startAnimationDigits()
        })*/
    }
    override fun loadData() {
        /*viewModel.state.oldPin = args.oldPinCode
        viewModel.state.newPin = args.newPinCode*/
      //  viewModel.state.cardSerialNumber = (activity as ChangeCardPinActivity).cardSerialNumber

    }
}