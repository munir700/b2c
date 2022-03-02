package co.yap.modules.dashboard.cards.addpaymentcard.spare.virtual.cardcolour

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.databinding.FragmentAddVirtualCardBinding
import co.yap.networking.cards.responsedtos.VirtualCardDesigns
import co.yap.widgets.CircleView
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IAddVirtualCard {
    interface State : IBase.State {
        var designCode: MutableLiveData<String>?
        var cardDesigns: MutableLiveData<MutableList<VirtualCardDesigns>>?
        var childName: MutableLiveData<String>
        var enabelCoreButton: Boolean
    }

    interface ViewModel : IBase.ViewModel<State> {
        var adapter: ObservableField<AddVirtualCardAdapter>
        fun getCardThemesOption(): MutableList<VirtualCardDesigns>
        val clickEvent: SingleClickEvent
        fun handlePressOnButton(id: Int)
        fun observeCardNameLength(str: String): Boolean
        var tabViews: ObservableField<ArrayList<CircleView>>

    }

    interface View : IBase.View<ViewModel> {
        fun getBindings(): FragmentAddVirtualCardBinding
        fun addObservers()
        fun removeObservers()
    }
}
