package co.yap.modules.onboarding.states

import android.app.Application
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.CountDownTimer
import android.widget.TextView
import androidx.databinding.Bindable
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.interfaces.IPhoneVerificationSignIn
import co.yap.yapcore.BaseState

class PhoneVerificationSignInState(application: Application) : BaseState(), IPhoneVerificationSignIn.State {
    val mContext = application.applicationContext

    @get:Bindable
     override var color: Int = mContext.resources.getColor(R.color.warning)
        get() =field
        set(value) {
            field = value
            notifyPropertyChanged(BR.color)
        }

    @get:Bindable
    override var valid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }

    @get:Bindable
    override var validateBtn: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.validateBtn)
        }
    @get:Bindable
    override var timer: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.timer)
        }
    @get:Bindable
    override var otp: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.otp)
            validate()
        }

    @get:Bindable
    override var passcode: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.passcode)
        }


    @get:Bindable
    override var username: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.username)
        }

    override fun reverseTimer(Seconds: Int) {
        object : CountDownTimer((Seconds * 1000 + 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var seconds = (millisUntilFinished / 1000).toInt()
                val minutes = seconds / 60
                seconds %= 60
                val timerMsg: String
                timerMsg = "00:$seconds"
                timer = timerMsg
            }
            override fun onFinish() {
                valid = true
              //  color = R.color.colorPrimarySoft
                timer = ""
            }
        }.start()
    }

    private fun validate(): Boolean {
        var vlidateOtp: Boolean = false
        if (!otp.isNullOrEmpty() && otp.length == 4) {
            vlidateOtp = true
            validateBtn=true

        }
        return vlidateOtp

    }
}