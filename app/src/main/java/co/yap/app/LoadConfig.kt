package co.yap.app

import android.content.Context
import android.content.Intent
import co.yap.app.main.MainActivity
import com.yap.core.enums.ProductFlavour
import com.yap.core.extensions.newIntent
import com.yap.ghana.configs.GhanaBuildConfigurations
import com.yap.yappakistan.configs.PKBuildConfigurations
import com.yap.yappakistan.utils.enums.PkAppEvent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoadConfig @Inject constructor() {
    fun initConfigs(context: Context, config: Any): Any {

        return when (config) {
            is PKBuildConfigurations -> {
                initializePkConfigs(
                    context, config, flavour = "qa",
                    buildType = "debug",
                    versionName = "1.0.0",
                    versionCode = "1",
                    applicationId = "co.yap.qa"
                )
            }

            is GhanaBuildConfigurations -> {
                initializeGhanaConfigs(
                    context, config, flavour = "qa",
                    buildType = "debug",
                    versionName = "1.0.0",
                    versionCode = "1",
                    applicationId = "co.yap.qa"
                )
            }

            else -> {
                throw IllegalStateException("Configuration has not been handled for $config")
            }
        }
    }

    //    public static final String APPLICATION_ID = "co.yap.stg";
//    public static final String BUILD_TYPE = "debug";
//    public static final String FLAVOR = "stg";
    private fun initializePkConfigs(
        context: Context,
        pkBuildConfigurations: PKBuildConfigurations,
        flavour: String,
        buildType: String,
        versionName: String,
        versionCode: String,
        applicationId: String
    ) {
        pkBuildConfigurations.configure(
            flavour = flavour,
            buildType = buildType,
            versionName = versionName,
            versionCode = versionCode,
            applicationId = applicationId
        ) { event ->
            runAppEvent(event, context)
        }
        pkBuildConfigurations.setAdjustAppId(appId = getAdjustReferralTrackerId(flavour))

    }

    private fun initializeGhanaConfigs(
        context: Context,
        ghanaBuildConfigurations: GhanaBuildConfigurations,
        flavour: String,
        buildType: String,
        versionName: String,
        versionCode: String,
        applicationId: String
    ) {
        ghanaBuildConfigurations.configure(
            flavour = flavour,
            buildType = buildType,
            versionName = versionName,
            versionCode = versionCode,
            applicationId = applicationId
        )
        ghanaBuildConfigurations.setAdjustAppId(appId = getAdjustReferralTrackerId(flavour))

    }

    private fun runAppEvent(event: PkAppEvent, context: Context) {
        when (event) {
            PkAppEvent.LOGOUT -> {
                startDemoActivity(context)
            }
        }
    }

    private fun startDemoActivity(context: Context) {
        val intent = newIntent<MainActivity>(context)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        intent.putExtra(NAVIGATION_GRAPH_ID, R.navigation.nav_graph)
//        intent.putExtra(
//            NAVIGATION_GRAPH_START_DESTINATION_ID,
//            R.id.loginFragment
//        )
        context.startActivity(intent)

    }

    private fun getAdjustReferralTrackerId(flavour: String): String {
        return when (flavour) {
            ProductFlavour.DEV.flavour, ProductFlavour.QA.flavour -> "fj4r46p"
            else -> throw IllegalStateException("There is no app has been created on adjust dashboard against this flavour:=> $flavour")
        }
    }
}