package co.yap.modules.dashboard.more.main.interfaces

import androidx.databinding.ObservableBoolean
import co.yap.networking.customers.responsedtos.documents.GetMoreDocumentsResponse
import co.yap.yapcore.IBase

interface IMore {
    interface State : IBase.State {
        var tootlBarVisibility: Int
        var tootlBarBadgeVisibility: Boolean
        var toolbarVisibility: ObservableBoolean
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnTickButton()
        var BadgeVisibility: Boolean
        var document: GetMoreDocumentsResponse.Data.CustomerDocument.DocumentInformation?
    }

    interface View : IBase.View<ViewModel>
}