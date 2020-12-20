package co.yap.modules.dashboard.addionalinfo.interfaces

import androidx.databinding.ObservableField
import co.yap.networking.customers.requestdtos.UploadAdditionalInfo
import co.yap.yapcore.IBase

interface IAdditionalInfoQuestion {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        fun moveToNext()
        fun uploadAnswer(uploadAdditionalInfo: UploadAdditionalInfo, success: () -> Unit)
    }

    interface State : IBase.State {
        val answer: ObservableField<String>
    }
}