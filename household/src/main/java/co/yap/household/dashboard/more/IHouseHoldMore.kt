package co.yap.household.dashboard.more

import androidx.databinding.ObservableField
import co.yap.modules.sidemenu.ProfilePictureAdapter
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHouseHoldMore {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>{
    }
    interface State : IBase.State
}