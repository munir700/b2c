package co.yap.yapcore.flagsmith

class MockFeatureClient : FeatureFlagClient {
    override fun hasFeature(flag: String, hasFeatureEnable: (Boolean) -> Unit) {
        hasFeatureEnable(true)
    }

    override fun configure() {

    }
}