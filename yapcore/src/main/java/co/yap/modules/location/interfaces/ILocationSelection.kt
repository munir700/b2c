package co.yap.modules.location.interfaces

import android.graphics.Bitmap
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.cards.responsedtos.Address
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent


interface ILocationSelection {

    interface View : IBase.View<ViewModel>{
        fun setObservers()
    }
    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        var isMapExpanded: MutableLiveData<Boolean>
        var address: Address?
        fun onLocationSelected()
        fun handleOnPressView(id: Int)
    }

    interface State : IBase.State {
        var toolbarVisibility: Boolean
        var isShowLocationCard: ObservableField<Boolean>
        var headingTitle: ObservableField<String>
        var subHeadingTitle: ObservableField<String>
        var placeTitle: ObservableField<String>
        var placeSubTitle: ObservableField<String>
        var placePhoto: ObservableField<Bitmap>
        var addressTitle: String
        var addressSubtitle: ObservableField<String>
        var isTermsChecked: ObservableField<Boolean>
        var valid: ObservableField<Boolean>
        var showTermsCondition: ObservableField<Boolean>
        var isLocationInAllowedCountry: ObservableField<Boolean>

    }
}