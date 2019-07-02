package co.yap.app.modules.login.states

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.opengl.Visibility
import androidx.databinding.Bindable
import co.yap.app.BR
import co.yap.app.modules.login.interfaces.ISystemPermission
import co.yap.yapcore.BaseState

class SystemPermissionState : BaseState(), ISystemPermission.State {
 /*   @get:Bindable
    override var icon: Bitmap?= null
        set(value) {
            field = value
            notifyPropertyChanged(BR.icon)

        }*/
 @get:Bindable
 override var icon: Bitmap?= null
     set(value) {
         field = value
         notifyPropertyChanged(BR.icon)

     }

    override var title: String = ""
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }

    @get:Bindable
    override var termsAndConditionsVisibility: Boolean = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.termsAndConditionsVisibility)
        }

    @get:Bindable
    override var buttonTitle: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.buttonTitle)
        }


    /*   override var icon : Drawable? = null
       set(value)  {
           field=value
           notifyPropertyChanged(BR.emailError)
       }*/

}
