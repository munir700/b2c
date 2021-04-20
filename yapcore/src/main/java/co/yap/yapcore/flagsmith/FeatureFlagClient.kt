package co.yap.yapcore.flagsmith

interface FeatureFlagClient {
    fun hasFeature(flag: String): Boolean
    fun configure()
}