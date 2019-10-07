package co.yap.modules.dashboard.more.profile.intefaces

import co.yap.networking.customers.responsedtos.documents.Data
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IProfile {

    interface State : IBase.State {
        var fullName: String
        var profilePictureUrl: String
        var nameInitialsVisibility: Int
        var errorBadgeVisibility: Int

    }

    interface ViewModel : IBase.ViewModel<State> {

        val clickEvent: SingleClickEvent

        val data: Data

        fun handlePressOnBackButton()

        fun handlePressOnPersonalDetail(id: Int)

        fun handlePressOnPrivacy(id: Int)

        fun handlePressOnPasscode(id: Int)

        fun handlePressOnAppNotification(id: Int)

        fun handlePressOnTermsAndConditions(id: Int)

        fun handlePressOnInstagram(id: Int)

        fun handlePressOnTwitter(id: Int)

        fun handlePressOnFaceBook(id: Int)

        fun handlePressOnLogOut(id: Int)

        fun handlePressOnAddNewPhoto(id: Int)

        fun handlePressOnPhoto(id: Int)

        fun requestProfileDocumentsInformation()

    }

    interface View : IBase.View<ViewModel>
}