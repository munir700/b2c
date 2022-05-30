package co.yap.modules.dashboard.more.home.interfaces

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import co.yap.databinding.FragmentMoreHomeBinding
import co.yap.modules.dashboard.more.home.models.MoreOption
import co.yap.networking.coreitems.CoreBottomSheetData
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IMoreHome {
    interface State : IBase.State {
        var image: ObservableField<String>
        var initials: ObservableField<String>
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        var badgeCount: ObservableField<String>
        var hasBadge: ObservableField<Boolean>
        val list : MutableList<CoreBottomSheetData>
        val notificationCountData: LiveData<Int?>
        fun handlePressOnView(id: Int)
        fun handlePressOnYAPforYou(id: Int)
        fun getMoreOptions(): MutableList<MoreOption>
        fun getTransactionsNotificationsCount()
        fun loadBottomSheetData(): MutableList<CoreBottomSheetData>
    }

    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()

    }
}