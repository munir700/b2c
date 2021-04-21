package co.yap.yapcore.flagsmith

class MockFeatureClient : FeatureFlagClient {
    override fun hasFeature(flag: String): Boolean {
        return true
    }

    override fun configure() {

    }
}