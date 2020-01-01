package co.yap.modules.location.interfaces

import android.graphics.Bitmap
import androidx.databinding.ObservableField
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent


interface ILocationSelection {

    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handleOnPressView(id: Int)
    }

    interface State : IBase.State {
        var headingTitle: ObservableField<String>
        var subHeadingTitle: ObservableField<String>
        var placeTitle: ObservableField<String>
        var placeSubTitle: ObservableField<String>
        var placePhoto: ObservableField<Bitmap>

    }
}