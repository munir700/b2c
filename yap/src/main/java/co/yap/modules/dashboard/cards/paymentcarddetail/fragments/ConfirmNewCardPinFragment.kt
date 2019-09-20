package co.yap.modules.dashboard.cards.paymentcarddetail.fragments

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.modules.dashboard.cards.paymentcarddetail.viewmodels.ConfirmNewCardPinViewModel
import co.yap.modules.setcardpin.fragments.ConfirmCardPinFragment
import co.yap.modules.setcardpin.interfaces.ISetCardPin

class ConfirmNewCardPinFragment : ConfirmCardPinFragment() {
    override val viewModel: ISetCardPin.ViewModel
        get() = ViewModelProviders.of(this).get(ConfirmNewCardPinViewModel::class.java)

    override fun loadData() {
        viewModel.pincode=""
    }
}