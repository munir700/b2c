package co.yap.modules.onboarding.models

import android.content.Context
import co.yap.BuildConfig
import co.yap.app.YAPApplication
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.SharedPreferenceManager
import com.yap.core.enums.ProductFlavour
import com.yap.ghana.configs.GhanaBuildConfigurations
import com.yap.ghana.utils.enums.GhanaAppEvent
import com.yap.yappakistan.configs.PKBuildConfigurations
import com.yap.yappakistan.utils.enums.PkAppEvent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoadConfig @Inject constructor(@ApplicationContext val appContext: Context) {

    fun initYapRegion(countryDialCode: String) {
        when (countryDialCode) {
            "+92", "0092" -> initYapPakistan(context = appContext)
            "+233", "00233" -> initYapGhana(context = appContext)
            else -> throw IllegalStateException("Configuration has not been handled for $countryDialCode")
        }
    }

    private fun initYapPakistan(context: Context): PKBuildConfigurations {
        val pkConfigs = PKBuildConfigurations(context)
        pkConfigs.configure(
            flavour = "qa",
            buildType = "debug",
            versionName = YAPApplication.configManager?.versionName ?: "1.0.0",
            versionCode = YAPApplication.configManager?.versionCode ?: "1",
            applicationId = "co.yap.qa"
        ) { event ->
            handlePkAppEvent(event, context)
        }
        return pkConfigs
    }

    private fun initYapGhana(context: Context): GhanaBuildConfigurations {
        val ghConfigs = GhanaBuildConfigurations(context)
        ghConfigs.configure(
            flavour = "qa",
            buildType = BuildConfig.BUILD_TYPE,
            versionName = YAPApplication.configManager?.versionName ?: "1.0.0",
            versionCode = YAPApplication.configManager?.versionCode ?: "1",
            applicationId = "co.yap.qa"
        ) {
            handleGhanaAppEvent(it, context)
        }
        return ghConfigs
    }

    private fun handleGhanaAppEvent(event: GhanaAppEvent, context: Context) {
        when (event) {
            GhanaAppEvent.IS_LOGGED_IN -> {
                SharedPreferenceManager.getInstance(context)
                    .save(Constants.KEY_IS_USER_LOGGED_IN, true)
            }
            GhanaAppEvent.LOGOUT -> {
                SharedPreferenceManager.getInstance(context)
                    .save(Constants.KEY_IS_USER_LOGGED_IN, false)
            }
        }

    }

    private fun handlePkAppEvent(event: PkAppEvent, context: Context) {
        when (event) {
            PkAppEvent.IS_LOGGED_IN -> {

            }
            PkAppEvent.LOGOUT -> {
                SharedPreferenceManager.getInstance(context)
                    .save(Constants.KEY_IS_USER_LOGGED_IN, false)
            }
        }
    }

    private fun startDemoActivity(context: Context) {
//        val intent = newIntent<MainActivity>(context)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        intent.putExtra(NAVIGATION_GRAPH_ID, R.navigation.nav_graph)
////        intent.putExtra(
////            NAVIGATION_GRAPH_START_DESTINATION_ID,
////            R.id.loginFragment
////        )
//        context.startActivity(intent)

    }

    private fun getAdjustReferralTrackerId(flavour: String): String {
        return when (flavour) {
            ProductFlavour.DEV.flavour, ProductFlavour.QA.flavour -> "fj4r46p"
            else -> throw IllegalStateException("There is no app has been created on adjust dashboard against this flavour:=> $flavour")
        }
    }
}