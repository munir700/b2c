package co.yap.modules.onboarding.states

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.databinding.Bindable
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.interfaces.IInformationError
import co.yap.yapcore.BaseState

class InformationErrorState(application: Application) : BaseState(), IInformationError.State {
    val mContext = application.applicationContext
    @get:Bindable
    override var errorTitle: String="Looks like you're from the United States"
        set(value) {
            field=value
            notifyPropertyChanged(BR.errorTitle)
        }
    @get:Bindable
    override var errorImage: Drawable= mContext.resources.getDrawable(R.drawable.ic_country)
        set(value) {
            field=value
            notifyPropertyChanged(BR.errorImage)
        }
    @get:Bindable
    override var errorGuide: String="Sorry, we're unable to activate your YAP account at this time. We're working on it and we will let you know once resolved."
        set(value) {
            field=value
            notifyPropertyChanged(BR.errorGuide)
        }
    @get:Bindable
    override var buttonTitle: String="Go to dashboard"
        set(value) {
            field=value
            notifyPropertyChanged(BR.buttonTitle)
        }
}