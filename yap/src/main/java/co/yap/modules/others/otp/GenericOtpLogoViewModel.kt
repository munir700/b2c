package co.yap.modules.others.otp

import android.app.Application
import android.content.Context
import co.yap.translation.Strings
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.helpers.Utils

class GenericOtpLogoViewModel(application: Application) :
    GenericOtpViewModel(application = application) {
    private lateinit var descriptionString: String


    override fun onCreate() {
        super.onCreate()
        when (action) {
            SendMoneyBeneficiaryType.DOMESTIC_TRANSFER.type, SendMoneyBeneficiaryType.CASHPAYOUT.type, SendMoneyBeneficiaryType.UAEFTS.type, SendMoneyBeneficiaryType.SWIFT.type, SendMoneyBeneficiaryType.RMT.type -> {
                descriptionString =
                    getString(Strings.screen_cash_pickup_funds_display_otp_text_description).format(
                        state.currencyType,
                        Utils.getFormattedCurrency(state.amount),
                        state.fullName
                    )
                state.currencyType?.let {
                    state.amount?.let {
                        state.verificationDescriptionForLogo = Utils.getSppnableStringForAmount(
                            context,
                            descriptionString,
                            state.currencyType!!,
                            Utils.getFormattedCurrencyWithoutComma(state.amount!!)
                        )
                    }
                }
                state.fullName?.let {
                    state.verificationTitle = it
                }
                state.verificationDescription =
                    Strings.screen_cash_pickup_funds_display_otp_text_description
            }
        }
        state.validResend = false
    }

    override fun handlePressOnResendOTP(context: Context) {
        createOtp(context)
    }
}