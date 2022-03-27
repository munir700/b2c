package co.yap.widgets.luhn

import android.content.Context

/**
 * Created by zone2 on 7/6/17.
 */
interface LuhnCallback {
    fun cardDetailsRetrieved(
        luhnContext: Context?,
        creditCard: LuhnCard?,
        cardVerifier: LuhnCardVerifier?
    )

    fun otpRetrieved(luhnContext: Context?, cardVerifier: LuhnCardVerifier?, otp: String?)
    fun onFinished(isVerified: Boolean)
}