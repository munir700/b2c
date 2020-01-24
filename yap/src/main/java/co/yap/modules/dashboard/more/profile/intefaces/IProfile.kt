package co.yap.modules.dashboard.more.profile.intefaces

import android.content.Context
import android.net.Uri
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.customers.responsedtos.documents.GetMoreDocumentsResponse
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import java.io.File

interface IProfile {

    interface State : IBase.State {
        var fullName: String
        var profilePictureUrl: String
        var nameInitialsVisibility: Int
        var errorBadgeVisibility: Int
        var imageUri: Uri

    }

    interface ViewModel : IBase.ViewModel<State> {
        val authRepository: AuthRepository
        var clickEvent: SingleClickEvent

        val showExpiredBadge: Boolean

        val data: GetMoreDocumentsResponse

        var PROFILE_PICTURE_UPLOADED: Int
        var EVENT_LOGOUT_SUCCESS: Int

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

        fun requestUploadProfilePicture(file: File)

        fun uploadProfconvertUriToFile(selectedImageUri: Uri)

        fun getRealPathFromUri(context: Context, contentUri: Uri): String
        fun logout()

    }

    interface View : IBase.View<ViewModel>
}