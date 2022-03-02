package co.yap.app

import com.yap.core.IUserVerifier
import com.yap.yappakistan.configs.verifyuser.PkUserVerifierFactory

class UserVerifierProvider {

    fun provide(countryCode: String): IUserVerifier {
        return when (countryCode) {
            "+92", "0092" -> {
                val pkUserVerifierFactory = PkUserVerifierFactory()
                pkUserVerifierFactory.create()
            }
//            "+233", "00233" -> {
//                val ghanaUserVerifierFactory = GhanaUserVerifyFactory()
//                ghanaUserVerifierFactory.create()
//            }
            else -> throw IllegalStateException("Country code $countryCode is not supported.")
        }
    }

}