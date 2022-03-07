package co.yap.modules.onboarding.models

import com.yap.core.ICreateOtpVerifier
import com.yap.core.IUserVerifier
import com.yap.ghana.configs.verifyuser.login.GhanaUserVerifyFactory
import com.yap.ghana.configs.verifyuser.signup.GhanaSignupOtpVerifierFactory
import com.yap.yappakistan.configs.verifyuser.login.PkUserVerifierFactory
import com.yap.yappakistan.configs.verifyuser.signup.PkSignupOtpVerifierFactory

class UserVerifierProvider {

    fun provide(countryCode: String): IUserVerifier {
        return when (countryCode) {
            "+92", "0092" -> {
                val pkUserVerifierFactory = PkUserVerifierFactory()
                pkUserVerifierFactory.create()
            }
            "+233", "00233" -> {
                val ghanaUserVerifierFactory = GhanaUserVerifyFactory()
                ghanaUserVerifierFactory.create()
            }
            else -> throw IllegalStateException("Country code $countryCode is not supported.")
        }
    }

    fun provideOtpVerifier(countryCode: String): ICreateOtpVerifier {
        return when (countryCode) {
            "+92", "0092" -> {
                val pkUserVerifierFactory = PkSignupOtpVerifierFactory()
                pkUserVerifierFactory.create()
            }
            "+233", "00233" -> {
                val ghanaUserVerifierFactory = GhanaSignupOtpVerifierFactory()
                ghanaUserVerifierFactory.create()
            }
            else -> throw IllegalStateException("Country code $countryCode is not supported.")
        }
    }

}