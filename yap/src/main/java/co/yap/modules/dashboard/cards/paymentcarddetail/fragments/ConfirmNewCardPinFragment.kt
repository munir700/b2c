package co.yap.modules.dashboard.cards.paymentcarddetail.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.yap.R
import co.yap.modules.dashboard.cards.paymentcarddetail.activities.ChangeCardPinActivity
import co.yap.modules.dashboard.cards.paymentcarddetail.viewmodels.ConfirmNewCardPinViewModel
import co.yap.modules.setcardpin.fragments.ConfirmCardPinFragment
import co.yap.modules.setcardpin.interfaces.ISetCardPin
import kotlinx.android.synthetic.main.activity_create_passcode.*

class ConfirmNewCardPinFragment : ConfirmCardPinFragment() {
    private val args: ConfirmNewCardPinFragmentArgs by navArgs()

    override val viewModel: ISetCardPin.ViewModel
        get() = ViewModelProviders.of(this).get(ConfirmNewCardPinViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadData()
    }

    override fun loadData() {
        viewModel.state.oldPin = args.oldPinCode
        viewModel.state.newPin = args.newPinCode
        viewModel.state.cardSerialNumber = (activity as ChangeCardPinActivity).cardSerialNumber

    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnAction -> {
                    findNavController().navigate(R.id.action_confirmNewCardPinFragment_to_changePinSuccessFragment)
                }
            }
        })
        viewModel.errorEvent.observe(this, Observer {
            dialer.startAnimationDigits()
        })
    }
}