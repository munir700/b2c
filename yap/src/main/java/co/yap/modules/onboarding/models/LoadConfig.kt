package co.yap.modules.onboarding.models

import android.content.Context
import co.yap.BuildConfig
import co.yap.app.YAPApplication
import co.yap.modules.adjustevents.AnalyticsEventsHandler
import co.yap.yapcore.adjust.ReferralInfo
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.Constants.REFERRAL_COUNTRY_ISO_CODE
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.managers.SessionManager
import com.yap.core.analytics.AnalyticsEvent
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
        val analyticsEvent: AnalyticsEvent = AnalyticsEventsHandler()
        when (countryDialCode) {
            "+92", "0092" -> initYapPakistan(
                context = appContext,
                analyticsEvent = analyticsEvent
            )
            "+233", "00233" -> initYapGhana(
                context = appContext,
                analyticsEvent = analyticsEvent
            )
            else -> throw IllegalStateException("Configuration has not been handled for $countryDialCode")
        }
    }

    private fun initYapPakistan(
        context: Context,
        analyticsEvent: AnalyticsEvent
    ): PKBuildConfigurations {
        val pkConfigs = PKBuildConfigurations(context)
        pkConfigs.configure(
            flavour = ProductFlavour.PROD.flavour,
            buildType = BuildConfig.BUILD_TYPE,
            versionName = YAPApplication.configManager?.versionName ?: "1.0.0",
            versionCode = YAPApplication.configManager?.versionCode ?: "1",
            applicationId = YAPApplication.configManager?.applicationId ?: "",
            analyticsEvent = analyticsEvent
        ) { event ->
            handlePkAppEvent(event, context)
        }
        pkConfigs.setAdjustAppId(getAdjustReferralTrackerId("PK", ProductFlavour.PROD.flavour))
        getReferralInfo("PK")?.let {
            pkConfigs.setReferralInfo(it.id, it.date)
            SharedPreferenceManager.getInstance(context).setReferralInfo(null)
        }

        return pkConfigs
    }

    private fun initYapGhana(
        context: Context,
        analyticsEvent: AnalyticsEvent
    ): GhanaBuildConfigurations {
        val ghConfigs = GhanaBuildConfigurations(context)
        ghConfigs.configure(
            flavour = ProductFlavour.PREPROD.flavour,
            buildType = BuildConfig.BUILD_TYPE,
            versionName = YAPApplication.configManager?.versionName ?: "1.0.0",
            versionCode = YAPApplication.configManager?.versionCode ?: "1",
            applicationId = YAPApplication.configManager?.applicationId ?: "",
            analyticsEvent = analyticsEvent
        ) {
            handleGhanaAppEvent(it, context)
        }
        ghConfigs.setAdjustAppId(
            getAdjustReferralTrackerId(
                "GH",
                ProductFlavour.PROD.flavour
            )
        )
        getReferralInfo("GH")?.let {
            ghConfigs.setReferralInfo(it.id, it.date)
            SharedPreferenceManager.getInstance(context).setReferralInfo(null)
        }
        return ghConfigs
    }

    private fun handleGhanaAppEvent(event: GhanaAppEvent, context: Context) {
        when (event) {
            GhanaAppEvent.IS_LOGGED_IN -> {
                SessionManager.tempLoginState.postValue(true)
                SharedPreferenceManager.getInstance(context)
                    .save(Constants.KEY_IS_USER_LOGGED_IN, true)
            }
            GhanaAppEvent.LOGOUT -> {
                SessionManager.getAppCountries(context) { _, _ ->
                    SessionManager.tempLoginState.postValue(false)
                }
                SharedPreferenceManager.getInstance(context)
                    .save(Constants.KEY_IS_USER_LOGGED_IN, false)
            }
        }

    }

    private fun handlePkAppEvent(event: PkAppEvent, context: Context) {
        when (event) {
            PkAppEvent.IS_LOGGED_IN -> {
                SessionManager.tempLoginState.postValue(true)
                SharedPreferenceManager.getInstance(context)
                    .save(Constants.KEY_IS_USER_LOGGED_IN, true)
            }
            PkAppEvent.LOGOUT -> {
                SessionManager.getAppCountries(context) { _, _ ->
                    SessionManager.tempLoginState.postValue(false)
                }

                SharedPreferenceManager.getInstance(context)
                    .save(Constants.KEY_IS_USER_LOGGED_IN, false)
            }
        }
    }

    private fun getAdjustReferralTrackerId(countryCode: String, flavour: String): String {
        return when (flavour) {
            ProductFlavour.PROD.flavour -> if (countryCode == "PK") "j2l7bsr" else "n44w5ee"
            ProductFlavour.PREPROD.flavour -> "oo71763"
            ProductFlavour.DEV.flavour, ProductFlavour.QA.flavour, ProductFlavour.STG.flavour -> "q3o2z0e"
            else -> throw IllegalStateException("There is no app has been created on adjust dashboard against this flavour:=> $flavour")
        }
    }

    private fun getReferralInfo(countryCode: String): ReferralInfo? {
        val sharedPref = SharedPreferenceManager.getInstance(appContext)
        return when (sharedPref.getValueString(REFERRAL_COUNTRY_ISO_CODE)) {
            countryCode -> {
                sharedPref.getReferralInfo()
            }
            else -> null
        }
    }
}