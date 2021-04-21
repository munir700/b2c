package co.yap.yapcore.flagsmith

import co.yap.yapcore.BuildConfig

private val client: FSClient by lazy {
    FSClient()
}

val getFeatureFlagClient: FeatureFlagClient
    get() {
        return if (BuildConfig.DEBUG) {
            client //MockFeatureClient()
        } else {
            client
        }
    }