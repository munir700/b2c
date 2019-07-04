package co.yap.modules.onboarding.viewmodels

import android.app.Application
import android.graphics.Color
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.annotation.RequiresApi
import co.yap.R
import co.yap.modules.onboarding.interfaces.ICreatePasscode
import co.yap.modules.onboarding.states.CreatePasscodeState
import co.yap.yapcore.BaseViewModel

class CreatePasscodeViewModel(application: Application):BaseViewModel<ICreatePasscode.State>(application),ICreatePasscode.ViewModel {
    override val state: ICreatePasscode.State
        get() = CreatePasscodeState()

    /*@RequiresApi(Build.VERSION_CODES.M)
    fun setSpannableString(text:String, start: Int, end: Int) :SpannableString{

        val text=SpannableString(text)

        text.setSpan(ForegroundColorSpan(context.resources.getColor(R.color.colorPrimaryDark,null)),start,end,Spannable.SPAN_INCLUSIVE_INCLUSIVE)

        return text

    }*/
}