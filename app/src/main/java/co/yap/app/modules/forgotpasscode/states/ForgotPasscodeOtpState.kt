package co.yap.app.modules.forgotpasscode.states

import android.app.Application
import android.os.CountDownTimer
import androidx.databinding.Bindable
import co.yap.BR
import co.yap.R
import co.yap.app.modules.forgotpasscode.interfaces.IForgotPasscodeOtp
import co.yap.modules.onboarding.states.PhoneVerificationState
import co.yap.yapcore.BaseState

class ForgotPasscodeOtpState(application: Application) : BaseState(), IForgotPasscodeOtp.State {
    @get:Bindable
    override var verificationTitle: String="I am your title"
        set(value) {
            field=value
            notifyPropertyChanged(BR.verificationTitle)
        }
    @get:Bindable
    override var verificationDescription: String=""
        set(value) {
            field=value
            notifyPropertyChanged(BR.verificationDescription)
        }

    val mContext = application.applicationContext
  override var mobileNumber: Array<String?> = arrayOfNulls(1)

    @get:Bindable
    override var otp: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.otp)
            validate()
        }


    @get:Bindable
    override var valid: Boolean = false
        get() = validate()
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }

    @get:Bindable
    override var validResend: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.validResend)
        }

    @get:Bindable
    override var timer: String = "00:00"
        set(value) {
            field = value
            notifyPropertyChanged(BR.timer)
            validate()
        }


    @get:Bindable
    override var color: Int = mContext.resources.getColor(R.color.disabled)
        set(value) {
            field = value
            notifyPropertyChanged(BR.color)
        }

    private fun validate(): Boolean {
        var validateOtp: Boolean = false
        if (!otp.isNullOrEmpty() && otp.length == 4) {
            validateOtp = true
            valid = true
        }
        return validateOtp
    }

    override fun reverseTimer(Seconds: Int) {
        color = mContext.resources.getColor(R.color.disabled)
        object : CountDownTimer((Seconds * 1000 + 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var seconds = (millisUntilFinished / 1000).toInt()
                val minutes = seconds / 60
                seconds %= 60
                val timerMsg: String
                if (seconds == 10) {
                    timerMsg = "00:$seconds"
                } else {
                    timerMsg = "00:0$seconds"
                }
                timer = timerMsg
            }

            override fun onFinish() {
                validResend = true
                color = mContext.resources.getColor(R.color.colorPrimary)
                timer = "00:00"
            }
        }.start()
    }
}