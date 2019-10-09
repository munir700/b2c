package co.yap.modules.dashboard.more.profile.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.ChangeCardPinFragment
import co.yap.modules.dashboard.cards.paymentcarddetail.fragments.SetNewCardPinFragment
import co.yap.modules.dashboard.more.activities.MoreActivity
import co.yap.modules.dashboard.more.profile.viewmodels.CurrentPasscodeViewModel
import co.yap.modules.setcardpin.interfaces.ISetCardPin

class CurrentPasscodeFragment :ChangeCardPinFragment(){
    override val viewModel: ISetCardPin.ViewModel
        get() = ViewModelProviders.of(this).get(CurrentPasscodeViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (context as MoreActivity).goneToolbar()
        viewModel.forgotPasscodeclickEvent.observe(this, Observer {
            findNavController().navigate(R.id.action_currentPasscodeFragment_to_forgot_passcode_navigation)
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