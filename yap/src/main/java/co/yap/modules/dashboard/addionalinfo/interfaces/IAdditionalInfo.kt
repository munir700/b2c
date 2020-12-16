package co.yap.modules.dashboard.addionalinfo.interfaces

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase

interface IAdditionalInfo {
    interface View : IBase.View<ViewModel>{
        fun setObserver()
    }

    interface ViewModel : IBase.ViewModel<State>{
        val stepCount: MutableLiveData<Int>
    }

    interface State : IBase.State {
        val title: ObservableField<String>
        val subTitle: ObservableField<String>
        val steps: ObservableField<Int>
    }
}