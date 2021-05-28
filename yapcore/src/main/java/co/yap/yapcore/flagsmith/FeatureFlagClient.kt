package co.yap.yapcore.flagsmith

interface FeatureFlagClient {
    fun hasFeature(flag: String, hasFeatureEnable: (Boolean) -> Unit)
    fun configure()
}