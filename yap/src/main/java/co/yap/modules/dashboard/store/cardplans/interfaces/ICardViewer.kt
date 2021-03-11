package co.yap.modules.dashboard.store.cardplans.interfaces

import co.yap.databinding.FragmentViewerCardPlansBinding
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ICardViewer {
    interface View : IBase.View<ViewModel> {
        fun initArguments()
        fun getBindings(): FragmentViewerCardPlansBinding
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State>{
        fun getFragmentToDisplay(id :  String?):Int
        var clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
    }
    interface State : IBase.State
}