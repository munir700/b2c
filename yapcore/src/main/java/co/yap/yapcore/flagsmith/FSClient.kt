package co.yap.yapcore.flagsmith

import co.yap.app.YAPApplication
import co.yap.yapcore.helpers.SingleSingletonHolder
import co.yap.yapcore.managers.SessionManager
import com.flagsmith.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class FSClient private constructor() : FeatureFlagClient {
    private var client: FlagsmithClient? = null

    companion object : SingleSingletonHolder<FSClient>(::FSClient)

    init {
        configure()
    }

    override fun configure() {
        client = getFeatureFlagClient()
    }

    override fun hasFeature(flag: String, hasFeatureEnable: (Boolean) -> Unit) {
        val featureUser = FeatureUser()
        if(SessionManager.user?.currentCustomer?.customerId.isNullOrBlank()) {
            hasFeatureEnable(false)
            return
        }
        featureUser.identifier = SessionManager.user?.currentCustomer?.customerId ?: ""
        GlobalScope.launch(Dispatchers.IO) {
            client?.updateTrait(
                featureUser,
                Trait(
                    featureUser,
                    ToggleFeature.EMAIL_ADDRESS.flag,
                    SessionManager.user?.currentCustomer?.email ?: ""
                )
            )
        }
        GlobalScope.launch(Dispatchers.IO) {
            val isEnable = client?.hasFeatureFlag(flag, featureUser) ?: false
            hasFeatureEnable(isEnable)
        }
    }

    private fun getFeatureFlagClient(): FlagsmithClient {
        return FlagsmithClient.newBuilder()
            .setApiKey(YAPApplication.configManager?.flagSmithAPIKey)
            .build()
    }


}