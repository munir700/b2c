package co.yap.modules.dashboard.addionalinfo.interfaces

import co.yap.modules.dashboard.addionalinfo.adapters.AdditionalInfoEmploymentAdapter
import co.yap.modules.dashboard.addionalinfo.adapters.UploadAdditionalDocumentAdapter
import co.yap.yapcore.IBase

interface IAdditionalInfoEmployment {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State>{
        val additionalInfoEmploymentAdapter: AdditionalInfoEmploymentAdapter
        fun moveToNext()
    }

    interface State : IBase.State
}