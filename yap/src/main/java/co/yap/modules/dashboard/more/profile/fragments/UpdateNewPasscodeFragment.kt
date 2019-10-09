package co.yap.modules.dashboard.more.profile.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.SetNewCardPinFragment
import co.yap.modules.dashboard.more.activities.MoreActivity
import co.yap.modules.dashboard.more.profile.viewmodels.UpdateNewPasscodeViewModel
import co.yap.modules.setcardpin.interfaces.ISetCardPin

class UpdateNewPasscodeFragment:SetNewCardPinFragment() {
    override val viewModel: ISetCardPin.ViewModel
        get() = ViewModelProviders.of(this).get(UpdateNewPasscodeViewModel::class.java)


    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnAction -> {
                    //oldPinCode = args.oldPinCode
//                    findNavController().navigate(R.id.action_updateNewPasscodeFragment_to_updateConfirmPasscodeFragment)

//                    val action=UpdateNewPasscodeFragmentDirections.actionUpdateNewPasscodeFragmentToUpdateConfirmPasscodeFragment(oldPinCode.toString(),viewModel.state.pincode)
                    val action=UpdateNewPasscodeFragmentDirections.actionUpdateNewPasscodeFragmentToUpdateConfirmPasscodeFragment("",viewModel.state.pincode)
                    findNavController().navigate(action)
                }
            }
        })
        viewModel.forgotPasscodeclickEvent.observe(this, Observer {
            findNavController().navigate(R.id.action_updateConfirmPasscodeFragment_to_forgot_passcode_navigation)
        })
    }

}