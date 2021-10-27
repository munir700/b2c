package co.yap.modules.dashboard.cards.addpaymentcard.spare.virtual.cardname

import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.MutableLiveData
import co.yap.databinding.FragmentAddVirtualCardNameBinding
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IAddVirtualCardName {
    interface State : IBase.State {
        var cardName: MutableLiveData<String>
        var enabelCoreButton: Boolean
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnButton(id: Int)
        fun observeCardNameLength(str: String): Boolean
        fun setCardImage(imgCard: AppCompatImageView)
    }

    interface View : IBase.View<ViewModel> {
        fun getBindings(): FragmentAddVirtualCardNameBinding
        fun addObservers()
        fun removeObservers()
    }
}
