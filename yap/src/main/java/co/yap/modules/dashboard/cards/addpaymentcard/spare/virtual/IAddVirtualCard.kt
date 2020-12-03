package co.yap.modules.dashboard.cards.addpaymentcard.spare.virtual

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.databinding.FragmentAddVirtualCardBinding
import co.yap.modules.dashboard.cards.addpaymentcard.models.VirtualCardModel
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IAddVirtualCard {
    interface State : IBase.State {
        var designCode: MutableLiveData<String>?
        var cardDesigns: MutableLiveData<MutableList<VirtualCardModel>>?
        var cardName: ObservableField<String>
        var childName: MutableLiveData<String>
        var enabelCoreButton : Boolean
    }

    interface ViewModel : IBase.ViewModel<State> {
        var adapter: ObservableField<AddVirtualCardAdapter>
        fun getCardThemesOption(): MutableList<VirtualCardModel>
        val clickEvent: SingleClickEvent
        fun handlePressOnButton(id: Int)
        fun observeCardNameLength(str : String) : Boolean
    }

    interface View : IBase.View<ViewModel> {
        fun getBindings(): FragmentAddVirtualCardBinding
        fun addObservers()
        fun removeObservers()
    }
}
