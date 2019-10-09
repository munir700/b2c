package co.yap.modules.dashboard.more.profile.states

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import co.yap.R
import co.yap.modules.dashboard.more.profile.intefaces.IPersonalDetail
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.BaseState

class PersonalDetailState(val application: Application) : BaseState(), IPersonalDetail.State {

    @get:Bindable
    override var fullName: String = " "
        set(value) {
            field = value
            notifyPropertyChanged(BR.fullName)

        }

    @get:Bindable
    override var phoneNumber: String = " "
        set(value) {
            field = value
            notifyPropertyChanged(BR.phoneNumber)

        }


    @get:Bindable
    override var email: String = " "
        set(value) {
            field = value
            notifyPropertyChanged(BR.email)
        }


    @get:Bindable
    override var address: String = " "
        set(value) {
            field = value
            notifyPropertyChanged(BR.address)
        }


    @get:Bindable
    override var verificationText: String = Translator.getString(
        application,
        Strings.screen_personal_detail_display_text_verification_completed
    )
        set(value) {
            field = value
            notifyPropertyChanged(BR.verificationText)

        }


    @get:Bindable
    override var drawbleRight: Drawable? =
        application!!.resources.getDrawable(co.yap.yapcore.R.drawable.ic_tick_enabled)
        set(value) {
            field = value
            notifyPropertyChanged(BR.drawbleRight)
        }


    @get:Bindable
    override var errorVisibility: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.errorVisibility)
            setUpVerificationLayout()
        }


    fun setUpVerificationLayout() {
        if (!errorVisibility) {
            drawbleRight =
                application!!.resources.getDrawable(co.yap.yapcore.R.drawable.ic_tick_enabled)
            verificationText = Translator.getString(
                application,
                Strings.screen_personal_detail_display_text_verification_completed
            )

        } else {
            drawbleRight =
                application!!.resources.getDrawable(R.drawable.ic_doc_error)
            verificationText = Translator.getString(
                application,
                Strings.screen_personal_detail_display_text_verification_expired
            )
        }

        notifyPropertyChanged(BR.drawbleRight)
        notifyPropertyChanged(BR.verificationText)
    }
}