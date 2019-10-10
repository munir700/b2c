package co.yap.modules.dashboard.more.profile.intefaces

import android.graphics.drawable.Drawable
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import com.digitify.identityscanner.modules.docscanner.models.IdentityScannerResult

interface IPersonalDetail {

    interface State : IBase.State {
        var fullName: String
        var phoneNumber: String
        var email: String
        var address: String
        var drawbleRight: Drawable?
        var errorVisibility: Boolean
        var verificationText: String

    }

    interface ViewModel : IBase.ViewModel<State> {

        val clickEvent: SingleClickEvent

        fun handlePressOnBackButton()

        fun handlePressOnEditPhone(id: Int)

        fun handlePressOnEditEmail(id: Int)

        fun handlePressOnEditAddress(id: Int)

        fun handlePressOnDocumentCard(id: Int)

        fun handlePressOnScanCard(id: Int)

        fun toggleToolBar(hide: Boolean)

    }

    interface View : IBase.View<ViewModel>
}