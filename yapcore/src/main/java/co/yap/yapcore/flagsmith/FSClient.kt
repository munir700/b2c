package co.yap.yapcore.flagsmith

import co.yap.app.YAPApplication
import com.flagsmith.FlagsmithClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class FSClient : FeatureFlagClient {
    private var client: FlagsmithClient? = null

    init {
        configure()
    }

    override fun configure() {
        client = getFeatureFlagClient()
    }

    override fun hasFeature(flag: String): Boolean {
        return runBlocking { getFlagValue(flag) }
    }

    private suspend fun getFlagValue(flag: String): Boolean = withContext(Dispatchers.IO) {
        client?.hasFeatureFlag(flag) ?: false
    }

    private fun getFeatureFlagClient(): FlagsmithClient {
        return FlagsmithClient.newBuilder()
            .setApiKey(YAPApplication.configManager?.flagSmithAPIKey)
            .build()
    }
}