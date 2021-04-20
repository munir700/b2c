package co.yap.yapcore.flagsmith

import com.flagsmith.FlagsmithClient


class FSClient : FeatureFlagClient {
    private var client: FlagsmithClient? = null

    init {
        configure()
    }

    override fun configure() {
        client = getFeatureFlagClient()
    }

    override fun hasFeature(flag: String): Boolean {
        return client?.hasFeatureFlag(flag) ?: true
    }

    private fun getFeatureFlagClient(): FlagsmithClient {
        val flagSmithClient = FlagsmithClient.newBuilder()
            .setApiKey("9RksySm2nD9J852bYYFwKQ")
            .enableLogging()
            .build()

        flagSmithClient.hasFeatureFlag("bill_payments")

        return flagSmithClient
    }

}