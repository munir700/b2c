package co.yap.modules.dashboard.more.profile.intefaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IPersonalDetail {

    interface State : IBase.State {
    }

    interface ViewModel : IBase.ViewModel<State> {

        val clickEvent: SingleClickEvent

        fun handlePressOnBackButton()

        fun handlePressOnEditPhone(id: Int)

        fun handlePressOnEditEmail(id: Int)

        fun handlePressOnEditAddress(id: Int)

        fun handlePressOnDocumentCard(id: Int)

    }

    interface View : IBase.View<ViewModel>
}