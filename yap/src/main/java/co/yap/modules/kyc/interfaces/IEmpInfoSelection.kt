package co.yap.modules.kyc.interfaces

import androidx.databinding.ObservableBoolean
import co.yap.modules.kyc.adapters.EmpInfoSelectionAdapter
import co.yap.modules.kyc.models.EmpInfoStatusModel
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IEmpInfoSelection {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handleOnPressNext(id: Int)
        var empInfoSelectionAdapter: EmpInfoSelectionAdapter
        var empInfoStatusList: MutableList<EmpInfoStatusModel>
        var lastItemCheckedPosition: Int
    }

    interface State : IBase.State {
        val enableNextButton: ObservableBoolean
    }
}
