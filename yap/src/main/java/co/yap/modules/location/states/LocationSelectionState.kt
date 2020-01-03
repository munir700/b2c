package co.yap.modules.location.states

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.databinding.library.baseAdapters.BR
import co.yap.R
import co.yap.modules.location.interfaces.ILocationSelection
import co.yap.yapcore.BaseState

class LocationSelectionState(application: Application) : BaseState(), ILocationSelection.State {
    private val mContext: Context = application.applicationContext

    override var headingTitle: ObservableField<String> = ObservableField("")
    override var subHeadingTitle: ObservableField<String> = ObservableField("")
    override var placeTitle: ObservableField<String> = ObservableField("")
    override var placeSubTitle: ObservableField<String> = ObservableField("")
    override var placePhoto: ObservableField<Bitmap> = ObservableField(
        BitmapFactory.decodeResource(
            mContext.resources,
            R.drawable.location_place_holder
        )
    )
    override var isShowLocationCard: ObservableField<Boolean> = ObservableField(false)
    override var isTermsChecked: ObservableField<Boolean> = ObservableField(false)
    override var showTermsCondition: ObservableField<Boolean> = ObservableField(false)

    @get:Bindable
    override var addressTitle: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.addressTitle)
            validate()
        }

    @get:Bindable
    override var addressSubtitle: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.addressSubtitle)
        }

    @get:Bindable
    override var valid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }

    fun validate() {
        showTermsCondition.set(addressTitle.isNotEmpty())
        valid = addressTitle.isNotEmpty() && isTermsChecked.get() == true
    }
}