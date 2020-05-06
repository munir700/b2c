package co.yap.app.modules.login.interfaces

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IOtpBlockedInfo {

    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        val onHelpNoSuccess: MutableLiveData<Boolean>
        fun handlePressOnView(id: Int)
        fun getHelpPhoneNo()
    }

    interface State : IBase.State {
        val userFirstName: ObservableField<String>
        val helpPhoneNo: ObservableField<String>
    }
}