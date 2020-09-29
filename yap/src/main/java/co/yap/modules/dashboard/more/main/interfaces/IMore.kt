package co.yap.modules.dashboard.more.main.interfaces

import co.yap.networking.customers.responsedtos.documents.GetMoreDocumentsResponse
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

interface IMore {
    interface State : IBase.State {
        var tootlBarTitle: String
        var tootlBarVisibility: Int
        var tootlBarBadgeVisibility: Boolean
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnBackButton()
        fun handlePressOnTickButton()
        fun handlePressOnBadge()
        fun requestProfileDocumentsInformation(success: () -> Unit)
        val backButtonPressEvent: SingleLiveEvent<Boolean>
        var BadgeVisibility: Boolean
        val badgeButtonPressEvent: SingleLiveEvent<Boolean>
        var document: GetMoreDocumentsResponse.Data.CustomerDocument.DocumentInformation?
    }

    interface View : IBase.View<ViewModel>
}