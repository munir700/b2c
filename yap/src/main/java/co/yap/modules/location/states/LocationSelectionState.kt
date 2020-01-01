package co.yap.modules.location.states

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.databinding.ObservableField
import co.yap.R
import co.yap.modules.location.interfaces.ILocationSelection
import co.yap.yapcore.BaseState

class LocationSelectionState : BaseState(), ILocationSelection.State {
    val mContext: Context = application.applicationContext

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

}