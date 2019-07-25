package co.yap.modules.kyc.interfaces

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase

interface IKYCHome {
    interface State : IBase.State

    interface ViewModel : IBase.ViewModel<State> {
        val clickListener: MutableLiveData<Int>
        fun handlePressOnScanCard(id: Int)
        fun handlePressOnNextButton(id: Int)
        fun handlePressOnSkipButton(id: Int)
    }

    interface View : IBase.View<ViewModel>
}