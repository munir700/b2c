package co.yap.modules.dashboard.more.profile.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.ConfirmNewCardPinFragment
import co.yap.modules.dashboard.more.profile.viewmodels.UpdateConfirmPasscodeViewModel
import co.yap.modules.setcardpin.interfaces.ISetCardPin
import kotlinx.android.synthetic.main.activity_create_passcode.*

class UpdateConfirmPasscodeFragment : ConfirmNewCardPinFragment() {
    val args:UpdateConfirmPasscodeFragmentArgs by navArgs()

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
                }
            }
        })
        viewModel.forgotPasscodeclickEvent.observe(this, Observer {
            findNavController().navigate(R.id.action_updateConfirmPasscodeFragment_to_forgot_passcode_navigation)
        })
        viewModel.errorEvent.observe(this, Observer {
            dialer.startAnimationDigits()
        })
    }
    override fun loadData() {
        viewModel.state.newPin=args.newPinCode

    }
}