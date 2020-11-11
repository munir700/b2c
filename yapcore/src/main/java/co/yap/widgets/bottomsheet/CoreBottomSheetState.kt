package co.yap.widgets.bottomsheet

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState

class CoreBottomSheetState():BaseState(), ICoreBottomSheet.State {
    override var searchText: MutableLiveData<String> = MutableLiveData()
    override var searchBarVisibility: MutableLiveData<Boolean> = MutableLiveData(true)
}