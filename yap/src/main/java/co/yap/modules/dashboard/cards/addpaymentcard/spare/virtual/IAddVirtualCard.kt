package co.yap.modules.dashboard.cards.addpaymentcard.spare.virtual

import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.cards.addpaymentcard.models.VirtualCardModel
import co.yap.yapcore.IBase

interface IAddVirtualCard {
    interface State : IBase.State {
        var designCode: MutableLiveData<String>?
        var cardDesigns: MutableLiveData<MutableList<VirtualCardModel>>?
        var cardName: MutableLiveData<String>
        var childName: MutableLiveData<String>
    }

    interface ViewModel : IBase.ViewModel<State> {
        var adapter: AddVirtualCardAdapter
    }

    interface View : IBase.View<ViewModel>
}
