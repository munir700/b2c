package co.yap.modules.dashboard.cards.paymentcarddetail.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.viewmodels.SetNewPinViewModel
import co.yap.modules.setcardpin.fragments.SetCardPinFragment
import co.yap.modules.setcardpin.interfaces.ISetCardPin

class SetNewCardPinFragment : SetCardPinFragment() {
    private val args: SetNewCardPinFragmentArgs by navArgs()

    override val viewModel: ISetCardPin.ViewModel
        get() = ViewModelProviders.of(this).get(SetNewPinViewModel::class.java)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnAction -> {
                    //    val action = ChangeCardPinFragmentDirections.actionChangeCardPinFragmentToSetNewCardPinFragment(viewModel.state.pincode)
                    //   findNavController().navigate(action)
                    findNavController().navigate(R.id.action_setNewCardPinFragment_to_confirmNewCardPinFragment)
                }
            }
        })
    }
}