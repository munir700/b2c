package co.yap.modules.onboarding.models

import android.os.Parcelable
import co.yap.BR
import co.yap.networking.models.ApiResponse
import co.yap.yapcore.interfaces.IBindable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WelcomeContent(val title: String, val subTitle: String, val imageResource: Int) :
    IBindable,Parcelable,ApiResponse() {
    override var bindingVariable: Int = BR.content
}