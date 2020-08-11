package co.yap.modules.dashboard.more.profile.intefaces

import android.net.Uri
import androidx.databinding.ObservableField
import co.yap.networking.authentication.AuthRepository
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import java.io.File

interface IProfile {
    interface State : IBase.State {
        var fullName: String
        var profilePictureUrl: String
        var nameInitialsVisibility: Int
        var imageUri: Uri
        var isShowErrorIcon: ObservableField<Boolean>

    }

    interface ViewModel : IBase.ViewModel<State> {
        val authRepository: AuthRepository
        var clickEvent: SingleClickEvent
        var PROFILE_PICTURE_UPLOADED: Int
        fun handlePressOnViewClick(id: Int)
        fun requestProfileDocumentsInformation()
        fun requestUploadProfilePicture(actualFile: File)
    }

    interface View : IBase.View<ViewModel>
}