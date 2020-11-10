package co.yap.widgets.scanqrcode

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.requestdtos.Contact
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

interface IScanQRCode {
    interface State : IBase.State {

    }

    interface ViewModel : IBase.ViewModel<State> {
        var contactInfo: MutableLiveData<Contact>
        val noContactFoundEvent: SingleLiveEvent<Boolean>
        fun uploadQRCode(uuid: String?)
    }

    interface View : IBase.View<ViewModel> {

    }
}