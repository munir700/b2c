package co.yap.widgets.bottomsheet

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase

interface ICoreBottomSheet {
    interface State : IBase.State {
        var searchText : MutableLiveData<String>
        var searchBarVisibility : ObservableBoolean
        var noItemFound: ObservableBoolean
        val headerSeparatorVisibility: ObservableBoolean
        val buttonVisibility: ObservableBoolean
    }

    interface ViewModel : IBase.ViewModel<State> {
        var selectedViewsList: ArrayList<String>
    }

    interface View : IBase.View<ViewModel> {
    }
}