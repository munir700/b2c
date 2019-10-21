package co.yap.modules.dashboard.cards.paymentcarddetail.fragments

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.viewmodels.ChangeCardPinViewModel
import co.yap.modules.setcardpin.fragments.SetCardPinFragment
import co.yap.modules.setcardpin.interfaces.ISetCardPin

open class ChangeCardPinFragment : SetCardPinFragment() {
    override val viewModel: ISetCardPin.ViewModel
        get() = ViewModelProviders.of(this).get(ChangeCardPinViewModel::class.java)

    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnAction -> {
                    val action = ChangeCardPinFragmentDirections.actionChangeCardPinFragmentToSetNewCardPinFragment(viewModel.state.pincode)
                    findNavController().navigate(action)
                }
            }
        })
    }
}