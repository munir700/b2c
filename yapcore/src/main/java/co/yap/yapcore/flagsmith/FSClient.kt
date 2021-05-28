package co.yap.yapcore.flagsmith

import co.yap.app.YAPApplication
import co.yap.yapcore.helpers.SingleSingletonHolder
import com.flagsmith.FlagsmithClient
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
        GlobalScope.launch(Dispatchers.IO) {
            val isEnable = client?.hasFeatureFlag(flag) ?: false
            hasFeatureEnable(isEnable)
        }
    }

    private fun getFeatureFlagClient(): FlagsmithClient {
        return FlagsmithClient.newBuilder()
            .setApiKey(YAPApplication.configManager?.flagSmithAPIKey)
            .build()
    }


}