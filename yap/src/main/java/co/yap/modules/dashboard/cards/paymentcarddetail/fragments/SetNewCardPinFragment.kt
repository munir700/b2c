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

open class SetNewCardPinFragment : SetCardPinFragment() {
    private val args: SetNewCardPinFragmentArgs by navArgs()

    var oldPinCode: String? = null
    override val viewModel: ISetCardPin.ViewModel
        get() = ViewModelProviders.of(this).get(SetNewPinViewModel::class.java)

    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnAction -> {
                    oldPinCode = args.oldPinCode
                    val action =
                        SetNewCardPinFragmentDirections.actionSetNewCardPinFragmentToConfirmNewCardPinFragment(
                            args.flowType,
                            oldPinCode.toString(),
                            viewModel.state.pincode

                        )

                    findNavController().navigate(action)
                }
            }
        })
    }
}