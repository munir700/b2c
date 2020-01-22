package co.yap.modules.forgotpasscode.states

import android.app.Application
import android.os.CountDownTimer
import android.text.SpannableStringBuilder
import androidx.databinding.Bindable
import co.yap.yapcore.BR
import co.yap.modules.forgotpasscode.interfaces.IForgotPasscodeOtp
import co.yap.yapcore.BaseState
import co.yap.yapcore.R
import co.yap.yapcore.helpers.ThemeColorUtils

class ForgotPasscodeOtpState(application: Application) : BaseState(), IForgotPasscodeOtp.State {
    @get:Bindable
    override var verificationTitle: String = "I am your title"
        set(value) {
            field = value
            notifyPropertyChanged(BR.verificationTitle)
        }
    @get:Bindable
    override var verificationDescription: String = ""
        set(value) {
            field = value
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
    @get:Bindable
    override var verificationDescriptionForLogo: SpannableStringBuilder? =
        SpannableStringBuilder("")
        set(value) {
            field = value
            notifyPropertyChanged(BR.verificationDescriptionForLogo)
        }
    @get:Bindable
    override var imageUrl: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.imageUrl)
        }
    @get:Bindable
    override var fullName: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.fullName)
        }
    @get:Bindable
    override var currencyType: String? = "AED"
        set(value) {
            field = value
            notifyPropertyChanged(BR.currencyType)
        }
    @get:Bindable
    override var amount: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.amount)
        }
    @get:Bindable
    override var position: Int? = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.position)
        }
    @get:Bindable
    override var flagLayoutVisibility: Boolean? = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.flagLayoutVisibility)
        }
    @get:Bindable
    override var beneficiaryCountry: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.beneficiaryCountry)
        }

    private fun validate(): Boolean {
        var validateOtp: Boolean = false
        if (!otp.isNullOrEmpty() && otp.length == 6) {
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
                color = ThemeColorUtils.colorPrimaryAttribute(mContext)
                timer = "00:00"
            }
        }.start()
    }
}