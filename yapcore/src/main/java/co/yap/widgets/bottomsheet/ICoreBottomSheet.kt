package co.yap.widgets.bottomsheet

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ICoreBottomSheet {
    interface State : IBase.State {
        var searchText : MutableLiveData<String>
        var searchBarVisibility : ObservableBoolean
        var noItemFound : ObservableBoolean
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
    }

    interface View : IBase.View<ViewModel> {
    }
}