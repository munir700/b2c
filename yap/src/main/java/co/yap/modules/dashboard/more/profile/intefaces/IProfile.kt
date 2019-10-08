package co.yap.modules.dashboard.more.profile.intefaces

import android.net.Uri
import co.yap.networking.customers.responsedtos.documents.GetMoreDocumentsResponse
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

        val showExpiredBadge: Boolean

        val data: GetMoreDocumentsResponse

        var PROFILE_PICTURE_UPLOADED: Int

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

        fun requestUploadProfilePicture()

        fun uploadProfconvertUriToFile(selectedImageUri: Uri)

    }

    interface View : IBase.View<ViewModel>
}